/**
 * 
 */
package com.lorenzoberti.session09;

import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationFromArray;

/**
 * @author Lorenzo Berti
 *
 */
public class IncrementProcessTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int numberOfPaths = 1;
		double initialTime = 0.0;
		double finalTime = 10.0;
		double timeStep = 0.1;
		int numberOfTimeSteps = (int) (finalTime / timeStep);
		TimeDiscretization times = new TimeDiscretizationFromArray(initialTime, numberOfTimeSteps, timeStep);

		double lambda = 2.0;

		IndependentIncrement poissonIncrement = new PoissonIncrement(lambda, numberOfPaths, times);

		System.out.println("index\t \tPoisson increments");
		for (int i = 0; i < finalTime; i++) {

			System.out.format("%d\t\t%f\n", i, 
					poissonIncrement.getIncrement(i).getAverage());
		}


		AbstractLevyProcess poissonProcess = new PoissonProcess(lambda, numberOfPaths, times);
		poissonProcess.printSpecificPath(0);
		for (int i = 0; i < numberOfTimeSteps; i++) {
			System.out.println("Between time 0 and " + i + "\tNumber of events: "
					+ poissonProcess.getSpecificRealizationAtGivenTimeIndex(0, i));
		}

	}

}