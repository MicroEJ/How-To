/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.microej.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.image;

import java.io.IOException;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.transform.ImageDeformation;
import ej.microui.util.EventHandler;


/**
 * This class shows how to use the ImageDeformation utility class
 */
public class DeformedImages extends Displayable {

	public DeformedImages(Display display) {
		super(display);
	}

	@Override
	public void paint(GraphicsContext g) {

		final int DISPLAY_WIDTH = getDisplay().getWidth();
		final int DISPLAY_HEIGHT = getDisplay().getHeight();

		final int DISPLAY_CENTER_X = DISPLAY_WIDTH / 2;
		final int DISPLAY_CENTER_Y = DISPLAY_HEIGHT / 2;

		final int left = DISPLAY_WIDTH / 4;
		final int top = DISPLAY_HEIGHT / 4;
		final int right = 3 * DISPLAY_WIDTH / 4;
		final int bottom = 3 * DISPLAY_HEIGHT / 4;

		// fill up background with black
		g.setColor(Colors.BLACK);
		g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);


		try {
			Image microejImage = Image.createImage("/images/microej.png");

			final int IMAGE_WIDTH = microejImage.getWidth();
			final int IMAGE_HEIGHT = microejImage.getHeight();

			{ // top-left corner - stretch X and Y
				int xys[] = { 0, 0, DISPLAY_CENTER_X - 1, 0, DISPLAY_CENTER_X - 1, DISPLAY_CENTER_Y - 1, 0,
						DISPLAY_CENTER_Y - 1 };
				ImageDeformation.Singleton.draw(g, microejImage, xys, 0, 0,
						GraphicsContext.TOP | GraphicsContext.LEFT);
			}

			{ // top-right corner - stretch Y
				int xys[] = { 0, 0, IMAGE_WIDTH - 1, 0, IMAGE_WIDTH - 1,
						DISPLAY_CENTER_Y - 1, 0, DISPLAY_CENTER_Y - 1 };

				g.translate(right, 0);
				ImageDeformation.Singleton.draw(g, microejImage, xys, 0, 0,
						GraphicsContext.TOP | GraphicsContext.HCENTER);
				g.translate(-right, 0);

			}

			{ // bottom-left corner - stretch X
				int xys[] = { 0, 0, DISPLAY_CENTER_X - 1, 0, DISPLAY_CENTER_X - 1, IMAGE_HEIGHT - 1, 0,
						IMAGE_HEIGHT - 1 };
				g.translate(0, bottom);
				ImageDeformation.Singleton.draw(g, microejImage, xys, 0, 0,
						GraphicsContext.VCENTER | GraphicsContext.LEFT);
				g.translate(0, -bottom);
			}

			{ // bottom-right corner - NO stretch
				g.drawImage(microejImage, right, bottom, GraphicsContext.VCENTER | GraphicsContext.HCENTER);
			}


			// draw grid for visual alignment control
			// divide display in 4 sections
			g.setColor(Colors.YELLOW);
			g.drawLine(0, DISPLAY_CENTER_Y, DISPLAY_WIDTH, DISPLAY_CENTER_Y);
			g.drawLine(DISPLAY_CENTER_X, 0, DISPLAY_CENTER_X, DISPLAY_HEIGHT);

			// divide each section along horizontal & vertical axis
			g.setColor(Colors.MAGENTA);
			g.drawLine(0, top, DISPLAY_WIDTH, top);
			g.drawLine(0, bottom, DISPLAY_WIDTH, bottom);
			g.drawLine(left, 0, left, DISPLAY_HEIGHT);
			g.drawLine(right, 0, right, DISPLAY_HEIGHT);

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

		DeformedImages deformedImages = new DeformedImages(display);
		deformedImages.show();
	}

}
