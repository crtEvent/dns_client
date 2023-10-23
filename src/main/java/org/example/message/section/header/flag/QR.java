package org.example.message.section.header.flag;

public enum QR {
	QUERY('0'), RESPONSE('1');

	private final char sign;

	QR(char sign) {
		this.sign = sign;
	}

	public char getSign() {
		return sign;
	}
}
