package org.example.message.section.header.flag;

/**
 * Recursion Available - this be is set or cleared in a
 * response, and denotes whether recursive query support is
 * available in the name server.
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

	public static RA generateBy(char oneBitBinary) {
		return switch (oneBitBinary) {
			case '0' -> RA.FALSE;
			case '1' -> RA.TRUE;
			default -> throw new IllegalArgumentException("잘못된 binary 값이 들어왔습니다.");
		};
	}
}
