/**
 * 
 */
package com.lorenzoberti.session11;

/**
 * This class represents an European put option.
 * 
 * @author Lorenzo Berti
 *
 */
public class PutOption extends AbstractEuropeanProduct {

	private double strike;

	public PutOption(double maturity, double strike) {
		super(maturity);
		this.strike = strike;
		this.payoffFunction = (x) -> x.sub(strike).mult(-1).floor(0.0); // Payoff: max(K-S_T,0)
	}


}