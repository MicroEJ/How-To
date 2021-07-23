/*
 * Copyright 2020-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.widget;

import ej.annotation.Nullable;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.motion.Motion;
import ej.motion.quad.QuadEaseOutFunction;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.animation.Animator;
import ej.widget.util.motion.MotionAnimation;
import ej.widget.util.motion.MotionAnimationListener;

/**
 * Used by {@link Navigation} to do a transition between desktops.
 */
public class TransitionDisplayable extends Displayable implements MotionAnimationListener {

	private static final int DURATION = 250;

	private final Desktop newDesktop;
	private final boolean forward;
	private final Animator animator;

	private @Nullable MotionAnimation animation;
	private int currentPosition;
	private int lastPosition;

	public TransitionDisplayable(Desktop newDesktop, boolean forward) {
		this.newDesktop = newDesktop;
		this.forward = forward;
		this.animator = new Animator();
	}

	@Override
	protected void onShown() {
		super.onShown();

		Motion motion = createMotion(this.forward);
		this.animation = new MotionAnimation(this.animator, motion, this);
		this.animation.start();
		this.currentPosition = motion.getStartValue();
		this.lastPosition = this.currentPosition;
	}

	@Override
	protected void onHidden() {
		super.onHidden();

		if (this.animation != null) {
			this.animation.stop();
			this.animation = null;
		}
	}

	@Override
	public void tick(int value, boolean finished) {
		if (finished) {
			this.newDesktop.requestShow();
		} else {
			this.currentPosition = value;
			requestRender();
		}
	}

	@Override
	protected void render(GraphicsContext gc) {
		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();

		int currentPosition = this.currentPosition;

		if (this.forward) {
			renderNewDesktop(gc, currentPosition, currentPosition,
					displayWidth - currentPosition);
		} else {
			int lastPosition = this.lastPosition;
			Painter.drawDisplayRegion(gc, lastPosition, 0, displayWidth - currentPosition, displayHeight,
					currentPosition, 0);
			renderNewDesktop(gc, 0, lastPosition, currentPosition - lastPosition);
		}

		this.lastPosition = currentPosition;
	}

	private void renderNewDesktop(GraphicsContext gc, int x, int clipX, int clipWidth) {
		gc.setClip(clipX, 0, clipWidth, gc.getHeight());
		gc.setTranslation(x, 0);

		Desktop newDesktop = this.newDesktop;
		newDesktop.setAttached();
		Widget widget = newDesktop.getWidget();
		assert (widget != null);
		widget.render(gc);
	}

	@Override
	public boolean handleEvent(int event) {
		return false;
	}

	private static Motion createMotion(boolean forward) {
		int width = Display.getDisplay().getWidth();
		if (forward) {
			return new Motion(QuadEaseOutFunction.INSTANCE, width, 0, DURATION);
		} else {
			return new Motion(QuadEaseOutFunction.INSTANCE, 0, width, DURATION);
		}
	}
}
