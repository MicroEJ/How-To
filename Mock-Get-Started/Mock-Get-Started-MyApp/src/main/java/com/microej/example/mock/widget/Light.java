/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mock.widget;

import com.microej.example.mock.MySNI;

import ej.widget.basic.image.ImageSwitch;
import ej.widget.composed.Toggle;
import ej.widget.listener.OnStateChangeListener;

/**
 *
 */
public class Light extends Toggle implements OnStateChangeListener {

	/**
	 *
	 */
	public Light() {
		super(new ImageSwitch(), "Light");
		MySNI.switchLight(isChecked());
		addOnStateChangeListener(this);
	}

	@Override
	public void onStateChange(boolean newState) {
		MySNI.switchLight(newState);
	}

}
