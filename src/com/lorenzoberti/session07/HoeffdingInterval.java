/**
 * 
 */
package com.lorenzoberti.session07;

/**
 * This class has a method that computes the confidence interval by using
 * Hoeffding's inequality for the sample mean of a random variable. Hoeffding's
 * inequality: X1,...,Xn i.i.d. Bernoulli variables, then P(|X-\mu| >= t) <=
 * 2*exp(-2*n*t^2). Note: the Hoeffding's inequality can be generalized to any
 * sub-Gaussian variable.
 * 
 * @author Lorenzo Berti
 *
 */
public class HoeffdingInterval implements ConfidenceInterval {

	@Override
	public double[] getConfidenceInterval(int numberOfSimulations, double level) {
		// TODO Auto-generated method stub
		return null;
	}

}
