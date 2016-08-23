/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.image;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.util.EventHandler;


/**
 * This class shows how to draw in an Image Graphics Context
 */
public class TilingWithImages {

	final int TILE_SIZE = 20;

	public void display() {

		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		// A displayable is an object that will draw on the display
		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, display.getWidth(), display.getHeight());

				Image squareTile = Image.createImage(TILE_SIZE, TILE_SIZE);

				GraphicsContext graphicsContext = squareTile.getGraphicsContext();
				// ensure dark background for tile (images are filled with white
				// pixels by default))
				graphicsContext.setColor(Colors.BLACK);
				graphicsContext.fillRect(0, 0, TILE_SIZE, TILE_SIZE);

				// draw
				graphicsContext.setColor(Colors.YELLOW);
				int xys[] = { 0, TILE_SIZE, TILE_SIZE / 2, 0, TILE_SIZE, TILE_SIZE };
				graphicsContext.fillPolygon(xys);

				final int nbTilesX = display.getWidth() / TILE_SIZE;
				final int nbTilesY = display.getHeight() / TILE_SIZE;
				int alphaIncrement = 0xFF / nbTilesX;
				int currentAlpha = 0;

				for (int x = 0; x < nbTilesX; x++, currentAlpha += alphaIncrement) {
					for (int y = 0; y < nbTilesY; y++) {
						g.drawImage(squareTile, x * TILE_SIZE, y * TILE_SIZE,
								GraphicsContext.LEFT | GraphicsContext.TOP, currentAlpha);
					}
				}

			}

			@Override
			public EventHandler getController() {
				// No event handling is performed for this sample, therefore do
				// not bother with implementing this
				return null;
			}
		};

		displayable.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		TilingWithImages tilingWithImages = new TilingWithImages();
		tilingWithImages.display();
	}

}
