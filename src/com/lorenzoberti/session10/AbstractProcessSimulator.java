/**
 * 
 */
package com.lorenzoberti.session10;

import java.util.function.DoubleUnaryOperator;

import com.lorenzoberti.session09.BrownianMotionIncrement;

import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import net.finmath.plots.Plot2D;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;

/**
 * @author Lorenzo Berti
 *
 */
public abstract class AbstractProcessSimulator implements ProcessSimulator {

	private double initialValue;
	private int numberOfPaths;
	private TimeDiscretization times;
	private int seed;

	private BrownianMotionIncrement brownianIncrement;
	private RandomVariable[] paths;// not yet initialized: default value is null

	protected abstract RandomVariable getDrift(RandomVariable lastRealization, int timeIndex);

	protected abstract RandomVariable getDiffusion(RandomVariable lastRealization, int timeIndex);

	protected DoubleUnaryOperator transform;
	protected DoubleUnaryOperator inverseTransform;// log for log Euler

	protected AbstractProcessSimulator(int numberOfPaths, TimeDiscretization times, double initialValue, int seed) {
		this.numberOfPaths = numberOfPaths;
		this.times = times;
		this.initialValue = initialValue;
		this.seed = seed;
		// this.brownianIncrement = new BrownianMotionIncrement(numberOfPaths, times);
	}

	@Override
	public RandomVariable[] getAllPaths() {
		if (paths == null) {
			generate();
		}
		/*
		 * NOTE: here we have to return a clone of our array object. If we return the
		 * object itself, it might be accessed and modified from the outside.
		 */
		return paths.clone();
	}

	private void generate() {

		final int numberOfTimes = times.getNumberOfTimes();

		brownianIncrement = new BrownianMotionIncrement(numberOfPaths, times, seed);

		RandomVariable processDrift;
		RandomVariable processDiffusion;

		paths = new RandomVariable[numberOfTimes];// one random variable every time

		paths[0] = new RandomVariableFromDoubleArray(times.getTime(0), initialValue);

		for (int timeIndex = 1; timeIndex < times.getNumberOfTimes(); timeIndex++) {

			RandomVariable inverseOfLastSimulation = paths[timeIndex - 1].apply(inverseTransform);
			processDrift = getDrift(inverseOfLastSimulation, timeIndex);
			processDiffusion = getDiffusion(inverseOfLastSimulation, timeIndex);
			RandomVariable simulatedInverseTransform = inverseOfLastSimulation.add(processDrift).add(processDiffusion);
			// ..and then we transform back
			paths[timeIndex] = simulatedInverseTransform.apply(transform);
		}
	}

	/**
	 * It returns the Brownian motion increments driving the process
	 *
	 * @return the Brownian motion driving the process, as a BrownianMotion object
	 */
	public BrownianMotionIncrement getStochasticDriver() {

		return brownianIncrement;
	}

	@Override
	public double getInitialValue() {

		return initialValue;
	}

	@Override
	public TimeDiscretization getTimeDiscretization() {

		return times;
	}

	@Override
	public int getNumberOfPaths() {

		return numberOfPaths;
	}

	@Override
	public RandomVariable getProcessAtGivenTimeIndex(int timeIndex) {

		return getAllPaths()[timeIndex];

	}

	@Override
	public RandomVariable getProcessAtGivenTime(double time) {

		return getProcessAtGivenTimeIndex(times.getTimeIndex(time));

	}

	@Override
	public double[] getSpecificPath(int pathIndex) {

		double[] path = new double[times.getNumberOfTimes() + 1];
		path[0] = 0;
		for (int i = 0; i < times.getNumberOfTimeSteps(); i++) {

			path[i] = getAllPaths()[i].get(pathIndex);

		}

		return path;
	}

	@Override
	public double getSpecificValueOfSpecificPath(int pathIndex, int timeIndex) {

		return getSpecificPath(pathIndex)[timeIndex];
	}

	@Override
	public void printPath(int pathIndex) {

		DoubleUnaryOperator trajectory = t -> {
			return getSpecificValueOfSpecificPath(pathIndex, (int) t);
		};

		Plot2D plot = new Plot2D(0, times.getNumberOfTimes(), times.getNumberOfTimes(), trajectory);
		plot.setTitle(getName() + " path");
		plot.setXAxisLabel("Time");
		plot.setYAxisLabel("Process");
		plot.show();

	}

}