package org.example.message.section.header.flag;

/**
 * Reserved for future use.  Must be zero in all queries
 * and responses.
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
