/**
 * 
 */
package com.lorenzoberti.session07;

import net.finmath.randomnumbers.RandomNumberGenerator1D;

/**
 * @author Lorenzo Berti
 *
 */
public class Bernoulli implements RandomVariable {

	double p;

	public Bernoulli(double p) {
		if (p < 0 || p > 1) {
			throw new IllegalArgumentException("Bernoulli parameter must be in [0,1]");
		}
		this.p = p;
	}

	public double getParameter() {
		return p;
	}

	@Override
	public double generate() {
		// TODO Auto-generated method stub
		return getInverse(Math.random());
	}

	private double getInverse(double x) {
		if (x <= 1 - p) {
			return 0;
		} else
			return 1;

	}

	@Override
	public double generate(RandomNumberGenerator1D random) {
		// TODO Auto-generated method stub
		return getInverse(random.getAsDouble());
	}

	@Override
	public double generate(double x) {
		// TODO Auto-generated method stub
		return getInverse(x);
	}

	@Override
	public double getAnalyticMean() {
		// TODO Auto-generated method stub
		return p;
	}

	@Override
	public double getAnalyticVariance() {
		// TODO Auto-generated method stub
		return p * (1 - p);
	}

	@Override
	public double getSampleMean(int numberOfSimulations) {
		// TODO Auto-generated method stub
		double sum = 0.0;
		for (int i = 0; i < numberOfSimulations; i++) {
			sum += generate();
		}
		return sum / numberOfSimulations;
	}

	@Override
	public double getSampleMeanEquidistributed(int numberOfSimulations) {
		// TODO Auto-generated method stub
		double sum = 0.0;
		for (int i = 0; i < numberOfSimulations; i++) {
			sum += generate((double) i / numberOfSimulations);
		}
		return sum / numberOfSimulations;
	}

	@Override
	public double getSampleMean(int numberOfSimulations, RandomNumberGenerator1D random) {
		// TODO Auto-generated method stub
		double sum = 0.0;
		for (int i = 0; i < numberOfSimulations; i++) {
			sum += generate(random.getAsDouble());
		}
		return sum / numberOfSimulations;
	}

	@Override
	public double getSampleVariance(int numberOfSimulations) {
		// TODO Auto-generated method stub
		double sampleVariance = 0.0;
		double sampleMean = getSampleMean(numberOfSimulations);
		for (int i = 0; i < numberOfSimulations; i++) {
			// sampleVariance += Math.pow(generate() - sampleMean, 2);
			sampleVariance += Math.pow(generate() - getAnalyticMean(), 2);
		}
		return sampleVariance / (numberOfSimulations - 1);
	}

	@Override
	public double getSampleVariance(int numberOfSimulations, RandomNumberGenerator1D random) {
		// TODO Auto-generated method stub
		double sampleVariance = 0.0;
		double sampleMean = getSampleMean(numberOfSimulations, random);
		for (int i = 0; i < numberOfSimulations; i++) {
			// sampleVariance += Math.pow(generate(random.getAsDouble()) - sampleMean, 2);
			sampleVariance += Math.pow(generate(random.getAsDouble()) - getAnalyticVariance(), 2);
		}
		return sampleVariance / (numberOfSimulations - 1);
	}

	@Override
	public double getSampleVarianceEquidistributed(int numberOfSimulations) {
		// TODO Auto-generated method stub
		double sampleVariance = 0.0;
		double sampleMean = getSampleMeanEquidistributed(numberOfSimulations);
		for (int i = 0; i < numberOfSimulations; i++) {
			// sampleVariance += Math.pow(generate((double) i / numberOfSimulations) -
			// sampleMean, 2);
			sampleVariance += Math.pow(generate((double) i / numberOfSimulations) - getAnalyticVariance(), 2);
		}
		return sampleVariance / (numberOfSimulations - 1);
	}

	@Override
	public double getCumulativeDistributionFunction(double x) {
		// TODO Auto-generated method stub
		if (x < 0) {
			return 0;
		} else if (x >= 0 & x < 1) {
			return 1 - p;
		} else
			return 1;
	}

	@Override
	public double getDensityFunction(double x) {
		// TODO Auto-generated method stub
		if (x == 0) {
			return 1 - p;
		} else if (x == 1) {
			return p;
		} else
			throw new IllegalArgumentException("Bernoulli random variable takes only values 0 and 1");

	}

}
