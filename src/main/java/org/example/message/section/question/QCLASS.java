package org.example.message.section.question;

import java.util.Arrays;

/**
 * a two octet code that specifies the class of the query.
 * For example, the QCLASS field is IN for the Internet.
 * <br><br>
 * CLASS fields appear in resource records.  The following CLASS mnemonics
 * and values are defined:
 * <pre>
 * IN              1 the Internet
 * CS              2 the CSNET class (Obsolete - used only for examples in
 *                 some obsolete RFCs)	<i>* Unimplemented *</i>
 * CH              3 the CHAOS class	<i>* Unimplemented *</i>
 * HS              4 Hesiod [Dyer 87]	<i>* Unimplemented *</i>
 * </pre>
 * QCLASS fields appear in the question section of a query.  QCLASS values
 * are a superset of CLASS values; every CLASS is a valid QCLASS.  In
 * addition to CLASS values, the following QCLASSes are defined:
 * <pre>
 * *               255 any class
 * </pre>
 */
public enum QCLASS {
	IN(new byte[]{0x00, 0x01}),
	CS(new byte[]{0x00, 0x02}),
	CH(new byte[]{0x00, 0x03}),
	HS(new byte[]{0x00, 0x04}),
	ASTERISK(new byte[]{0x00, (byte) 0xff});

	private final byte[] code;

	QCLASS(byte[] code) {
		this.code = code;
	}

	public byte[] getBytes() {
		return code;
	}

	public static QCLASS generateByBytes(byte[] receivedBytes) {
		for (QCLASS qClass : QCLASS.values()) {
			if(Arrays.equals(qClass.getBytes(), receivedBytes)) {
				return qClass;
			}
		}
		throw new IllegalArgumentException("잘못된 bytes 값이 들어왔습니다. QCLASS으로 변환할 수 없습니다.");
	}
}
