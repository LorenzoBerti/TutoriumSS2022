/**
 * 
 */
package com.lorenzoberti.session06;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Create a List of Random doubles from the given seed.
 * 
 * @author Lorenzo Berti
 *
 */
public class RandomToList {

	/**
	 * Create a list of random Double.
	 *
	 * @param seed The seed for the Random Number Generator.
	 * @param size The size of the list.
	 * @return The list of random Double.
	 */
	public static List<Double> getList(long seed, int size) {

		return new Random(seed).doubles(size).boxed().collect(Collectors.toList());

	}

}
