/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mock;

import com.is2t.hil.HIL;
import com.microej.example.mock.ui.MyMockFrame;

/**
 *
 */
public class MySNI {

	private static char[] inputText;
	private static char[] outputText;

	private static Object mutex = new Object();

	/**
	 * Provides the shared array to the native world.
	 *
	 * @param input
	 *            the array to write a data from the native world.
	 * @param output
	 *            the array to write a data to the native world.
	 */
	public static void init(char[] input, char[] output) {
		inputText = input;
		outputText = output;

		// Force init of instance that will open the UI.
		MyMockFrame.INSTANCE.getTemperature();
	}

	/**
	 * Switches on/off a native light.
	 *
	 * @param on
	 *            the state to set the light.
	 */
	public static void switchLight(boolean on) {
		MyMockFrame.INSTANCE.switchLight(on);
	}

	/**
	 * Sends a text to the native world.
	 *
	 * @param length
	 *            the length of the text.
	 */
	public static void sendText(int length) {
		HIL.getInstance().refreshContent(inputText);
		MyMockFrame.INSTANCE.setText(new String(inputText, 0, length));
	}

	/**
	 * Gets a text from the native world. This function is blocking until a text
	 * is received.
	 *
	 * @return the length of the text.
	 */
	public static int getText() {
		// Wait for a text to be received.
		synchronized (mutex) {
			try {
				mutex.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String text = MyMockFrame.INSTANCE.getText();
		text.getChars(0, text.length(), outputText, 0);
		HIL.getInstance().flushContent(outputText);
		return text.length();
	}

	/**
	 * Sends the text.
	 */
	public static void sendText() {
		synchronized (mutex) {
			mutex.notify();
		}
	}
}
