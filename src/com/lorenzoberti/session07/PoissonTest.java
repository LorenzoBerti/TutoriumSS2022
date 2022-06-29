/**
 * 
 */
package com.lorenzoberti.session07;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.function.DoubleUnaryOperator;

import net.finmath.plots.Named;
import net.finmath.plots.Plot2D;
import net.finmath.randomnumbers.MersenneTwister;
import net.finmath.randomnumbers.RandomNumberGenerator1D;
import net.finmath.randomnumbers.VanDerCorputSequence;

/**
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

		AbstractRandomVariable poisson = new Poisson(lambda);
		double mean = poisson.getAnalyticMean();
		double variance = poisson.getAnalyticVariance();

		RandomNumberGenerator1D mersenne = new MersenneTwister();
		RandomNumberGenerator1D vanDerCorput2 = new VanDerCorputSequence(2);
		RandomNumberGenerator1D vanDerCorput3 = new VanDerCorputSequence(3);

		double sampleMeanRandom = poisson.getSampleMean(numberOfSimulations);
		double sampleMeanMersenne = poisson.getSampleMean(numberOfSimulations, mersenne);
		double sampleMeanCorput2 = poisson.getSampleMean(numberOfSimulations, vanDerCorput2);
		double sampleMeanCorput3 = poisson.getSampleMean(numberOfSimulations, vanDerCorput3);
		double sampleMeanEquidistributed = poisson.getSampleMeanEquidistributed(numberOfSimulations);

		double sampleVarianceRandom = poisson.getSampleVariance(numberOfSimulations);
		double sampleVarianceMersenne = poisson.getSampleVariance(numberOfSimulations, mersenne);
		double sampleVarianceCorput2 = poisson.getSampleVariance(numberOfSimulations, vanDerCorput2);
		double sampleVarianceCorput3 = poisson.getSampleVariance(numberOfSimulations, vanDerCorput3);
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
		System.out.println("Equidistributed..: " + formatterValue.format(sampleVarianceEquidistributed) + "\tError: "
				+ formatterValue.format(Math.abs(sampleVarianceEquidistributed - variance)));

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

		// Plot the density
		DecimalFormat formatterValuePlot = new DecimalFormat("#0.0000");
		int evaluationPoints = 20;

		Plot2D plotDensity = new Plot2D(0, evaluationPoints, evaluationPoints + 1,
				Arrays.asList(new Named<DoubleUnaryOperator>("lambda = 1", densityLambda(1)),
						new Named<DoubleUnaryOperator>("lambda = 2", densityLambda(2)),
						new Named<DoubleUnaryOperator>("lambda = 3", densityLambda(3)),
						new Named<DoubleUnaryOperator>("lambda = 10", densityLambda(10))));
		plotDensity.setTitle("Probability density function");
		plotDensity.setXAxisLabel("k");
		plotDensity.setYAxisLabel("Density");
		plotDensity.setYAxisNumberFormat(formatterValuePlot);
		plotDensity.show();

		// Plot the error mean

		int numberOfTimeSteps = 10000;

		Plot2D plotMathRandom = new Plot2D(0, numberOfTimeSteps, numberOfTimeSteps + 1, plotError(poisson));
		plotMathRandom.setTitle("Error Math Random");
		plotMathRandom.setXAxisLabel("NumberOfSimulations");
		plotMathRandom.setYAxisLabel("Error");
		plotMathRandom.setYAxisNumberFormat(formatterValuePlot);
		plotMathRandom.show();

		Plot2D plotMersenne = new Plot2D(0, numberOfTimeSteps, numberOfTimeSteps + 1, plotError(poisson, mersenne));
		plotMersenne.setTitle("Error Mersenne");
		plotMersenne.setXAxisLabel("NumberOfSimulations");
		plotMersenne.setYAxisLabel("Error");
		plotMersenne.setYAxisNumberFormat(formatterValuePlot);
		plotMersenne.show();

		Plot2D plotVanDerCorput2 = new Plot2D(0, numberOfTimeSteps, numberOfTimeSteps + 1,
				plotError(poisson, vanDerCorput2));
		plotVanDerCorput2.setTitle("Error Van Der Corput 2");
		plotVanDerCorput2.setXAxisLabel("NumberOfSimulations");
		plotVanDerCorput2.setYAxisLabel("Error");
		plotVanDerCorput2.setYAxisNumberFormat(formatterValuePlot);
		plotVanDerCorput2.show();

		Plot2D plotVanDerCorput3 = new Plot2D(0, numberOfTimeSteps, numberOfTimeSteps + 1,
				plotError(poisson, vanDerCorput3));
		plotVanDerCorput3.setTitle("Error Van Der Corput 3");
		plotVanDerCorput3.setXAxisLabel("NumberOfSimulations");
		plotVanDerCorput3.setYAxisLabel("Error");
		plotVanDerCorput3.setYAxisNumberFormat(formatterValuePlot);
		plotVanDerCorput3.show();

	}

	static DoubleUnaryOperator plotError(AbstractRandomVariable randomVariable, RandomNumberGenerator1D random) {

		double mean = randomVariable.getAnalyticMean();
		DoubleUnaryOperator errorFunction = n -> {
			try {
				return Math.abs(randomVariable.getSampleMeanParallel((int) n, random) - mean);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return 0;
		};
		return errorFunction;
	}

	static DoubleUnaryOperator plotError(AbstractRandomVariable randomVariable) {

		double mean = randomVariable.getAnalyticMean();
		DoubleUnaryOperator errorFunction = n -> {
			try {
				return Math.abs(randomVariable.getSampleMeanParallel((int) n) - mean);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return 0;
		};
		return errorFunction;
	}

	static DoubleUnaryOperator densityLambda(double lambda) {

		AbstractRandomVariable poissonLambda = new Poisson(lambda);
		DoubleUnaryOperator density = (n) -> poissonLambda.getDensityFunction((int) n);
		return density;
	}
}