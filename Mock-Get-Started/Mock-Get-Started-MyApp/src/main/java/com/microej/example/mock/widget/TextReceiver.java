/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mock.widget;

import com.microej.example.mock.MySNI;

import ej.widget.basic.Label;

/**
 *
 */
public class TextReceiver extends Label {

	private static String TEXT = "Text received: ";
	private String text;

	/**
	 *
	 */
	public TextReceiver() {
		super();
		text = "";
		updateText();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					text = MySNI.getTextString();
					updateText();
				}
			}
		}).start();
	}

	/**
	 *
	 */
	private void updateText() {
		setText(TEXT + text);
	}
}
