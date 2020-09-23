package hr.fer.zemris.java.hw06.crypto;

import java.util.Objects;

/**
 * Utility class that provides 2 static methods. 1 for transforming hash string
 * in byte array, and 1 that takes byte array and returns hex string.
 * 
 * @author Matej Fureš
 *
 */
public class Util {
	/**
	 * Creates an hex representation of given byte array. Array mustn't be null
	 * 
	 * @param array to represent as hex string
	 * @return string representation of array
	 * @throws NullPointerException if array is null
	 */
	public static String bytetohex(byte[] array) {
		Objects.requireNonNull(array);

		StringBuilder sb = new StringBuilder();
		char tmp;
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		for (byte b : array) {
			tmp = digits[b & 0b1111];
			sb.append(digits[(b >> 4) & 0b1111]);
			sb.append(tmp);
		}

		return sb.toString();
	}

	/**
	 * Creates byte array representation of given String. String must have length
	 * divisible by 2, and all containing non digit symbols must be letters used in
	 * hex numbers. Method isn't case sensitive. If those rules aren't followed, an
	 * exception will be thrown. Input String mustn't be null.
	 * 
	 * @param keyText to be parsed to byte array
	 * @return byte array representation of hex string
	 * @throws IllegalArgumentException if String is invalid
	 * @throws NullPointerException     if keyText is null
	 */
	public static byte[] hextobyte(String keyText) {
		Objects.requireNonNull(keyText);
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Input length should be divisible by 2");
		}

		byte[] output = new byte[keyText.length() / 2];
		char[] input = keyText.toCharArray();

		for (int i = 0; i < output.length; i++) {
			output[i] = (byte) (getByteRepresentation(input[2 * i]) << 4);
			output[i] += getByteRepresentation(input[2 * i + 1]);
		}

		return output;
	}

	/**
	 * Returns byte representation of hex digit. If digit isn't of hex format, an
	 * exception is thrown.
	 * 
	 * @param c to parsed to byte
	 * @return byte representation of c
	 * @throws IllegalArgumentException if c is invalid
	 */
	private static byte getByteRepresentation(char c) {
		if (Character.isDigit(c)) {
			return Byte.valueOf(String.valueOf(c));
		}

		if (Character.isLetter(c)) {
			switch (c) {
			case 'a':
			case 'A':
				return 10;

			case 'b':
			case 'B':
				return 11;

			case 'c':
			case 'C':
				return 12;

			case 'd':
			case 'D':
				return 13;

			case 'e':
			case 'E':
				return 14;

			case 'f':
			case 'F':
				return 15;

			}
		}

		throw new IllegalArgumentException("Incorrect input format. " + c + " found.");
	}
}
