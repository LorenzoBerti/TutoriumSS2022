/**
 * 
 */
package com.lorenzoberti.session09;

import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;

/**
 * @author Lorenzo Berti
 *
 */
public class PoissonProcess extends AbstractLevyProcess {

	private double lambda; // intensity of the jump process
	RandomVariable[] poissonProcessPaths;

	public PoissonProcess(double lambda, int numberOfPaths, TimeDiscretization times) {
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
		// TODO Auto-generated method stub
		int numberOfTimes = times.getNumberOfTimes();

		IndependentIncrement poissonIncrement = new PoissonIncrement(lambda, numberOfPaths, times);

		poissonProcessPaths = new RandomVariable[numberOfTimes];

		poissonProcessPaths[0] = new RandomVariableFromDoubleArray(0);

		for (int i = 0; i < numberOfTimes - 1; i++) {

			poissonProcessPaths[i + 1] = poissonProcessPaths[i].add(poissonIncrement.getIncrement(i));
		}

	}

}
