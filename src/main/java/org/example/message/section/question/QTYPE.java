package org.example.message.section.question;

import java.util.Arrays;

/**
 * a two octet code which specifies the type of the query.
 * The values for this field include all codes valid for a
 * TYPE field, together with some more general codes which
 * can match more than one type of RR.
 * <br><br>
 * TYPE fields are used in resource records.  Note that these types are a
 * subset of QTYPEs.
 * <pre>
 * A               1 a host address
 * MX              15 mail exchange
 * </pre>
 * QTYPE fields appear in the question part of a query.  QTYPES are a
 * superset of TYPEs, hence all TYPEs are valid QTYPEs.  In addition, the
 * following QTYPEs are defined:
 * <pre>
 * MAILA           254 A request for mail agent RRs (Obsolete - see MX)
 * *               255 A request for all records
 * </pre>
 */
public enum QTYPE {
	A(new byte[]{0x00, 0x01}),
	MX(new byte[]{0x00, 0x0f}),
	MAILA(new byte[]{0x00, (byte) 0xfe}),
	ASTERISK(new byte[]{0x00, (byte) 0xff});

	private final byte[] code;

	QTYPE(byte[] code) {
		this.code = code;
	}

	public byte[] getBytes() {
		return code;
	}

	public static QTYPE generateByBytes(byte[] receivedBytes) {
		for (QTYPE qType : QTYPE.values()) {
			if(Arrays.equals(qType.getBytes(), receivedBytes)) {
				return qType;
			}
		}
		throw new IllegalArgumentException("잘못된 bytes 값이 들어왔습니다. QTYPE으로 변환할 수 없습니다.");
	}

	public static QTYPE covertStringToQTYPE(String qType) {
		return switch (qType.toLowerCase()) {
			case "a" -> QTYPE.A;
			case "mx" -> QTYPE.MX;
			case "maila" -> QTYPE.MAILA;
			case "*" -> QTYPE.ASTERISK;
			default -> throw new IllegalArgumentException("잘못된 QTYPE 입니다.");
		};
	}
}
