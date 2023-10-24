package org.example.message.section.record;

import java.util.Arrays;

/**
 * two octets containing one of the RR type codes.  This
 * field specifies the meaning of the data in the RDATA
 * field.
 * <br><br>
 * TYPE fields are used in resource records.  Note that these types are a
 * subset of QTYPEs.
 * <pre>
 * TYPE            value and meaning
 * A               1 a host address
 * NS              2 an authoritative name server
 * MD              3 a mail destination (Obsolete - use MX)
 * MF              4 a mail forwarder (Obsolete - use MX)
 * CNAME           5 the canonical name for an alias
 * SOA             6 marks the start of a zone of authority
 * MB              7 a mailbox domain name (EXPERIMENTAL)
 * MG              8 a mail group member (EXPERIMENTAL)
 * MR              9 a mail rename domain name (EXPERIMENTAL)
 * NULL            10 a null RR (EXPERIMENTAL)
 * WKS             11 a well known service description
 * PTR             12 a domain name pointer
 * HINFO           13 host information
 * MINFO           14 mailbox or mail list information
 * MX              15 mail exchange
 * TXT             16 text strings
 * </pre>
 */
public enum TYPE {
	A(new byte[]{0x00, 0x01}),
	NS(new byte[]{0x00, 0x02}),
	MD(new byte[]{0x00, 0x03}),
	MF(new byte[]{0x00, 0x04}),
	CNAME(new byte[]{0x00, 0x05}),
	SOA(new byte[]{0x00, 0x06}),
	MB(new byte[]{0x00, 0x07}),
	MG(new byte[]{0x00, 0x08}),
	MR(new byte[]{0x00, 0x09}),
	NULL(new byte[]{0x00, 0x0a}),
	WKS(new byte[]{0x00, 0x0b}),
	PTR(new byte[]{0x00, 0x0c}),
	HINFO(new byte[]{0x00, 0x0d}),
	MINFO(new byte[]{0x00, 0x0e}),
	MX(new byte[]{0x00, 0x0f}),
	TXT(new byte[]{0x00, 0x10});

	private final byte[] code;

	TYPE(byte[] code) {
		this.code = code;
	}

	public byte[] getBytes() {
		return code;
	}

	public static TYPE generateByTwoBytes(byte[] receivedBytes) {
		for (TYPE type : TYPE.values()) {
			if(Arrays.equals(type.getBytes(), receivedBytes)) {
				return type;
			}
		}
		throw new IllegalArgumentException("잘못된 bytes 값이 들어왔습니다. TYPE으로 변환할 수 없습니다.");
	}
}
