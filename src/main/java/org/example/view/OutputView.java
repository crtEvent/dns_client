package org.example.view;

import java.util.ArrayList;

public class OutputView {

	public void printIpList(ArrayList<String> ipList) {
		for (var ip : ipList) {
			System.out.println(ip);
		}
	}
}
