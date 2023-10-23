package org.example.utill;

public class ByteConvertor {

	private ByteConvertor() {}

	public static byte[] intToTwoBytes(int value) {
		if (!ByteValidator.is16BitInteger(value)) {
			throw new IllegalArgumentException("16bit 범위 내에서 입력 가능합니다. (0~65535)");
		}

		var bytes = new byte[2];

		bytes[0] = (byte) ((value >> 8) & 0xFF);
		bytes[1] = (byte) (value & 0xFF);

		return bytes;
	}

	public static byte[] binaryToTwoBytes(String binaryString) {
		if (!ByteValidator.is16BitBinaryString(binaryString)) {
			throw new IllegalArgumentException("16bit 이진수만 입력 가능합니다.");
		}

		var bytes = new byte[2];

		bytes[0] = (byte) Integer.parseInt(binaryString.substring(0, 8), 2);
		bytes[1] = (byte) Integer.parseInt(binaryString.substring(8, 16), 2);

		return bytes;
	}
}
