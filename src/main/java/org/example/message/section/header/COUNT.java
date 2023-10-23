package org.example.message.section.header;

import org.example.utill.ByteConvertor;

/**
 * <pre>
 * QDCOUNT         an unsigned 16 bit integer specifying the number of
 *                 entries in the question section.
 *
 * ANCOUNT         an unsigned 16 bit integer specifying the number of
 *                 resource records in the answer section.
 *
 * NSCOUNT         an unsigned 16 bit integer specifying the number of name
 *                 server resource records in the authority records
 *                 section.
 *
 * ARCOUNT         an unsigned 16 bit integer specifying the number of
 *                 resource records in the additional records section.
 * </pre>
 */
public class COUNT {
	private final int count;

	private COUNT(int count) {
		this.count = count;
	}

	public static COUNT generate(int count) {
		return new COUNT(count);
	}

	public static COUNT generateZeroCount() {
		return new COUNT(0);
	}


	public byte[] convertToTwoBytes() {
		return ByteConvertor.intToTwoBytes(count);
	}


}
