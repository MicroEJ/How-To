/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.image;

import ej.drawing.TransformPainter;
import ej.library.ui.MicroEJColors;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;

/**
 * This class shows how to use the TransformPainter utility class with deformed draw methods.
 */
public class DeformedImages extends Displayable {

	private final Display display = Display.getDisplay();

	private final int displayWidth = this.display.getWidth();
	private final int displayHeight = this.display.getHeight();

	final int displayCenterX = this.displayWidth / 2;
	final int diplayCenterY = this.displayHeight / 2;

	final int left = this.displayWidth / 4;
	final int top = this.displayHeight / 4;
	final int right = 3 * this.displayWidth / 4;
	final int bottom = 3 * this.displayHeight / 4;

	final int imageWidth;
	final int imageHeight;

	final Image image;

	/**
	 * Instantiates a new DeformedImages instance.
	 */
	public DeformedImages() {
		this.image = Image.getImage("/images/mascot.png"); //$NON-NLS-1$
		this.imageWidth = this.image.getWidth();
		this.imageHeight = this.image.getHeight();
	}

	@Override
	public void render(GraphicsContext g) {

		// fill up background with black
		g.setColor(Colors.BLACK);

		Painter.fillRectangle(g, 0, 0, this.displayWidth, this.displayHeight);

		drawTopLeftDeformedImage(g);

		drawTopRightDeformedImage(g);

		drawBottomLeftDeformedImage(g);

		drawBottomRightImage(g);

		drawSections(g);
	}

	private void drawTopLeftDeformedImage(GraphicsContext g) {
		// top-left corner - stretch X and Y
		int xys[] = { 0, 0, this.displayCenterX - 1, 0, this.displayCenterX - 1, this.diplayCenterY - 1, 0,
				this.diplayCenterY - 1 };
		TransformPainter.drawDeformedImage(g, this.image, 0, 0, xys);
	}

	private void drawTopRightDeformedImage(GraphicsContext g) {
		// top-right corner - stretch Y
		int xys[] = { 0, 0, this.imageWidth - 1, 0, this.imageWidth - 1, this.diplayCenterY - 1, 0,
				this.diplayCenterY - 1 };

		g.translate(this.right - this.imageWidth / 2, 0);

		TransformPainter.drawDeformedImage(g, this.image, 0, 0, xys);
		g.translate(-this.right + this.imageWidth / 2, 0);
	}

	private void drawBottomLeftDeformedImage(GraphicsContext g) {
		// bottom-left corner - stretch X
		int xys[] = { 0, 0, this.displayCenterX - 1, 0, this.displayCenterX - 1, this.imageHeight - 1, 0,
				this.imageHeight - 1 };
		g.translate(0, this.bottom - this.imageHeight / 2);
		TransformPainter.drawDeformedImage(g, this.image, 0, 0, xys);

		g.translate(0, -this.bottom + this.imageHeight / 2);
	}

	private void drawBottomRightImage(GraphicsContext g) {
		// bottom-right corner - NO stretch
		Painter.drawImage(g, this.image, this.right - this.imageWidth / 2, this.bottom - this.imageHeight / 2);
	}

	private void drawSections(GraphicsContext g) {
		// draw grid for visual alignment control
		// divide display in 4 sections
		g.setColor(MicroEJColors.ABSINTHE);
		Painter.drawLine(g, 0, this.diplayCenterY, this.displayWidth, this.diplayCenterY);
		Painter.drawLine(g, this.displayCenterX, 0, this.displayCenterX, this.displayHeight);

		// divide each section along horizontal & vertical axis
		g.setColor(MicroEJColors.TURQUOISE);

		Painter.drawLine(g, 0, this.top, this.displayWidth, this.top);
		Painter.drawLine(g, 0, this.bottom, this.displayWidth, this.bottom);
		Painter.drawLine(g, this.left, 0, this.left, this.displayHeight);
		Painter.drawLine(g, this.right, 0, this.right, this.displayHeight);
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

		DeformedImages sample = new DeformedImages();
		Display.getDisplay().requestShow(sample);
	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
