/**
 * 
 */
package com.lorenzoberti.session10;

import com.lorenzoberti.session09.BrownianMotionIncrement;

import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;

/**
 * Geometric Brownian motion dX(t) = \mu X(t) dt + \sigma X(t) dW(t)
 * 
 * Milstein Scheme: X(t_{i+1}) = X(t_i) + \mu X(t_i) (t_{i+1}-t_i) + \sigma
 * X(t_i) W(t_{i+1})-W(t_i) + 1/2 sigma^2 X(t_i) [ (W(t_{i+1})-W(t_i))^2 -
 * (t_{i+1}-t_i) ]
 * 
 * @author Lorenzo Berti
 *
 */
public class MilsteinBlackScholesProcess extends AbstractProcessSimulator {

	private double mu;
	private double sigma;

	public MilsteinBlackScholesProcess(int numberOfPaths, TimeDiscretization times, double initialValue, int seed,
			double mu, double sigma) {
		super(numberOfPaths, times, initialValue, seed);
		this.mu = mu;
		this.sigma = sigma;
		this.transform = (x -> x);
		this.inverseTransform = (x -> x);
	}

	@Override
	public String getName() {

		return "Milstein scheme for Black Scholes process";
	}

	@Override
	protected RandomVariable getDrift(RandomVariable lastRealization, int timeIndex) {

		TimeDiscretization times = getTimeDiscretization();
		double timeStep = times.getTimeStep(timeIndex - 1);

		return lastRealization.mult(mu).mult(timeStep);
	}

	@Override
	protected RandomVariable getDiffusion(RandomVariable lastRealization, int timeIndex) {

		BrownianMotionIncrement brownianIncrement = getStochasticDriver();

		TimeDiscretization times = getTimeDiscretization();
		double timeStep = times.getTimeStep(timeIndex - 1);

		RandomVariable brownianIncrementRandomVariable = brownianIncrement.getIncrement(timeIndex - 1);

		RandomVariable linearTerm = lastRealization.mult(sigma).mult(brownianIncrementRandomVariable);

		RandomVariable adjustment = brownianIncrementRandomVariable.mult(brownianIncrementRandomVariable).sub(timeStep)
				.mult(lastRealization).mult(sigma * sigma * 0.5);

		return linearTerm.add(adjustment);
	}

}
