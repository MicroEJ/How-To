/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock;

import ej.sp.EmptyBlockException;
import ej.sp.ShieldedPlug;
import ej.widget.listener.OnStateChangeListener;
import ej.widget.listener.OnValueChangeListener;

/**
 * Gather the SP functionality
 */
public class MySP {

	/**
	 * The SP IDs described in `resources\database-definition.xml`.
	 */
	private final static int DATABASE = 0;
	private final static int TEMPERATURE = 0;
	private final static int CONNECT = 1;

	/**
	 * Singleton of the {@link MySP}.
	 */
	public final static MySP INSTANCE = new MySP();

	private OnStateChangeListener connectionListener;
	private OnValueChangeListener temperatureListener;

	/**
	 * Forbid instantiation.
	 */
	private MySP() {
		// Thread listening for the connect state.
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						getDB().waitFor(MySP.CONNECT);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					OnStateChangeListener changeListener = connectionListener;
					if (changeListener != null) {
						changeListener.onStateChange(isConnected());
					}
				}
			}
		}).start();

		// Thread listening for the temperature.
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						getDB().waitFor(MySP.TEMPERATURE);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					OnValueChangeListener listener = temperatureListener;
					if (listener != null) {
						listener.onValueChange(getTemperature());
					}
				}
			}

		}).start();
	}

	private int getTemperature() {
		try {
			return getDB().readInt(MySP.TEMPERATURE);
		} catch (EmptyBlockException e) {
			return 0;
		}
	}

	private static ShieldedPlug getDB() {
		return ShieldedPlug.getDatabase(DATABASE);
	}

	/**
	 * Sets the connectionListener.
	 *
	 * @param connectionListener the connectionListener to set.
	 */
	public void setConnectionListener(OnStateChangeListener connectionListener) {
		this.connectionListener = connectionListener;
	}

	/**
	 * Sets the temperatureListener.
	 *
	 * @param temperatureListener the temperatureListener to set.
	 */
	public void setTemperatureListener(OnValueChangeListener temperatureListener) {
		this.temperatureListener = temperatureListener;
	}

	/**
	 * Checks the connection state.
	 *
	 * @return the connection state.
	 */
	public boolean isConnected() {
		byte[] connectedByte = new byte[1];
		boolean connected = false;
		try {
			getDB().read(MySP.CONNECT, connectedByte);
			connected = connectedByte[0] != 0;
		} catch (EmptyBlockException e) {
			// Do nothing.
		}
		return connected;
	}
}
