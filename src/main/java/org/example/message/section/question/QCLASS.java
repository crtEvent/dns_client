package org.example.message.section.question;

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
	ASTERISK(new byte[]{0x00, (byte) 0xff});

	private final byte[] code;

	QCLASS(byte[] code) {
		this.code = code;
	}

	public byte[] getBytes() {
		return code;
	}
}
