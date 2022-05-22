/**
 * 
 */
package com.lorenzoberti.session03.exception;

/**
 * @author Lorenzo Berti
 *
 */
public class ExceptionExperiment {
	
	static int numberOfDrawings = 10000;

	static int simulation = 5;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		int number = numberOfDrawings;

		for (int i = 0; i < simulation; i++) {

			number = number / 10;
			double approximationOfPi = piMonteCarlo(number);
			System.out.println(
					"Approximation: " + approximationOfPi + "\t Error: " + Math.abs(approximationOfPi - Math.PI));

		}



		}

		/**
		 * This method computes the approximation of PI by using Monte Carlo
		 * Integration.
		 * 
		 * @param numberOfDrawings
		 * @return
		 * @throws Exception
		 */

		private static double piMonteCarlo(int numberOfDrawings) throws Exception {

//			if (numberOfDrawings == 0) {
//				throw new Exception("Error: the numberOfDrawings cannot be zero!");
//			}

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

//			double multiplier = numberOfPointsInside / numberOfDrawings;
//			double pi = 4.0 * multiplier;

//			if (Double.isNaN(pi)) {
//				throw new Exception("Error: the result is NaN. Something went wrong!");
//			}

			return pi;

		}




}
