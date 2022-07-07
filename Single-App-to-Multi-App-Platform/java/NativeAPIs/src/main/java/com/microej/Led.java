/*
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
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
