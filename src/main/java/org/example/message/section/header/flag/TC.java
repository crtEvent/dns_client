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

	public static TC generateBy(char oneBitBinary) {
		return switch (oneBitBinary) {
			case '0' -> TC.FALSE;
			case '1' -> TC.TRUE;
			default -> throw new IllegalArgumentException("잘못된 binary 값이 들어왔습니다.");
		};
	}
}
