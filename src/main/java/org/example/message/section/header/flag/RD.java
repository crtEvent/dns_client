package org.example.message.section.header.flag;

/**
 * Recursion Desired - this bit may be set in a query and
 * is copied into the response.  If RD is set, it directs
 * the name server to pursue the query recursively.
 * Recursive query support is optional.
 */
public enum RD {
	TRUE('1'), FALSE('0');

	private final char sign;

	RD(char sign) {
		this.sign = sign;
	}

	public char getSign() {
		return sign;
	}

	public static RD generateBy(char oneBitBinary) {
		return switch (oneBitBinary) {
			case '0' -> RD.FALSE;
			case '1' -> RD.TRUE;
			default -> throw new IllegalArgumentException("잘못된 binary 값이 들어왔습니다.");
		};
	}
}
