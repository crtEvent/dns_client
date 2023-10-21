package org.example.parser;

import java.util.ArrayList;
import java.util.List;

public class RequestParser {

	private static final ArrayList<Byte> HEADER_SECTION;
	private static final ArrayList<Byte> QTYPE;
	private static final ArrayList<Byte> QCLASS;

	static {
		HEADER_SECTION = new ArrayList<Byte>(List.of(
			(byte) 0x00, (byte) 0x01, // ID
			(byte) 0x00, (byte) 0x00, // FLAG
			(byte) 0x00, (byte) 0x01, // QDCOUNT
			(byte) 0x00, (byte) 0x00, // ANCOUNT
			(byte) 0x00, (byte) 0x00, // NSCOUNT
			(byte) 0x00, (byte) 0x00  // ARCOUNT
		));
		QTYPE = new ArrayList<Byte>(List.of(
			(byte) 0x00, (byte) 0x01) // QTYPE  : A(1)
		);
		QCLASS = new ArrayList<Byte>(List.of(
			(byte) 0x00, (byte) 0x01) // QCLASS : IN(1) the Internet
		);
	}

	public byte[] createRequestPacket(String hostName) {
		var qname = parseHostNameToQName(hostName);

		var requestMessage = new ArrayList<Byte>();
		requestMessage.addAll(HEADER_SECTION);
		requestMessage.addAll(qname);
		requestMessage.addAll(QTYPE);
		requestMessage.addAll(QCLASS);

		var requestPacket = new byte[requestMessage.size()];
		for (int i = 0; i < requestMessage.size(); i++) {
			requestPacket[i] = requestMessage.get(i);
		}

		return requestPacket;
	}

	private ArrayList<Byte> parseHostNameToQName(String hostName) {
		var hostParts = hostName.split("\\.");
		var qname = new ArrayList<Byte>();

		for (var part : hostParts) {
			qname.add((byte) part.length());

			for (var byteCode : part.getBytes()) {
				qname.add(byteCode);
			}
		}

		qname.add((byte) 0x00);

		return qname;
	}
}
