/**
 * 
 */
package com.lorenzoberti.session02;

/**
 * From Wikipedia: In mathematics, the Fibonacci numbers, commonly denoted Fn,
 * form a sequence, called the Fibonacci sequence, such that each number is the
 * sum of the two preceding ones, starting from 0 and 1.
 *
 * That is, F_{0}=0, F_{1}=1, F_{n}=F_{n-1}+F_{n-2}, for n larger 1.
 *
 * The beginning of the sequence is thus: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89,
 * 144, ...
 * 
 * @author Lorenzo Berti
 *
 */
public interface Fibonacci {

	/**
	 * This method returns the first "range" Fibonacci numbers; e.g. if a = 4 then
	 * it returns the vector {0 1 1 2}
	 * 
	 * @param range
	 * @return the a Fibonacci numbers
	 */
	int[] fibonacciNumbers(int range);

	/**
	 * This method returns the sum of the Fibonacci numbers up to (and including) a
	 * given barrier; e.g. if barrier = 5 the result should be 12
	 * 
	 * @param barrier
	 * @return the sum of the Fibonacci numbers up to (including) a given barrier
	 */
	int fibonacciSum(double barrier);

	/**
	 * This method returns the sum of the even Fibonacci numbers up to (and
	 * including) a given barrier; e.g. if barrier = 13 the result should be 23
	 * 
	 * @param barrier
	 * @return the sum of the even Fibonacci numbers up to (including) a given
	 *         barrier
	 */
	int fibonacciEvenSum(double barrier);

	/**
	 * This method returns the sum of the odd Fibonacci numbers up to (and
	 * including) a given barrier; e.g. if barrier = 13 the result should be 10
	 * 
	 * @param barrier
	 * @return the sum of the odd Fibonacci numbers up to (including) a given
	 *         barrier
	 */
	int fibonacciOddSum(double barrier);

}
