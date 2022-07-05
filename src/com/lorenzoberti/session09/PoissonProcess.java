/**
 * 
 */
package com.lorenzoberti.session09;

import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;

/**
 * This class represents a Poisson process.
 * 
 * @author Lorenzo Berti
 *
 */
public class PoissonProcess extends AbstractLevyProcess {

	private double lambda;
	RandomVariable[] poissonProcessPaths;

	protected PoissonProcess(double lambda, int numberOfPaths, TimeDiscretization times) {
		super(numberOfPaths, times);
		this.lambda = lambda;
	}

	@Override
	public RandomVariable[] getPaths() {

		if (poissonProcessPaths == null) {
		generate();
		}
		return poissonProcessPaths;
	}

	private void generate() {

		final int numberOfTimes = times.getNumberOfTimes(); // numberOfTimes = numberOfTimeSteps + 1

		IndependentIncrement poissonIncrement = new PoissonIncrement(lambda, numberOfPaths, times);

		poissonProcessPaths = new RandomVariable[numberOfTimes];

		poissonProcessPaths[0] = new RandomVariableFromDoubleArray(0);

		for (int i = 0; i < numberOfTimes - 1; i++) {

			poissonProcessPaths[i + 1] = poissonProcessPaths[i].add(poissonIncrement.getIncrement(i));
		}

	}

}
