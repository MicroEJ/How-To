/*
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.kernel;
import java.io.IOException;
import java.io.InputStream;

import ej.bon.Immortals;

/**
 * Input stream to load a feature.
 */
public class FeatureInputStream extends InputStream {

	private final static byte[] immortals;

	static {
		init();
		byte[] tmp = new byte[512];
		immortals = (byte[]) Immortals.setImmortal(tmp);
	}

	/**
	 * Waiting for an input stream to be available.
	 *
	 * @return an input stream.
	 * @see #isFeatureAvailable()
	 */
	public static InputStream getInputStream() {
		System.out.println("Waiting for a feature to be available");
		while (!isFeatureAvailable()) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Application available. Loading application.");
		return new FeatureInputStream();
	}

	private FeatureInputStream() {
		super();
	}

	@Override
	public int read() throws IOException {
		byte[] b = new byte[1];
		int read = read(b, 0, 1);
		return (read > 0) ? b[0] : -1;
	}

	@Override
	public int read(byte[] out, int outOffset, int outLen) throws IOException {
		// Input sanity check
		if (out == null || outOffset < 0
				|| outLen > (out.length - outOffset) || outLen < 0) {
			throw new IllegalArgumentException("Bad arguments");
		}
		int r = readIntoArray(immortals, 0, Math.min(outLen, immortals.length));
		System.arraycopy(immortals, 0, out, outOffset, r);
		return r;
	}

	@Override
	public void close() throws IOException {
		closeFeature();
	}


	/**
	 * Checks whether a feature is available.
	 *
	 * @return <code>true</code> if the feature is available.
	 */
	private static native boolean isFeatureAvailable();

	/**
	 *
	 * @param outOffset
	 *            write start offset in array
	 * @param length
	 *            number of bytes to read
	 * @return the number of bytes read or -1 if EOF
	 */
	private static native int readIntoArray(byte[] array, int outOffset, int length);

	/**
	 * Closes the associated ressources.
	 */
	private static native void closeFeature();

	/**
	 * Initializes the stack. Called once before any other function.
	 */
	private static native void init();
}
