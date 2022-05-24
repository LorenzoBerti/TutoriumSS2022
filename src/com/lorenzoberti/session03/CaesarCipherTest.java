package com.lorenzoberti.session03;

/**
 * @author Lorenzo Berti
 *
 */
public class CaesarCipherTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int shift = 5;

		Cipher cipher = new CaesarCipher(shift);
		// String string = "ABCdefghijklmnopqrstuvwXyzäöü";
		String string = "Lorenzo8";
		System.out.println(string);
		System.out.println(cipher.encode(string));
		System.out.println(cipher.decode(cipher.encode(string)));

	}

}