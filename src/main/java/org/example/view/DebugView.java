package org.example.view;

import java.net.DatagramPacket;

public class DebugView {

	public static void printBytes(byte[] bytes) {
		System.out.println("배열의 사이즈는요? " + bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			System.out.printf("%02x ", bytes[i]);
			if (i % 2 == 1) {
				System.out.println();
			}
		}
	}

	public static void printDatagramPacket(DatagramPacket bytes) {
		var response = bytes.getData();
		System.out.println("배열의 사이즈는요? " + response.length);
		System.out.printf("received: %d bytes%n", bytes.getLength());
		for (int i = 0; i < bytes.getLength(); i++) {
			System.out.printf("%02x ", response[i]);
			if (i % 2 == 1) {
				System.out.println();
			}
		}
	}

}
