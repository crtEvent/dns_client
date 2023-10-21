package org.example.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputView {

	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public String inputHostName() throws IOException {
		System.out.println("호스트명을 입력하세요. (e.g. naver.com)");
		return reader.readLine();
	}
}
