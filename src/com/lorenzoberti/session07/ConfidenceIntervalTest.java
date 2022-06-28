/**
 * 
 */
package com.lorenzoberti.session07;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import net.finmath.randomnumbers.MersenneTwister;
import net.finmath.randomnumbers.RandomNumberGenerator1D;
import net.finmath.randomnumbers.VanDerCorputSequence;

/**
 * @author Lorenzo Berti
 *
 */
public class ConfidenceIntervalTest {

	static DecimalFormat formatterValue = new DecimalFormat("#0.000000");

	static DecimalFormat formatterPercentage = new DecimalFormat("#0.00%");

	static double level = 0.05; // Confidence level 95%
	static int numberOfSimulations = 10000;

	/**
	 * @param args
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		double p = 0.3;

		AbstractRandomVariable bernoulli = new Bernoulli(p);
		double mean = bernoulli.getAnalyticMean();

		RandomNumberGenerator1D mersenne = new MersenneTwister();
		RandomNumberGenerator1D vanDerCorput2 = new VanDerCorputSequence(2);
		RandomNumberGenerator1D vanDerCorput3 = new VanDerCorputSequence(3);

		double sampleMeanRandom = bernoulli.getSampleMean(numberOfSimulations);
		double sampleMeanMersenne = bernoulli.getSampleMean(numberOfSimulations, mersenne);
		double sampleMeanCorput2 = bernoulli.getSampleMean(numberOfSimulations, vanDerCorput2);
		double sampleMeanCorput3 = bernoulli.getSampleMean(numberOfSimulations, vanDerCorput3);
		double sampleMeanEquidistributed = bernoulli.getSampleMeanEquidistributed(numberOfSimulations);

		ConfidenceInterval chebyshevInterval = new ChebyshevInterval(bernoulli);
		ConfidenceInterval hoeffdingInterval = new HoeffdingInterval(bernoulli);
		double[] intervalChebyshev = chebyshevInterval.getConfidenceInterval(numberOfSimulations, level);
		double[] intervalHoeffding = hoeffdingInterval.getConfidenceInterval(numberOfSimulations, level);

		System.out.println("Mean");
		System.out.println("Math Random......: " + formatterValue.format(sampleMeanRandom) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanRandom - mean)));
		System.out.println("Mersenne.........: " + formatterValue.format(sampleMeanMersenne) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanMersenne - mean)));
		System.out.println("Van Der Corput2..: " + formatterValue.format(sampleMeanCorput2) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanCorput2 - mean)));
		System.out.println("Van Der Corput3..: " + formatterValue.format(sampleMeanCorput3) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanCorput3 - mean)));
		System.out.println("Equidistributed..: " + formatterValue.format(sampleMeanEquidistributed) + "\tError: "
				+ formatterValue.format(Math.abs(sampleMeanEquidistributed - mean)));

		System.out.println("_".repeat(80) + "\n");

		System.out.println("Confidence Interval Chebyshev: " + Arrays.toString(intervalChebyshev));
		System.out.println("Confidence Interval Hoeffding: " + Arrays.toString(intervalHoeffding));

		int numberOfMeanComputations = 10;

		System.out.println("_".repeat(80) + "\n");

		System.out.println("Math random: ");
		System.out.println("The frequence of p being in the Chebyshev confidence interval is " + formatterPercentage
				.format(frequenceOfInterval(bernoulli, chebyshevInterval, numberOfMeanComputations, level)));
		System.out.println("The frequence of p being in the Hoeffding confidence interval is " + formatterPercentage
				.format(frequenceOfInterval(bernoulli, hoeffdingInterval, numberOfMeanComputations, level)));

		System.out.println("_".repeat(80) + "\n");

		System.out.println("Mersenne: ");
		System.out.println("The frequence of p being in the Chebyshev confidence interval is " + formatterPercentage
				.format(frequenceOfInterval(bernoulli, mersenne, chebyshevInterval, numberOfMeanComputations, level)));
		System.out.println("The frequence of p being in the Hoeffding confidence interval is " + formatterPercentage
				.format(frequenceOfInterval(bernoulli, mersenne, hoeffdingInterval, numberOfMeanComputations, level)));

		System.out.println("_".repeat(80) + "\n");

		System.out.println("Van Der Corput 2: ");
		System.out.println("The frequence of p being in the Chebyshev confidence interval is "
				+ formatterPercentage.format(frequenceOfInterval(bernoulli, vanDerCorput2, chebyshevInterval,
						numberOfMeanComputations, level)));
		System.out.println("The frequence of p being in the Hoeffding confidence interval is "
				+ formatterPercentage.format(frequenceOfInterval(bernoulli, vanDerCorput2, hoeffdingInterval,
						numberOfMeanComputations, level)));

		System.out.println("_".repeat(80) + "\n");

		System.out.println("Van Der Corput 3: ");
		System.out.println("The frequence of p being in the Chebyshev confidence interval is "
				+ formatterPercentage.format(frequenceOfInterval(bernoulli, vanDerCorput3, chebyshevInterval,
						numberOfMeanComputations, level)));
		System.out.println("The frequence of p being in the Hoeffding confidence interval is "
				+ formatterPercentage.format(frequenceOfInterval(bernoulli, vanDerCorput3, hoeffdingInterval,
						numberOfMeanComputations, level)));

		System.out.println("_".repeat(80) + "\n");

		System.out.println("Equidistributed: ");
		System.out.println("The frequence of p being in the Chebyshev confidence interval is "
				+ formatterPercentage.format(frequenceOfIntervalEquidistributed(bernoulli, chebyshevInterval,
						numberOfMeanComputations, level)));
		System.out.println("The frequence of p being in the Hoeffding confidence interval is "
				+ formatterPercentage.format(frequenceOfIntervalEquidistributed(bernoulli, hoeffdingInterval,
						numberOfMeanComputations, level)));

	}

	public static double frequenceOfInterval(AbstractRandomVariable randomVariable, ConfidenceInterval interval,
			int numberOfMeanComputations, double confidenceLevel) throws InterruptedException, ExecutionException {

		double numberOfTimesInsideTheInterval = 0;
		double[] confidenceInterval = interval.getConfidenceInterval(numberOfSimulations, confidenceLevel);
		double lowerBound = confidenceInterval[0];
		double upperBound = confidenceInterval[1];
		double sampleMean;
		for (int i = 0; i < numberOfMeanComputations; i++) {
			sampleMean = randomVariable.getSampleMeanParallel(numberOfSimulations); // sample mean
			if (sampleMean > lowerBound && sampleMean < upperBound) {
				numberOfTimesInsideTheInterval++; // sample mean within the confidence interval
			}
		}
		return numberOfTimesInsideTheInterval / numberOfMeanComputations;
	}

	public static double frequenceOfInterval(AbstractRandomVariable randomVariable, RandomNumberGenerator1D random,
			ConfidenceInterval interval, int numberOfMeanComputations, double confidenceLevel)
			throws InterruptedException, ExecutionException {

		double numberOfTimesInsideTheInterval = 0;
		double[] confidenceInterval = interval.getConfidenceInterval(numberOfSimulations, confidenceLevel);
		double lowerBound = confidenceInterval[0];
		double upperBound = confidenceInterval[1];
		double sampleMean;
		for (int i = 0; i < numberOfMeanComputations; i++) {
			sampleMean = randomVariable.getSampleMeanParallel(numberOfSimulations, random); // sample mean
			if (sampleMean > lowerBound && sampleMean < upperBound) {
				numberOfTimesInsideTheInterval++; // sample mean within the confidence interval
			}
		}
		return numberOfTimesInsideTheInterval / numberOfMeanComputations;
	}

	public static double frequenceOfIntervalEquidistributed(AbstractRandomVariable randomVariable,
			ConfidenceInterval interval, int numberOfMeanComputations, double confidenceLevel) {

		double numberOfTimesInsideTheInterval = 0;
		double[] confidenceInterval = interval.getConfidenceInterval(numberOfSimulations, confidenceLevel);
		double lowerBound = confidenceInterval[0];
		double upperBound = confidenceInterval[1];
		double sampleMean;
		for (int i = 0; i < numberOfMeanComputations; i++) {
			sampleMean = randomVariable.getSampleMeanEquidistributed(numberOfSimulations); // sample mean
			if (sampleMean > lowerBound && sampleMean < upperBound) {
				numberOfTimesInsideTheInterval++; // sample mean within the confidence interval
			}
		}
		return numberOfTimesInsideTheInterval / numberOfMeanComputations;
	}

}