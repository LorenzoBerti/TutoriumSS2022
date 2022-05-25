/**
 * 
 */
package com.lorenzoberti.session04;

/**
 * Here you have to compute the value of Pi with Monte Carlo method and the
 * value of a digital option (handout 2 exercise 2) by using stream. Hint: for
 * the option price you can use
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
		// TODO Auto-generated method stub


	}


	/**
	 * This method return the approximation of Pi by using Monte Carlo method.
	 * 
	 * @param numberOfDrawings
	 * @return approximation of Pi
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

		return 4 * numberOfPointsInside / numberOfDrawings;

	}

}
