/*
 * Java
 *
 * Copyright 2018-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock;

import com.microej.example.mock.style.StylesheetPopulator;
import com.microej.example.mock.widget.KeyboardPage;
import com.microej.example.mock.widget.MyPage;

import ej.microui.MicroUI;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.mwt.Desktop;
import ej.mwt.MWT;
import ej.mwt.Panel;
import ej.widget.container.transition.SlideTransitionContainer;
import ej.widget.container.transition.TransitionContainer;

/**
 * This demo illustrates the widgets library.
 */
public class MockUsageDemo {

	private static Panel Panel;
	private static TransitionContainer TransitionContainer;
	private static MyPage mainPage;
	private static KeyboardPage keyboardPage;

	// Prevents initialization.
	private MockUsageDemo() {
	}

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		start();
	}

	/**
	 * Starts the demo.
	 */
	public static void start() {
		// Start MicroUI framework.
		MicroUI.start();

		// Initialize stylesheet rules.
		StylesheetPopulator.initialize();

		// Create the navigator.
		TransitionContainer = new SlideTransitionContainer(MWT.LEFT, false);

		// Show the main page.
		mainPage = new MyPage();
		TransitionContainer.show(mainPage, false);

		keyboardPage = new KeyboardPage();

		// Show the navigator.
		Desktop desktop = new Desktop() {
			@Override
			public boolean handleEvent(int event) {
				// set panel focus to null when we click on a blank space
				int type = Event.getType(event);
				if (type == Event.POINTER) {
					int action = Pointer.getAction(event);
					if (action == Pointer.RELEASED) {
						getPanel().setFocus(null);
					}
				}
				return super.handleEvent(event);
			}
		};
		Panel = new Panel();
		Panel.setWidget(TransitionContainer);
		Panel.showFullScreen(desktop);
		desktop.show();
	}

	/**
	 * Gets the panel.
	 *
	 * @return the panel.
	 */
	public static Panel getPanel() {
		return Panel;
	}

	/**
	 * Shows the main page of the application.
	 */
	public static void showMainPage() {
		TransitionContainer.show(mainPage, false);
	}

	/**
	 * Shows the keyboard page.
	 */
	public static void showKeyboardPage() {
		TransitionContainer.show(keyboardPage, true);
	}
}
