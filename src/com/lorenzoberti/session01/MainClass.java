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

//		System.out.println("Hi! I am Java " + System.getProperty("java.version") + ". What's your name?");
//
//		Scanner in = new Scanner(System.in);
//
//		String answer1 = in.nextLine();
//
//		System.out.println("Hello " + answer1 + ", nice to meet you! How are you?");
//
//		String answer2 = in.nextLine();
//
//		if (answer2.equals("Bad")) {
//			System.out.println("Oh no! Why?");
//			String answer3 = in.nextLine();
//			System.out.println("I am so sorry about that");
//
//		} else if (answer2.equals("Fine")) {
//			System.out.println("Great! I am happy for you!");
//		}
//
//		in.close();

		System.out.println("Hi! I am not Java " + System.getProperty("java.version") + ". What's your name?");

		Scanner in = new Scanner(System.in);

		String answer1 = in.nextLine();

		System.out.println("Hello " + answer1 + ", nice to meet you!");

		String answer2 = in.nextLine();

	}

}
