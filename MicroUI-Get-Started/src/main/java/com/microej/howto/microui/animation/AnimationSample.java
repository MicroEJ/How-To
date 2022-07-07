/*
 * Java
 *
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.microui.animation;

import ej.bon.TimerTask;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;

/**
 * This class shows how to create an animation using MicroUI and MWT Animator.
 *
 * <p>
 * It will draw the MicroEJ robot moving from one side of the screen to the other.
 * </p>
 *
 * Note that the whole display is redrawn on every frame.
 *
 * @see TimerTask
 */
public class AnimationSample extends Displayable {

	private static final int ABSOLUTE_INCREMENT = 1;
	private static final int LEFT_LIMIT = 0;

	private int imageX;
	private final Image image;

	// Animation variables
	private final int rightLimit;
	private int horizontalIncrement = ABSOLUTE_INCREMENT;

	/**
	 * Instantiates an AnimationSample.
	 */
	public AnimationSample() {

		final Display display = Display.getDisplay();

		this.image = Image.getImage("/images/mascot.png"); //$NON-NLS-1$
		this.rightLimit = display.getWidth() - this.image.getWidth();

		// Starts at the centerX of the screen.
		this.imageX = display.getWidth() / 2 - this.image.getWidth();

		display.requestShow(this);

	}

	/**
	 * Animator doing an horizontal linear motion of MicroEJ.
	 */

	private void animate() {

		Animation animation = new Animation() {

			@Override
			public boolean tick(long platformTimeMillis) {
				AnimationSample.this.imageX += AnimationSample.this.horizontalIncrement;
				if (AnimationSample.this.imageX > AnimationSample.this.rightLimit) {
					AnimationSample.this.imageX = AnimationSample.this.rightLimit;
					AnimationSample.this.horizontalIncrement = -ABSOLUTE_INCREMENT;
				} else {
					if (AnimationSample.this.imageX < LEFT_LIMIT) {
						AnimationSample.this.imageX = LEFT_LIMIT;
						AnimationSample.this.horizontalIncrement = ABSOLUTE_INCREMENT;
					}
				}
				AnimationSample.this.requestRender();
				return true;
			}

		};

		Animator animator = new Animator();
		animator.startAnimation(animation);
	}

	@Override
	public void render(GraphicsContext g) {

		final Display display = Display.getDisplay();
		final int displayWidth = display.getWidth();
		final int displayHeight = display.getHeight();

		// fill up background with black
		g.setColor(Colors.BLACK);
		Painter.fillRectangle(g, 0, 0, displayWidth, displayHeight);

		// fill up half the area with white
		g.setColor(Colors.WHITE);
		Painter.fillRectangle(g, 0, 0, displayWidth / 2, displayHeight);

		// get imageY to draw it
		int imageY = (displayHeight - this.image.getHeight()) / 2;

		// draw the image
		Painter.drawImage(g, this.image, this.imageX, imageY);

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

		AnimationSample sample = new AnimationSample();
		sample.animate();

	}

	@Override
	public boolean handleEvent(int event) {
		// No event handling is required for this sample.
		return false;
	}

}
