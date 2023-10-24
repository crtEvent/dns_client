package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.example.message.RequestMessage;

public class DnsResolver {

	/**
	 * <p>The UDP message size returned by the DNS server is limited to 512 octets.</p>
	 * <i>[Reference from: RFC 1035 - 2.3.4. Size limits]</i>
	 */
	private static final int UDP_SIZE_LIMITS = 512;

	public DatagramPacket query(RequestMessage requestMessage) {

		var requestPacket = requestMessage.getBytes();

		try (var datagramSocket = new DatagramSocket()) {
			datagramSocket.connect(InetAddress.getByName("111.118.0.1"), 53);
			datagramSocket.send(new DatagramPacket(requestPacket, requestPacket.length));

			var response = new byte[UDP_SIZE_LIMITS];
			var receivedPacket = new DatagramPacket(response, response.length);
			datagramSocket.receive(receivedPacket);

			// TYPE이 NS면 재요청한다 어디로?

			return receivedPacket;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// public DatagramPacket query(byte[] requestPacket) {
	//
	// 	try (var datagramSocket = new DatagramSocket()) {
	// 		datagramSocket.connect(InetAddress.getByName("111.118.0.1"), 53);
	// 		datagramSocket.send(new DatagramPacket(requestPacket, requestPacket.length));
	//
	// 		var response = new byte[1024];
	// 		var received = new DatagramPacket(response, response.length);
	// 		datagramSocket.receive(received);
	//
	// 		// TYPE이 NS면 재요청한다 어디로?
	//
	// 		return received;
	// 	} catch (IOException e) {
	// 		throw new RuntimeException(e);
	// 	}
	// }
}
