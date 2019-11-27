/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Light extends JLabel {

	private static final long serialVersionUID = 7433451550567725098L;

	private final static String ON = "/images/Bulb-On.png";
	private final static String OFF = "/images/Bulb.png";

	private boolean on;

	public Light() {
		switchLight(true);
		setSize(50, 50);
	}

	/**
	 * Sets the connected.
	 *
	 * @param on
	 *            the connected to set.
	 */
	public void switchLight(boolean on) {
		if (on != this.on) {
			this.on = on;
			try {
				setIcon(new ImageIcon(ImageIO.read(Light.class.getResourceAsStream(on ? ON : OFF))));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		ImageIcon icon = (ImageIcon) getIcon();
		if (icon != null) {
			drawScaledImage(icon.getImage(), this, g);
		}
	}

	private static void drawScaledImage(Image image, Component canvas, Graphics g) {
		int imgWidth = image.getWidth(null);
		int imgHeight = image.getHeight(null);

		double imgAspect = (double) imgHeight / imgWidth;

		int canvasWidth = canvas.getWidth();
		int canvasHeight = canvas.getHeight();

		double canvasAspect = (double) canvasHeight / canvasWidth;

		int x1 = 0; // top left X position
		int y1 = 0; // top left Y position
		int x2 = 0; // bottom right X position
		int y2 = 0; // bottom right Y position

		if (imgWidth < canvasWidth && imgHeight < canvasHeight) {
			// the image is smaller than the canvas
			x1 = (canvasWidth - imgWidth) / 2;
			y1 = (canvasHeight - imgHeight) / 2;
			x2 = imgWidth + x1;
			y2 = imgHeight + y1;

		} else {
			if (canvasAspect > imgAspect) {
				y1 = canvasHeight;
				// keep image aspect ratio
				canvasHeight = (int) (canvasWidth * imgAspect);
				y1 = (y1 - canvasHeight) / 2;
			} else {
				x1 = canvasWidth;
				// keep image aspect ratio
				canvasWidth = (int) (canvasHeight / imgAspect);
				x1 = (x1 - canvasWidth) / 2;
			}
			x2 = canvasWidth + x1;
			y2 = canvasHeight + y1;
		}

		g.drawImage(image, x1, y1, x2, y2, 0, 0, imgWidth, imgHeight, null);
	}

}
