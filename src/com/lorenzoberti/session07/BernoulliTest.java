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
 * Use this class to test your implementation of the Bernoulli distribution.
 * 
 * @author Lorenzo Berti
 *
 */
public class BernoulliTest {

	static DecimalFormat formatterValue = new DecimalFormat("#0.000000");

	static DecimalFormat formatterPercentage = new DecimalFormat("#0.00%");

	static double level = 0.05; // Confidence level 95%
	static int numberOfSimulations = 100000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		double p = 0.3;

		Bernoulli bernoulli = new Bernoulli(p);
		double mean = bernoulli.getAnalyticMean();
		double variance = bernoulli.getAnalyticVariance();

		RandomNumberGenerator1D mersenne = new MersenneTwister();
		RandomNumberGenerator1D vanDerCorput2 = new VanDerCorputSequence(2);
		RandomNumberGenerator1D vanDerCorput3 = new VanDerCorputSequence(3);
		RandomNumberGenerator1D sobolev = new SobolSequence1D();

		double sampleMeanRandom = bernoulli.getSampleMean(numberOfSimulations);
		double sampleMeanMersenne = bernoulli.getSampleMean(numberOfSimulations, mersenne);
		double sampleMeanCorput2 = bernoulli.getSampleMean(numberOfSimulations, vanDerCorput2);
		double sampleMeanCorput3 = bernoulli.getSampleMean(numberOfSimulations, vanDerCorput3);
		double sampleMeanSobolev = bernoulli.getSampleMean(numberOfSimulations, sobolev);
		double sampleMeanEquidistributed = bernoulli.getSampleMeanEquidistributed(numberOfSimulations);

		double sampleVarianceRandom = bernoulli.getSampleVariance(numberOfSimulations);
		double sampleVarianceMersenne = bernoulli.getSampleVariance(numberOfSimulations, mersenne);
		double sampleVarianceCorput2 = bernoulli.getSampleVariance(numberOfSimulations, vanDerCorput2);
		double sampleVarianceCorput3 = bernoulli.getSampleVariance(numberOfSimulations, vanDerCorput3);
		double sampleVarianceSobolev = bernoulli.getSampleVariance(numberOfSimulations, sobolev);
		double sampleVarianceEquidistributed = bernoulli.getSampleVarianceEquidistributed(numberOfSimulations);

		ChebyshevInterval interval = new ChebyshevInterval(bernoulli);
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

		double probability0 = bernoulli.getDensityFunction(0);
		double probability1 = bernoulli.getDensityFunction(1);

		System.out.println("X = 0 with probability: " + probability0);
		System.out.println("X = 1 with probability: " + probability1);



	}
}
