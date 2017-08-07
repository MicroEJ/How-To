/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
 */
package com.microej.howto.microui.image;

import java.io.IOException;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.util.EventHandler;

/**
 * This class shows how to render an image using alpha blending
 */
public class TransparentImages extends Displayable {

	public TransparentImages(Display display) {
		super(display);
	}

	@Override
	public void paint(GraphicsContext g) {

		final int DISPLAY_WIDTH = getDisplay().getWidth();
		final int DISPLAY_HEIGHT = getDisplay().getHeight();

		// fill up background with black
		g.setColor(Colors.BLACK);
		g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

		// fill up half the area with white
		g.setColor(Colors.WHITE);
		g.fillRect(0, 0, DISPLAY_WIDTH/2, DISPLAY_HEIGHT);

		// draw transparent image at the center
		try {
			Image microejImage = Image.createImage("/images/microej.png");
			g.drawImage(microejImage, DISPLAY_WIDTH / 2, DISPLAY_HEIGHT / 2,
					GraphicsContext.HCENTER | GraphicsContext.VCENTER, GraphicsContext.OPAQUE / 2);
		} catch (IOException e) {
			throw new AssertionError(e);
		}

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
	 *             Not used.
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		TransparentImages transparentImages = new TransparentImages(display);
		transparentImages.show();
	}

}
