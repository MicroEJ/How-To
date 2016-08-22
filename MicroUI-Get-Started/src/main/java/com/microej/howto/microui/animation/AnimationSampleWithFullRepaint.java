/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.howto.microui.animation;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.util.EventHandler;

/**
 * This class shows how to create an animation using MicroUI and TimerTask
 *
 * Note that the whole display is redrawn on every frame
 */
public class AnimationSampleWithFullRepaint {

	private static final int ANIMATION_TIME = 40; // in ms

	int imageX;
	int imageY;
	Image microejImage;
	Display display;
	Displayable displayable;

	class HorizontalAnimatorTask extends TimerTask {

		final int ABSOLUTE_INCREMENT = 2;
		AnimationSampleWithFullRepaint animated;
		int horizontalIncrement = ABSOLUTE_INCREMENT;
		final int animatedImageHalfWidth;

		public HorizontalAnimatorTask(AnimationSampleWithFullRepaint animated) {
			this.animated = animated;
			animatedImageHalfWidth = animated.microejImage.getWidth() / 2;
		}

		@Override
		public void run() {
			animated.imageX += horizontalIncrement;
			if (animated.imageX > animated.display.getWidth() - animatedImageHalfWidth) {
				animated.imageX = animated.display.getWidth() - animatedImageHalfWidth;
				horizontalIncrement = -ABSOLUTE_INCREMENT;
			} else {
				if (animated.imageX < animatedImageHalfWidth) {
					animated.imageX = animatedImageHalfWidth;
					horizontalIncrement = ABSOLUTE_INCREMENT;
				}
			}
			animated.displayable.repaint();
		}
	}

	public void createDisplayable() {

		displayable = new Displayable(display) {
			@Override
			public void paint(GraphicsContext g) {

				// fill up background with black
				g.setColor(Colors.BLACK);
				g.fillRect(0, 0, display.getWidth(), display.getHeight());

				// fill up half the area with white
				g.setColor(Colors.WHITE);
				g.fillRect(0, 0, display.getWidth()/2, display.getHeight());
				g.drawImage(microejImage, imageX, imageY, GraphicsContext.HCENTER | GraphicsContext.VCENTER);

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

	public void animate() {
		this.createDisplayable();
		HorizontalAnimatorTask animator = new HorizontalAnimatorTask(this);
		Timer animationTimer = new Timer();
		animationTimer.scheduleAtFixedRate(animator, ANIMATION_TIME, ANIMATION_TIME);
	}

	/**
	 *
	 */
	public AnimationSampleWithFullRepaint(Display display) {
		this.display = display;
		this.imageX = display.getWidth() / 2;
		this.imageY = display.getHeight() / 2;

		try {
			microejImage = Image.createImage("/images/microej.png");
		} catch (IOException e) {
			throw new AssertionError(e);
		}

		this.animate();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// A call to MicroUI.start is required to initialize the graphics
		// runtime environment
		MicroUI.start();
		final Display display = Display.getDefaultDisplay();

		AnimationSampleWithFullRepaint animationSample = new AnimationSampleWithFullRepaint(display);
	}

}
