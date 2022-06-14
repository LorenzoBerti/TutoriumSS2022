/**
 * 
 */
package com.lorenzoberti.session07;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

import net.finmath.randomnumbers.MersenneTwister;
import net.finmath.randomnumbers.RandomNumberGenerator1D;
import net.finmath.randomnumbers.VanDerCorputSequence;

/**
 * @author Lorenzo Berti
 *
 */
public class MonteCarloConfidenceInterval {

	// private static DecimalFormat formatter = new DecimalFormat("
	// 0.000E00;-0.000E00");

	static DecimalFormat formatterValue = new DecimalFormat("#0.00000");

	static DecimalFormat formatterPercentage = new DecimalFormat("#0.00%");

	static double level = 0.1;
	static int numberOfSimulations = 1000000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {


//		DoubleUnaryOperator function = x -> Math.cos(x);
//		double lowerBoundX = 0;
//		double upperBoundX = 100;
//		double analytic = 0;

		DoubleUnaryOperator function = x -> Math.exp(x);
		double lowerBoundX = 9;
		double upperBoundX = 10;
		double analytic = Math.exp(upperBoundX);

//		DoubleUnaryOperator function = x -> 2; // max variance!
//		double lowerBoundX = 0;
//		double upperBoundX = 2;
//		double analytic = 4;

		double range = upperBoundX - lowerBoundX;

		System.out.println("Analytic: " + formatterValue.format(analytic));
		RandomNumberGenerator1D mersenne = new MersenneTwister();
		RandomNumberGenerator1D vanDerCorput2 = new VanDerCorputSequence(2);
		RandomNumberGenerator1D vanDerCorput3 = new VanDerCorputSequence(3);

		System.out.println("----------------------------");

		double mersenneMonteCarlo = MonteCarloIntegration(function, mersenne, lowerBoundX, upperBoundX,
				numberOfSimulations);
		double[] mersenneConfidenceInterval = getConfidenceInterval(function, mersenne, lowerBoundX, upperBoundX,
				numberOfSimulations, mersenneMonteCarlo, level);
		System.out.println("Mersenne: " + formatterValue.format(mersenneMonteCarlo) + "\tError: "
				+ formatterValue.format(Math.abs(mersenneMonteCarlo - analytic)) + "\nConfidence interval: "
				+ Arrays.toString(mersenneConfidenceInterval));

		System.out.println("----------------------------");

		double vanDerCorputMonteCarlo2 = MonteCarloIntegration(function, vanDerCorput2, lowerBoundX, upperBoundX,
				numberOfSimulations);
		double[] vanDerCorputConfidenceInterval2 = getConfidenceInterval(function, vanDerCorput2, lowerBoundX,
				upperBoundX, numberOfSimulations, mersenneMonteCarlo, level);
		System.out.println("Van Der Corput base 2: " + formatterValue.format(vanDerCorputMonteCarlo2) + "\tError: "
				+ formatterValue.format(Math.abs(vanDerCorputMonteCarlo2 - analytic)) + "\nConfidence interval: "
				+ Arrays.toString(vanDerCorputConfidenceInterval2));

		System.out.println("----------------------------");

		double vanDerCorputMonteCarlo3 = MonteCarloIntegration(function, vanDerCorput3, lowerBoundX, upperBoundX,
				numberOfSimulations);
		double[] vanDerCorputConfidenceInterval3 = getConfidenceInterval(function, vanDerCorput3, lowerBoundX,
				upperBoundX, numberOfSimulations, mersenneMonteCarlo, level);
		System.out.println("Van Der Corput base 3: " + formatterValue.format(vanDerCorputMonteCarlo3) + "\tError: "
				+ formatterValue.format(Math.abs(vanDerCorputMonteCarlo3 - analytic)) + "\nConfidence interval: "
				+ Arrays.toString(vanDerCorputConfidenceInterval3));

		System.out.println("----------------------------");

		// Equidistributed sequence and Math.random()
		double sumEquidistributed = 0;
		double sampleVarianceEquidistributed = 0;
		double sumMathRandom = 0;
		double sampleVarianceMathRandom = 0;

		for (int i = 0; i < numberOfSimulations; i++) {
			sumEquidistributed += function.applyAsDouble(lowerBoundX + range * i / numberOfSimulations);
			sumMathRandom += function.applyAsDouble(lowerBoundX + range * Math.random());
		}

		double equidistributedMonteCarlo = sumEquidistributed / numberOfSimulations * range;
		double mathRandomMonteCarlo = sumMathRandom / numberOfSimulations * range;

		for (int i = 0; i < numberOfSimulations; i++) {
			sampleVarianceEquidistributed += Math
					.pow(function.applyAsDouble(lowerBoundX + range * i / numberOfSimulations)
							- equidistributedMonteCarlo, 2)
					/ numberOfSimulations;
			sampleVarianceMathRandom += Math
					.pow(function.applyAsDouble(lowerBoundX + range * Math.random()) - equidistributedMonteCarlo, 2)
					/ numberOfSimulations;
		}


		double varianceEquidistributed = sampleVarianceEquidistributed * Math.pow(range, 2) / numberOfSimulations;
		double varianceMathRandom = sampleVarianceMathRandom * Math.pow(range, 2) / numberOfSimulations;

		double standardDeviationEquidistributed = Math.sqrt(varianceEquidistributed);
		double standardDeviationMathRandom = Math.sqrt(varianceMathRandom);
		
		double[] confidenceIntervalEquidistributed = new double[] {
				equidistributedMonteCarlo - standardDeviationEquidistributed / Math.sqrt(numberOfSimulations * level),
				equidistributedMonteCarlo + standardDeviationEquidistributed / Math.sqrt(numberOfSimulations * level) };

		double[] confidenceIntervalMathRandom = new double[] {
				mathRandomMonteCarlo - standardDeviationMathRandom / Math.sqrt(numberOfSimulations * level),
				mathRandomMonteCarlo + standardDeviationMathRandom / Math.sqrt(numberOfSimulations * level) };

		System.out.println("Variance: "
				+ varianceEquidistributed);
		System.out.println("Equidistributed: " + formatterValue.format(equidistributedMonteCarlo) + "\tError: "
				+ formatterValue.format(Math.abs(equidistributedMonteCarlo - analytic)) + "\nConfidence interval: "
				+ Arrays.toString(confidenceIntervalEquidistributed));

		System.out.println("----------------------------");

		System.out.println(
				"Variance: " + varianceMathRandom);
		System.out.println("Math random: " + formatterValue.format(mathRandomMonteCarlo) + "\tError: "
				+ formatterValue.format(Math.abs(mathRandomMonteCarlo - analytic)) + "\nConfidence interval: "
				+ Arrays.toString(confidenceIntervalMathRandom));

	}

