/**
 * 
 */
package com.lorenzoberti.session03.exception;

/**
 * @author Lorenzo Berti
 *
 */
public class ExceptionExperiment {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		}

		/**
		 * This method computes the approximation of PI by using Monte Carlo
		 * Integration.
		 * 
		 * @param numberOfDrawings
		 * @return
		 */
		private static double piMonteCarlo(int numberOfDrawings) {

			double numberOfPointsInside = 0;

			for (int i = 0; i < numberOfDrawings; i++) {
				double x = 2 * (Math.random() - 0.5);
				double y = 2 * (Math.random() - 0.5);
				if (x * x + y * y < 1) {
					numberOfPointsInside += 1;
				}
			}

			double multiplier = numberOfPointsInside / numberOfDrawings;
			double pi = 4.0 * multiplier;

			return pi;


		}




}
