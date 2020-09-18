/*
 * Java
 *
 * Copyright 2018-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.widget;

import com.microej.example.mock.MockUsageDemo;

import ej.widget.basic.Button;
import ej.widget.basic.Label;
import ej.widget.container.List;
import ej.widget.container.Split;
import ej.widget.listener.OnClickListener;
import ej.widget.navigation.page.Page;

/**
 *
 */
public class MyPage extends Page {

	public MyPage() {
		super();
		List list = new List(false);

		list.add(new Connected());

		Split lightSplit = new Split();
		lightSplit.setFirst(new Label("Light"));
		lightSplit.setLast(new Light());
		list.add(lightSplit);

		list.add(new TemperatureWidget());

		list.add(new TextReceiver());

		Button button = new Button("Send a text");
		button.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				MockUsageDemo.showKeyboardPage();
			}
		});
		list.add(button);

		setWidget(list);
	}

}