	private static double MonteCarloIntegration(DoubleUnaryOperator function, RandomNumberGenerator1D random,
			double lowerBoundX, double upperBoundX, int numberOfSimulations) {

		double range = upperBoundX - lowerBoundX;
		double sum = 0;
		for (int i = 0; i < numberOfSimulations; i++) {
			sum += function.applyAsDouble(lowerBoundX + range * random.nextDouble());
		}
		return sum / numberOfSimulations * range;

	}

	private static double[] getConfidenceInterval(DoubleUnaryOperator function, RandomNumberGenerator1D random,
			double lowerBoundX, double upperBoundX, int numberOfSimulations, double mean, double level) {

		double range = upperBoundX - lowerBoundX;
		double sampleVariance = 0;

		for (int i = 0; i < numberOfSimulations; i++) {
			sampleVariance += Math.pow(function.applyAsDouble(lowerBoundX + range * random.nextDouble()) - mean, 2)
					/ numberOfSimulations;
		}

		double variance = sampleVariance / numberOfSimulations * Math.pow(range, 2);
		System.out.println("Variance: " + variance);
		double standardDeviation = Math.sqrt(variance);
		double upperBound = mean + standardDeviation / Math.sqrt(numberOfSimulations * (level));
		double lowerBound = mean - standardDeviation / Math.sqrt(numberOfSimulations * (level));

		return new double[] { lowerBound, upperBound };
	}


}
