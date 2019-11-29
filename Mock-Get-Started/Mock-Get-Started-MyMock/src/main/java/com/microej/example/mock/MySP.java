/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock;

import ej.sp.ShieldedPlug;

/**
 *
 */
public class MySP {
	/**
	 * The SP IDs described in `resources\database-definition.xml`.
	 */
	private static final int DATABASE = 0;
	private static final int TEMPERATURE = 0;
	private static final int CONNECT = 1;

	/**
	 * Sends the connection state.
	 *
	 * @param connected
	 *            <code>true</code> if connected.
	 */
	public static void sendConnected(boolean connected) {
		byte[] b = new byte[1];
		if (connected) {
			b[0] = 1;
		} else {
			b[0] = 0;
		}
		getDB().write(MySP.CONNECT, b);
	}

	/**
	 * Send the temperature.
	 *
	 * @param temperature
	 *            the temperature
	 */
	public static void sendTemperature(int temperature) {
		getDB().writeInt(MySP.TEMPERATURE, temperature);
	}

	private static ShieldedPlug getDB() {
		return ShieldedPlug.getDatabase(MySP.DATABASE);
	}
}
