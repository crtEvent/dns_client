package org.example.message.section;

import java.io.ByteArrayOutputStream;

import org.example.message.section.header.COUNT;
import org.example.message.section.header.FLAG;
import org.example.message.section.header.ID;
import org.example.message.section.header.flag.OPCODE;

/**
 * The header contains the following fields:
 * <pre>
 *                                 1  1  1  1  1  1
 *   0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                      ID                       |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |QR|   Opcode  |AA|TC|RD|RA|   Z    |   RCODE   |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                    QDCOUNT                    |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                    ANCOUNT                    |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                    NSCOUNT                    |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                    ARCOUNT                    |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * </pre>
 */
public class Header {
	private final ID id;
	private final FLAG flag;
	private final COUNT qdCount;
	private final COUNT anCount;
	private final COUNT nsCount;
	private final COUNT arCount;

	private final byte[] bytes;

	private Header(ID id, FLAG flag, COUNT qdCount, COUNT anCount, COUNT nsCount, COUNT arCount) {
		this.id = id;
		this.flag = flag;
		this.qdCount = qdCount;
		this.anCount = anCount;
		this.nsCount = nsCount;
		this.arCount = arCount;
		this.bytes = convertToBytes();
	}

	public static Header generateForRequestQuery(int id) {
		return new Header(
			ID.generateBy(id),
			FLAG.generateForRequest(OPCODE.QUERY),
			COUNT.generate(1),
			COUNT.generateZeroCount(),
			COUNT.generateZeroCount(),
			COUNT.generateZeroCount()
		);
	}

	public byte[] getBytes() {
		return bytes;
	}

	public boolean isRequest() {
		return flag.isRequest();
	}

	public boolean isResponse() {
		return flag.isResponse();
	}

	private byte[] convertToBytes() {
		var fields = new byte[][]{
			id.convertToTwoBytes(),
			flag.convertToTwoBytes(),
			qdCount.convertToTwoBytes(),
			anCount.convertToTwoBytes(),
			nsCount.convertToTwoBytes(),
			arCount.convertToTwoBytes()
		};

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		for (byte[] field : fields) {
			outputStream.write(field, 0, field.length);
		}

		return outputStream.toByteArray();
	}

	@Override
	public String toString() {
		return "[Header Section]\n"
			+ String.format("%-7s: %02x %02x\n", "ID", bytes[0], bytes[1])
			+ String.format("%-7s: %02x %02x\n", "FLAG", bytes[2], bytes[3])
			+ String.format("%-7s: %02x %02x\n", "QDCOUNT", bytes[4], bytes[5])
			+ String.format("%-7s: %02x %02x\n", "ANCOUNT", bytes[6], bytes[7])
			+ String.format("%-7s: %02x %02x\n", "NSCOUNT", bytes[8], bytes[9])
			+ String.format("%-7s: %02x %02x\n", "ARCOUNT", bytes[10], bytes[11]);
	}
}
