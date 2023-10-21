package org.example;

import java.io.IOException;

import org.example.message.RequestGenerator;
import org.example.message.ResponseParser;
import org.example.view.InputView;
import org.example.view.OutputView;

public class Main {
	public static void main(String[] args) throws IOException {
		var hostName = new InputView().inputHostName();
		var requestPacket = new RequestGenerator().generateRequestPacket(hostName);
		var response = new DnsResolver().query(requestPacket);
		var ipList = new ResponseParser().extractIpAddresses(requestPacket, response);
		new OutputView().printIpList(ipList);
	}
}