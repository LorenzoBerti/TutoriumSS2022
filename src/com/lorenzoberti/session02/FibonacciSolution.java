/**
 * 
 */
package com.lorenzoberti.session02;

/**
 * @author Lorenzo Berti
 *
 */
public class FibonacciSolution implements Fibonacci {

	@Override
	public int[] fibonacciNumbers(int range) {

		int[] numbers = new int[range];

		numbers[0] = 0;
		numbers[1] = 1;

		for (int i = 2; i < range; i++) {
			numbers[i] = numbers[i - 1] + numbers[i - 2];
		}

		return numbers;
	}

	@Override
	public int fibonacciSum(double barrier) {

		int previous = 0;
		int seq = 1;
		int fibonacci = previous + seq;

		int sum = fibonacci;

		while (fibonacci <= barrier) {
			sum += fibonacci;
			previous = seq;
			seq = fibonacci;
			fibonacci = previous + seq;

		}

		return sum;
	}

	@Override
	public int fibonacciEvenSum(double barrier) {

		int previous = 0;
		int seq = 1;
		int fibonacci = previous + seq;

		int sum = fibonacci;

		while (fibonacci <= barrier) {

			if (fibonacci % 2 != 0) {
				sum += fibonacci;
			}

			previous = seq;
			seq = fibonacci;
			fibonacci = previous + seq;

		}

		return sum;

	}

	@Override
	public int fibonacciOddSum(double barrier) {

		int previous = 0;
		int seq = 1;
		int fibonacci = previous + seq;

		int sum = 0;

		while (fibonacci <= barrier) {

			if (fibonacci % 2 == 0) {
				sum += fibonacci;
			}

			previous = seq;
			seq = fibonacci;
			fibonacci = previous + seq;

		}

		return sum;
	}

}
