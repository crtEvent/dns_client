package org.example.message.section.record;

import java.util.Arrays;

/**
 * two octets which specify the class of the data in the
 * RDATA field.
 */
public enum CLASS {
	IN(new byte[]{0x00, 0x01}),
	CS(new byte[]{0x00, 0x02}),
	CH(new byte[]{0x00, 0x03}),
	HS(new byte[]{0x00, 0x04});

	private final byte[] code;

	CLASS(byte[] code) {
		this.code = code;
	}

	public byte[] getBytes() {
		return code;
	}

	public static CLASS generateByTwoBytes(byte[] receivedBytes) {
		for (CLASS clazz : CLASS.values()) {
			if(Arrays.equals(clazz.getBytes(), receivedBytes)) {
				return clazz;
			}
		}
		throw new IllegalArgumentException("잘못된 bytes 값이 들어왔습니다. CLASS으로 변환할 수 없습니다.");
	}
}
