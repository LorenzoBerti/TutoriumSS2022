/**
 * 
 */
package com.lorenzoberti.session09;

import java.util.function.DoubleUnaryOperator;

import net.finmath.plots.Plot2D;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;

/**
 * @author Lorenzo Berti
 *
 */
public abstract class AbstractLevyProcess implements LevyProcess {

	int numberOfPaths;
	TimeDiscretization times;

	protected AbstractLevyProcess(int numberOfPaths, TimeDiscretization times) {
		this.numberOfPaths = numberOfPaths;
		this.times = times;
	}

	@Override
	public RandomVariable getProcessAtGivenTimeIndex(int timeIndex) {

		return getPaths()[timeIndex];
	}

	@Override
	public RandomVariable getProcessAtGivenTime(double time) {

		return getPaths()[times.getTimeIndex(time)];
	}

	@Override
	public double[] getSpecificPath(int pathIndex) {

		double[] path = new double[times.getNumberOfTimes() + 1];
		path[0] = 0;
		for (int i = 0; i < times.getNumberOfTimes(); i++) {
			path[i] = getPaths()[i].get(pathIndex);
		}
		return path;

	}

	@Override
	public double getSpecificRealizationAtGivenTimeIndex(int pathIndex, int timeIndex) {

		return getSpecificPath(pathIndex)[timeIndex];
	}

	@Override
	public double getSpecificRealizationAtGivenTime(int pathIndex, double time) {

		return getSpecificPath(pathIndex)[times.getTimeIndex(time)];
	}

	@Override
	public void printSpecificPath(int pathIndex) {

		DoubleUnaryOperator trajectory = t -> {
			return getSpecificRealizationAtGivenTime(pathIndex, (int) t);
		};

		Plot2D plot = new Plot2D(0, times.getNumberOfTimes(), times.getNumberOfTimes(), trajectory);
		plot.setTitle("Simulated process path");
		plot.setXAxisLabel("Time");
		plot.setYAxisLabel("Process");
		plot.show();
	}

}
