package org.example.message;

import java.net.DatagramPacket;

import org.example.message.section.Header;
import org.example.message.section.Question;
import org.example.message.section.RecordList;

public class ResponseMessage {
	private final Header header;
	private final Question question;
	private final RecordList records;

	private ResponseMessage(Header header, Question question, RecordList records) {
		this.header = header;
		this.question = question;
		this.records = records;
	}

	public static ResponseMessage generateBy(DatagramPacket receivedPacket) {
		var header = Header.generateBy(receivedPacket);
		var question = Question.generateBy(receivedPacket);
		var records = RecordList.generateBy(receivedPacket, header, question);

		return new ResponseMessage(
			header, question, records
		);
	}

	@Override
	public String toString() {
		return "[◀◀] Response Message\n"
			+ header.toString() + "\n"
			+ question.toString() + "\n"
			+ records.toString();
	}
}
