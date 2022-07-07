/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.drawing;

import ej.drawing.ShapePainter;
import ej.library.ui.MicroEJColors;
import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * This class shows the usage of the main drawing methods available in MicroUI.
 */
public class BasicDrawings extends Displayable {

	private static final int CIRCLE_START_ANGLE = 25;
	private static final int CIRCLE_ARC_ANGLE = 310;
	private static final int RECTANGLE_ARC_HEIGHT = 40;
	private static final int TRIANGLE_RADIUS_DIVIDER = 20;

	private final int displayWidth;
	private final int displayHeight;
	private final int displayCenterX;
	private final int displayCenterY;

	/**
	 * Instantiates a BasicDrawings instance with variables initialization.
	 */
	public BasicDrawings() {
		Display display = Display.getDisplay();
		this.displayWidth = display.getWidth();
		this.displayHeight = display.getHeight();
		this.displayCenterX = this.displayWidth / 2;
		this.displayCenterY = this.displayHeight / 2;
	}

	@Override
	public void render(GraphicsContext g) {

		// fill up background with black
		g.setColor(MicroEJColors.CONCRETE_BLACK_75);
		Painter.fillRectangle(g, 0, 0, this.displayWidth, this.displayHeight);

		// fill up half the area with white
		g.setColor(MicroEJColors.WHITE);
		Painter.fillRectangle(g, 0, 0, this.displayWidth / 2, this.displayHeight);

		// draw a line across the area diagonal
		g.setColor(MicroEJColors.TURQUOISE);
		Painter.drawLine(g, 0, 0, this.displayWidth, this.displayHeight);

		// draw different kinds of basic shapes
		drawCircle(g);

		drawRoundedRectangle(g);

		drawEllipse(g);

		drawCircleArc(g);

		drawTriangle(g);
	}

	private void drawCircle(GraphicsContext g) {
		g.setColor(MicroEJColors.CORAL);

		final int diameter = this.displayWidth / 2;

		// Note that x and y parameters are the top left coordinates
		// of the circle bounding box.
		// Therefore some offset of half the rectangle width and
		// height have to be applied to center the circle on the
		// display
		final int x = this.displayCenterX - diameter / 2;
		final int y = this.displayCenterY - diameter / 2;

		Painter.fillCircle(g, x, y, diameter);
	}

	private void drawRoundedRectangle(GraphicsContext g) {
		g.setColor(MicroEJColors.POMEGRANATE);

		final int rectangleWidth = this.displayWidth / 3;
		final int rectangleHeight = this.displayHeight / 3;
		final int arcWidth = RECTANGLE_ARC_HEIGHT * (this.displayWidth / this.displayHeight);

		// Note that x and y parameters are the top left coordinates
		// of the rectangle bounding box.
		// Therefore some offset of half the rectangle width and
		// height have to be applied to center the rectangle on the
		// display
		final int x = this.displayCenterX - rectangleWidth / 2;
		final int y = this.displayCenterY - rectangleHeight / 2;

		Painter.fillRoundedRectangle(g, x, y, rectangleWidth, rectangleHeight, arcWidth, RECTANGLE_ARC_HEIGHT);
	}

	private void drawEllipse(GraphicsContext g) {
		g.setColor(MicroEJColors.TURQUOISE);

		final int ellipseWidth = this.displayWidth / 4;
		final int ellipseHeight = this.displayHeight / 4;
		// Note that x and y parameters are the top left coordinates
		// of the ellipse bounding box.
		// Therefore some offset of half the ellipse width and
		// height have to be applied to center the ellipse on the
		// display
		final int x = this.displayCenterX - ellipseWidth / 2;
		final int y = this.displayCenterY - ellipseHeight / 2;
		Painter.fillEllipse(g, x, y, ellipseWidth, ellipseHeight);
	}

	private void drawCircleArc(GraphicsContext g) {
		g.setColor(MicroEJColors.BONDI);

		final int diameter = this.displayHeight / 6;
		final int radius = diameter / 2;

		// Note that x and y parameters are the top left coordinates
		// of the circle bounding box.
		// Therefore some offset of half the circle diameter
		// has to be applied to center the circle on the display
		final int x = this.displayCenterX - radius;
		final int y = this.displayCenterY - radius;

		Painter.fillCircleArc(g, x, y, diameter, CIRCLE_START_ANGLE, CIRCLE_ARC_ANGLE);
	}

	private void drawTriangle(GraphicsContext g) {
		g.setColor(MicroEJColors.CHICK);

		final int radius = this.displayHeight / TRIANGLE_RADIUS_DIVIDER;
		final int centerX = this.displayCenterX;
		final int leftX = centerX - radius;
		final int rightX = centerX + radius;
		final int topY = this.displayCenterY - radius;
		final int bottomY = this.displayCenterY + radius;
		final int[] xys = { leftX, bottomY, // Bottom left vertex
				centerX, topY, // Top vertex
				rightX, bottomY // Bottom right vertex
		};

		ShapePainter.fillPolygon(g, xys);
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

		BasicDrawings sample = new BasicDrawings();
		Display.getDisplay().requestShow(sample);
	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
