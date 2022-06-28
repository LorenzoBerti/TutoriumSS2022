/**
 * 
 */
package com.lorenzoberti.session07;

/**
 * Bernoulli random variable: discrete random variable X that takes only values
 * 0 and 1; X = 1 with probability p, X = 1 with probability 1-p
 * 
 * @author Lorenzo Berti
 *
 */
public class Bernoulli extends AbstractRandomVariable {

	double p; // The parameter of Bernoulli experiment

	public Bernoulli(double p) {
		if (p < 0 || p > 1) {
			throw new IllegalArgumentException("Bernoulli parameter must be in [0,1]");
		}
		this.p = p;
	}

	public double getParameter() {
		return p;
	}

	@Override
	public double getAnalyticMean() {
		return p;
	}

	@Override
	public double getAnalyticVariance() {
		return p * (1 - p);
	}

	@Override
	public double getDensityFunction(double x) {

		if (x == 0) {
			return 1 - p;
		} else if (x == 1) {
			return p;
		} else
			throw new IllegalArgumentException("Bernoulli random variable takes only values 0 and 1");
	}

	@Override
	public double getCumulativeDistributionFunction(double x) {
		if (x < 0) {
			return 0;
		} else if (x >= 0 & x < 1) {
			return 1 - p;
		} else
			return 1;
	}

	@Override
	public double getInverse(double x) {
		if (x <= 1 - p) {
			return 0;
		} else
			return 1;
	}

}
