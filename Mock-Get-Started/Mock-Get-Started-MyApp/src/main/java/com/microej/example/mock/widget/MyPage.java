/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.widget;

import com.microej.example.mock.MockUsageDemo;

import ej.widget.basic.Button;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * The page which lists the items
 */
public class MyPage extends List {

	public MyPage() {
		super(LayoutOrientation.VERTICAL);

		addChild(new Connected());

		addChild(new Light());

		addChild(new TemperatureWidget());

		addChild(new TextReceiver());

		Button button = new Button("Send a text");
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				MockUsageDemo.showKeyboardPage();
			}
		});
		addChild(button);
	}


}
