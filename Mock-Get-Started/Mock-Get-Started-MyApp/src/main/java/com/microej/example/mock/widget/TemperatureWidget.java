/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
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
