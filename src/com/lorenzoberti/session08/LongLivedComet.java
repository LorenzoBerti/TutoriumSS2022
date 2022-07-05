/**
 * 
 */
package com.lorenzoberti.session08;

import java.util.Arrays;
import java.util.List;

import com.andreamazzon.handout3.HistogramData;
import com.andreamazzon.handout6.randomvariables.NormalRandomVariable;
import com.andreamazzon.session4.usefulmatrices.UsefulMethodsMatricesVectors;
import com.andreamazzon.usefulmethodsmatricesandvectors.UsefulMethodsMatricesAndVectors;

/**
 * This class represents a Long Lived Comet according to the Hammersley and
 * Handscomb (1964) model.
 * 
 * @author Lorenzo Berti
 *
 */
public class LongLivedComet {

	double sigma;
	double x0; // initial energy

	NormalRandomVariable normal;

	double[][] cometSample; // store the lifetime period of comets and the respective number of orbits
							// completed

	public LongLivedComet(double x0, double sigma) {
		super();
		this.x0 = x0;
		this.sigma = sigma;
		this.normal = new NormalRandomVariable(0, sigma);
	}

	/**
	 * This method computes the comet lifetime and the respective number of orbits.
	 * 
	 * @return a vector storing the lifetime period length and the number of orbits
	 *         completed (in this order)
	 */
	double[] cometLifetimeOrbit() {
		double x = x0;
		int orbit = 0;
		double sum = 0;
		// Comet lifetime: the comet "lives" as long as its energy is negative
		while (x < 0) {
			orbit++;
			sum += Math.pow(-x, -3 / 2);
			x += normal.generate();
		}
		return new double[] { sum, orbit };
	}

	/**
	 * This method computes the comet lifetime and the respective number of orbits
	 * up to max limit number of orbits.
	 * 
	 * @return a vector storing the lifetime period length and the number of orbits
	 *         completed (in this order)
	 */
	double[] cometLifetimeOrbit(double maxOrbit) {
		double x = x0;
		int orbit = 0;
		double sum = 0;
		// Comet lifetime according to the following rule: the comet "lives" until
		// either its energy is positive or complete a given maximum number of orbits
		while (x < 0 & orbit <= maxOrbit) {
			orbit++;
			sum += Math.pow(-x, -3 / 2);
			x += normal.generate();
		}
		return new double[] { sum, orbit };
	}

