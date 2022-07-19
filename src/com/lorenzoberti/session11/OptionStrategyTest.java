/**
 * 
 */
package com.lorenzoberti.session11;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

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
import net.finmath.plots.Named;
import net.finmath.plots.Plot2D;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationFromArray;

/**
 * @author Lorenzo Berti
 *
 */
public class OptionStrategyTest {

	static final DecimalFormat FORMATTERPOSITIVE = new DecimalFormat("0.0000");
	static final DecimalFormat FORMATTERPERCENTAGE = new DecimalFormat("0.000%");

	/**
	 * @param args
	 * @throws CalculationException
	 */
	public static void main(String[] args) throws CalculationException {

		int numberOfPaths = 100000;
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

		double analyticPrice = AnalyticFormulas.blackScholesOptionValue(initialValue, riskFree, sigma, maturity, strike)
				+ AnalyticFormulas.blackScholesOptionValue(initialValue, riskFree, sigma, maturity, strike, false);

		System.out.println("Straddle Option Experiment\n");
		System.out.println("Analytic price BS.............: " + FORMATTERPOSITIVE.format(analyticPrice));

		for (int i = 0; i < 5; i++) {

			System.out.println("_".repeat(80) + "\n");

			System.out.print("Experiment n° " + (i + 1) + ":\n");

			// Price with finmath library
			int seed = 3013 * i;

			ProcessModel blackScholesModel = new BlackScholesModel(initialValue, riskFree, sigma);

			BrownianMotion brownianMotion = new BrownianMotionFromMersenneRandomNumbers(times, 1, numberOfPaths, seed);

			MonteCarloProcess process = new EulerSchemeFromProcessModel(blackScholesModel, brownianMotion);

			MonteCarloAssetModel blackScholesMonteCarloModel = new MonteCarloAssetModel(process);

			EuropeanOption option = new EuropeanOption(maturity, strike);

			// Recover the straddle price from the put-call parity:
			// C_t - P_t = S_t - K * e^{-r(T-t)}
			double valueCallFinmath = option.getValue(blackScholesMonteCarloModel);

			double valueFinmath = 2 * valueCallFinmath - blackScholesMonteCarloModel.getAssetValue(0, 0).getAverage()
					+ strike * Math.exp(-riskFree * maturity);

			// Price with our classes
			ProcessSimulator eulerProcess = new EulerBlackScholesProcess(numberOfPaths, times, initialValue, riskFree,
					sigma, seed);
			ProcessSimulator milsteinProcess = new MilsteinBlackScholesProcess(numberOfPaths, times, initialValue,
					riskFree, sigma, seed);
			ProcessSimulator logEulerProcess = new LogEulerBlackScholesProcess(numberOfPaths, times, initialValue,
					riskFree, sigma, seed);

			FinancialProductInterface straddle = new StraddleOption(maturity, strike);

			double eulerPrice = straddle.getPriceAsDouble(eulerProcess, discountFactor);
			double milsteinPrice = straddle.getPriceAsDouble(milsteinProcess, discountFactor);
			double logEulerPrice = straddle.getPriceAsDouble(logEulerProcess, discountFactor);

			System.out.println("Price Euler scheme......: " + FORMATTERPOSITIVE.format(eulerPrice) + "\tError: "
					+ FORMATTERPOSITIVE.format(Math.abs(eulerPrice - analyticPrice)));
			;
			System.out.println("Price Milstein scheme...: " + FORMATTERPOSITIVE.format(milsteinPrice) + "\tError: "
					+ FORMATTERPOSITIVE.format(Math.abs(milsteinPrice - analyticPrice)));
			;
			System.out.println("Price LogEuler scheme...: " + FORMATTERPOSITIVE.format(logEulerPrice) + "\tError: "
					+ FORMATTERPOSITIVE.format(Math.abs(logEulerPrice - analyticPrice)));
			;
			System.out.println("Price finmath library...: " + FORMATTERPOSITIVE.format(valueFinmath) + "\tError: "
					+ FORMATTERPOSITIVE.format(Math.abs(valueFinmath - analyticPrice)));

			System.out.println("_".repeat(80) + "\n");
		}

		StraddleOption straddle = new StraddleOption(maturity, strike);

		DoubleUnaryOperator straddlePayoff = process -> {
			return straddle.getPayoffStrategy(process);
		};

		Plot2D plotStraddle = new Plot2D(0, 200, 200, straddlePayoff);
		plotStraddle.setTitle("Straddle strategy payoff");
		plotStraddle.setXAxisLabel("Stock price");
		plotStraddle.setYAxisLabel("Straddle payoff");
		plotStraddle.setYAxisNumberFormat(FORMATTERPOSITIVE);
		plotStraddle.show();

//		double strike1 = 50;
//		double strike2 = 25;
//		StrangleOption strangle = new StrangleOption(maturity, strike1, strike2);
//
//		DoubleUnaryOperator stranglePayoff = process -> {
//			return strangle.getPayoffStrategy(process);
//		};
//
//		Plot2D plotStrangle = new Plot2D(0, 75, 75, stranglePayoff);
//		plotStrangle.setTitle("Strangle strategy payoff");
//		plotStrangle.setXAxisLabel("Stock price");
//		plotStrangle.setYAxisLabel("Strangle payoff");
//		plotStrangle.setYAxisNumberFormat(FORMATTERPOSITIVE);
//		plotStrangle.show();

		// Straddle hedging strategy
		int seed = 3003;
		double shift = Math.pow(10, -13);

		ProcessSimulator eulerProcess = new EulerBlackScholesProcess(numberOfPaths, times, initialValue, riskFree,
				sigma, seed);
		RandomVariable eulerProcessAtMaturity = eulerProcess.getProcessAtGivenTime(maturity);
		double centralDifference = (straddle.getPriceAsDouble(eulerProcessAtMaturity.add(shift), discountFactor)
				- straddle.getPriceAsDouble(eulerProcessAtMaturity.add(-shift), discountFactor)) / shift;

		double deltaCall = AnalyticFormulas.blackScholesOptionDelta(initialValue, riskFree, sigma, maturity, strike);
		double deltaPut = deltaCall - 1;

		double analyticHedging = deltaCall + deltaPut;

		System.out.println("Delta hedging:\n");
		System.out.println("Analytic...............: " + FORMATTERPOSITIVE.format(analyticHedging) + "\tError: "
				+ FORMATTERPOSITIVE.format(Math.abs(analyticHedging - analyticHedging)));
		System.out.println("Central difference.....: " + FORMATTERPOSITIVE.format(centralDifference) + "\tError: "
				+ FORMATTERPOSITIVE.format(Math.abs(centralDifference - analyticHedging)));

		DoubleUnaryOperator deltaHedging = scale -> {
			double shiftScaled = Math.pow(10, -scale);
			return (straddle.getPriceAsDouble(eulerProcessAtMaturity.add(shiftScaled), discountFactor)
					- straddle.getPriceAsDouble(eulerProcessAtMaturity.add(-shiftScaled), discountFactor))
					/ shiftScaled;
		};

		DoubleUnaryOperator analytic = (x) -> analyticHedging;

		Plot2D plotHedging = new Plot2D(0, 16, 100,
				Arrays.asList(new Named<DoubleUnaryOperator>("Central difference", deltaHedging),
						new Named<DoubleUnaryOperator>("Analytic delta", analytic)));
		plotHedging.setTitle("Delta Hedging strategy");
		plotHedging.setXAxisLabel("Scale: shift = 10^(-scale)");
		plotHedging.setYAxisLabel("Central difference");
		plotHedging.setYAxisNumberFormat(FORMATTERPOSITIVE);
		plotHedging.show();

	}

}
