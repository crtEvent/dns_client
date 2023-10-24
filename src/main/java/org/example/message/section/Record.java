package org.example.message.section;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.util.Arrays;

import org.example.message.section.record.CLASS;
import org.example.message.section.record.NAME;
import org.example.message.section.record.RDATA;
import org.example.message.section.record.RDLENGTH;
import org.example.message.section.record.TTL;
import org.example.message.section.record.TYPE;

/**
 * <p>Resource record format</p>
 * The answer, authority, and additional sections all share the same
 * format: a variable number of resource records, where the number of
 * records is specified in the corresponding count field in the header.
 * Each resource record has the following format:
 * <pre>
 *                                 1  1  1  1  1  1
 *   0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                                               |
 * /                                               /
 * /                      NAME                     /
 * |                                               |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                      TYPE                     |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                     CLASS                     |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                      TTL                      |
 * |                                               |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                   RDLENGTH                    |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--|
 * /                     RDATA                     /
 * /                                               /
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * </pre>
 */
public class Record {
	private final NAME name;
	private final TYPE type;
	private final CLASS clazz;
	private final TTL ttl;
	private final RDLENGTH rdLength;
	private final RDATA rData;

	private final byte[] bytes;

	private Record(NAME name, TYPE type, CLASS clazz, TTL ttl, RDLENGTH rdLength, RDATA rData) {
		this.name = name;
		this.type = type;
		this.clazz = clazz;
		this.ttl = ttl;
		this.rdLength = rdLength;
		this.rData = rData;
		this.bytes = convertToBytes();
	}

	public static Record generateBy(DatagramPacket receivedPacket, int startIndex) {
		var receivedBytes = receivedPacket.getData();


		var name = NAME.generateByBytes(extractNameBytes(receivedBytes, startIndex));
		var type = TYPE.generateByTwoBytes(extractTypeBytes(receivedBytes, startIndex, name));
		var clazz = CLASS.generateByTwoBytes(extractClassBytes(receivedBytes, startIndex, name));
		var ttl = TTL.generateByFourBytes(extractTtlBytes(receivedBytes, startIndex, name));
		var rdLength = RDLENGTH.generateByTwoBytes(extractRdLengthBytes(receivedBytes, startIndex, name));
		var rData = RDATA.generateByBytes(extractRDataBytes(receivedBytes, startIndex, name, rdLength));

		return new Record(
			name,
			type,
			clazz,
			ttl,
			rdLength,
			rData
		);
	}

	private static byte[] extractNameBytes(byte[] receivedBytes, int startIndex) {
		var pointer = startIndex;
		while (pointer < receivedBytes.length) {
			var binaryOneBit = Integer.toBinaryString(receivedBytes[pointer] & 0xFF);
			binaryOneBit = String.format("%8s", binaryOneBit).replace(' ', '0');

			if (binaryOneBit.startsWith("11")) {
				return Arrays.copyOfRange(receivedBytes, startIndex, pointer + 2);
			} else if (receivedBytes[pointer] == 0x00) {
				return Arrays.copyOfRange(receivedBytes, startIndex, pointer + 1);
			}

			pointer++;
		}
		throw new IllegalArgumentException("잘못된 입력값이 들어왔습니다. NAME을 찾을 수 없습니다.");
	}

	private static byte[] extractTypeBytes(byte[] receivedBytes, int startIndex, NAME name) {
		startIndex += name.getLength();
		if (startIndex + 2 < receivedBytes.length) {
			return Arrays.copyOfRange(receivedBytes, startIndex, startIndex + 2);
		}
		throw new IllegalArgumentException("잘못된 입력값이 들어왔습니다. TYPE을 찾을 수 없습니다.");
	}

	private static byte[] extractClassBytes(byte[] receivedBytes, int startIndex, NAME name) {
		startIndex += name.getLength() + 2;
		if (startIndex + 2 < receivedBytes.length) {
			return Arrays.copyOfRange(receivedBytes, startIndex, startIndex + 2);
		}
		throw new IllegalArgumentException("잘못된 입력값이 들어왔습니다. CLASS를 찾을 수 없습니다.");
	}

	private static byte[] extractTtlBytes(byte[] receivedBytes, int startIndex, NAME name) {
		startIndex += name.getLength() + 2 + 2;
		if (startIndex + 2 < receivedBytes.length) {
			return Arrays.copyOfRange(receivedBytes, startIndex, startIndex + 4);
		}
		throw new IllegalArgumentException("잘못된 입력값이 들어왔습니다. TTL을 찾을 수 없습니다.");
	}

	private static byte[] extractRdLengthBytes(byte[] receivedBytes, int startIndex, NAME name) {
		startIndex += name.getLength() + 2 + 2 + 4;
		if (startIndex + 2 < receivedBytes.length) {
			return Arrays.copyOfRange(receivedBytes, startIndex, startIndex + 2);
		}
		throw new IllegalArgumentException("잘못된 입력값이 들어왔습니다. RDLENGTH을 찾을 수 없습니다.");
	}

	private static byte[] extractRDataBytes(byte[] receivedBytes, int startIndex, NAME name, RDLENGTH rdLength) {
		startIndex += name.getLength() + 2 + 2 + 4 + 2;
		if (startIndex + rdLength.getRDataLength() < receivedBytes.length) {
			return Arrays.copyOfRange(receivedBytes, startIndex, startIndex + rdLength.getRDataLength());
		}
		throw new IllegalArgumentException("잘못된 입력값이 들어왔습니다. RDLENGTH을 찾을 수 없습니다.");
	}

	public byte[] getBytes() {
		return bytes;
	}

	public int getLength() {
		return bytes.length;
	}

	private byte[] convertToBytes() {
		var fields = new byte[][]{
			name.getBytes(),
			type.getBytes(),
			clazz.getBytes(),
			ttl.getBytes(),
			rdLength.getBytes(),
			rData.getBytes()
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

		builder.append(String.format("%-8s: ", "QNAME"));
		for (byte b : name.getBytes()) {
			builder.append(String.format("%02x ", b));
		}
		builder.append("\n");

		int bytesIndex = name.getLength();
		builder.append(String.format("%-8s: %02x %02x\n", "TYPE", bytes[bytesIndex++], bytes[bytesIndex++]));
		builder.append(String.format("%-8s: %02x %02x\n", "CLASS", bytes[bytesIndex++], bytes[bytesIndex++]));
		builder.append(String.format("%-8s: %02x %02x %02x %02x\n", "TTL", bytes[bytesIndex++], bytes[bytesIndex++], bytes[bytesIndex++], bytes[bytesIndex++]));
		builder.append(String.format("%-8s: %02x %02x\n", "RDLENGTH", bytes[bytesIndex++], bytes[bytesIndex]));

		builder.append(String.format("%-8s: ", "RDATA"));
		for (byte b : rData.getBytes()) {
			builder.append(String.format("%02x ", b));
		}
		builder.append("\n");

		return builder.toString();
	}
}
