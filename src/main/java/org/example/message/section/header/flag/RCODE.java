package org.example.message.section.header.flag;

/**
 * Response code - this 4 bit field is set as part of
 *                 responses.  The values have the following
 *  <pre>
 * interpretation:
 *  0               No error condition
 *
 *  1               Format error - The name server was
 *                  unable to interpret the query.
 *
 *  2               Server failure - The name server was
 *                  unable to process this query due to a
 *                  problem with the name server.
 *
 *  3               Name Error - Meaningful only for
 *                  responses from an authoritative name
 *                  server, this code signifies that the
 *                  domain name referenced in the query does
 *                  not exist.
 *
 *  4               Not Implemented - The name server does
 *                  not support the requested kind of query.
 *
 *  5               Refused - The name server refuses to
 *                  perform the specified operation for
 *                  policy reasons.  For example, a name
 *                  server may not wish to provide the
 *                  information to the particular requester,
 *                  or a name server may not wish to perform
 *                  a particular operation (e.g., zone
 * </pre>
 */
public enum RCODE {
	NO_ERROR_CONDITION("0000")
	, FORMAT_ERROR("0001")
	, SERVER_FAILURE("0010")
	, NAME_ERROR("0011")
	, NOT_IMPLEMENTED("0100")
	, REFUSED("0101");

	private final String code;

	RCODE(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
