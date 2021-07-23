/*
 * Copyright 2013-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.scrollablelist;

import ej.annotation.Nullable;
import ej.bon.XMath;
import ej.microui.MicroUI;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.animation.Animator;
import ej.mwt.util.Size;
import ej.widget.util.swipe.SwipeEventHandler;
import ej.widget.util.swipe.SwipeListener;
import ej.widget.util.swipe.Swipeable;

/**
 * Allows to scroll a widget horizontally or vertically.
 */
public class Scroll extends Container {

	@Nullable
	private Widget child;
	@Nullable
	private Scrollable scrollableChild;
	private final Scrollbar scrollbar;
	private boolean horizontal;

	// Swipe management.
	@Nullable
	private SwipeEventHandler swipeEventHandler;
	private final ScrollAssistant assistant;
	private int value;
	private boolean shifting;

	/**
	 * Creates a scroll container specifying its orientation and the visibility of the scrollbar.
	 *
	 * @param horizontal
	 *            <code>true</code> to scroll horizontally, <code>false</code> to scroll vertically.
	 */
	public Scroll(boolean horizontal) {
		this.horizontal = horizontal;
		this.scrollbar = new Scrollbar(0);
		this.scrollbar.setHorizontal(horizontal);
		this.assistant = new ScrollAssistant();

		addChild(this.scrollbar);
	}

	/**
	 * Sets the child to scroll.
	 * <p>
	 * The given widget can implement {@link Scrollable} and be notified about when the visible area changes (for
	 * example for optimization purpose).
	 *
	 * @param child
	 *            the child to scroll.
	 */
	public void setChild(Widget child) {
		Widget oldChild = this.child;
		if (child != oldChild) {
			if (oldChild != null) {
				// replace old child by new child
				replaceChild(getChildIndex(oldChild), child);
			} else {
				// insert new child before scrollbar
				insertChild(child, 0);
			}

			// update fields
			this.child = child;
			if (child instanceof Scrollable) {
				this.scrollableChild = (Scrollable) child;
			} else {
				this.scrollableChild = null;
			}
		}
	}

