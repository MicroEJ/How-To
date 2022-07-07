/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.image;

import ej.library.ui.MicroEJColors;
import ej.microui.MicroUI;
import ej.microui.display.BufferedImage;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * This class shows how to create and use a {@link BufferedImage}.
 *
 * A {@link BufferedImage} is a mutable image backed by a pixel buffer, which is stored in the images heap. The
 * {@link GraphicsContext} associated with this image can be retrieved to draw on the image.
 *
 * In this example, we build a table of square tiles that fills the display. We first draw the content of one tile into
 * an image, then repeat this image along the y-axis to build the image of a column. The image of a column is then
 * repeated along the x-axis.
 *
 * Note that drawing N times the column image is more efficient than drawing N x M times the tile image.
 */
public class BufferedImages extends Displayable {

	private static final int TILE_SIZE = 24;
	private static final int BORDER_THICKNESS = 2;

	private final int displayWidth;
	private final int displayHeight;
	private BufferedImage image;

	/**
	 * Creates an example that demonstrates how to use a {@link BufferedImage}.
	 */
	public BufferedImages() {
		final Display display = Display.getDisplay();
		this.displayWidth = display.getWidth();
		this.displayHeight = display.getHeight();
	}

	@Override
	public void render(GraphicsContext g) {
		int width = this.displayWidth;
		int height = this.displayHeight;

		// fill the screen with black.
		g.setColor(Colors.BLACK);
		Painter.fillRectangle(g, 0, 0, width, height);

		// draw repeatedly an image that represents a column of tiles, with increasing alpha.
		int tileCountPerRow = width / TILE_SIZE;
		int alphaIncrement = GraphicsContext.OPAQUE / tileCountPerRow;
		for (int x = 0, alpha = alphaIncrement; x < tileCountPerRow; x++, alpha += alphaIncrement) {
			Painter.drawImage(g, this.image, x * TILE_SIZE, 0, alpha);
		}
	}

	@Override
	protected void onShown() {
		super.onShown();
		this.image = createColumn();
	}

	private BufferedImage createColumn() {
		// creates an image to store a column of tiles.
		int tileCountPerColumn = this.displayHeight / TILE_SIZE;
		BufferedImage columnImage = new BufferedImage(TILE_SIZE, TILE_SIZE * tileCountPerColumn);

		// draws N tiles into the image.
		// note that we use the graphics context of the image when drawing.
		GraphicsContext g = columnImage.getGraphicsContext();
		try (BufferedImage tileImage = createTile()) {
			for (int y = 0; y < tileCountPerColumn; y++) {
				Painter.drawImage(g, tileImage, 0, y * TILE_SIZE);
			}
		}

		return columnImage;
	}

	private BufferedImage createTile() {
		// creates an image to store a tile.
		BufferedImage tileImage = new BufferedImage(TILE_SIZE, TILE_SIZE);
		// note that we use the graphics context of the image when drawing.
		GraphicsContext g = tileImage.getGraphicsContext();

		g.setColor(MicroEJColors.CONCRETE_BLACK_75);
		Painter.fillRectangle(g, 0, 0, tileImage.getWidth(), tileImage.getHeight());

		g.setColor(MicroEJColors.CHICK);
		Painter.fillRectangle(g, 0 + BORDER_THICKNESS, 0 + BORDER_THICKNESS, TILE_SIZE - BORDER_THICKNESS - 1,
				TILE_SIZE - BORDER_THICKNESS - 1);

		return tileImage;
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		if (this.image != null) {
			// buffered image must be closed after use, to release associated resources.
			this.image.close();
		}
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

		BufferedImages sample = new BufferedImages();
		Display.getDisplay().requestShow(sample);

	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
