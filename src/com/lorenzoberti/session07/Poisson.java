/**
 * 
 */
package com.lorenzoberti.session07;

/**
 * Poisson random variable: discrete random variable with support on N and
 * density f(k) = exp(-lambda) * lambda^k/k!, where lambda in (0, \infty)
 * 
 * @author Lorenzo Berti
 *
 */
public class Poisson extends AbstractRandomVariable {

	double lambda; // intensity

	public Poisson(double lambda) {
		super();
		this.lambda = lambda;
	}

	@Override
	public double getAnalyticMean() {
		// TODO Auto-generated method stub
		return lambda;
	}

	@Override
	public double getAnalyticVariance() {
		// TODO Auto-generated method stub
		return lambda;
	}

	@Override
	public double getCumulativeDistributionFunction(double x) {
		// TODO Auto-generated method stub
		int n = (int) x;
		double sum = 0;
		for (int i = 0; i < n + 1; i++) {
			sum += getDensityFunction(i);
		}

		return sum;
	}

	@Override
	public double getDensityFunction(double x) {
		// TODO Auto-generated method stub
		int n = (int) x;
		if (n < 0) {
			throw new IllegalArgumentException("The support of Poisson distribution is positive!");
		}
		double value = Math.pow(lambda, n) / factorial(n) * Math.exp(-lambda);
		return value;
	}

	private double factorial(int n) {
		if (n == 0 || n == 1) {
			return 1;
		} else {
			double factorial = n;
			n = n - 1;
			do {
				factorial *= n;
				n = n - 1;
			} while (n > 0);
			return factorial;
		}

	}

	@Override
	public double getInverse(double x) {
		// TODO Auto-generated method stub
		int p = 0;
		double value = getCumulativeDistributionFunction(p);
		while (x > value) {
			p++;
			value = getCumulativeDistributionFunction(p);
		}
		return p;
	}


}
