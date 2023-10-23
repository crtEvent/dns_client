package org.example.message.section.header.flag;

/**
 * Recursion Desired - this bit may be set in a query and
 * is copied into the response.  If RD is set, it directs
 * the name server to pursue the query recursively.
 * Recursive query support is optional.
 */
public enum Z {
	FALSE("000");

	private final String sign;

	Z(String sign) {
		this.sign = sign;
	}

	public String getSign() {
		return sign;
	}
}
