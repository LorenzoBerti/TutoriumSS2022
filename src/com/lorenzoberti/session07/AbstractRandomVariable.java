/**
 * 
 */
package com.lorenzoberti.session07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.finmath.randomnumbers.RandomNumberGenerator1D;

/**
 * @author Lorenzo Berti
 *
 */
public abstract class AbstractRandomVariable implements RandomVariable {

	@Override
	public double generate() {
		return getInverse(Math.random());
	}

	@Override
	public double generate(RandomNumberGenerator1D random) {
		return getInverse(random.getAsDouble());
	}

	@Override
	public double generate(double x) {
		return getInverse(x);
	}

	@Override
	public double getSampleMean(int numberOfSimulations) {
		double sum = 0;
		for (int i = 0; i < numberOfSimulations; i++) {
			sum += generate();
		}
		return sum / numberOfSimulations;
	}

	@Override
	public double getSampleVariance(int numberOfSimulations) {
		double sampleVariance = 0;
		double sampleMean = getSampleMean(numberOfSimulations);
		for (int i = 0; i < numberOfSimulations; i++) {
			sampleVariance += Math.pow(generate() - sampleMean, 2);
		}
		return sampleVariance / (numberOfSimulations - 1);
	}

	@Override
	public double getSampleMean(int numberOfSimulations, RandomNumberGenerator1D random) {
		double sum = 0;
		for (int i = 0; i < numberOfSimulations; i++) {
			sum += generate(random);
		}
		return sum / numberOfSimulations;
	}

	@Override
	public double getSampleVariance(int numberOfSimulations, RandomNumberGenerator1D random) {
		double sampleVariance = 0;
		double sampleMean = getSampleMean(numberOfSimulations);
		for (int i = 0; i < numberOfSimulations; i++) {
			sampleVariance += Math.pow(generate(random) - sampleMean, 2);
		}
		return sampleVariance / (numberOfSimulations - 1);
	}

	@Override
	public double getSampleMeanEquidistributed(int numberOfSimulations) {
		double sum = 0;
		for (int i = 0; i < numberOfSimulations; i++) {
			sum += generate((double) i / numberOfSimulations);
		}
		return sum / numberOfSimulations;
	}

	@Override
	public double getSampleVarianceEquidistributed(int numberOfSimulations) {
		double sampleVariance = 0;
		double sampleMean = getSampleMeanEquidistributed(numberOfSimulations);
		for (int i = 0; i < numberOfSimulations; i++) {
			sampleVariance += Math.pow(generate((double) i / numberOfSimulations) - sampleMean, 2);
		}
		return sampleVariance / (numberOfSimulations - 1);
	}

	public double getSampleMeanParallel(int numberOfSimulations) throws InterruptedException, ExecutionException {

		int numberOfTask = 100;
		int numberOfSamplesPerTask = numberOfSimulations / numberOfTask;

		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

		double sampleMean = 0;

		try {
			/*
			 * Distribute the tasks.
			 */
			List<Future<Double>> results = new ArrayList<>();
			for (int taskIndex = 0; taskIndex < numberOfTask; taskIndex++) {

				int startIndex = taskIndex * numberOfSamplesPerTask;

				Future<Double> value = executor.submit(() -> getValue(startIndex, numberOfSamplesPerTask));
				results.add(value);
			}

			/*
			 * Collect the results
			 */
			double sum = 0.0;
			for (int taskIndex = 0; taskIndex < numberOfTask; taskIndex++) {
				sum += results.get(taskIndex).get();
			}
			sampleMean = sum / numberOfTask;

		} finally {
			executor.shutdown();
		}
		return sampleMean;
	}

	public double getSampleMeanParallel(int numberOfSimulations, RandomNumberGenerator1D random)
			throws InterruptedException, ExecutionException {

		int numberOfTask = 100;
		int numberOfSamplesPerTask = numberOfSimulations / numberOfTask;

		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

		double sampleMean = 0;

		try {
			/*
			 * Distribute the tasks.
			 */
			List<Future<Double>> results = new ArrayList<>();
			for (int taskIndex = 0; taskIndex < numberOfTask; taskIndex++) {

				int startIndex = taskIndex * numberOfSamplesPerTask;

				Future<Double> value = executor.submit(() -> getValue(startIndex, numberOfSamplesPerTask, random));
				results.add(value);
			}

			/*
			 * Collect the results
			 */
			double sum = 0.0;
			for (int taskIndex = 0; taskIndex < numberOfTask; taskIndex++) {
				sum += results.get(taskIndex).get();
			}
			sampleMean = sum / numberOfTask;

		} finally {
			executor.shutdown();
		}
		return sampleMean;
	}

	private double getValue(int startIndex, int numberOfSamplePerTask) {

		double sum = 0.0;
		for (int i = startIndex; i < startIndex + numberOfSamplePerTask; i++) {
			sum += generate();
		}
		double value = sum / numberOfSamplePerTask;
		return value;

	}

	private double getValue(int startIndex, int numberOfSamplePerTask, RandomNumberGenerator1D random) {

		double sum = 0.0;
		for (int i = startIndex; i < startIndex + numberOfSamplePerTask; i++) {
			sum += generate(random);
		}
		double value = sum / numberOfSamplePerTask;
		return value;

	}

}