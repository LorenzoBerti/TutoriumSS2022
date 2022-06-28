/**
 * 
 */
package com.lorenzoberti.session07;

import java.util.concurrent.ExecutionException;

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

		return 0;
	}

	public double getSampleMeanParallel(int numberOfSimulations, RandomNumberGenerator1D random)
			throws InterruptedException, ExecutionException {

		return 0;
	}



}