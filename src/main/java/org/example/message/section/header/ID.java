package org.example.message.section.header;

import org.example.utill.ByteConvertor;
import org.example.utill.ByteValidator;

/**
 * A 16 bit identifier assigned by the program that
 * generates any kind of query.  This identifier is copied
 * the corresponding reply and can be used by the requester
 * to match up replies to outstanding queries.
 */
public class ID {
	private final int id;

	private ID(int id) {
		this.id = id;
	}

	public static ID generateBy(int id) {
		if (!ByteValidator.is16BitInteger(id)) {
			throw new IllegalArgumentException("ID는 16bit 범위 내에서 입력 가능합니다. (0~65535)");
		}
		return new ID(id);
	}

	public byte[] convertToTwoBytes() {
		return ByteConvertor.intToTwoBytes(id);
	}
}
