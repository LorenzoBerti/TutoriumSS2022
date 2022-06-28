package com.lorenzoberti.session07;

import net.finmath.randomnumbers.RandomNumberGenerator1D;

public interface RandomVariable {

	/**
	 * It generates a realization of a random variable with given distribution.
	 * 
	 * @return a realization of the random variable
	 */
	double generate();

	/**
	 * It generates a realization of a random variable with given distribution. It
	 * uses the given RandomNumberGenerator to generate random numbers.
	 * 
	 * @param random, the random number generator
	 * @return a realization of the random variable
	 */
	double generate(RandomNumberGenerator1D random);

	/**
	 * It generates a realization of a random variable with given distribution by a
	 * double x.
	 * 
	 * @return a realization of the random variable
	 */
	double generate(double x);

	/**
	 * It returns the analytic mean (if known) of the random variable.
	 * 
	 * @return the mean of the random variable
	 */
	double getAnalyticMean();

	/**
	 * It returns the analytic variance (if known) of the random variable.
	 * 
	 * @return the variance of the random variable
	 */
	double getAnalyticVariance();

	/**
	 * It returns the sample mean of the random variable. It uses the Math.Random()
	 * object to generate random numbers.
	 *
	 * @param numberOfSimulations, the number of simulations
	 * @return the sample mean of the random variable
	 *
	 */
	double getSampleMean(int numberOfSimulations);

	/**
	 * It returns the sample mean of the random variable. It uses equidistributed
	 * numbers.
	 * 
	 * @param numberOfSimulations, the number of simulations
	 * @return the sample mean of the random variable
	 */
	double getSampleMeanEquidistributed(int numberOfSimulations);

	/**
	 * It returns the sample mean of the random variable. It uses the given
	 * RandomNumberGenerator to generate random numbers.
	 * 
	 * @param numberOfSimulations, the number of simulations
	 * @param random,              the random number generator
	 * @return the sample mean of the random variable
	 */
	double getSampleMean(int numberOfSimulations, RandomNumberGenerator1D random);

	/**
	 * It returns the sample variance of the random variable. It uses the
	 * Math.Random() object to generate random numbers.
	 * 
	 * @param numberOfSimulations, the number of simulations
	 * @return the sample variance of the random variable
	 */
	double getSampleVariance(int numberOfSimulations);

	/**
	 * It returns the sample variance of the random variable. It uses the given
	 * RandomNumberGenerator to generate random numbers.
	 * 
	 * @param numberOfSimulations, the number of simulations
	 * @param random,              the random number generator
	 * @return the sample variance of the random variable
	 */
	double getSampleVariance(int numberOfSimulations, RandomNumberGenerator1D random);

	/**
	 * It returns the sample mean of the random variable. It uses equidistributed
	 * numbers.
	 * 
	 * @param numberOfSimulations, the number of simulations
	 * @return the sample variance of the random variable
	 */
	double getSampleVarianceEquidistributed(int numberOfSimulations);

	/**
	 * It returns the cumulative distribution function of evaluated at x, i.e. P(X
	 * <= x).
	 *
	 * @param x, the point where the cumulative distribution function is evaluated
	 * @return the cumulative distribution function evaluated at x
	 */
	double getCumulativeDistributionFunction(double x);

	/**
	 * It returns the density function of the random variable evaluated at x.
	 *
	 * @param x, the point where the cumulative distribution function is evaluated
	 * @return the density function evaluated at x
	 */
	double getDensityFunction(double x);

	/**
	 * It return the inverse of the cdf of evaluated at x; i.e. the quantile
	 * function evaluate at x.
	 * 
	 * @param x
	 * @return the inverse of the cdf evaluated at x
	 */
	double getInverse(double x);

}