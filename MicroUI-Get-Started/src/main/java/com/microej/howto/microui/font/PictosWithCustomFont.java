/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.font;


import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.util.EventHandler;

/**
 * This class shows how to print pictograms to the screen using a custom font
 * via MicroUI APIs
 *
 * @see <b><code>Help > Help Contents > Font Designer User Guide</code></b> for
 *      indications on how to create a MicroEJ font
 */
public class PictosWithCustomFont {

	private static final int CUSTOM_FONT_ID = 81;
	private static final int CUSTOM_FONT_SIZE = 32;

	/**
	 * We use the range 0xF04A-0xF04E, it matches the picto positions defined in
	 * media-player-pictos-32px.ejf.
	 */
	private static final char PICTO_START = 0xF04A;
	private static final char PICTO_END = 0xF04E;

	public void display() {
		final Display display = Display.getDefaultDisplay();

		final Font myCustomFont = Font.getFont(CUSTOM_FONT_ID, CUSTOM_FONT_SIZE, Font.STYLE_PLAIN);
		if (myCustomFont == Font.getDefaultFont()) {
			System.out.println("Unable to find custom font! Using default font instead");
		}

		final StringBuilder messageBuilder = new StringBuilder();
		for (char pictoId = PICTO_START; pictoId <= PICTO_END; pictoId++) {
			messageBuilder.append(pictoId);
			if (pictoId != PICTO_END) {
				messageBuilder.append(" ");
			}
		}
		final String message = messageBuilder.toString();


		// A displayable is an object that will be drawn on the display
		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {


				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, display.getWidth(), display.getHeight());

				g.setFont(myCustomFont);
				// use White color to render text
				g.setColor(Colors.WHITE);

				g.drawString(message, display.getWidth() / 2, display.getHeight() / 4,
						GraphicsContext.HCENTER | GraphicsContext.VCENTER);

				// use Yellow color to render text
				g.setColor(Colors.YELLOW);
				g.drawString(message, display.getWidth() / 2, (display.getHeight() / 4) * 3,
						GraphicsContext.HCENTER | GraphicsContext.VCENTER);

			}

			@Override
			public EventHandler getController() {
				// No event handling is required for this sample.
				return null;
			}
		};

		displayable.show();
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
		sample.display();
	}

}
