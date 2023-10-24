package org.example;

import java.io.IOException;

import org.example.message.RequestMessage;
import org.example.message.ResponseMessage;
import org.example.view.InputView;

public class Main {
	public static void main(String[] args) throws IOException {
		// var hostName = new InputView().inputHostName();
		// var requestPacket = new RequestGenerator().generateRequestPacket(hostName);
		// var response = new DnsResolver().query(requestPacket);
		// DebugView.printBytes(response);
		// var ipList = new ResponseParser().extractIpAddresses(requestPacket, response);
		// new OutputView().printIpList(ipList);

		var commands = new InputView().inputCommand();
		var requestMessage = RequestMessage.generate(1, commands);
		System.out.println("1. -----------------------");
		//DebugView.printBytes(requestPacket.getBytes());
		System.out.println(requestMessage.toString());
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		System.out.println("-----------------------");
		System.out.println("2. -----------------------");
		var response = new DnsResolver().query(requestMessage);
		var responseMessage = ResponseMessage.generateBy(response);
		System.out.println(responseMessage.toString());
		//DebugView.printDatagramPacket(response);
		//var ipList = new ResponseParser().extractIpAddresses(requestMessage.getBytes(), response);
		//new OutputView().printIpList(ipList);
	}
}