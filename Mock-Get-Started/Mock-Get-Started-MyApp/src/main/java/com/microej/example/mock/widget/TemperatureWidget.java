/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.widget;

import com.microej.example.mock.MySP;

import ej.widget.basic.Label;
import ej.widget.listener.OnValueChangeListener;

/**
 *
 */
public class TemperatureWidget extends Label implements OnValueChangeListener {

	private static String TEXT = "Temperature: ";
	private int temperature;

	public TemperatureWidget() {
		super();
		updateTemperature();
		MySP.INSTANCE.setTemperatureListener(this);
	}

	private void updateTemperature() {
		setText(TEXT + temperature);
	}

	@Override
	public void onValueChange(int newValue) {
		temperature = newValue;
		updateTemperature();
	}

	@Override
	public void onMaximumValueChange(int newMaximum) {

	}

	@Override
	public void onMinimumValueChange(int newMinimum) {

	}
}
