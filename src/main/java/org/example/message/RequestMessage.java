package org.example.message;

import java.io.ByteArrayOutputStream;

import org.example.message.section.Header;
import org.example.message.section.Question;
import org.example.view.tuple.InputCommand;

public class RequestMessage {
	private final Header header;
	private final Question question;

	private final byte[] bytes;

	private RequestMessage(Header header, Question question) {
		this.header = header;
		this.question = question;
		this.bytes = convertToBytes();
	}

	public static RequestMessage generate(int id, InputCommand commands) {
		return new RequestMessage(
			Header.generateForRequestQuery(id),
			Question.generateForRequest(commands.hostName(), commands.qtype())
		);
	}

	public byte[] getBytes() {
		return bytes;
	}

	private byte[] convertToBytes() {
		var sections = new byte[][]{
			header.getBytes(),
			question.getBytes()
		};

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		for (byte[] section : sections) {
			outputStream.write(section, 0, section.length);
		}

		return outputStream.toByteArray();
	}

	@Override
	public String toString() {
		return header.toString() + "\n" + question.toString();
	}
}
