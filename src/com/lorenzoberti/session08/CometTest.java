/**
 * 
 */
package com.lorenzoberti.session08;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

import com.andreamazzon.handout3.HistogramData;

import net.finmath.plots.Named;
import net.finmath.plots.Plot2D;

/**
 * @author Lorenzo Berti
 *
 */
public class CometTest {

	private final static DecimalFormat formatterDouble = new DecimalFormat("0.00000");
	static int numberOfSimulations = 10000;
	static int maxOrbit = (int) Math.pow(10, 5);
	static double level = 0.05;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		double x0 = -1;
		double sigma = 1;
		LongLivedComet comet = new LongLivedComet(x0, sigma);
		double sampleMean = comet.getSampleMeanCometLifetime(numberOfSimulations);
		System.out.println("Mean....: " + sampleMean);
		double sampleMeanMaxOrbit = comet.getSampleMeanCometLifetime(numberOfSimulations, maxOrbit);
		System.out.println("Mean............: " + sampleMeanMaxOrbit);
		double stdDeviationMaxOrbit = comet.getStdDeviationCometLifetime(numberOfSimulations, maxOrbit);
		System.out.println("Std deviation...: " + stdDeviationMaxOrbit);

		System.out.println("------------------------------------------");

		DoubleUnaryOperator function = (t) -> Math.pow(t, -3 / 2);

//		DoubleUnaryOperator cdf = (t) -> 1 - comet.empiricalDistributionCometLifetime(numberOfSimulations, t); // survival
//																												// distribution
//
//		int numberOfTimeSteps = 10;
//		DecimalFormat formatterValuePlot = new DecimalFormat("#0.0000");
//
//		Plot2D plot = new Plot2D(0.1, 50, numberOfTimeSteps, Arrays.asList(
//				new Named<DoubleUnaryOperator>("t^(-3/2)", function), new Named<DoubleUnaryOperator>("Comet", cdf)));
//		plot.setTitle("Comet lifetime distribution N = " + numberOfSimulations);
//		plot.setXAxisLabel("t");
//		plot.setYAxisLabel("F(t)");
//		plot.setIsLegendVisible(true);
//		plot.show();

		int numberOfTimeSteps = 10;
		DecimalFormat formatterValuePlot = new DecimalFormat("#0.0000");

		DoubleUnaryOperator cdfMaxOrbit = (t) -> 1
				- comet.empiricalDistributionCometLifetimeMaxOrbit(numberOfSimulations, t, maxOrbit); // survival
																										// distribution

		Plot2D plot2 = new Plot2D(100, 1000, numberOfTimeSteps,
				Arrays.asList(new Named<DoubleUnaryOperator>("t^(-3/2)", function),
						new Named<DoubleUnaryOperator>("Comet", cdfMaxOrbit)));
		plot2.setTitle("Comet lifetime distribution with max orbit " + maxOrbit);
		plot2.setXAxisLabel("t");
		plot2.setYAxisLabel("F(t)");
		plot2.setIsLegendVisible(true);
		plot2.setYAxisNumberFormat(formatterValuePlot);
		plot2.show();

		for (int i = 0; i < 5; i++) {
			double maxOrbit = Math.pow(10, i);
			double[] confidenceInterval = comet.chebyshevIntervalMaxOrbit(numberOfSimulations, level, maxOrbit);
			System.out.println("Confidence interval for the probability that a comet will make more than " + maxOrbit
					+ " orbits\n" + Arrays.toString(confidenceInterval));
			System.out.println("------------------------------------------");
		}

//		// Histogram of orbits survived after the max number of orbits
//		int numberOfBins = 10;
//
//		HistogramData histogramData = comet.getHistogram(numberOfBins, numberOfSimulations, maxOrbit);
//
//		// getters of the container class HistogramData
//		int[] histogram = histogramData.getHistogram();
//		double min = histogramData.getMinBin();
//		double max = histogramData.getMaxBin();
//
//		System.out.println("Results with " + numberOfSimulations + " simulations:");
//
//		System.out.println();
//
//		System.out.println("Min orbit: " + min);
//		System.out.println("Max orbit: " + max);
//
//		System.out.println();
//
//		System.out.println("Histogram:");
//
//		System.out.println();
//
//		double binSize = (max - min) / numberOfBins;
//
//		for (int i = 1; i < histogram.length; i++) {
//			System.out.println("The number of orbits has been " + histogram[i] + " times between "
//					+ formatterDouble.format((i - 1) * binSize + min) + " included and "
//					+ formatterDouble.format(i * binSize + min) + " excluded");
//		}

		// Histogram of number of orbits of comets that leave the solar system before
		// completing the max number of orbits
		int numberOfBins = 10;

		HistogramData histogramData = comet.getHistogramCometsLeaveSolarSystem(numberOfBins, numberOfSimulations,
				maxOrbit);

		// getters of the container class HistogramData
		int[] histogram = histogramData.getHistogram();
		double min = histogramData.getMinBin();
		double max = histogramData.getMaxBin();

		System.out.println("Results with " + numberOfSimulations + " simulations:");

		System.out.println();

		System.out.println("Min orbit: " + min);
		System.out.println("Max orbit: " + max);

		System.out.println();

		System.out.println("Histogram:");

		System.out.println();

		double binSize = (max - min) / numberOfBins;

		for (int i = 1; i < histogram.length; i++) {
			System.out.println("The number of orbits has been " + histogram[i] + " times between "
					+ formatterDouble.format((i - 1) * binSize + min) + " included and "
					+ formatterDouble.format(i * binSize + min) + " excluded");
		}

	}

}