package org.example.message.section.header.flag;

/**
 * A four bit field that specifies kind of query in this
 * message.  This value is set by the originator of a query
 * and copied into the response.
 * <pre>
 * The values are:
 *   0               a standard query (QUERY)
 *   1               an inverse query (IQUERY)		<i>* Unimplemented *</i>
 *   2               a server status request (STATUS)	<i>* Unimplemented *</i>
 *   3-15            reserved for future use		<i>* Unimplemented *</i>
 * </pre>
 */
public enum OPCODE {
	QUERY("0000"),
	I_QUERY("0001"),
	STATUS("0010");

	private final String opCode;

	OPCODE(String opCode) {
		this.opCode = opCode;
	}

	public String getOpCode() {
		return opCode;
	}

	public static OPCODE generateBy(String fourBitBinary) {
		return switch (fourBitBinary) {
			case "0000" -> OPCODE.QUERY;
			case "0001" -> OPCODE.I_QUERY;
			case "0010" -> OPCODE.STATUS;
			default -> throw new IllegalArgumentException("잘못된 binary 값이 들어왔습니다.");
		};
	}
}
