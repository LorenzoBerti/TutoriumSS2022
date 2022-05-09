/**
 * 
 */
package com.lorenzoberti.session02;

import java.util.Arrays;

/**
 * Use this class to test your implementation of the interface: Fibonacci.
 * 
 * @author Lorenzo Berti
 *
 */
public class FibonacciTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// replace the following line with the constructor of your class
		Fibonacci fibonacci = 0;

		int range = 8;
		// use the method fibonacciNumbers(range)
		int[] fibonacciNumbers;

		System.out.println("The first " + range + " Fibonacci numbers are " + Arrays.toString(fibonacciNumbers));

		double barrier = 13;

		// use the method fibonacciSum(barrier); for barrier = 13 the result should be
		// 33
		int sum;

		System.out.println("The sum of the Fibonacci numbers up to " + barrier + " is " + sum);

		System.out.println();

		// use the method fibonacciEvenSum(barrier); for barrier = 13 the result should
		// be 23
		int sumEven;
		System.out.println("The sum of the even Fibonacci numbers up to " + barrier + " is " + sumEven);

		System.out.println();

		// use the method fibonacciEvenSum(barrier); for barrier = 13 the result should
		// be 10
		int sumOdd;
		System.out.println("The sum of the odd Fibonacci numbers up to " + barrier + " is " + sumOdd);

	}

}
