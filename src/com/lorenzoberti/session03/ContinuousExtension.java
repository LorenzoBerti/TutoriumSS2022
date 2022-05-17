/**
 * 
 */
package com.lorenzoberti.session03;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Lorenzo Berti
 *
 */
public class ContinuousExtension {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		double x1 = 1.0;
		double x2 = Math.PI;

		// Let's make the output a little nicer..
		NumberFormat formatter = new DecimalFormat(" 0.0000;-0.0000");

		System.out.println("The function at x= " + formatter.format(x1) + " equals "
				+ formatter.format(evaluateFunction(x1)) + ".");
		System.out.println("The function at x= " + formatter.format(x2) + " equals "
				+ formatter.format(evaluateFunction(x2)) + ".");
	}

	/**
	 * Calculates the value the function sin(x)/((x+pi)(x-pi)), i.e. extend it
	 * continuously using the try/catch block.
	 *
	 * @param argument at which to evaluate.
	 * @return The value of the function.
	 */
	public static double evaluateFunction(double argument) {

		return 0;

	}

	/**
	 * Calculates the division of the numerator by the denominator and checks that
	 * the result is a regular number (here you can use here the if statement).
	 *
	 * @param numerator
	 * @param denominator
	 * @return The fraction.
	 */
	private static double checkedDivision(double numerator, double denominator) {

		return 0;
	}

}
