/**
 * 
 */
package com.lorenzoberti.session07;

import net.finmath.randomnumbers.RandomNumberGenerator1D;

/**
 * Poisson random variable: discrete random variable with support on N and
 * density f(k) = exp(-lambda) * lambda^k/k!, where lambda in (0, \infty)
 * 
 * @author Lorenzo Berti
 *
 */
public class Poisson implements RandomVariable {

	@Override
	public double generate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double generate(RandomNumberGenerator1D random) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double generate(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAnalyticMean() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAnalyticVariance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSampleMean(int numberOfSimulations) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSampleMeanEquidistributed(int numberOfSimulations) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSampleMean(int numberOfSimulations, RandomNumberGenerator1D random) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSampleVariance(int numberOfSimulations) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSampleVariance(int numberOfSimulations, RandomNumberGenerator1D random) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSampleVarianceEquidistributed(int numberOfSimulations) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getCumulativeDistributionFunction(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDensityFunction(double x) {
		// TODO Auto-generated method stub
		return 0;
	}

}
