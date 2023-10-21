package org.example;

import java.io.IOException;

import org.example.parser.RequestParser;
import org.example.parser.ResponseParser;
import org.example.view.InputView;
import org.example.view.OutputView;

public class Main {
	public static void main(String[] args) throws IOException {
		var hostName = new InputView().inputHostName();
		var requestPacket = new RequestParser().createRequestPacket(hostName);
		var response = new DnsResolver().query(requestPacket);
		var ipList = new ResponseParser().parseResponse(requestPacket, response);
		new OutputView().printIpList(ipList);
	}
}