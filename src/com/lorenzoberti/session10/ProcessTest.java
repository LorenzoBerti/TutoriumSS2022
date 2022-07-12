/**
 * 
 */
package com.lorenzoberti.session10;

import java.util.function.DoubleUnaryOperator;

import com.lorenzoberti.session09.BrownianMotionSimple;

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

		int numberOfPaths = 10000;
		int seed = 3003;
		double initialTime = 0.0;
		double finalTime = 1.0;
		double timeStep = 0.1;
		int numberOfTimeSteps = (int) (finalTime / timeStep);

		TimeDiscretization times = new TimeDiscretizationFromArray(initialTime, numberOfTimeSteps, timeStep);

		double mu = 0.1;
		double sigma = 0.3;

		double initialValue = 100;
		ProcessSimulator eulerBlackProcess = new EulerBlackScholesProcess(numberOfPaths, times, initialValue, mu, sigma,
				seed);
		// eulerBlackProcess.printPath(0);
		ProcessSimulator milsteinBlackProcess = new MilsteinBlackScholesProcess(numberOfPaths, times, initialValue, mu,
				sigma, seed);
		// milsteinBlackProcess.printPath(0);
		ProcessSimulator logBlackProcess = new LogEulerBlackScholesProcess(numberOfPaths, times, initialValue, mu,
				sigma, seed);
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

}

// Private method that generate the value of the random variable at the given
// time under the Black Scholes model, i.e. the asset follows a geometric
// brownian motion
	private static RandomVariable getAssetAtSpecificTime(BrownianMotionSimple brownian, int indexFactor,
			double riskFreeRate, double sigma, double initialValue, double time) {

		DoubleUnaryOperator geometricBrownian = b -> {
			return initialValue * Math.exp((riskFreeRate - sigma * sigma * 0.5) * time + sigma * b);
		};

		RandomVariable asset = brownian.getProcessAtGivenTime(time).apply(geometricBrownian);

		return asset;

	}

}