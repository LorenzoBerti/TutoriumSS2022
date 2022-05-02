/**
 * 
 */
package com.lorenzoberti.session01;

import java.util.Scanner;

/**
 * @author Lorenzo Berti
 *
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Hello World!");

		System.out.println("You are running Java version " + System.getProperty("java.version"));

		System.out.println("Whats's your name?");

		Scanner in = new Scanner(System.in);

		String answer = in.nextLine();

		System.out.println("Hello " + answer);

		in.close();

	}

}
