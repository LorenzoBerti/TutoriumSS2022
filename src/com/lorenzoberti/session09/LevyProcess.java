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

	RandomVariable[] getPaths();

	RandomVariable getProcessAtGivenTimeIndex(int timeIndex);

	RandomVariable getProcessAtGivenTime(double time);

	double[] getSpecificPath(int pathIndex);

	double getSpecificRealizationAtGivenTimeIndex(int pathIndex, int timeIndex);

	double getSpecificRealizationAtGivenTime(int pathIndex, double time);

	void printSpecificPath(int pathIndex);

}