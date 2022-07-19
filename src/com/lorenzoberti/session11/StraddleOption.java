/**
 * 
 */
package com.lorenzoberti.session11;

import com.lorenzoberti.session10.ProcessSimulator;

import net.finmath.stochastic.RandomVariable;

/**
 * This class represent a Straddle option strategy, i.e. a strategy involving
 * the purchase of both a put and call option on the same underlying security
 * for the same expiration date and strike price.
 * 
 * @author Lorenzo Berti
 *
 */
public class StraddleOption implements FinancialProductInterface {

	private double maturity;
	private double strike;
	private CallOption call;
	private PutOption put;

	public StraddleOption(double maturity, double strike) {
		super();
		this.maturity = maturity;
		this.strike = strike;
		this.call = new CallOption(maturity, strike);
		this.put = new PutOption(maturity, strike);
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

		double callPayoff = Math.max(processValue - strike, 0);
		double putPayoff = Math.max(strike - processValue, 0);
		return (callPayoff + putPayoff);

	}

	// This method allows us to compute the hedging strategy by using the central
	// difference method
	public double getPriceAsDouble(RandomVariable process, RandomVariable discountFactor) {

		double callPrice = call.getPriceAsDouble(process, discountFactor);
		double putPrice = put.getPriceAsDouble(process, discountFactor);
		return callPrice + putPrice;
	}
}