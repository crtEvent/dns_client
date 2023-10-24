package org.example.message.section.record;

/**
 * a variable length string of octets that describes the
 * resource.  The format of this information varies
 * according to the TYPE and CLASS of the resource record.
 * For example, the if the TYPE is A and the CLASS is IN,
 * the RDATA field is a 4 octet ARPA Internet address.
 */
public class RDATA {
	private final byte[] rData;

	private RDATA(byte[] rData) {
		this.rData = rData;
	}

	public static RDATA generateByBytes(byte[] bytes) {
		return new RDATA(bytes);
	}

	public byte[] getBytes() {
		return rData;
	}

	public int getLength() {
		return rData.length;
	}

}
