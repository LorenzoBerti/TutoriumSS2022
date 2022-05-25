package com.lorenzoberti.session03;

/**
 * @author Lorenzo Berti
 *
 */
public class CaesarCipher implements Cipher {

	private int shift;

	public CaesarCipher(int shift) {
		super();
		this.shift = shift % 26; // resto della divisione per 26
	}

	@Override
	public String encode(String message) {

		String encodeMessage = new String();

		int lastCharValue = 'z';
		int alphabetLength = 'z' - 'a' + 1;

		for (char character : message.toLowerCase().toCharArray()) {
			int charValue;
			if (isLetter(character)) {
				int currentChar = character + shift;
				if (currentChar <= lastCharValue) {
					charValue = currentChar;
				} else {
					charValue = currentChar - alphabetLength;
				}
				encodeMessage += (char) charValue;
			} else {
				encodeMessage += character;
			}

		}

		return encodeMessage;

	}

	@Override
	public String decode(String message) {

		String decodeMessage = new String();

		int firstCharValue = 'a';
		int alphabetLength = 'z' - 'a' + 1;

		for (char character : message.toLowerCase().toCharArray()) {
			int charValue;
			if (isLetter(character)) {
				int currentChar = character - shift;
				if (currentChar >= firstCharValue) {
					charValue = currentChar;
				} else {
					charValue = currentChar + alphabetLength;
				}
				decodeMessage += (char) charValue;
			} else {
				decodeMessage += character;
			}

		}

		return decodeMessage;
	}

	private static boolean isLetter(char character) {
		return (character >= 'a' && character <= 'z');
	}
}