/**
 * 
 */
package com.lorenzoberti.session11;

/**
 * This class represents an European call option.
 * 
 * @author Lorenzo Berti
 *
 */
public class CallOption extends AbstractEuropeanProduct {

	private double strike;

	public CallOption(double maturity, double strike) {
		super(maturity);
		this.strike = strike;
		this.payoffFunction = (x) -> x.sub(strike).floor(0.0); // Payoff: max(S_T-K,0)
	}

}