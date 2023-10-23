package org.example.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.example.message.section.question.QTYPE;
import org.example.view.tuple.InputCommand;

public class InputView {

	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public String inputHostName() throws IOException {
		System.out.println("호스트명을 입력하세요. (e.g. naver.com)");
		return reader.readLine();
	}

	public InputCommand inputCommand() throws IOException {
		System.out.println("'QTYPE'과 '호스트명'을 입력하세요.");
		var commands = reader.readLine().split(" ");

		if (commands.length != 2) {
			throw new IllegalArgumentException("잘못된 명령어 입니다. 다시 입력해 주세요.");
		}

		QTYPE qType = QTYPE.covertStringToQTYPE(commands[0]);
		String hostName = commands[1];

		return new InputCommand(qType, hostName);
	}
}
