/**
 * 
 */
package com.lorenzoberti.session09;

import net.finmath.stochastic.RandomVariable;

/**
 * This interface represents general independent increments.
 * 
 * @author Lorenzo Berti
 *
 */
public interface IndependentIncrement {

	/**
	 * This method return the Random variable X_t-X_s
	 * 
	 * @param s
	 * @param t
	 * @return random variable X_t-X_s
	 */
	RandomVariable getIncrement(double s, double t);

	/**
	 * This method return the increment at the given time index
	 * 
	 * @param timeIndex
	 * @return random variable X_{t_{n+1}} - X_{t_n}
	 */
	RandomVariable getIncrement(int timeIndex);

}