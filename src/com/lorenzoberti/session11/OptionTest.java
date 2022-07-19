/**
 * 
 */
package com.lorenzoberti.session11;

import java.text.DecimalFormat;

import com.lorenzoberti.session10.EulerBlackScholesProcess;
import com.lorenzoberti.session10.LogEulerBlackScholesProcess;
import com.lorenzoberti.session10.MilsteinBlackScholesProcess;
import com.lorenzoberti.session10.ProcessSimulator;

import net.finmath.exception.CalculationException;
import net.finmath.functions.AnalyticFormulas;
import net.finmath.montecarlo.BrownianMotion;
import net.finmath.montecarlo.BrownianMotionFromMersenneRandomNumbers;
import net.finmath.montecarlo.RandomVariableFromDoubleArray;
import net.finmath.montecarlo.assetderivativevaluation.MonteCarloAssetModel;
import net.finmath.montecarlo.assetderivativevaluation.models.BlackScholesModel;
import net.finmath.montecarlo.assetderivativevaluation.products.EuropeanOption;
import net.finmath.montecarlo.model.ProcessModel;
import net.finmath.montecarlo.process.EulerSchemeFromProcessModel;
import net.finmath.montecarlo.process.MonteCarloProcess;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationFromArray;

/**
 * @author Lorenzo Berti
 *
 */
public class OptionTest {

	static final DecimalFormat FORMATTERPOSITIVE = new DecimalFormat("0.0000");
	static final DecimalFormat FORMATTERPERCENTAGE = new DecimalFormat("0.000%");

	/**
	 * @param args
	 * @throws CalculationException
	 */
	public static void main(String[] args) throws CalculationException {

		int numberOfPaths = 100000; // 10^5
		double initialTime = 0.0;
		double finalTime = 1.0;
		double timeStep = 0.1;
		int numberOfTimeSteps = (int) (finalTime / timeStep);
		TimeDiscretization times = new TimeDiscretizationFromArray(initialTime, numberOfTimeSteps, timeStep);

		double initialValue = 100.0;
		double riskFree = 0.1;
		double sigma = 0.3;

		double strike = 100.0;
		double maturity = finalTime;

		RandomVariable discountFactor = new RandomVariableFromDoubleArray(Math.exp(-riskFree * maturity));

		// Analytic price
		double analyticPrice = AnalyticFormulas.blackScholesOptionValue(initialValue, riskFree, sigma, maturity,
				strike);
		System.out.println("Call Option Experiment\n");
		System.out.println("Analytic price BS.............: " + FORMATTERPOSITIVE.format(analyticPrice));

		for (int i = 0; i < 5; i++) {

			System.out.println("_".repeat(80) + "\n");

			System.out.print("Experiment n°: " + (i + 1) + "\n");

			// Price with finmath library
			int seed = 3013 * i;

			ProcessModel blackScholesModel = new BlackScholesModel(initialValue, riskFree, sigma);

			BrownianMotion brownianMotion = new BrownianMotionFromMersenneRandomNumbers(times, 1, numberOfPaths, seed);

			MonteCarloProcess process = new EulerSchemeFromProcessModel(blackScholesModel, brownianMotion);

			MonteCarloAssetModel blackScholesMonteCarloModel = new MonteCarloAssetModel(process);

			EuropeanOption option = new EuropeanOption(maturity, strike);

			double valueFinmath = option.getValue(blackScholesMonteCarloModel);

			// With our processes

			ProcessSimulator eulerProcess = new EulerBlackScholesProcess(numberOfPaths, times, initialValue, riskFree,
					sigma, seed);
			ProcessSimulator milsteinProcess = new MilsteinBlackScholesProcess(numberOfPaths, times, initialValue,
					riskFree, sigma, seed);
			ProcessSimulator logEulerProcess = new LogEulerBlackScholesProcess(numberOfPaths, times, initialValue,
					riskFree, sigma, seed);

			AbstractEuropeanProduct call = new CallOption(maturity, strike);

			double eulerPrice = call.getPriceAsDouble(eulerProcess, discountFactor);
			double milsteinPrice = call.getPriceAsDouble(milsteinProcess, discountFactor);
			double logEulerPrice = call.getPriceAsDouble(logEulerProcess, discountFactor);

			System.out.println("Price Euler scheme......: " + FORMATTERPOSITIVE.format(eulerPrice) + "\tError: "
					+ FORMATTERPOSITIVE.format(Math.abs(eulerPrice - analyticPrice)));
			System.out.println("Price Milstein scheme...: " + FORMATTERPOSITIVE.format(milsteinPrice) + "\tError: "
					+ FORMATTERPOSITIVE.format(Math.abs(milsteinPrice - analyticPrice)));
			System.out.println("Price LogEuler scheme...: " + FORMATTERPOSITIVE.format(logEulerPrice) + "\tError: "
					+ FORMATTERPOSITIVE.format(Math.abs(logEulerPrice - analyticPrice)));
			System.out.println("Price finmath library...: " + FORMATTERPOSITIVE.format(valueFinmath) + "\tError: "
					+ FORMATTERPOSITIVE.format(Math.abs(valueFinmath - analyticPrice)));

			System.out.println("_".repeat(80) + "\n");

		}

	}

}