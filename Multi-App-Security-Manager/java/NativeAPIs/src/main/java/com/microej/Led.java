/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
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
	 * @throws SecurityException
	 *             when the SecurityManager does not give {@link LedPermission}.
	 */
	public static void switchLed(boolean on) throws SecurityException {
		SecurityManager securityManager = System.getSecurityManager();
		// SecurityManager may be null if the Kernel has not yet set it.
		if (securityManager != null) {
			securityManager.checkPermission(LedPermission.INSTANCE);
		}
		switchLedNative(on);
	}

	private static native void switchLedNative(boolean on);

	private static native void initNative();
}
