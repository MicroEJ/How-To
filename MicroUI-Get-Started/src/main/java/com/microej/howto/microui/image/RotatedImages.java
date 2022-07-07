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
 * This class shows how to use the TransformPainter utility class with rotate draw methods.
 */
public class RotatedImages extends Displayable {

	private final static int LEFT_IMAGE_ROTATION_ANGLE = 45;
	private final static int RIGHT_IMAGE_ROTATION_ANGLE = 135;

	private final int displayWidth;
	private final int displayHeight;

	private final int left;
	private final int top;
	private final int right;
	private final int bottom;

	private final Image image;

	private final int halfImageWidth;
	private final int halfImageHeight;

	/**
	 * Instantiates the RotatedImages example.
	 */
	public RotatedImages() {

		final Display display = Display.getDisplay();

		this.image = Image.getImage("/images/mascot.png"); //$NON-NLS-1$

		this.displayWidth = display.getWidth();
		this.displayHeight = display.getHeight();

		this.left = this.displayWidth / 4;
		this.top = this.displayHeight / 4;
		this.right = 3 * this.displayWidth / 4;
		this.bottom = 3 * this.displayHeight / 4;

		this.halfImageWidth = this.image.getWidth() / 2;
		this.halfImageHeight = this.image.getHeight() / 2;
	}

	@Override
	public void render(GraphicsContext g) {

		// fill up background with black
		g.setColor(Colors.BLACK);
		Painter.fillRectangle(g, 0, 0, this.displayWidth, this.displayHeight);

		// draws images
		drawTopLeftRotatedImage(g);

		drawTopRightRotatedImage(g);

		drawBottomLeftRotatedImage(g);

		drawBottomRightRotatedImage(g);

	}

	private void drawTopLeftRotatedImage(GraphicsContext g) {
		// top-left corner - 45°

		// Use the bilinear algorithm to render the image. This
		// algorithm performs better rendering than nearest
		// neighbor algorithm but it is slower to apply.
		TransformPainter.drawRotatedImageBilinear(g, this.image, this.left - this.halfImageWidth,
				this.top - this.halfImageHeight, this.left, this.top, LEFT_IMAGE_ROTATION_ANGLE);
	}

	private void drawTopRightRotatedImage(GraphicsContext g) {
		// top-right corner - 135°

		// Use the bilinear algorithm to render the image. This
		// algorithm performs better rendering than nearest
		// neighbor algorithm but it is slower to apply.

		TransformPainter.drawRotatedImageBilinear(g, this.image, this.right - this.halfImageWidth,
				this.top - this.halfImageHeight, this.right, this.top, RIGHT_IMAGE_ROTATION_ANGLE);
	}

	private void drawBottomLeftRotatedImage(GraphicsContext g) {
		// bottom-left corner - 45°

		// Uses the nearest neighbor algorithm to render the
		// image. This algorithm is faster than bilinear
		// algorithm but its rendering is more simple.

		TransformPainter.drawRotatedImageBilinear(g, this.image, this.left - this.halfImageWidth,
				this.bottom - this.halfImageHeight, this.left, this.bottom, LEFT_IMAGE_ROTATION_ANGLE);
	}

	private void drawBottomRightRotatedImage(GraphicsContext g) {
		// bottom-right corner - 135°

		// Uses the nearest neighbor algorithm to render the
		// image. This algorithm is faster than bilinear
		// algorithm but its rendering is more simple.

		TransformPainter.drawRotatedImageBilinear(g, this.image, this.right - this.halfImageWidth,
				this.bottom - this.halfImageHeight, this.right, this.bottom, RIGHT_IMAGE_ROTATION_ANGLE);
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

		RotatedImages sample = new RotatedImages();
		Display.getDisplay().requestShow(sample);
	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
