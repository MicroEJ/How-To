/*
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej;

/**
 * Manage the Led.
 */
public class Led {

	static {
		initNative();
	}

	/**
	 * Switch the led state.
	 *
	 * @param on
	 *            <code>true</code> to switch on the led.
	 */
	public static void switchLed(boolean on) {
		switchLedNative(on);
	}

	private static native void switchLedNative(boolean on);

	private static native void initNative();
}
