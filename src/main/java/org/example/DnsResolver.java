package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DnsResolver {

	public byte[] query(byte[] requestPacket) {

		var response = new byte[1024];

		try (var datagramSocket = new DatagramSocket()) {
			datagramSocket.connect(InetAddress.getByName("1.1.1.1"), 53);
			datagramSocket.send(new DatagramPacket(requestPacket, requestPacket.length));

			var received = new DatagramPacket(response, response.length);
			datagramSocket.receive(received);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return response;
	}
}
