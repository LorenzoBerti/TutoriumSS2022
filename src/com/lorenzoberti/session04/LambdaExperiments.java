package com.lorenzoberti.session04;


import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.DoubleUnaryOperator;

/**
 * @author Lorenzo Berti
 *
 */
public class LambdaExperiments {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Sintax: parameter(s) -> expression
		int a = 10;
		DoubleUnaryOperator nextInteger = (x) -> (x + 1); // Look at the DoubleUnaryOperator javadoc
		double next = nextInteger.applyAsDouble(a);
		// System.out.println(next);

		// The expression could be also a block of code
		DoubleUnaryOperator nextOdd = x -> {
			if (x % 2 == 0) {
				return x + 2;
			} else
				return x + 1;
		};

		double nextOne = nextOdd.applyAsDouble(a);
		// System.out.println(nextOne);

		// Lambda expressions are useful to reduce the code whereby increasing the
		// readability

		ArrayList<Integer> numbers = new ArrayList<Integer>();
		numbers.add(5);
		numbers.add(9);
		numbers.add(8);
		numbers.add(1);

		// Print without using lambda expression:
		for (int i = 0; i < numbers.size(); i++) {
			// System.out.println(numbers.get(i));
		}

		// Print using lambda expression
		numbers.forEach((n) -> {
			// System.out.println(n);
		});

		// Lambda expression can be stored in variables...

		Consumer<Integer> method = (n) -> {
			// System.out.println(n);
		};
		numbers.forEach(method);
//
		DoubleUnaryOperator operation = x -> {
			return (Math.exp(x) + 1) / Math.log(x) * Math.sin(x + Math.sqrt(x - 2));
		};

		// ...but the return parameter must be of the same type!

		Consumer<Integer> doubled = (n) -> {
			GeneralOperation(n, operation);
		};
		// numbers.forEach(doubled);

		// Lambda expression can be a parameter of methods

		GeneralOperation(a, operation);
//
	}

	/**
	 * 
	 * @param x
	 * @param operation
	 * @return result of the operation
	 */
	public static void GeneralOperation(double x, DoubleUnaryOperator operation) {

		System.out.println(operation.applyAsDouble(x));
	}
}