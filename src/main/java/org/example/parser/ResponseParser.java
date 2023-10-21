package org.example.parser;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class ResponseParser {

	public ArrayList<String> parseResponse(byte[] requestPacket, byte[] response) throws UnknownHostException {
		var ipList = new ArrayList<String>();

		var index = requestPacket.length;
		for (var i = index; i < response.length; i += 16) {
			var startIndex = i + 12;
			var endIndex = startIndex + 4;
			var byteCodes = Arrays.copyOfRange(response, startIndex, endIndex);

			InetAddress inetAddress = InetAddress.getByAddress(byteCodes);
			String ipAddress = inetAddress.getHostAddress();

			if (ipAddress.equals("0.0.0.0")) {
				break;
			}

			ipList.add(ipAddress);
		}

		return ipList;
	}
}
