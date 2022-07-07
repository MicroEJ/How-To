/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.image;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;

/**
 * This class shows how to render an image using alpha blending
 */
public class TransparentImages extends Displayable {

	@Override
	public void render(GraphicsContext g) {

		final Display display = Display.getDisplay();

		final int displayWidth = display.getWidth();
		final int displayHeight = display.getHeight();

		// fill up background with black
		g.setColor(Colors.BLACK);
		Painter.fillRectangle(g, 0, 0, displayWidth, displayHeight);

		// fill up half the area with white
		g.setColor(Colors.WHITE);
		Painter.fillRectangle(g, 0, 0, displayWidth / 2, displayHeight);

		Image microejImage = Image.getImage("/images/mascot.png"); //$NON-NLS-1$

		final int halfImageWidth = microejImage.getWidth() / 2;
		final int halfImageHeight = microejImage.getHeight() / 2;

		Painter.drawImage(g, microejImage, displayWidth / 2 - halfImageWidth, displayHeight / 2 - halfImageHeight,
				GraphicsContext.OPAQUE / 2);

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

		TransparentImages sample = new TransparentImages();
		Display.getDisplay().requestShow(sample);
	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
