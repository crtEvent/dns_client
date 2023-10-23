package org.example.message.section.question;

import java.io.ByteArrayOutputStream;

/**
 * a domain name represented as a sequence of labels, where
 * each label consists of a length octet followed by that
 * number of octets.  The domain name terminates with the
 * zero length octet for the null label of the root.  Note
 * that this field may be an odd number of octets; no
 * padding is used.
 */
public class QNAME {
	private final byte[] qname;

	private QNAME(byte[] qname) {
		this.qname = qname;
	}

	public static QNAME generateByHostName(String hostName) {
		var hostParts = hostName.split("\\.");

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		for (var part : hostParts) {
			outputStream.write(new byte[]{(byte) part.length()}, 0, 1);
			outputStream.write(part.getBytes(), 0, part.getBytes().length);
		}
		outputStream.write(new byte[]{0x00}, 0, 1);

		return new QNAME(outputStream.toByteArray());
	}

	public byte[] getBytes() {
		return qname;
	}

	public int length() {
		return qname.length;
	}

}
