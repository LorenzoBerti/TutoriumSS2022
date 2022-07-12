/**
 * 
 */
package com.lorenzoberti.session10;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

import com.lorenzoberti.session09.BrownianMotionSimple;

import net.finmath.plots.Named;
import net.finmath.plots.Plot2D;
import net.finmath.stochastic.RandomVariable;
import net.finmath.time.TimeDiscretization;
import net.finmath.time.TimeDiscretizationFromArray;

/**
 * @author Lorenzo Berti
 *
 */
public class ProcessTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int numberOfPaths = 100;
		int seed = 3003;
		double initialTime = 0.0;
		double finalTime = 1.0;
		double timeStep = 0.01;
		int numberOfTimeSteps = (int) (finalTime / timeStep);

		TimeDiscretization times = new TimeDiscretizationFromArray(initialTime, numberOfTimeSteps, timeStep);

		double mu = 0.1;
		double sigma = 0.3;
		double initialValue = 100;

		ProcessSimulator eulerBlackProcess = new EulerBlackScholesProcess(numberOfPaths, times, initialValue, seed, mu,
				sigma);
		// eulerBlackProcess.printPath(0);
		ProcessSimulator milsteinBlackProcess = new MilsteinBlackScholesProcess(numberOfPaths, times, initialValue,
				seed, mu, sigma);
		// milsteinBlackProcess.printPath(0);
		ProcessSimulator logBlackProcess = new LogEulerBlackScholesProcess(numberOfPaths, times, initialValue, seed, mu,
				sigma);
		// logBlackProcess.printPath(0);

		double lastTime = finalTime;
		RandomVariable lastValueEuler = eulerBlackProcess.getProcessAtGivenTime(lastTime);
		RandomVariable lastValueMilstein = milsteinBlackProcess.getProcessAtGivenTime(lastTime);
		RandomVariable lastValueLogEuler = logBlackProcess.getProcessAtGivenTime(lastTime);

		BrownianMotionSimple brownianMotion = new BrownianMotionSimple(numberOfPaths, times, seed);

		RandomVariable lastValuePrivate = getAssetAtSpecificTime(brownianMotion, 0, mu, sigma, initialValue, lastTime);

		// Take their average...

		double averageEuler = lastValueEuler.getAverage();
		double averageMilstein = lastValueMilstein.getAverage();
		double averageLogEuler = lastValueLogEuler.getAverage();
		double averagePrivate = lastValuePrivate.getAverage();

		System.out.println("Mean:\n");

		// ...and print
		System.out.println("The average with the private method is.....: " + averagePrivate);
		System.out.println("The Euler scheme average is................: " + averageEuler);
		System.out.println("The Milstein scheme average is.............: " + averageMilstein);
		System.out.println("The LogEuler scheme average is.............: " + averageLogEuler);

		System.out.println("_".repeat(80) + "\n");

		System.out.println("Variance:\n");

		// Take their variance...

		double varianceEuler = lastValueEuler.getVariance();
		double varianceMilstein = lastValueEuler.getVariance();
		double varianceLogEuler = lastValueEuler.getVariance();
		double variancePrivate = lastValuePrivate.getVariance();

		// ...and print
		System.out.println("The variance with the private method is....: " + variancePrivate);
		System.out.println("The Euler scheme variance is...............: " + varianceEuler);
		System.out.println("The Milstein scheme variance is............: " + varianceMilstein);
		System.out.println("The LogEuler scheme variance is............: " + varianceLogEuler);

		DoubleUnaryOperator eulerTrajectory = t -> {
			return eulerBlackProcess.getSpecificValueOfSpecificPath(0, (int) t);
		};

		DoubleUnaryOperator milsteinTrajectory = t -> {
			return milsteinBlackProcess.getSpecificValueOfSpecificPath(0, (int) t);
		};

		DoubleUnaryOperator logEulerTrajectory = t -> {
			return logBlackProcess.getSpecificValueOfSpecificPath(0, (int) t);
		};

		// Plot the Process
		DecimalFormat formatterValuePlot = new DecimalFormat("#0.0000");

		Plot2D plot = new Plot2D(0, times.getNumberOfTimes(), times.getNumberOfTimes() + 1,
				Arrays.asList(new Named<DoubleUnaryOperator>(eulerBlackProcess.getName(), eulerTrajectory),
						new Named<DoubleUnaryOperator>(milsteinBlackProcess.getName(), milsteinTrajectory),
						new Named<DoubleUnaryOperator>(logBlackProcess.getName(), logEulerTrajectory)));
		plot.setTitle("Process scheme trajectory");
		plot.setXAxisLabel("time");
		plot.setYAxisLabel("Process");
		plot.setYAxisNumberFormat(formatterValuePlot);
		plot.setIsLegendVisible(true);
		plot.show();

		// Zoom plot

		Plot2D plotZoom = new Plot2D(30, 50, 20,
				Arrays.asList(new Named<DoubleUnaryOperator>(eulerBlackProcess.getName(), eulerTrajectory),
						new Named<DoubleUnaryOperator>(milsteinBlackProcess.getName(), milsteinTrajectory),
						new Named<DoubleUnaryOperator>(logBlackProcess.getName(), logEulerTrajectory)));
		plotZoom.setTitle("Process scheme trajectory");
		plotZoom.setXAxisLabel("time");
		plotZoom.setYAxisLabel("Process");
		plotZoom.setYAxisNumberFormat(formatterValuePlot);
		plotZoom.setIsLegendVisible(true);
		plotZoom.show();

}


/**
 * Private method that generate the value of the random variable at the given
 * time under the Black Scholes model, i.e. the asset follows a geometric
 * brownian motion.
 * 
 * @param brownian
 * @param indexFactor
 * @param riskFreeRate
 * @param sigma
 * @param initialValue
 * @param time
 * @return the exact solution of the Brownian motion at the specific time
 */
	private static RandomVariable getAssetAtSpecificTime(BrownianMotionSimple brownian, int indexFactor,
			double riskFreeRate, double sigma, double initialValue, double time) {

		DoubleUnaryOperator geometricBrownian = b -> {
			return initialValue * Math.exp((riskFreeRate - sigma * sigma * 0.5) * time + sigma * b);
		};

		RandomVariable asset = brownian.getProcessAtGivenTime(time).apply(geometricBrownian);

		return asset;

	}

}