/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.example.mock.widget;

import com.microej.example.mock.MockUsageDemo;
import com.microej.example.mock.MySNI;
import com.microej.example.mock.keyboard.LowerCaseLayout;
import com.microej.example.mock.keyboard.NumericLayout;
import com.microej.example.mock.keyboard.SymbolLayout;
import com.microej.example.mock.keyboard.UpperCaseLayout;
import com.microej.example.mock.style.ClassSelectors;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.container.Dock;
import ej.widget.container.List;
import ej.widget.container.Scroll;
import ej.widget.keyboard.Keyboard;
import ej.widget.keyboard.KeyboardText;
import ej.widget.keyboard.Layout;
import ej.widget.listener.OnClickListener;
import ej.widget.listener.OnFocusListener;

/**
 * This page illustrates a keyboard.
 */
public class KeyboardPage extends Dock {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final String FIRST_NAME = "Text to send"; //$NON-NLS-1$
	private static final String RESULT_EMPTY = "Please set the text to send"; //$NON-NLS-1$
	private static final String SPECIAL_SUBMIT = "Send"; //$NON-NLS-1$

	private static final int MAX_TEXT_LENGTH = 33;

	private final Keyboard keyboard;

	private KeyboardText textInput;
	private Label resultLabel;

	/**
	 * Creates a keyboard page.
	 */
	public KeyboardPage() {
		super();

		this.keyboard = new Keyboard();

		// set keyboard layouts
		Layout[] layouts = new Layout[] { new LowerCaseLayout(), new UpperCaseLayout(), new NumericLayout(),
				new SymbolLayout() };
		setKeyboardLayouts(layouts);

		Widget editionContent = createForm();
		setCenter(editionContent);
	}

	/**
	 * Creates the page form.
	 *
	 * @return a widget containing the form.
	 */
	/**
	 * Creates the widget representing the main content of the page
	 */
	private Widget createForm() {
		// first name
		this.textInput = new KeyboardText(EMPTY_STRING, FIRST_NAME);
		this.textInput.setMaxTextLength(MAX_TEXT_LENGTH);
		this.textInput.addOnFocusListener(new OnFocusListener() {
			@Override
			public void onGainFocus() {
				showKeyboard(true);
			}

			@Override
			public void onLostFocus() {
				// Nothing to do.
			}
		});

		// result label
		this.resultLabel = new Label(RESULT_EMPTY);
		this.resultLabel.addClassSelector(ClassSelectors.RESULT_LABEL);

		// list
		List list = new List(false);
		list.add(this.textInput);
		list.add(this.resultLabel);
		list.addClassSelector(ClassSelectors.FORM);

		// scroll
		final Scroll scroll = new Scroll(false, false);
		scroll.setWidget(list);
		return scroll;
	}

	/**
	 * Sets the keyboard layouts to use
	 *
	 * @param keyboardLayouts
	 *            the four keyboard layouts to use
	 */
	public void setKeyboardLayouts(Layout[] keyboardLayouts) {
		this.keyboard.setLayouts(keyboardLayouts);
	}

	@Override
	public void showNotify() {
		super.showNotify();
		MockUsageDemo.getPanel().setFocus(this.textInput);
	}

	/**
	 * Gets the keyboard
	 *
	 * @return the keyboard
	 */
	protected Keyboard getKeyboard() {
		return this.keyboard;
	}

	/**
	 * Shows the keyboard
	 */
	protected void showKeyboard() {
		// show keyboard dialog
		if (this.keyboard.getParent() != this) {
			addBottom(this.keyboard);
			revalidate();
		}
	}

	/**
	 * Hides the keyboard
	 */
	protected void hideKeyboard() {
		remove(this.keyboard);
		revalidate();
	}

	private void showKeyboard(boolean first) {
		showKeyboard();
		getKeyboard().setSpecialKey(SPECIAL_SUBMIT, new OnClickListener() {
			@Override
			public void onClick() {
				submit();
			}
		});
		textInput.setActive(true);

		ej.microui.event.generator.Keyboard keyboard = ServiceLoaderFactory.getServiceLoader()
				.getService(ej.microui.event.generator.Keyboard.class);
		if (keyboard != null) {
			keyboard.setEventHandler(textInput);
		}
	}

	private void submit() {
		MySNI.sendText(textInput.getText());
		MockUsageDemo.show(new MyPage());
	}

}
