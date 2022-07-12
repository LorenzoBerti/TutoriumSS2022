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
 * EulerScheme: X(t_{i+1}) = X(t_i) + \mu X(t_i) (t_{i+1}-t_i) + \sigma X(t_i)
 * W(t_{i+1}-t_i)
 * 
 * @author Lorenzo Berti
 *
 */
public class EulerBlackScholesProcess extends AbstractProcessSimulator {

	double mu;
	double sigma;

	public EulerBlackScholesProcess(int numberOfPaths, TimeDiscretization times, double initialValue, int seed,
			double mu, double sigma) {
		super(numberOfPaths, times, initialValue, seed);
		this.mu = mu;
		this.sigma = sigma;
		this.transform = (x -> x);
		this.inverseTransform = (x -> x);
	}

	@Override
	public String getName() {

		return "Euler Scheme Black Scholes Process";
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

		return lastRealization.mult(sigma).mult(brownianIncrement.getIncrement(timeIndex - 1));
	}

}
