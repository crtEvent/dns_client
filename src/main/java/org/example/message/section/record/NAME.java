package org.example.message.section.record;

/**
 * NAME에 올 수 있는 CASE들
 *
 * 평범한 NAME이 온다.
 *  - 이 경우 NAME의 마지막 바이트는 0x00
 *
 * 압축된 메시지가 온다
 *  - 압축된 메시지의 처음 두 바이트는 11로 시작한다.
 *
 * 도메인 끝에 압축 메시지가 온다
 *
 */

/**
 * a domain name to which this resource record pertains.
 */
public class NAME {
	private final byte[] name;

	private NAME(byte[] name) {
		this.name = name;
	}

	public static NAME generateByBytes(byte[] bytes) {
		return new NAME(bytes);
	}

	public byte[] getBytes() {
		return name;
	}

	public int getLength() {
		return name.length;
	}

}
