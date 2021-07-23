/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.widget;

import com.microej.example.mock.MySP;
import com.microej.example.mock.style.Images;

import ej.widget.basic.ImageWidget;
import ej.widget.basic.Label;
import ej.widget.container.Flow;
import ej.widget.container.LayoutOrientation;
import ej.widget.listener.OnStateChangeListener;

/**
 *
 */
public class Connected extends Flow implements OnStateChangeListener {

	private final ImageWidget image;

	public Connected() {
		super(LayoutOrientation.HORIZONTAL);
		image = new ImageWidget(Images.CONNECTED);
		addChild(new Label("Connected"));
		addChild(image);
		MySP.INSTANCE.setConnectionListener(this);
		onStateChange(MySP.INSTANCE.isConnected());
	}

	@Override
	public void onStateChange(boolean newState) {
		if (newState) {
			image.setImagePath(Images.CONNECTED);
		} else {
			image.setImagePath(Images.UNCONNECTED);
		}
		image.requestRender();
	}
}
