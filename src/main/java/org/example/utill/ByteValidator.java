package org.example.utill;

public class ByteValidator {

	private ByteValidator() { }

	public static boolean is16BitInteger(int value) {
		return (value >= 0 && value <= 65535);
	}

	public static boolean is16BitBinaryString(String binaryString) {
		if (binaryString.length() != 16) {
			return false;
		}

		for (int i = 0; i < 16; i++) {
			char c = binaryString.charAt(i);
			if (c != '0' && c != '1') {
				return false;
			}
		}

		return true;
	}
}
