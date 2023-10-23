package org.example;

import java.io.IOException;
import java.util.Arrays;

import org.example.message.RequestMessage;
import org.example.message.ResponseParser;
import org.example.view.DebugView;
import org.example.view.InputView;
import org.example.view.OutputView;

public class Main {
	public static void main(String[] args) throws IOException {
		// var hostName = new InputView().inputHostName();
		// var requestPacket = new RequestGenerator().generateRequestPacket(hostName);
		// var response = new DnsResolver().query(requestPacket);
		// DebugView.printBytes(response);
		// var ipList = new ResponseParser().extractIpAddresses(requestPacket, response);
		// new OutputView().printIpList(ipList);

		var commands = new InputView().inputCommand();
		var requestPacket = RequestMessage.generate(1, commands);
		System.out.println("1. -----------------------");
		//DebugView.printBytes(requestPacket.getBytes());
		System.out.println(requestPacket.toString());
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		System.out.println("2. -----------------------");
		var response = new DnsResolver().query(requestPacket.getBytes());
		DebugView.printDatagramPacket(response);
		var ipList = new ResponseParser().extractIpAddresses(requestPacket.getBytes(), response);
		new OutputView().printIpList(ipList);
	}
}