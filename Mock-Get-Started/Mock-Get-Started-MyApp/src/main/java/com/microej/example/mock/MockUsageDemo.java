/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock;

import com.microej.example.mock.style.StylesheetPopulator;
import com.microej.example.mock.widget.KeyboardPage;
import com.microej.example.mock.widget.MyPage;
import com.microej.example.mock.widget.TransitionDisplayable;

import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.mwt.Widget;

/**
 * This demo illustrates the widgets library.
 */
public class MockUsageDemo {

	/**
	 * Instantiates a new mock usage demo.
	 */
	// Prevents initialization.
	private MockUsageDemo() {
	}

	/**
	 * Application entry point.
	 *
	 * @param args not used.
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
		showMainPage();


	}

	/**
	 * Shows the main page of the application.
	 */
	public static void showMainPage() {
		Desktop desktop = createDesktop(new MyPage());
		StylesheetPopulator.initialize(desktop);
		displayPage(desktop);
	}


	/**
	 * Shows the keyboard page.
	 */
	public static void showKeyboardPage() {
		KeyboardPage keyboardpage = new KeyboardPage();
		Desktop desktop = createDesktop(keyboardpage.getContentWidget());
		displayPage(desktop);
	}

	/**
	 * Display page.
	 *
	 * @param desktop the desktop
	 */
	public static void displayPage(Desktop desktop) {
		StylesheetPopulator.initialize(desktop);
		TransitionDisplayable displayable = new TransitionDisplayable(desktop, true);
		Display.getDisplay().requestShow(displayable);
	}

	private static Desktop createDesktop(Widget page) {
		Desktop desktop = new Desktop();
		desktop.setWidget(page);
		return desktop;
	}

}

