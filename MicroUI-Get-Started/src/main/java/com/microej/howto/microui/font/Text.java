/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.font;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * This class shows how to easily print text to the screen using the SourceSansPro font via MicroUI APIs.
 */
public class Text extends Displayable {

	private static final String FONT_PATH = "/fonts/source_sans_pro_24.ejf"; //$NON-NLS-1$
	private static final String HELLO_WORLD = "Hello World !"; //$NON-NLS-1$

	@Override
	public void render(GraphicsContext g) {

		final Display display = Display.getDisplay();

		final int displayWidth = display.getWidth();
		final int displayHeight = display.getHeight();

		// fill up background with black
		g.setColor(Colors.BLACK);
		Painter.fillRectangle(g, 0, 0, displayWidth, displayHeight);

		// use White color to render text
		g.setColor(Colors.WHITE);
		final Font font = Font.getFont(FONT_PATH);

		Painter.drawString(g, HELLO_WORLD, font, displayWidth / 2 - font.stringWidth(HELLO_WORLD) / 2,
				displayHeight / 2 - font.getHeight() / 2);

	}

	/**
	 * Entry Point for the example.
	 *
	 * @param args
	 *            Not used.
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		Text sample = new Text();
		Display.getDisplay().requestShow(sample);
	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
