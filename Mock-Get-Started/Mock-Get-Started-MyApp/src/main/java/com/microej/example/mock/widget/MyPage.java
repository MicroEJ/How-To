/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mock.widget;

import com.microej.example.mock.MockUsageDemo;

import ej.widget.basic.Button;
import ej.widget.container.List;
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
		list.add(new Light());
		list.add(new TemperatureWidget());
		list.add(new TextReceiver());
		Button button = new Button("Send a text");
		button.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				MockUsageDemo.show(new KeyboardPage());
			}
		});
		list.add(button);

		setWidget(list);
	}

}
