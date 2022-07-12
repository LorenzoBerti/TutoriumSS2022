/**
 * 
 */
package com.lorenzoberti.session09;


import net.finmath.functions.NormalDistribution;
import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import net.finmath.randomnumbers.MersenneTwister;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;

/**
 * This class represents independent Brownian increments, i.e.
 * B(t)-B(s)~N(0,t-s)
 * 
 * @author Lorenzo Berti
 *
 */
public class BrownianMotionIncrement implements IndependentIncrement {

	private int numberOfPaths;
	private int seed;
	private TimeDiscretization times;
	private RandomVariable[] brownianIncrements;

	public BrownianMotionIncrement(int numberOfPaths, TimeDiscretization times, int seed) {
		super();
		this.numberOfPaths = numberOfPaths;
		this.times = times;
		this.seed = seed;
	}

	public BrownianMotionIncrement(int numberOfPaths) {
		super();
		this.numberOfPaths = numberOfPaths;
		this.times = null;
	}

	@Override
	public RandomVariable getIncrement(double s, double t) {

		double[] values = new double[numberOfPaths];
		for (int i = 0; i < numberOfPaths; i++) {
			values[i] = NormalDistribution.inverseCumulativeDistribution(Math.random()) * Math.sqrt(t - s);
		}
		return new RandomVariableFromDoubleArray(0, values);
	}

	@Override
	public RandomVariable getIncrement(int timeIndex) {

		generateIncrements();

		return brownianIncrements[timeIndex];
	}

	private void generateIncrements() {

		final int numberOfTimeSteps = times.getNumberOfTimeSteps();

		final double[][] brownianIncrementsArray = new double[numberOfTimeSteps][numberOfPaths];

		MersenneTwister mersenne = new MersenneTwister(seed);

		for (int pathIndex = 0; pathIndex < numberOfPaths; pathIndex++) {
			for (int timeIndex = 0; timeIndex < numberOfTimeSteps; timeIndex++) {
				brownianIncrementsArray[timeIndex][pathIndex] = NormalDistribution
						.inverseCumulativeDistribution(mersenne.nextDouble()) * Math.sqrt(times.getTimeStep(timeIndex));
			}
		}

		brownianIncrements = new RandomVariable[numberOfTimeSteps];

		for (int timeIndex = 0; timeIndex < numberOfTimeSteps; timeIndex++) {
			brownianIncrements[timeIndex] = new RandomVariableFromDoubleArray(times.getTime(timeIndex),
					brownianIncrementsArray[timeIndex]);
		}
	}
}