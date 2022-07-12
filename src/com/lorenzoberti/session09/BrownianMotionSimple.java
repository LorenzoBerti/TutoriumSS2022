/**
 * 
 */
package com.lorenzoberti.session09;

import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;

/**
 * This class represents a 1-dimensional Brownian motion.
 * 
 * @author Lorenzo Berti
 *
 */
public class BrownianMotionSimple extends AbstractLevyProcess {

	private RandomVariable[] brownianPaths;
	private int seed;

	public BrownianMotionSimple(int numberOfPaths, TimeDiscretization times, int seed) {
		super(numberOfPaths, times);
	}

	@Override
	public RandomVariable[] getPaths() {

		if (brownianPaths == null) {
			generate();
		}
		generate();

		return brownianPaths;
	}

	private void generate() {

		final int numberOfTimes = times.getNumberOfTimes(); // numberOfTimes = numberOfTimeSteps + 1

		IndependentIncrement brownianIncrement = new BrownianMotionIncrement(numberOfPaths, times, seed);

		brownianPaths = new RandomVariable[numberOfTimes];

		brownianPaths[0] = new RandomVariableFromDoubleArray(0);

		for (int i = 0; i < numberOfTimes - 1; i++) {

			// W(t+1) = W(t+1)-W(t) + W(t)
			brownianPaths[i + 1] = brownianPaths[i].add(brownianIncrement.getIncrement(i));
		}

	}
}