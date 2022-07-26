/**
 * 
 */
package com.lorenzoberti.session11;

import com.lorenzoberti.session10.ProcessSimulator;

import net.finmath.stochastic.RandomOperator;
import net.finmath.stochastic.RandomVariable;

/**
 * This Abstract class represent a general Europena product, i.e. a product
 * whose execution is possible only at the expiration date.
 * 
 * @author Lorenzo Berti
 *
 */
public abstract class AbstractEuropeanProduct implements FinancialProductInterface {

	protected RandomOperator payoffFunction;
	private double maturity;

	public AbstractEuropeanProduct(double maturity) {
		super();
		this.maturity = maturity;
	}

	@Override
	public RandomVariable getPrice(ProcessSimulator process, RandomVariable discountFactor) {

		RandomVariable payoff = process.getProcessAtGivenTime(maturity).appy(payoffFunction);

		return payoff.mult(discountFactor).average();

	}

	@Override
	public double getPriceAsDouble(ProcessSimulator process, RandomVariable discountFactor) {

		RandomVariable payoff = process.getProcessAtGivenTime(maturity).appy(payoffFunction);

		return payoff.mult(discountFactor).getAverage();

	}

	public double getPriceAsDouble(RandomVariable processAtMaturity, RandomVariable discountFactor) {

		RandomVariable payoff = processAtMaturity.appy(payoffFunction);
		RandomVariable price = payoff.mult(discountFactor);
		return price.getAverage();

	}


}
