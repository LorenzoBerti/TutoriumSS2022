/**
 * 
 */
package com.lorenzoberti.session11;

import com.lorenzoberti.session10.ProcessSimulator;

import net.finmath.stochastic.RandomVariable;

/**
 * This class represent a Strangle option strategy, i.e. a strategy involving
 * the purchase of both a put and call option on the same underlying security
 * for the same expiration date with different strike prices.
 * 
 * @author Lorenzo Berti
 *
 */
public class StrangleOption implements FinancialProductInterface {

	private double maturity;
	private double strike1; // strike of the call
	private double strike2; // strike of the put
	private CallOption call;
	private PutOption put;

	public StrangleOption(double maturity, double strike1, double strike2) {
		super();
		this.maturity = maturity;
		this.strike1 = strike1;
		this.strike2 = strike2;
		this.call = new CallOption(maturity, strike1);
		this.put = new PutOption(maturity, strike2);
	}

	@Override
	public RandomVariable getPrice(ProcessSimulator process, RandomVariable discountFactor) {

		RandomVariable callPrice = call.getPrice(process, discountFactor);
		RandomVariable putPrice = put.getPrice(process, discountFactor);
		return callPrice.add(putPrice).average();
	}

	@Override
	public double getPriceAsDouble(ProcessSimulator process, RandomVariable discountFactor) {

		return getPrice(process, discountFactor).getAverage();
	}

	// This method allows us to print the payoff of the strategy as a function of
	// the underlying value
	public double getPayoffStrategy(double processValue) {

		double callPayoff = Math.max(processValue - strike1, 0);
		double putPayoff = Math.max(strike2 - processValue, 0);
		return (callPayoff + putPayoff);

	}

	// This method allows us to compute the hedging strategy
	public double getPriceAsDouble(RandomVariable process, RandomVariable discountFactor) {

		double callPrice = call.getPriceAsDouble(process, discountFactor);
		double putPrice = put.getPriceAsDouble(process, discountFactor);
		return callPrice + putPrice;
	}

}
