package org.example.message.section;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class RecordList {
	private final List<Record> records;

	private RecordList(List<Record> records) {
		this.records = records;
	}

	public static RecordList generateBy(DatagramPacket receivedPacket, Header header, Question question) {

		var recordList = new ArrayList<Record>();
		var startIndex = header.getLength() + question.getLength();

		while (startIndex < receivedPacket.getLength()) {
			var record = Record.generateBy(receivedPacket, startIndex);
			recordList.add(record);
			startIndex += record.getLength();
		}

		return new RecordList(recordList);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[Resource Record Section]\n");
		for (Record record : records) {
			builder.append(record.toString()).append(System.lineSeparator());
		}
		return builder.toString();
	}
}
