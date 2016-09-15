/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.microej.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.font;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.util.EventHandler;

/**
 * This class shows how to print text to the screen using the default system
 * font via MicroUI APIs.
 * 
 * Note that default font use 1 bit per pixel (bpp) only and therefore does not support antialiasing.
 * On displays supporting 16 bpp, such limited fonts are not really suitable.
 */
public class Text extends Displayable{

	public Text(Display display) {
		super(display);
	}

	@Override
	public void paint(GraphicsContext g) {

		final int DISPLAY_WIDTH = getDisplay().getWidth();
		final int DISPLAY_HEIGHT = getDisplay().getHeight();

		// fill up background with black
		g.setColor(Colors.BLACK);
		g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

		// use White color to render text
		g.setColor(Colors.WHITE);
		g.drawString("Hello World !", DISPLAY_WIDTH / 2, DISPLAY_HEIGHT / 2,
				GraphicsContext.HCENTER | GraphicsContext.VCENTER);

	}

	@Override
	public EventHandler getController() {
		// No event handling is required for this sample.
		return null;
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

		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		Text sample = new Text(display);
		sample.show();
	}

}
