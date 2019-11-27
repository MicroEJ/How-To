/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
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
