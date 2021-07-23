/*
 * Java
 *
 * Copyright 2018-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.widget;

import com.microej.example.mock.MySNI;

import ej.widget.basic.Label;
import ej.widget.container.Flow;
import ej.widget.container.LayoutOrientation;
import ej.widget.listener.OnStateChangeListener;

public class Light extends Flow implements OnStateChangeListener {

	private final Toggle toggle;

	public Light() {
		super(LayoutOrientation.HORIZONTAL);
		toggle = new Toggle("");
		toggle.addOnStateChangeListener(this);
		addChild(new Label("Light"));
		addChild(toggle);
		MySNI.switchLight(toggle.isChecked());
	}

	@Override
	public void onStateChange(boolean newState) {
		MySNI.switchLight(newState);
	}

}
