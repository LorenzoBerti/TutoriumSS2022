/**
 * 
 */
package com.lorenzoberti.session10;

import com.lorenzoberti.session09.BrownianMotionIncrement;

import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;

/**
 * Geometric Brownian motion dX(t) = \mu X(t) dt + \sigma X(t) dW(t)
 * 
 * LogEulerScheme: dlog(X(t_{i+1})) = (\mu - 1/2 \sigma^2) (t_{i+1}-t_i) +
 * \sigma W(t_{i+1}-t_i)
 * 
 * @author Lorenzo Berti
 *
 */
public class LogEulerBlackScholesProcess extends AbstractProcessSimulator {

	double mu;
	double sigma;

	public LogEulerBlackScholesProcess(int numberOfPaths, TimeDiscretization times, double initialValue, int seed,
			double mu, double sigma) {
		super(numberOfPaths, times, initialValue, seed);
		this.mu = mu;
		this.sigma = sigma;
		this.transform = (x -> Math.exp(x));
		this.inverseTransform = (x -> Math.log(x));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Log Euler scheme Black Scholes process";
	}

	@Override
	protected RandomVariable getDrift(RandomVariable lastRealization, int timeIndex) {

		TimeDiscretization times = getTimeDiscretization();

		return new RandomVariableFromDoubleArray(times.getTime(timeIndex - 1),
				(mu - 0.5 * sigma * sigma) * (times.getTimeStep(timeIndex - 1)));
	}

	@Override
	protected RandomVariable getDiffusion(RandomVariable lastRealization, int timeIndex) {
		// TODO Auto-generated method stub
		BrownianMotionIncrement brownianIncrement = getStochasticDriver();

		return brownianIncrement.getIncrement(timeIndex - 1).mult(sigma);
	}

}
