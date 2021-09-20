/*
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.widget;


import com.microej.example.mock.MockUsageDemo;
import com.microej.example.mock.MySNI;

import ej.bon.Timer;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.service.ServiceFactory;
import ej.widget.basic.OnClickListener;
import ej.widget.container.Grid;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;
import ej.widget.container.SimpleDock;
import ej.widget.keyboard.Keyboard;
import ej.widget.keyboard.TextField;

/**
 * Represents the content of the keyboard page.
 */
/* package */ class KeyboardPageContent {

	/** Empty string. */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	/** First name place holder. */
	private static final String TEXT_TO_SEND = "Text To Send"; //$NON-NLS-1$
	/** Special button caption for submit text field. */
	private static final String SPECIAL_SUBMIT = "Submit"; //$NON-NLS-1$
	/** Maximum text field length. */
	private static final int MAX_TEXT_LENGTH = 50;
	/** Text field length. */
	private static final int TEXT_FIELD_LENGTH = 25;

	/** Keyboard object. */
	private final Keyboard keyboard;
	/** First name text field. */
	private final TextField firstName;
	/** Page content. */
	private final SimpleDock content;

	/* package */ KeyboardPageContent() {
		Timer timer = ServiceFactory.getService(Timer.class, Timer.class);

		SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);
		this.keyboard = new Keyboard(timer);
		this.firstName = createTextField(TEXT_TO_SEND, timer);


		dock.addClassSelector(KeyboardPage.CONTENT);

		// list
		List list = new List(LayoutOrientation.VERTICAL);
		list.addChild(this.firstName);
		list.addClassSelector(KeyboardPage.FORM);

		Grid textGrid = new Grid(LayoutOrientation.HORIZONTAL, 2);
		textGrid.addChild(list);

		dock.setFirstChild(textGrid);
		dock.setCenterChild(this.keyboard);

		this.content = dock;
	}

	private TextField createTextField(String placeHolder, Timer timer) {
		TextField textField = new TextField(EMPTY_STRING, placeHolder, TEXT_FIELD_LENGTH, timer) {
			@Override
			public boolean handleEvent(int event) {
				if (Event.getType(event) == Pointer.EVENT_TYPE && Buttons.isPressed(event)) {
					activateKeyboardField(this);
				}
				return super.handleEvent(event);
			}
		};
		textField.setMaxTextLength(MAX_TEXT_LENGTH);
		textField.setEnabled(true);
		return textField;
	}

	private void activateKeyboardField(TextField keyboardText) {
		keyboardText.setActive(true);

		this.keyboard.setSpecialKey(SPECIAL_SUBMIT, Keyboard.SPECIAL_KEY_SELECTOR, new OnClickListener() {
			@Override
			public void onClick() {
				submit();
			}
		});

		SimpleDock pageContent = this.content;
		assert pageContent != null;
		pageContent.requestRender();

		this.keyboard.getEventGenerator().setEventHandler(keyboardText);
	}

	private void submit() {
		MySNI.sendText(firstName.getText());
		MockUsageDemo.showMainPage();
	}

	/* package */ Widget getContentWidget() {
		return this.content;
	}

}