	/**
	 * Sets the scroll orientation: horizontal or vertical.
	 *
	 * @param horizontal
	 *            <code>true</code> to scroll horizontally, <code>false</code> to scroll vertically.
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
		this.scrollbar.setHorizontal(horizontal);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int width = 0;
		int height = 0;

		Widget child = this.child;
		if (child != null) {
			int widthHint = size.getWidth();
			int heightHint = size.getHeight();

			if (this.horizontal) {
				computeChildOptimalSize(child, Widget.NO_CONSTRAINT, heightHint);
				computeChildOptimalSize(this.scrollbar, widthHint, Widget.NO_CONSTRAINT);
			} else {
				computeChildOptimalSize(child, widthHint, Widget.NO_CONSTRAINT);
				computeChildOptimalSize(this.scrollbar, Widget.NO_CONSTRAINT, heightHint);
			}

			width = child.getWidth();
			height = child.getHeight();
		}

		// Set container optimal size.
		size.setSize(width, height);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		Scrollable scrollableChild = this.scrollableChild;
		if (scrollableChild != null) {
			scrollableChild.initializeViewport(contentWidth, contentHeight);
		}

		Widget child = this.child;
		int childOptimalWidth;
		int childOptimalHeight;
		if (child != null) {
			childOptimalWidth = child.getWidth();
			childOptimalHeight = child.getHeight();
		} else {
			childOptimalWidth = 0;
			childOptimalHeight = 0;
		}

		int excess;
		if (this.horizontal) {
			excess = childOptimalWidth - contentWidth;
			int scrollbarHeight = 0;
			if (excess > 0) {
				this.scrollbar.setMaximum(excess);
				scrollbarHeight = this.scrollbar.getHeight();
				this.layOutChild(this.scrollbar, 0, contentHeight - scrollbarHeight, contentWidth, scrollbarHeight);
			}
			if (child != null) {
				layOutChild(child, 0, 0, childOptimalWidth, contentHeight);
			}
		} else {
			excess = childOptimalHeight - contentHeight;
			int scrollbarWidth = 0;
			if (excess > 0) {
				this.scrollbar.setMaximum(excess);
				scrollbarWidth = this.scrollbar.getWidth();
				this.layOutChild(this.scrollbar, contentWidth - scrollbarWidth, 0, scrollbarWidth, contentHeight);
			}
			if (child != null) {
				layOutChild(child, 0, 0, contentWidth, childOptimalHeight);
			}
		}
		if (excess > 0) {
			SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
			if (swipeEventHandler != null) {
				swipeEventHandler.stop();
			}

			Animator animator = getDesktop().getAnimator();
			swipeEventHandler = new SwipeEventHandler(excess, false, this.horizontal, this.assistant, animator);
			swipeEventHandler.setSwipeListener(this.assistant);
			swipeEventHandler.moveTo(this.value);
			this.swipeEventHandler = swipeEventHandler;
		}

		int childCoordinate = -this.scrollbar.getValue();
		updateViewport(childCoordinate);
	}

	@Override
	public void onShown() {
		setEnabled(true);
	}

	@Override
	protected void onHidden() {
		super.onHidden();

		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null) {
			swipeEventHandler.stop();
		}
	}

	/**
	 * Scrolls to a position.
	 *
	 * @param position
	 *            the x or y target (depending on the orientation).
	 * @param animate
	 *            whether the scrolling action should be animated.
	 */
	public void scrollTo(int position, boolean animate) {
		int max;
		Widget child = this.child;
		if (child != null) {
			if (this.horizontal) {
				max = child.getWidth() - getWidth();
			} else {
				max = child.getHeight() - getHeight();
			}
			max = Math.max(0, max);
		} else {
			max = 0;
		}
		position = XMath.limit(position, 0, max);
		this.scrollbar.setValue(position);
		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null && animate) {
			swipeEventHandler.moveTo(position, SwipeEventHandler.DEFAULT_DURATION);
		} else {
			this.assistant.onMove(position);
		}
	}

	/**
	 * Scrolls to a position without animation.
	 *
	 * @param position
	 *            the x or y target (depending on the orientation).
	 */
	public void scrollTo(int position) {
		scrollTo(position, false);
	}

	@Override
	public boolean handleEvent(int event) {
		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null && swipeEventHandler.handleEvent(event)) {
			return true;
		}
		return super.handleEvent(event);
	}

	private void updateViewport(int x, int y) {
		Scrollable scrollableChild = this.scrollableChild;
		if (scrollableChild != null) {
			scrollableChild.updateViewport(x, y);
		}
		Widget child = this.child;
		if (child != null) {
			child.setPosition(x, y);
		}
	}

	private void updateViewport(int childCoordinate) {
		Widget child = this.child;
		if (child != null) {
			if (this.horizontal) {
				updateViewport(childCoordinate, child.getY());
			} else {
				updateViewport(child.getX(), childCoordinate);
			}
		}
	}

	class ScrollAssistant implements Runnable, Swipeable, SwipeListener {

		@Override
		public void onSwipeStarted() {
			Scroll.this.scrollbar.show();
		}

		@Override
		public void onSwipeStopped() {
			Scroll.this.scrollbar.hide();
			Scroll.this.scrollbar.requestRender();
		}

		@Override
		public void run() {
			Scroll scroll = Scroll.this;
			scroll.shifting = false;
			if (scroll.isShown()) {
				scroll.scrollbar.setValue(scroll.value);
				int childCoordinate = (-scroll.value - scroll.scrollbar.getValue()) / 2;
				updateViewport(childCoordinate);
			}
		}

		@Override
		public void onMove(int position) {
			Scroll scroll = Scroll.this;
			if (scroll.value != position) {
				scroll.value = position;
				if (!scroll.shifting) {
					scroll.shifting = true;
					MicroUI.callSerially(this);
					requestRender();
				}
			}
		}

	}

}
