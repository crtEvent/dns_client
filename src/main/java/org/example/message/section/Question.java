package org.example.message.section;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.message.section.question.QCLASS;
import org.example.message.section.question.QNAME;
import org.example.message.section.question.QTYPE;

/**
 * The question section is used to carry the "question" in most queries,
 * i.e., the parameters that define what is being asked.  The section
 * contains QDCOUNT (usually 1) entries, each of the following format:
 * <pre>
 * 1  1  1  1  1  1
 * 0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                                               |
 * /                     QNAME                     /
 * /                                               /
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                     QTYPE                     |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                     QCLASS                    |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * </pre>
 */
public class Question {
	private final QNAME qName;
	private final QTYPE qType;
	private final QCLASS qClass;

	private final byte[] bytes;

	private Question(QNAME qName, QTYPE qType, QCLASS qClass) {
		this.qName = qName;
		this.qType = qType;
		this.qClass = qClass;
		this.bytes = convertToBytes();
	}

	public static Question generateForRequest(String hostName, QTYPE qType) {
		return new Question(
			QNAME.generateByHostName(hostName),
			qType,
			QCLASS.IN
		);
	}

	public static Question generateForTypeA(String hostName) {
		return new Question(
			QNAME.generateByHostName(hostName),
			QTYPE.A,
			QCLASS.IN
		);
	}

	public static Question generateForTypeMX(String hostName) {
		return new Question(
			QNAME.generateByHostName(hostName),
			QTYPE.MX,
			QCLASS.IN
		);
	}

	public byte[] getBytes() {
		return bytes;
	}

	private byte[] convertToBytes() {
		var fields = new byte[][]{
			qName.getBytes(),
			qType.getBytes(),
			qClass.getBytes()
		};

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		for (byte[] field : fields) {
			outputStream.write(field, 0, field.length);
		}

		return outputStream.toByteArray();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("[Question Section]\n");
		builder.append(String.format("%-7s: ", "QNAME"));
		for (byte b : qName.getBytes()) {
			builder.append(String.format("%02x ", b));
		}
		builder.append("\n");

		int bytesIndex = qName.length();
		builder.append(String.format("%-7s: %02x %02x\n", "QTYPE", bytes[bytesIndex++], bytes[bytesIndex++]));
		builder.append(String.format("%-7s: %02x %02x\n", "QCLASS", bytes[bytesIndex++], bytes[bytesIndex]));

		return builder.toString();
	}
}
