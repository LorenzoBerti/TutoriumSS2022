/**
 * 
 */
package com.lorenzoberti.session04;

import java.util.stream.IntStream;

import com.andreamazzon.exercises.simulators.BinomialModelSimulator;
import com.andreamazzon.handout2.DigitalOption;
import com.andreamazzon.handout2.EuropeanTypeOptionMonteCarlo;
import com.lorenzoberti.session03.exception.DivideByZeroException;
import com.lorenzoberti.session03.exception.Division;

/**
 * Here you have to compute Pi with Monte Carlo method by using Stream (look at
 * the method piMonteCarlo in the class ExceptionExperiment in
 * com.lorenzoberti.session03.exception). Try to compute the value of the
 * digital option by using stream (handout 2 exercise 2). Hint: for the option
 * price you can use
 * com.andreamazzon.exercises.simulators.BinomialModelSimulator
 * 
 * @author Lorenzo Berti
 *
 */
public class StreamExercise {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Model parameters
		double initialValue = 100;
		double increaseIfUp = 1.5;
		double decreaseIfDown = 0.5;
		int lastTime = 7;

		// Note what happen when you increase the number of Simulations (10^8): the
		// class
		// method fails because of memory space, but the stream not!
		int numberOfSimulations = 100000;
		int specifiedSeed = 1897;
		double interestRate = 0;

		// Option parameters
		int maturity = lastTime;
		double strike = 100;


		BinomialModelSimulator binomialModel = new BinomialModelSimulator(initialValue, increaseIfUp, decreaseIfDown,
				specifiedSeed, lastTime, numberOfSimulations);

		long timeStart = System.currentTimeMillis();
		double optionPriceStreamClass = (double) IntStream.range(0, numberOfSimulations)
				.mapToDouble(i -> binomialModel.getRealizations()[maturity][i]).filter(i -> (i - strike) > 0).count()
				/ numberOfSimulations;
		long timeEnd = System.currentTimeMillis();
		System.out.println("Option price with stream and class      : " + optionPriceStreamClass + " \t("
				+ (timeEnd - timeStart) / 1000.0 + " sec.)");

		long timeStart2 = System.currentTimeMillis();
		EuropeanTypeOptionMonteCarlo digitalOption = new DigitalOption(maturity, strike);
		double optionPriceClass = digitalOption.getPrice(binomialModel);

		long timeEnd2 = System.currentTimeMillis();
		System.out.println("Option price with class                 : " + optionPriceClass + " \t("
				+ (timeEnd2 - timeStart2) / 1000.0 + " sec.)");

		long timeStart3 = System.currentTimeMillis();
		double optionPriceStreamMethod = (double) IntStream.range(0, numberOfSimulations)
				.mapToDouble(i -> binomialModelPath(initialValue, increaseIfUp, decreaseIfDown, interestRate, lastTime))
				.filter(i -> (i - strike) > 0).count() / numberOfSimulations;
		long timeEnd3 = System.currentTimeMillis();
		System.out.println("Option price with with stream and method: " + optionPriceStreamMethod + " \t("
				+ (timeEnd3 - timeStart3) / 1000.0 + " sec.)");

		long timeStart4 = System.currentTimeMillis();
		double optionPriceStream = (double) IntStream.range(0, numberOfSimulations).mapToDouble(x -> {
					double lastValue = initialValue;
					double riskNeutralProbabilityUp = (1 + interestRate - decreaseIfDown)
							/ (increaseIfUp - decreaseIfDown);
			for (int i = 0; i < lastTime; i++) {
				if (Math.random() < riskNeutralProbabilityUp) {
							lastValue = lastValue * increaseIfUp;
						} else {
							lastValue = lastValue * decreaseIfDown;
						}
					}

					return lastValue;
				}).filter(i -> (i - strike) > 0).count() / numberOfSimulations;

		long timeEnd4 = System.currentTimeMillis();
		System.out.println("Option price with with stream           : " + optionPriceStream + " \t("
				+ (timeEnd4 - timeStart4) / 1000.0 + " sec.)");

		System.out.println("----------------------------------------------");

		long timeStart5 = System.currentTimeMillis();
		double monteCarloPiStream = (double) IntStream.range(0, numberOfSimulations)
				.mapToDouble(i -> Math.pow(2 * (Math.random() - 0.5), 2) + Math.pow(2 * (Math.random() - 0.5), 2))
				.filter(i -> i < 1).count() / numberOfSimulations * 4;
		long timeEnd5 = System.currentTimeMillis();
		System.out.println("Monte Carlo Pi with stream: " + monteCarloPiStream + "\t Error: "
				+ Math.abs(monteCarloPiStream - Math.PI) + " \t Time: " + (timeEnd5 - timeStart5) / 1000.0 + " sec.");

		long timeStart6 = System.currentTimeMillis();
		double monteCarloPiMethod = piMonteCarlo(numberOfSimulations);
		long timeEnd6 = System.currentTimeMillis();
		System.out.println("Monte Carlo Pi with method: " + monteCarloPiMethod + "\t Error: "
				+ Math.abs(monteCarloPiMethod - Math.PI) + " \t Time: " + (timeEnd6 - timeStart6) / 1000.0 + " sec.");



	}

	private static double piMonteCarlo(int numberOfDrawings) {

		double numberOfPointsInside = 0;

		for (int i = 0; i < numberOfDrawings; i++) {
			double x = 2 * (Math.random() - 0.5);
			double y = 2 * (Math.random() - 0.5);
			if (x * x + y * y < 1) {
				numberOfPointsInside += 1;
			}
		}

		double pi = 0;

		try {

			double multiplier = Division.division(numberOfPointsInside, numberOfDrawings);
			pi = 4.0 * multiplier;

		} catch (DivideByZeroException e) {

			e.printExceptionMessage();
		}

		return pi;

	}

	private static double binomialModelPath(double initialValue, double increaseIfUp, double decreaseIfDown,
			double interestRate, int lastTime) {

		double lastValue = initialValue;
		double riskNeutralProbabilityUp = (1 + interestRate - decreaseIfDown) / (increaseIfUp - decreaseIfDown);

		for (int i = 0; i < lastTime; i++) {
			double randomNumber = Math.random();
			if (randomNumber < riskNeutralProbabilityUp) {
				lastValue = lastValue * increaseIfUp;
			} else {
				lastValue = lastValue * decreaseIfDown;
			}
		}

		return lastValue;

	}


}
