/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.ui;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.microej.example.mock.MySP;

public class Thermostat extends JSlider {

	private static final long serialVersionUID = -1532088711239738950L;

	private static int MIN = -10;
	private static int MAX = 30;
	private static int CURRENT = 15;
	private final JLabel label;
	private int current;

	public Thermostat(JLabel label) {
		super(MIN, MAX, CURRENT);
		this.label = label;
		// Init the state to a different value as the current to force update.
		current = CURRENT - 1;
		ChangeListener listener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int newValue = getValue();
				if(current!=newValue){
					current = newValue;
					MySP.sendTemperature(newValue);
					updateLabel();
				}
			}
		};

		addChangeListener(listener);
		// Initialize the SP.
		listener.stateChanged(null);
	}

	private void updateLabel() {
		label.setText(" Temperature: " + getValue());
	}
}
