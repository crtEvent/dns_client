package org.example.message.section;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.util.Arrays;

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

	private static final int START_INDEX_OF_QUESTION_SECTION = 12;

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

	public static Question generateBy(DatagramPacket receivedPacket) {
		var receivedBytes = receivedPacket.getData();
		var qName = QNAME.generateByBytes(extractQNameBytes(receivedBytes));
		var qType = QTYPE.generateByBytes(extractQTypeBytes(receivedBytes, qName));
		var qClass = QCLASS.generateByBytes(extractQClassBytes(receivedBytes, qName));

		return new Question(
			qName,
			qType,
			qClass
		);
	}

	private static byte[] extractQNameBytes(byte[] receivedBytes) {
		var bytesLength = receivedBytes.length;
		var endIndex = START_INDEX_OF_QUESTION_SECTION;
		while (endIndex < bytesLength && receivedBytes[endIndex] != 0x00) {
			endIndex++;
		}
		endIndex++;

		if (endIndex < bytesLength) {
			return Arrays.copyOfRange(receivedBytes, START_INDEX_OF_QUESTION_SECTION, endIndex);
		}
		throw new IllegalArgumentException("잘못된 입력값이 들어왔습니다. QNAME을 찾을 수 없습니다.");
	}

	private static byte[] extractQTypeBytes(byte[] receivedBytes, QNAME qName) {
		var startIndex = START_INDEX_OF_QUESTION_SECTION + qName.length();
		if (startIndex + 2 < receivedBytes.length) {
			return Arrays.copyOfRange(receivedBytes, startIndex, startIndex + 2);
		}
		throw new IllegalArgumentException("잘못된 입력값이 들어왔습니다. QTYPE을 찾을 수 없습니다.");
	}

	private static byte[] extractQClassBytes(byte[] receivedBytes, QNAME qName) {
		var startIndex = START_INDEX_OF_QUESTION_SECTION + qName.length() + 2;
		if (startIndex + 2 < receivedBytes.length) {
			return Arrays.copyOfRange(receivedBytes, startIndex, startIndex + 2);
		}
		throw new IllegalArgumentException("잘못된 입력값이 들어왔습니다. QCLASS를 찾을 수 없습니다.");
	}

	public byte[] getBytes() {
		return bytes;
	}

	public int getLength() {
		return bytes.length;
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
