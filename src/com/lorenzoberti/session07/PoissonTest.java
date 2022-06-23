/**
 * 
 */
package com.lorenzoberti.session07;

import java.text.DecimalFormat;
import java.util.Arrays;

import net.finmath.randomnumbers.MersenneTwister;
import net.finmath.randomnumbers.RandomNumberGenerator1D;
import net.finmath.randomnumbers.SobolSequence1D;
import net.finmath.randomnumbers.VanDerCorputSequence;

/**
 * Use this class to test your implementation of the Poisson random variable.
 * 
 * @author Lorenzo Berti
 *
 */
public class PoissonTest {

	static DecimalFormat formatterValue = new DecimalFormat("#0.000000");

	static DecimalFormat formatterPercentage = new DecimalFormat("#0.00%");

	static double level = 0.05; // Confidence level 95%
	static int numberOfSimulations = 100000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		double lambda = 0.5;

		RandomVariable poisson = null;
		double mean = poisson.getAnalyticMean();
		double variance = poisson.getAnalyticVariance();

		RandomNumberGenerator1D mersenne = new MersenneTwister();
		RandomNumberGenerator1D vanDerCorput2 = new VanDerCorputSequence(2);
		RandomNumberGenerator1D vanDerCorput3 = new VanDerCorputSequence(3);
		RandomNumberGenerator1D sobolev = new SobolSequence1D();

		double sampleMeanRandom = poisson.getSampleMean(numberOfSimulations);
		double sampleMeanMersenne = poisson.getSampleMean(numberOfSimulations, mersenne);
		double sampleMeanCorput2 = poisson.getSampleMean(numberOfSimulations, vanDerCorput2);
		double sampleMeanCorput3 = poisson.getSampleMean(numberOfSimulations, vanDerCorput3);
		double sampleMeanSobolev = poisson.getSampleMean(numberOfSimulations, sobolev);
		double sampleMeanEquidistributed = poisson.getSampleMeanEquidistributed(numberOfSimulations);

		double sampleVarianceRandom = poisson.getSampleVariance(numberOfSimulations);
		double sampleVarianceMersenne = poisson.getSampleVariance(numberOfSimulations, mersenne);
		double sampleVarianceCorput2 = poisson.getSampleVariance(numberOfSimulations, vanDerCorput2);
		double sampleVarianceCorput3 = poisson.getSampleVariance(numberOfSimulations, vanDerCorput3);
		double sampleVarianceSobolev = poisson.getSampleVariance(numberOfSimulations, sobolev);
		double sampleVarianceEquidistributed = poisson.getSampleVarianceEquidistributed(numberOfSimulations);

		ChebyshevInterval interval = new ChebyshevInterval(poisson);
		double[] confidenceInterval = interval.getConfidenceInterval(numberOfSimulations, level);

		System.out.println("Mean");
		System.out.println("Math Random......: " + formatterValue.format(sampleMeanRandom) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanRandom - mean)));
		System.out.println("Mersenne.........: " + formatterValue.format(sampleMeanMersenne) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanMersenne - mean)));
		System.out.println("Van Der Corput2..: " + formatterValue.format(sampleMeanCorput2) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanCorput2 - mean)));
		System.out.println("Van Der Corput3..: " + formatterValue.format(sampleMeanCorput3) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanCorput3 - mean)));
		System.out.println("Sobolev..........: " + formatterValue.format(sampleMeanSobolev) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanSobolev - mean)));
		System.out.println("Equidistributed..: " + formatterValue.format(sampleMeanEquidistributed) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanEquidistributed - mean)));
		System.out.println("Confidence Interval: " + Arrays.toString(confidenceInterval));

		System.out.println("---------------------------------------");

		System.out.println("Variance");
		System.out.println("Math Random......: " + formatterValue.format(sampleVarianceRandom) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceRandom - mean)));
		System.out.println("Mersenne.........: " + formatterValue.format(sampleVarianceMersenne) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceMersenne - mean)));
		System.out.println("Van Der Corput2..: " + formatterValue.format(sampleVarianceCorput2) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceCorput2 - mean)));
		System.out.println("Van Der Corput3..: " + formatterValue.format(sampleVarianceCorput3) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceCorput3 - mean)));
		System.out.println("Sobolev..........: " + formatterValue.format(sampleVarianceSobolev) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceSobolev - mean)));
		System.out.println("Equidistributed..: " + formatterValue.format(sampleVarianceEquidistributed) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceEquidistributed - mean)));

		System.out.println("---------------------------------------");

		double density0 = poisson.getDensityFunction(0);
		double density1 = poisson.getDensityFunction(1);
		double density2 = poisson.getDensityFunction(2);
		double density3 = poisson.getDensityFunction(3);
		double cdf3 = poisson.getCumulativeDistributionFunction(3);
		System.out
				.println("Density in 1..: " + density1 + "\tError: " + Math.abs(density1 - Math.exp(-lambda) * lambda));
		System.out.println("Density in 2..: " + density2 + "\tError: "
				+ Math.abs(density2 - Math.exp(-lambda) * lambda * lambda / 2));
		System.out.println("Density in 3..: " + density3 + "\tError: "
				+ Math.abs(density3 - Math.exp(-lambda) * lambda * lambda * lambda / 6));
		System.out.println(
				"CDF in 3......: " + cdf3 + "\tError: " + Math.abs(cdf3 - density1 - density2 - density0 - density3));

	}

}

