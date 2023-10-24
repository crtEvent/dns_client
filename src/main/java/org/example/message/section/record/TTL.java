package org.example.message.section.record;

/**
 * a 32 bit unsigned integer that specifies the time
 * interval (in seconds) that the resource record may be
 * cached before it should be discarded.  Zero values are
 * interpreted to mean that the RR can only be used for the
 * transaction in progress, and should not be cached.
 */
public class TTL {
	private final byte[] ttl;

	private TTL(byte[] ttl) {
		this.ttl = ttl;
	}

	public static TTL generateByFourBytes(byte[] bytes) {
		return new TTL(bytes);
	}

	public byte[] getBytes() {
		return ttl;
	}
}
