/**
 * 
 */
package com.lorenzoberti.session07;

/**
 * @author Lorenzo Berti
 *
 */
public class ChebyshevInterval implements ConfidenceInterval {

	RandomVariable randomVariable;

	public ChebyshevInterval(RandomVariable randomVariable) {
		super();
		this.randomVariable = randomVariable;
	}

	@Override
	public double[] getConfidenceInterval(int numberOfSimulations, double level) {
		// TODO Auto-generated method stub
		double mean = randomVariable.getAnalyticMean();
		double stdDeviation = Math.sqrt(randomVariable.getAnalyticVariance());
		return new double[] { mean - stdDeviation / Math.sqrt(level * numberOfSimulations),
				mean + stdDeviation / Math.sqrt(level * numberOfSimulations) };
	}

}
