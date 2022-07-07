/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.image;

import ej.drawing.TransformPainter;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;

/**
 * This class shows how to use the TransformPainter utility class with flip draw methods.
 */
public class FlippedImages extends Displayable {

	private final Display display = Display.getDisplay();

	private final int displayWidth = this.display.getWidth();
	private final int displayHeight = this.display.getHeight();

	private final int left = this.displayWidth / 4;
	private final int top = this.displayHeight / 4;
	private final int right = 3 * this.displayWidth / 4;
	private final int bottom = 3 * this.displayHeight / 4;

	private final Image image;

	private final int halfImageWidth;
	private final int halfImageHeight;

	/**
	 * Instanciates a new FlippedImages instance and assign image related variable values.
	 */
	public FlippedImages() {
		super();
		this.image = Image.getImage("/images/mascot.png"); //$NON-NLS-1$
		this.halfImageWidth = this.image.getWidth() / 2;
		this.halfImageHeight = this.image.getHeight() / 2;

	}

	@Override
	public void render(GraphicsContext g) {

		// fill up background with black
		g.setColor(Colors.BLACK);
		Painter.fillRectangle(g, 0, 0, this.displayWidth, this.displayHeight);

		// draw 4 images with and without flipping aspect
		drawTopLeftImage(g);

		drawTopRightFlippedImage(g);

		drawBottomLeftFlippedImage(g);

		drawBottomRightFlippedImage(g);

	}

	private void drawTopLeftImage(GraphicsContext g) {
		// top-left corner - no flip
		Painter.drawImage(g, this.image, this.left - this.halfImageWidth, this.top - this.halfImageHeight);
	}

	private void drawTopRightFlippedImage(GraphicsContext g) {
		// top-right corner - 90° flip
		TransformPainter.drawFlippedImage(g, this.image, this.right - this.halfImageWidth,
				this.top - this.halfImageHeight, TransformPainter.Flip.FLIP_90);
	}

	private void drawBottomLeftFlippedImage(GraphicsContext g) {
		// bottom-left corner - 180° flip
		TransformPainter.drawFlippedImage(g, this.image, this.left - this.halfImageWidth,
				this.bottom - this.halfImageHeight, TransformPainter.Flip.FLIP_180);
	}

	private void drawBottomRightFlippedImage(GraphicsContext g) {
		// bottom-right corner - 270° flip
		TransformPainter.drawFlippedImage(g, this.image, this.right - this.halfImageWidth,
				this.bottom - this.halfImageHeight, TransformPainter.Flip.FLIP_270);
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

		FlippedImages sample = new FlippedImages();
		Display.getDisplay().requestShow(sample);
	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
