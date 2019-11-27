/*
 * Java
 *
 * Copyright 2016-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.image;

import ej.library.ui.MicroEJColors;
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
public class TilingWithImages extends Displayable {

	private static final int TILE_SIZE = 24;

	public TilingWithImages(Display display) {
		super(display);
	}

	@Override
	public void paint(GraphicsContext g) {

		final int DISPLAY_WIDTH = getDisplay().getWidth();
		final int DISPLAY_HEIGHT = getDisplay().getHeight();

		final int nbTilesX = DISPLAY_WIDTH / TILE_SIZE;
		final int nbTilesY = DISPLAY_HEIGHT / TILE_SIZE;

		// fill up background with black
		g.setColor(Colors.BLACK);
		g.fillRect(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

		// Creates a new mutable image to draw the tile.
		Image squareTile = Image.createImage(TILE_SIZE, TILE_SIZE);
		//draw one tile
		{
			GraphicsContext squareTileGraphicsContext = squareTile.getGraphicsContext();
			squareTileGraphicsContext.setColor(MicroEJColors.CONCRETE_BLACK_75);
			squareTileGraphicsContext.fillRect(0, 0, squareTile.getWidth(), squareTile.getHeight());
			squareTileGraphicsContext.setColor(MicroEJColors.CHICK);
			final int BORDER_THICKNESS = 2;
			int xys[] = {
					0 + BORDER_THICKNESS , 0 + BORDER_THICKNESS,
					TILE_SIZE - BORDER_THICKNESS , 0 + BORDER_THICKNESS,
					TILE_SIZE - BORDER_THICKNESS, TILE_SIZE - BORDER_THICKNESS,
					0 + BORDER_THICKNESS, TILE_SIZE - BORDER_THICKNESS
				};
			squareTileGraphicsContext.fillPolygon(xys);
		}

		// To leverage CPU usage, we will draw a full column and then
		// repeat the pattern horizontally.
		Image squareTileColumn = Image.createImage(TILE_SIZE, TILE_SIZE * nbTilesY);
		{
			GraphicsContext columnGraphicsContext = squareTileColumn.getGraphicsContext();
			// ensure dark background for tile (images are filled with white pixels by default))

			// draw a column of tiles.
			for (int y = 0; y < nbTilesY; y++) {
				columnGraphicsContext.drawImage(squareTile,0, y * TILE_SIZE , GraphicsContext.LEFT | GraphicsContext.TOP );
			}
		}

		final int alphaIncrement = GraphicsContext.OPAQUE / nbTilesX;
		int currentAlpha = alphaIncrement;
		int yOffsetColumn = (DISPLAY_HEIGHT - squareTileColumn.getHeight()) / 2;
		for (int x = 0; x < nbTilesX; x++, currentAlpha += alphaIncrement) {
			g.drawImage(squareTileColumn, x * TILE_SIZE, yOffsetColumn, GraphicsContext.LEFT | GraphicsContext.TOP, currentAlpha);
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

		TilingWithImages tilingWithImages = new TilingWithImages(display);
		tilingWithImages.show();

	}

}