	/**
	 * This private method generates the comet lifetime period and the respective
	 * number of orbits for the given sample.
	 * 
	 * @param n size of the sample
	 */
	private void generateLifeOrbitValues(int n) {
		cometSample = new double[n][2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 2; j++) {
				cometSample[i][j] = cometLifetimeOrbit()[j];
			}
		}
	}

	/**
	 * This private method generates the comet lifetime period and the respective
	 * number of orbits for the given sample of comets up to a max number of orbits
	 * up to a max limit number of orbits.
	 * 
	 * @param n        size of the sample
	 * @param maxOrbit the max number of orbits
	 */
	private void generateLifeOrbitValues(int n, double maxOrbit) {
		cometSample = new double[n][2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 2; j++) {
				cometSample[i][j] = cometLifetimeOrbit(maxOrbit)[j];
			}
		}
	}


	/**
	 * This method computes the mean of the lifetime of the given sample of comets.
	 * 
	 * @param numberOfSimulations size of the sample
	 * @return the mean of the lifetime period length of the sample of comets
	 */
	double getSampleMeanCometLifetime(int numberOfSimulations) {

		generateLifeOrbitValues(numberOfSimulations);
		double[] cometLifetime = new double[numberOfSimulations];
		for (int i = 0; i < numberOfSimulations; i++) {
			cometLifetime[i] = cometSample[i][0];
		}
		return UsefulMethodsMatricesAndVectors.getAverage(cometLifetime);

	}

	/**
	 * This method computes the mean of the lifetime of the given sample of comets
	 * up to a limit number of orbits.
	 * 
	 * @param numberOfSimulations size of the sample
	 * @param maxOrbit            the max number of orbits
	 * @return the mean of the lifetime period length of the sample of comets
	 */
	double getSampleMeanCometLifetime(int numberOfSimulations, double maxOrbit) {

		generateLifeOrbitValues(numberOfSimulations, maxOrbit);
		double[] cometLifetime = new double[numberOfSimulations];
		for (int i = 0; i < numberOfSimulations; i++) {
			cometLifetime[i] = cometSample[i][0];
		}
		return UsefulMethodsMatricesAndVectors.getAverage(cometLifetime);

	}

	/**
	 * This method computes the standard deviation of the lifetime of the given
	 * sample of comets.
	 * 
	 * @param numberOfSimulations size of the sample
	 * @return the standard deviation of the lifetime period length of the sample of
	 *         comets
	 */
	double getStdDeviationCometLifetime(int numberOfSimulations) {

		generateLifeOrbitValues(numberOfSimulations);
		double[] cometLifetime = new double[numberOfSimulations];
		for (int i = 0; i < numberOfSimulations; i++) {
			cometLifetime[i] = cometSample[i][0];
		}
		return UsefulMethodsMatricesAndVectors.getStandardDeviation(cometLifetime);

	}

	/**
	 * This method computes the standard deviation of the lifetime of the given
	 * sample of comets up to a limit number of orbits.
	 * 
	 * @param numberOfSimulations size of the sample
	 * @param maxOrbit            the max number of orbits
	 * @return the standard deviation of the lifetime period length of the sample of
	 *         comets
	 */
	double getStdDeviationCometLifetime(int numberOfSimulations, double maxOrbit) {

		generateLifeOrbitValues(numberOfSimulations, maxOrbit);
		double[] cometLifetime = new double[numberOfSimulations];
		for (int i = 0; i < numberOfSimulations; i++) {
			cometLifetime[i] = cometSample[i][0];
		}
		return UsefulMethodsMatricesAndVectors.getStandardDeviation(cometLifetime);

	}

	/**
	 * This method compute the empirical distribution of the lifetime of the comet,
	 * i.e. P(T <= t), where T is the comet lifetime random variable
	 * 
	 * @param numberOfSimulations, sample size
	 * @param t,                   the point where the cumulative distribution
	 *                             function is evaluated
	 * @return the cumulative distribution function evaluated at t
	 */
	double empiricalDistributionCometLifetime(int numberOfSimulations, double t) {

		generateLifeOrbitValues(numberOfSimulations);
		double sum = 0;
		for (int i = 0; i < numberOfSimulations; i++) {
			if (cometSample[i][0] < t) {
				sum++;
			}
		}
		return sum / numberOfSimulations;
	}

	/**
	 * This method compute the empirical distribution of the comet lifetime up to a
	 * max number of orbits, i.e. P(T <= t), where T is the comet lifetime random
	 * variable
	 * 
	 * @param numberOfSimulations, sample size
	 * @param t,                   the point where the cumulative distribution
	 *                             function is evaluated
	 * @param maxOrbit,
	 * @return the cumulative distribution function evaluated at t
	 */
	double empiricalDistributionCometLifetimeMaxOrbit(int numberOfSimulations, double t, int maxOrbit) {

		generateLifeOrbitValues(numberOfSimulations, maxOrbit);
		double sum = 0;
		for (int i = 0; i < numberOfSimulations; i++) {
			if (cometSample[i][0] < t) {
				sum++;
			}
		}

		return sum / numberOfSimulations;
	}

	/**
	 * Compute the Chebyshev's confidence interval for the probability that a comet
	 * completes more than a given number of orbits
	 * 
	 * @param numberOfSimulations, sample size
	 * @param level,               confidence interval level
	 * @param maxOrbit,            max number of orbits
	 * @return Chebyshev's confidence interval at level (1-level)
	 */
	public double[] chebyshevIntervalMaxOrbit(int numberOfSimulations, double level, double maxOrbit) {

		generateLifeOrbitValues(numberOfSimulations, maxOrbit);
		double[] savedValues = new double[numberOfSimulations];
		for (int i = 0; i < numberOfSimulations; i++) {
			if (cometSample[i][1] < maxOrbit) {
				savedValues[i] = 0;
			} else
				savedValues[i] = 1;
		}
		double mean = UsefulMethodsMatricesAndVectors.getAverage(savedValues);
		double stdDeviation = UsefulMethodsMatricesAndVectors.getStandardDeviation(savedValues);

		return new double[] { mean - stdDeviation / Math.sqrt((level) * numberOfSimulations),
				mean + stdDeviation / Math.sqrt((level) * numberOfSimulations) };

	}

	/**
	 * Compute the Chebyshev's confidence interval for the probability that a comet
	 * completes more than a given number of orbits
	 * 
	 * @param numberOfSimulations, sample size
	 * @param level,               confidence interval level
	 * @param maxOrbit,            max number of orbits
	 * @return Chebyshev's confidence interval at level (1-level)
	 */
	public double[] chebyshevIntervalOrbit(int numberOfSimulations, double level, double maxOrbit) {

		generateLifeOrbitValues(numberOfSimulations);
		double[] savedValues = new double[numberOfSimulations];
		for (int i = 0; i < numberOfSimulations; i++) {
			if (cometSample[i][1] < maxOrbit) {
				savedValues[i] = 0;
			} else
				savedValues[i] = 1;
		}
		double mean = UsefulMethodsMatricesAndVectors.getAverage(savedValues);
		double stdDeviation = UsefulMethodsMatricesAndVectors.getStandardDeviation(savedValues);

		return new double[] { mean - stdDeviation / Math.sqrt((level) * numberOfSimulations),
				mean + stdDeviation / Math.sqrt((level) * numberOfSimulations) };

	}

	/**
	 * This method returns the histogram of the number comets that survive after a
	 * given number of orbits.
	 * 
	 * @param numberOfBins
	 * @param numberOfSimulations, sample size
	 * @param maxOrbit,            the number of orbits that a comet must touch
	 * @return histogram of the number of survived comets
	 */
	public HistogramData getHistogram(int numberOfBins, int numberOfSimulations, double maxOrbit) {

		generateLifeOrbitValues(numberOfSimulations);
		double[] arraySample = new double[numberOfSimulations];

		for (int i = 0; i < numberOfSimulations; i++) {
			arraySample[i] = cometSample[i][1];
		}
		List<Double> survived = Arrays.stream(arraySample).filter(m -> m > maxOrbit).boxed().toList();
		int length = survived.size();
		double[] observations = new double[length];
		for (int i = 0; i < length; i++) {
			observations[i] = survived.get(i);
		}

		double min = UsefulMethodsMatricesVectors.getMin(observations);
		double max = UsefulMethodsMatricesVectors.getMax(observations);

		int[] histogram = UsefulMethodsMatricesVectors.buildHistogram(observations, min, max, numberOfBins);
		HistogramData histogramData = new HistogramData();

		histogramData.setHistogram(histogram);
		histogramData.setMinBin(min);
		histogramData.setMaxBin(max);
		return histogramData;
	}

	/**
	 * This method returns the histogram of the number comets that lives the solar
	 * system before touching the a given maximum number of orbits.
	 * 
	 * @param numberOfBins
	 * @param numberOfSimulations, sample size
	 * @param maxOrbit,            the number of orbits that a comet must touch
	 * @return histogram of the number of comets that leave the solar system before
	 *         completing the maxOrbit number of orbits
	 */
	public HistogramData getHistogramCometsLeaveSolarSystem(int numberOfBins, int numberOfSimulations,
			double maxOrbit) {

		generateLifeOrbitValues(numberOfSimulations, maxOrbit);
		double[] arraySample = new double[numberOfSimulations];

		// store the number of orbits completed by the comets
		for (int i = 0; i < numberOfSimulations; i++) {
			arraySample[i] = cometSample[i][1];
		}
		// Now we save only the comets that do not touch the barrier since they leave
		// the solar system before
		List<Double> survived = Arrays.stream(arraySample).filter(m -> m < maxOrbit).boxed().toList();
		int length = survived.size();
		double[] observations = new double[length];
		for (int i = 0; i < length; i++) {
			observations[i] = survived.get(i);
		}

		double min = UsefulMethodsMatricesVectors.getMin(observations);
		double max = UsefulMethodsMatricesVectors.getMax(observations);

		int[] histogram = UsefulMethodsMatricesVectors.buildHistogram(observations, min, max, numberOfBins);
		HistogramData histogramData = new HistogramData();

		histogramData.setHistogram(histogram);
		histogramData.setMinBin(min);
		histogramData.setMaxBin(max);
		return histogramData;
	}

}