/**
 * 
 */
package com.lorenzoberti.session09;

import com.lorenzoberti.session07.AbstractRandomVariable;
import com.lorenzoberti.session07.Poisson;

import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import net.finmath.randomnumbers.MersenneTwister;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;

/**
 * @author Lorenzo Berti
 *
 */
public class PoissonIncrement implements IndependentIncrement {

	private double lambda; // intensity
	private int numberOfPaths;
	private TimeDiscretization times;
	private RandomVariable[] increments;

	public PoissonIncrement(double lambda, int numberOfPaths, TimeDiscretization times) {
		super();
		this.lambda = lambda;
		this.numberOfPaths = numberOfPaths;
		this.times = times;
	}

	public PoissonIncrement(double lambda, int numberOfPaths) {
		super();
		this.lambda = lambda;
		this.numberOfPaths = numberOfPaths;
	}

	@Override
	public RandomVariable getIncrement(double s, double t) {
		// TODO Auto-generated method stub
		AbstractRandomVariable poisson = new Poisson(lambda * (t - s));
		double[] values = new double[numberOfPaths];
		for (int i = 0; i < numberOfPaths; i++) {
			values[i] = poisson.generate();
		}

		return new RandomVariableFromDoubleArray(0, values);
	}

	@Override
	public RandomVariable getIncrement(int timeIndex) {
		// TODO Auto-generated method stub
		generateIncrements();

		return increments[timeIndex];
	}

	private void generateIncrements() {

		int numberOfTimeSteps = times.getNumberOfTimeSteps();

		MersenneTwister mersenneTwister = new MersenneTwister();

		double[][] incrementsArray = new double[numberOfTimeSteps][numberOfPaths];

		// Pre-calculate the Poisson Distribution
		Poisson[] poissonDistribution = new Poisson[numberOfTimeSteps];
		for (int timeIndex = 0; timeIndex < numberOfTimeSteps; timeIndex++) {
			poissonDistribution[timeIndex] = new Poisson(times.getTimeStep(timeIndex) * lambda);
		}

		for (int pathIndex = 0; pathIndex < numberOfPaths; pathIndex++) {
			for (int timeIndex = 0; timeIndex < numberOfTimeSteps; timeIndex++) {

				double uniform = mersenneTwister.nextDouble();
				double numberOfJumps = poissonDistribution[timeIndex].generate(uniform);

				incrementsArray[timeIndex][pathIndex] = numberOfJumps;
			}
		}

		increments = new RandomVariable[numberOfTimeSteps];

		for (int timeIndex = 0; timeIndex < numberOfTimeSteps; timeIndex++) {
			increments[timeIndex] = new RandomVariableFromDoubleArray(times.getTime(timeIndex),
					incrementsArray[timeIndex]);
		}

	}

}
