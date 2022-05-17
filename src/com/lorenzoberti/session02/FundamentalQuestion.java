/**
 * 
 */
package com.lorenzoberti.session02;

import java.util.Scanner;

/**
 * @author Lorenzo Berti
 *
 */
public class FundamentalQuestion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		System.out.print("Which is the answer to the fundamental question?");
		System.out.println();
		Scanner in = new Scanner(System.in);
			
			String answer = in.nextLine();
			
			boolean word;
			
			do {
				word = answer.equals("42"); // "transform" the input in a boolean
				
				if(word) {
					System.out.println("Great! You are a man of culture ;-)");
				} else {

					System.out.println("No, I'm sorry. Try again");
					System.out.println();
					System.out.print("Which is the answer to the fundamental question?");
					answer = in.nextLine();

				}
			}
			
				while (word == false);

				in.close();
				
		}




}
