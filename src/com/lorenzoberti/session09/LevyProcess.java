/**
 * 
 */
package com.lorenzoberti.session09;

import net.finmath.stochastic.RandomVariable;

/**
 * A Lévy process is a stochastic process such that: X_0 = 0 a.s. and the
 * increments are independent and identically distributed.
 * 
 * @author Lorenzo Berti
 *
 */
public interface LevyProcess {

	/**
	 * This method returns the all paths of the Lévy process.
	 * 
	 * @return the paths of the Lévy process.
	 */
	RandomVariable[] getPaths();

	/**
	 * This method returns the Lévy process at a given time index.
	 * 
	 * @param timeIndex
	 * @return the process at the given time index.
	 */
	RandomVariable getProcessAtGivenTimeIndex(int timeIndex);

	/**
	 * This method returns the Lévy process at a given time.
	 * 
	 * @param time
	 * @return the process at the given time.
	 */
	RandomVariable getProcessAtGivenTime(double time);

	/**
	 * This method returns the specific path of the Lévy process as an array of
	 * doubles.
	 * 
	 * @param pathIndex
	 * @return the specific path of the Lévy process.
	 */
	double[] getSpecificPath(int pathIndex);

	/**
	 * This method returns the realization of the specific path of the Lévy process
	 * at the given time index.
	 * 
	 * @param pathIndex
	 * @param timeIndex
	 * @return the specific realization of the Lévy process.
	 */
	double getSpecificRealizationAtGivenTimeIndex(int pathIndex, int timeIndex);

	/**
	 * This method returns the realization of the specific path of the Lévy process
	 * at the given time.
	 * 
	 * @param pathIndex
	 * @param time
	 * @return the specific realization of the Lévy process.
	 */
	double getSpecificRealizationAtGivenTime(int pathIndex, double time);

	/**
	 * This method prints the specific path of the Lévy process.
	 * 
	 * @param pathIndex
	 */
	void printSpecificPath(int pathIndex);

}