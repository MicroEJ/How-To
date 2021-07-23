/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock;

import ej.bon.Immortals;

/**
 * Gather the SNI functions.
 */
public class MySNI {

	public static char[] inputText;
	public static char[] outputText;

	static {
		Immortals.run(new Runnable() {

			@Override
			public void run() {
				inputText = new char[50];
				outputText = new char[50];
			}
		});
		init(inputText, outputText);
	}

	/**
	 * Sends a text to the native world.
	 *
	 * @param text
	 *            the text to send
	 */
	public static void sendText(String text) {
		text.getChars(0, text.length(), inputText, 0);
		sendText(text.length());
	}

	/**
	 * Gets a text from the native world.
	 *
	 * This function is blocking until a text a return.
	 *
	 * @return the text.
	 */
	public static String getTextString() {
		return new String(outputText, 0, getText());
	}

	/**
	 * Provides the shared array to the native world.
	 *
	 * @param input
	 *            the array to write a data from the native world.
	 * @param output
	 *            the array to write a data to the native world.
	 */
	private native static void init(char[] input, char[] output);

	/**
	 * Switches on/off a native light.
	 *
	 * @param on
	 *            the state to set the light.
	 */
	public native static void switchLight(boolean on);

	/**
	 * Sends a text to the native world.
	 *
	 * @param length
	 *            the length of the text.
	 */
	private native static void sendText(int length);

	/**
	 * Gets a text from the native world. This function is blocking until a text
	 * is received.
	 *
	 * @return the length of the text.
	 */
	private native static int getText();

}
