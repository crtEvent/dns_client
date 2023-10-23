package org.example.view.tuple;

import org.example.message.section.question.QTYPE;

public record InputCommand(
	QTYPE qtype,
	String hostName
) { }
