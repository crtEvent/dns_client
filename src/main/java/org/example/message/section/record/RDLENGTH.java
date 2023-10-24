package org.example.message.section.record;

/**
 * an unsigned 16 bit integer that specifies the length in
 * octets of the RDATA field.
 */
public class RDLENGTH {
	private final byte[] rdLength;
	private final int length;

	private RDLENGTH(byte[] rdLength) {
		this.rdLength = rdLength;
		this.length = convertToInteger(rdLength[0], rdLength[1]);
	}

	public static RDLENGTH generateByTwoBytes(byte[] bytes) {
		return new RDLENGTH(bytes);
	}

	private int convertToInteger(byte firstByte, byte secondByte) {
		return ((firstByte & 0xFF) << 8) | (secondByte & 0xFF);
	}

	public byte[] getBytes() {
		return rdLength;
	}

	public int getLength() {
		return rdLength.length;
	}

	public int getRDataLength() {
		return length;
	}
}
