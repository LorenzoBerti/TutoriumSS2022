/**
 * 
 */
package com.lorenzoberti.session03.exception;

/**
 * @author Lorenzo Berti
 *
 */
public class DivideByZeroException extends Exception {

	public void printExceptionMessage() {

		System.out.println("Error: you cannot divide by zero!");
	}

}
