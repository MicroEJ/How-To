/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mock.widget;

import com.microej.example.mock.MySP;
import com.microej.example.mock.style.Images;

import ej.widget.basic.Image;
import ej.widget.basic.Label;
import ej.widget.container.Split;
import ej.widget.listener.OnStateChangeListener;

/**
 *
 */
public class Connected extends Split implements OnStateChangeListener {

	private final Image image;

	public Connected() {
		super();
		image = new ej.widget.basic.Image();
		setLast(image);
		setFirst(new Label("Connected"));
		MySP.INSTANCE.setConnectionListener(this);
		onStateChange(MySP.INSTANCE.isConnected());
	}

	@Override
	public void onStateChange(boolean newState) {
		if (newState) {
			image.setSource(Images.CONNECTED);
		} else {
			image.setSource(Images.UNCONNECTED);
		}
	}
}
