/**
 * 
 */
package com.lorenzoberti.session08;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.function.DoubleUnaryOperator;

import com.lorenzoberti.session07.AbstractRandomVariable;
import com.lorenzoberti.session07.ChebyshevInterval;

import net.finmath.plots.Named;
import net.finmath.plots.Plot2D;
import net.finmath.randomnumbers.MersenneTwister;
import net.finmath.randomnumbers.RandomNumberGenerator1D;
import net.finmath.randomnumbers.SobolSequence1D;
import net.finmath.randomnumbers.VanDerCorputSequence;

/**
 * @author Lorenzo Berti
 *
 */
public class ParetoTest {

	static DecimalFormat formatterValue = new DecimalFormat("#0.000000");

	static DecimalFormat formatterPercentage = new DecimalFormat("#0.00%");

	static double level = 0.5; // Confidence level 95%
	static int numberOfSimulations = 100000;

	/**
	 * @param args
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		double shape = 1;
		double scale = 1;

		AbstractRandomVariable pareto = new Pareto(shape, scale);
		double mean = pareto.getAnalyticMean();
		double variance = pareto.getAnalyticVariance();

		RandomNumberGenerator1D mersenne = new MersenneTwister();
		RandomNumberGenerator1D vanDerCorput2 = new VanDerCorputSequence(2);
		RandomNumberGenerator1D vanDerCorput3 = new VanDerCorputSequence(3);
		RandomNumberGenerator1D sobolev = new SobolSequence1D();

		double sampleMeanRandom = pareto.getSampleMean(numberOfSimulations);
		double sampleMeanMersenne = pareto.getSampleMean(numberOfSimulations, mersenne);
		double sampleMeanCorput2 = pareto.getSampleMean(numberOfSimulations, vanDerCorput2);
		double sampleMeanCorput3 = pareto.getSampleMean(numberOfSimulations, vanDerCorput3);
		double sampleMeanSobolev = pareto.getSampleMean(numberOfSimulations, sobolev);
		double sampleMeanEquidistributed = pareto.getSampleMeanEquidistributed(numberOfSimulations);

		double sampleVarianceRandom = pareto.getSampleVariance(numberOfSimulations);
		double sampleVarianceMersenne = pareto.getSampleVariance(numberOfSimulations, mersenne);
		double sampleVarianceCorput2 = pareto.getSampleVariance(numberOfSimulations, vanDerCorput2);
		double sampleVarianceCorput3 = pareto.getSampleVariance(numberOfSimulations, vanDerCorput3);
		double sampleVarianceSobolev = pareto.getSampleVariance(numberOfSimulations, sobolev);
		double sampleVarianceEquidistributed = pareto.getSampleVarianceEquidistributed(numberOfSimulations);

		ChebyshevInterval interval = new ChebyshevInterval(pareto);
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
				+ formatterValue.format(Math.abs(sampleVarianceRandom - variance)));
		System.out.println("Mersenne.........: " + formatterValue.format(sampleVarianceMersenne) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceMersenne - variance)));
		System.out.println("Van Der Corput2..: " + formatterValue.format(sampleVarianceCorput2) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceCorput2 - variance)));
		System.out.println("Van Der Corput3..: " + formatterValue.format(sampleVarianceCorput3) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceCorput3 - variance)));
		System.out.println("Sobolev..........: " + formatterValue.format(sampleVarianceSobolev) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceSobolev - variance)));
		System.out.println("Equidistributed..: " + formatterValue.format(sampleVarianceEquidistributed) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceEquidistributed - variance)));

		System.out.println("---------------------------------------");

		AbstractRandomVariable pareto1 = new Pareto(1, scale);
		AbstractRandomVariable pareto2 = new Pareto(2, scale);
		AbstractRandomVariable pareto3 = new Pareto(3, scale);
		AbstractRandomVariable pareto10 = new Pareto(10, scale);
		DoubleUnaryOperator cdf1 = (x) -> pareto1.getCumulativeDistributionFunction(x);
		DoubleUnaryOperator cdf2 = (x) -> pareto2.getCumulativeDistributionFunction(x);
		DoubleUnaryOperator cdf3 = (x) -> pareto3.getCumulativeDistributionFunction(x);
		DoubleUnaryOperator cdf10 = (x) -> pareto10.getCumulativeDistributionFunction(x);

		int numberOfTimeSteps = 100;
		Plot2D plot = new Plot2D(0, 10, numberOfTimeSteps,
				Arrays.asList(new Named<DoubleUnaryOperator>("Shape = 1", cdf1),
						new Named<DoubleUnaryOperator>("Shape = 2", cdf2),
						new Named<DoubleUnaryOperator>("Shape = 3", cdf3),
						new Named<DoubleUnaryOperator>("Shape = 10", cdf10)));
		plot.setTitle("Pareto distribution cdf");
		plot.setXAxisLabel("x");
		plot.setYAxisLabel("F(x)");
		plot.setIsLegendVisible(true);
		plot.show();

		DoubleUnaryOperator density1 = (x) -> pareto.getDensityFunction(x);
		DoubleUnaryOperator density2 = (x) -> pareto2.getDensityFunction(x);
		DoubleUnaryOperator density3 = (x) -> pareto3.getDensityFunction(x);
		DoubleUnaryOperator density10 = (x) -> pareto10.getDensityFunction(x);

		Plot2D plot2 = new Plot2D(0.9, 2, numberOfTimeSteps,
				Arrays.asList(new Named<DoubleUnaryOperator>("Shape = 1", density1),
						new Named<DoubleUnaryOperator>("Shape = 2", density2),
						new Named<DoubleUnaryOperator>("Shape = 3", density3),
						new Named<DoubleUnaryOperator>("Shape = 10", density10)));
		plot2.setTitle("Pareto density");
		plot2.setXAxisLabel("x");
		plot2.setYAxisLabel("f(x)");
		plot2.setIsLegendVisible(true);
		plot2.show();

//		System.out.println("_".repeat(80) + "\n");
//
//		System.out.println("Confidence Interval Chebyshev: " + Arrays.toString(confidenceInterval));
//
//		int numberOfMeanComputations = 100;
//
//		System.out.println("_".repeat(80) + "\n");
//
//		System.out.println("Math random: ");
//		System.out.println("The frequence of mean being in the Chebyshev confidence interval is "
//				+ formatterPercentage.format(frequenceOfInterval(pareto, interval, numberOfMeanComputations, level)));
//
//		System.out.println("_".repeat(80) + "\n");
//
//		System.out.println("Mersenne: ");
//		System.out.println("The frequence of p being in the Chebyshev confidence interval is " + formatterPercentage
//				.format(frequenceOfInterval(pareto, mersenne, interval, numberOfMeanComputations, level)));
//
//		System.out.println("_".repeat(80) + "\n");
//
//		System.out.println("Van Der Corput 2: ");
//		System.out.println("The frequence of p being in the Chebyshev confidence interval is "
//				+ formatterPercentage.format(frequenceOfInterval(pareto, vanDerCorput2, interval,
//						numberOfMeanComputations, level)));
//
//		System.out.println("_".repeat(80) + "\n");
//
//		System.out.println("Van Der Corput 3: ");
//		System.out.println("The frequence of p being in the Chebyshev confidence interval is "
//				+ formatterPercentage.format(frequenceOfInterval(pareto, vanDerCorput3, interval,
//						numberOfMeanComputations, level)));
//
//		System.out.println("_".repeat(80) + "\n");
//
//		System.out.println("Equidistributed: ");
//		System.out.println("The frequence of p being in the Chebyshev confidence interval is "
//				+ formatterPercentage
//						.format(frequenceOfIntervalEquidistributed(pareto, interval,
//						numberOfMeanComputations, level)));
//	}
//
//	public static double frequenceOfInterval(AbstractRandomVariable randomVariable, ConfidenceInterval interval,
//			int numberOfMeanComputations, double confidenceLevel) throws InterruptedException, ExecutionException {
//
//		double numberOfTimesInsideTheInterval = 0;
//		double[] confidenceInterval = interval.getConfidenceInterval(numberOfSimulations, confidenceLevel);
//		double lowerBound = confidenceInterval[0];
//		double upperBound = confidenceInterval[1];
//		double sampleMean;
//		for (int i = 0; i < numberOfMeanComputations; i++) {
//			sampleMean = randomVariable.getSampleMeanParallel(numberOfSimulations); // sample mean
//			if (sampleMean > lowerBound && sampleMean < upperBound) {
//				numberOfTimesInsideTheInterval++; // sample mean within the confidence interval
//			}
//		}
//		return numberOfTimesInsideTheInterval / numberOfMeanComputations;
//	}
//
//	public static double frequenceOfInterval(AbstractRandomVariable randomVariable, RandomNumberGenerator1D random,
//			ConfidenceInterval interval, int numberOfMeanComputations, double confidenceLevel)
//			throws InterruptedException, ExecutionException {
//
//		double numberOfTimesInsideTheInterval = 0;
//		double[] confidenceInterval = interval.getConfidenceInterval(numberOfSimulations, confidenceLevel);
//		double lowerBound = confidenceInterval[0];
//		double upperBound = confidenceInterval[1];
//		double sampleMean;
//		for (int i = 0; i < numberOfMeanComputations; i++) {
//			sampleMean = randomVariable.getSampleMeanParallel(numberOfSimulations, random); // sample mean
//			if (sampleMean > lowerBound && sampleMean < upperBound) {
//				numberOfTimesInsideTheInterval++; // sample mean within the confidence interval
//			}
//		}
//		return numberOfTimesInsideTheInterval / numberOfMeanComputations;
//	}
//
//	public static double frequenceOfIntervalEquidistributed(AbstractRandomVariable randomVariable,
//			ConfidenceInterval interval, int numberOfMeanComputations, double confidenceLevel) {
//
//		double numberOfTimesInsideTheInterval = 0;
//		double[] confidenceInterval = interval.getConfidenceInterval(numberOfSimulations, confidenceLevel);
//		double lowerBound = confidenceInterval[0];
//		double upperBound = confidenceInterval[1];
//		double sampleMean;
//		for (int i = 0; i < numberOfMeanComputations; i++) {
//			sampleMean = randomVariable.getSampleMeanEquidistributed(numberOfSimulations); // sample mean
//			if (sampleMean > lowerBound && sampleMean < upperBound) {
//				numberOfTimesInsideTheInterval++; // sample mean within the confidence interval
//			}
//		}
//		return numberOfTimesInsideTheInterval / numberOfMeanComputations;
	}

}
