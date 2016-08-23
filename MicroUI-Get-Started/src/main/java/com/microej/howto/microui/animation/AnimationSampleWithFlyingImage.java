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
import ej.microui.display.FlyingImage;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.util.EventHandler;

/**
 * This class shows how to create an animation using MicroUI Flying Images and
 * TimerTask
 *
 * Note that the whole display is NOT redrawn on every frame
 */
public class AnimationSampleWithFlyingImage {

	private static final int ANIMATION_TIME = 40; // in ms

	int imageX;
	int imageY;
	Image microejImage;
	FlyingImage flyingImage;
	Display display;
	Displayable displayable;

	class HorizontalAnimatorTask extends TimerTask {

		final int ABSOLUTE_INCREMENT = 2;
		AnimationSampleWithFlyingImage animated;
		int horizontalIncrement = ABSOLUTE_INCREMENT;
		final int animatedImageHalfWidth;

		public HorizontalAnimatorTask(AnimationSampleWithFlyingImage animated) {
			this.animated = animated;
			animatedImageHalfWidth = animated.microejImage.getWidth() / 2;
		}

		@Override
		public void run() {
			flyingImage.hide();
			animated.imageX += horizontalIncrement;
			if (animated.imageX > animated.display.getWidth() - microejImage.getWidth()) {
				animated.imageX = animated.display.getWidth() - microejImage.getWidth();
				horizontalIncrement = -ABSOLUTE_INCREMENT;
			} else {
				if (animated.imageX < 0) {
					animated.imageX = 0;
					horizontalIncrement = ABSOLUTE_INCREMENT;
				}
			}

			flyingImage.setLocation(animated.imageX, animated.imageY);
			flyingImage.show();
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
	public AnimationSampleWithFlyingImage(Display display) {
		this.display = display;

		try {
			microejImage = Image.createImage("/images/microej.png");
			flyingImage = new FlyingImage(microejImage);
		} catch (IOException e) {
			throw new AssertionError(e);
		}

		this.imageX = display.getWidth() / 2 - microejImage.getWidth() / 2;
		this.imageY = display.getHeight() / 2 - microejImage.getHeight() / 2;

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

		AnimationSampleWithFlyingImage animationSample = new AnimationSampleWithFlyingImage(display);
	}

}
