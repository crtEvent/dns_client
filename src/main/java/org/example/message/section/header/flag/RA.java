package org.example.message.section.header.flag;

/**
 * Recursion Desired - this bit may be set in a query and
 * is copied into the response.  If RD is set, it directs
 * the name server to pursue the query recursively.
 * Recursive query support is optional.
 */
public enum RA {
	TRUE('1'), FALSE('0');

	private final char sign;

	RA(char sign) {
		this.sign = sign;
	}

	public char getSign() {
		return sign;
	}
}
