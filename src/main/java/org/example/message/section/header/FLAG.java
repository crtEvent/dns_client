package org.example.message.section.header;

import org.example.message.section.header.flag.AA;
import org.example.message.section.header.flag.OPCODE;
import org.example.message.section.header.flag.QR;
import org.example.message.section.header.flag.RA;
import org.example.message.section.header.flag.RCODE;
import org.example.message.section.header.flag.RD;
import org.example.message.section.header.flag.TC;
import org.example.message.section.header.flag.Z;
import org.example.utill.ByteConvertor;

/**
 * <pre>
 *   0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |QR|   Opcode  |AA|TC|RD|RA|   Z    |   RCODE   |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * </pre>
 */
public class FLAG {
	private final QR qr;
	private final OPCODE opCode;
	private final AA aa;
	private final TC tc;
	private final RD rd;
	private final RA ra;
	private final Z z;
	private final RCODE rCode;

	public FLAG(QR qr, OPCODE opCode, AA aa, TC tc, RD rd, RA ra, Z z, RCODE rCode) {
		this.qr = qr;
		this.opCode = opCode;
		this.aa = aa;
		this.tc = tc;
		this.rd = rd;
		this.ra = ra;
		this.z = z;
		this.rCode = rCode;
	}

	public static FLAG generateForRequest(OPCODE opCode) {
		return new FLAG(
			QR.QUERY,
			opCode,
			AA.FALSE,
			TC.FALSE,
			RD.FALSE,
			RA.FALSE,
			Z.FALSE,
			RCODE.NO_ERROR_CONDITION
		);
	}

	public String convertBinary() {

		return qr.getSign()
			+ opCode.getOpCode()
			+ aa.getSign()
			+ tc.getSign()
			+ rd.getSign()
			+ ra.getSign()
			+ z.getSign()
			+ rCode.getCode();
	}

	public byte[] convertToTwoBytes() {
		return ByteConvertor.binaryToTwoBytes(convertBinary());
	}

	public boolean isRequest() {
		return qr == QR.QUERY;
	}

	public boolean isResponse() {
		return qr == QR.RESPONSE;
	}

}
