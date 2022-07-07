/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.events;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;

/**
 * This class shows how to handle touch input events.
 */
public class InputEvents extends Displayable {

	private static final String FONT_PATH = "/fonts/source_sans_pro_24.ejf"; //$NON-NLS-1$

	private int nbClicks;
	private int pointerX;
	private int pointerY;
	private String topMessage;
	private String bottomMessage;

	/**
	 * Instantiates an InputEvents instance.
	 */
	public InputEvents() {
		this.nbClicks = 0;
		this.pointerX = 0;
		this.pointerY = 0;
		this.topMessage = "Please touch the screen to start!"; //$NON-NLS-1$
		this.bottomMessage = ""; //$NON-NLS-1$

	}

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

		final int displayHalfWidth = displayWidth / 2;
		final int displayHalfHeight = displayHeight / 2;
		final int fontHeight = font.getHeight();

		// draw texts
		Painter.drawString(g, this.topMessage, font, displayHalfWidth - font.stringWidth(this.topMessage) / 2,
				displayHalfHeight - fontHeight);

		Painter.drawString(g, this.bottomMessage, font, displayHalfWidth - font.stringWidth(this.bottomMessage) / 2,
				displayHalfHeight + fontHeight / 2);

	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int action = Buttons.getAction(event);
			this.pointerX = pointer.getAbsoluteX();
			this.pointerY = pointer.getAbsoluteY();
			if (action == Buttons.RELEASED) {
				this.nbClicks++;
			}
			this.topMessage = "Clicks count : " + this.nbClicks; //$NON-NLS-1$
			this.bottomMessage = "Pointer position at x = " + this.pointerX + ", y = " + this.pointerY; //$NON-NLS-1$ //$NON-NLS-2$
			requestRender();

			// event is consumed
			return true;
		}

		return false;

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

		InputEvents sample = new InputEvents();
		Display.getDisplay().requestShow(sample);
	}

}
