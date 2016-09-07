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

	private static final int TILE_SIZE = 20;

	public void display() {
		// We will need to access the display to draw stuff
		final Display display = Display.getDefaultDisplay();

		// A displayable is an object that will be drawn on the display
		Displayable displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				final int DISPLAY_WIDTH = display.getWidth();
				final int DISPLAY_HEIGHT = display.getHeight();

				final int nbTilesX = DISPLAY_WIDTH / TILE_SIZE;
				final int nbTilesY = DISPLAY_HEIGHT / TILE_SIZE;

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

				// Creates a new mutable image to draw the tile.
				// To leverage CPU usage, we will draw a full column and then
				// repeat the pattern horizontally.
				Image squareTileColumn = Image.createImage(TILE_SIZE, TILE_SIZE * nbTilesY);

				GraphicsContext columnGraphicsContext = squareTileColumn.getGraphicsContext();
				// ensure dark background for tile (images are filled with white pixels by default))
				columnGraphicsContext.setColor(Colors.BLACK);
				columnGraphicsContext.fillRect(0, 0, squareTileColumn.getWidth(), squareTileColumn.getHeight());

				// draw a column of tiles.
				columnGraphicsContext.setColor(Colors.YELLOW);
				for (int y = 0; y < nbTilesY; y++) {
					int yOffsetTile = TILE_SIZE * y;
					// A single triangle-shaped tile
					int xys[] = { 0, yOffsetTile + TILE_SIZE, TILE_SIZE / 2, yOffsetTile, TILE_SIZE,
							yOffsetTile + TILE_SIZE };
					columnGraphicsContext.fillPolygon(xys);
				}

				final int alphaIncrement = GraphicsContext.OPAQUE / nbTilesX;
				int currentAlpha = 0;
				int yOffsetColumn = (DISPLAY_HEIGHT - squareTileColumn.getHeight()) / 2;
				for (int x = 0; x < nbTilesX; x++, currentAlpha += alphaIncrement) {
					g.drawImage(squareTileColumn, x * TILE_SIZE, yOffsetColumn,
							GraphicsContext.LEFT | GraphicsContext.TOP,
							currentAlpha);
				}
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
	 *             Not used.
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();

		TilingWithImages tilingWithImages = new TilingWithImages();
		tilingWithImages.display();

	}

}
