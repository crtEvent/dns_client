package org.example.message.section.header.flag;

/**
 * Authoritative Answer - this bit is valid in responses,
 * and specifies that the responding name server is an
 * authority for the domain name in question section.
 * <br>
 * Note that the contents of the answer section may have
 * multiple owner names because of aliases.  The AA bit
 * corresponds to the name which matches the query name, or
 * the first owner name in the answer section.
 */
public enum AA {
	TRUE('1'), FALSE('0');

	private final char sign;

	AA(char sign) {
		this.sign = sign;
	}

	public char getSign() {
		return sign;
	}
}
