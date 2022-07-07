/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.font;

import ej.library.ui.MicroEJColors;
import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * This class shows how to print pictograms to the screen using a custom font via MicroUI APIs.
 */
public class PictosWithCustomFont extends Displayable {

	private static final String CUSTOM_FONT_PATH = "/fonts/media-player-pictos-32px.ejf"; //$NON-NLS-1$

	/**
	 * We use the range 0xF04A-0xF04E, it matches the picto positions defined in media-player-pictos-32px.ejf.
	 */
	private static final char PICTO_START = 0xF04A;
	private static final char PICTO_END = 0xF04E;

	@Override
	public void render(GraphicsContext g) {

		final Display display = Display.getDisplay();

		final int displayWidth = display.getWidth();
		final int displayHeight = display.getHeight();

		// fill up background with black
		g.setColor(MicroEJColors.CONCRETE_BLACK_75);

		Painter.fillRectangle(g, 0, 0, displayWidth, displayHeight);

		// useful for antialiasing optimisation
		g.setBackgroundColor(MicroEJColors.CONCRETE_BLACK_75);

		final Font myCustomFont = Font.getFont(CUSTOM_FONT_PATH);

		// use White color to render text
		g.setColor(MicroEJColors.WHITE);

		final String message = getMessage();
		final int messageHalfWidth = myCustomFont.stringWidth(message) / 2;

		Painter.drawString(g, message, myCustomFont, displayWidth / 2 - messageHalfWidth, displayHeight / 4);

		// use 'Chick' color to render text
		g.setColor(MicroEJColors.CHICK);

		Painter.drawString(g, message, myCustomFont, displayWidth / 2 - messageHalfWidth,
				(displayHeight / 4) * 3 - myCustomFont.getHeight() / 2);

	}

	private String getMessage() {

		final StringBuilder messageBuilder = new StringBuilder();
		for (char pictoId = PICTO_START; pictoId <= PICTO_END; pictoId++) {
			messageBuilder.append(pictoId);
			if (pictoId != PICTO_END) {
				messageBuilder.append(' ');
			}
		}

		return messageBuilder.toString();
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

		PictosWithCustomFont sample = new PictosWithCustomFont();
		Display.getDisplay().requestShow(sample);
	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
