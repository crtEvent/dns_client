package org.example.message.section.header.flag;

/**
 * TrunCation - specifies that this message was truncated
 * due to length greater than that permitted on the
 * transmission channel.
 */
public enum TC {
	TRUE('1'), FALSE('0');

	private final char sign;

	TC(char sign) {
		this.sign = sign;
	}

	public char getSign() {
		return sign;
	}
}
