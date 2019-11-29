/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
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
