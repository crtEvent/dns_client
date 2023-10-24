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

	public static QR generateBy(char oneBitBinary) {
		return switch (oneBitBinary) {
			case '0' -> QR.QUERY;
			case '1' -> QR.RESPONSE;
			default -> throw new IllegalArgumentException("잘못된 binary 값이 들어왔습니다.");
		};
	}
}
