/**
 * 
 */
package com.lorenzoberti.session08;

import com.lorenzoberti.session07.AbstractRandomVariable;

/**
 * @author Lorenzo Berti
 *
 */
public class Pareto extends AbstractRandomVariable {

	private double shape;
	private double scale;

	public Pareto(double shape, double scale) {
		super();
		this.shape = shape;
		this.scale = scale;
	}

	@Override
	public double getAnalyticMean() {
		if (shape > 1) {
			return shape * scale / (shape - 1);
		} else
			return Double.POSITIVE_INFINITY;

	}

	@Override
	public double getAnalyticVariance() {
		if (shape > 2) {
			return shape * Math.pow(scale, 2) / (Math.pow((shape - 1), 2) * (shape - 2));
		} else
			return Double.POSITIVE_INFINITY;
	}

	@Override
	public double getCumulativeDistributionFunction(double x) {

		if (x < scale) {
			return 0;
		} else
		return 1 - Math.pow(scale / x, shape);
	}

	@Override
	public double getDensityFunction(double x) {

		if (x < scale) {
			return 0;
		} else
		return shape * Math.pow(scale, shape) / Math.pow(x, shape + 1);
	}

	@Override
	public double getInverse(double x) {

		double denominator = Math.pow(1 - x, 1 / shape);
		return scale / denominator;
	}

}
