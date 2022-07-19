package com.lorenzoberti.session11;

import com.lorenzoberti.session10.ProcessSimulator;

import net.finmath.stochastic.RandomVariable;

/**
 * This interface represents a generic financial product.
 * 
 * @author Lorenzo Berti
 *
 */
public interface FinancialProductInterface {

	/**
	 * This method return the price as object of type RandomVariable of the
	 * financial product.
	 * 
	 * @param process
	 * @param discountFactor
	 * @return the price of the product
	 */
	RandomVariable getPrice(ProcessSimulator process, RandomVariable discountFactor);

	/**
	 * This method return the price of the financial product as a double.
	 * 
	 * @param process
	 * @param discountFactor
	 * @return the price of the product
	 */
	double getPriceAsDouble(ProcessSimulator process, RandomVariable discountFactor);

}