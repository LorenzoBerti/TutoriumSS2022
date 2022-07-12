/**
 * 
 */
package com.lorenzoberti.session10;

import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;

/**
 * @author Lorenzo Berti
 *
 */
public interface ProcessSimulator {

	/**
	 * This method returns the initial value of the stochastic process.
	 * 
	 * @return initial value
	 */
	public double getInitialValue();

	/**
	 * This method returns the time discretization used to discretize the stochastic
	 * process.
	 * 
	 * @return the time discretization
	 */
	public TimeDiscretization getTimeDiscretization();

	/**
	 * This method returns the number of paths of the stochastic process simulation.
	 * 
	 * @return the number of paths
	 */
	public int getNumberOfPaths();

	/**
	 * This method returns the name of the simulated process.
	 * 
	 * @return name of the process
	 */
	public String getName();

	/**
	 * This method returns an object of type RandomVariable representing the
	 * simulation of the stochastic process at the given time index.
	 * 
	 * @param timeIndex
	 * @return the stochastic process at the given time index
	 */
	public RandomVariable getProcessAtGivenTimeIndex(int timeIndex);

	/**
	 * This method returns an object of type RandomVariable representing the
	 * simulation of the stochastic process at the given time.
	 * 
	 * @param time
	 * @return the stochastic process at the given time
	 */
	public RandomVariable getProcessAtGivenTime(double time);

	/**
	 * This method returns all the paths of our simulated stochastic process as an
	 * array of objects of type RandomVariable.
	 * 
	 * @return the whole paths of the simulated stochastic process
	 */
	public RandomVariable[] getAllPaths();

	/**
	 * This method returns an array of double representing the path of the simulated
	 * stochastic process of the given path index.
	 * 
	 * @param pathIndex
	 * @return array of double representing the given path of the stochastic process
	 */
	public double[] getSpecificPath(int pathIndex);

	/**
	 * This method returns an object of type double representing the specific value
	 * of the given path at the given time index of the simulated stochastic
	 * process.
	 * 
	 * @param pathIndex
	 * @param timeIndex
	 * @return the value of the given path of stochastic process at the given time
	 *         index
	 */
	public double getSpecificValueOfSpecificPath(int pathIndex, int timeIndex);

	/**
	 * This method print the given path of the simulated stochastic process
	 * 
	 * @param pathIndex
	 */
	public void printPath(int pathIndex);
}