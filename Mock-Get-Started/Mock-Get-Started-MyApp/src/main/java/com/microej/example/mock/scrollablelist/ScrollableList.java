/*
 * Copyright 2014-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.scrollablelist;

import ej.microui.display.GraphicsContext;
import ej.mwt.Widget;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * Lays out its widgets the same way as a regular list, but provides an optimization when added to a scroll.
 */
public class ScrollableList extends List implements Scrollable {

	private int viewportWidth;
	private int viewportHeight;
	private int firstVisibleChildIndex;
	private int lastVisibleChildIndex;

	/**
	 * Creates a scrollable list specifying its orientation.
	 *
	 * @param orientation
	 *            the orientation of the scrollable list (see {@link LayoutOrientation}).
	 */
	public ScrollableList(boolean orientation) {
		super(orientation);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		int translateX = g.getTranslationX();
		int translateY = g.getTranslationY();
		int x = g.getClipX();
		int y = g.getClipY();
		int width = g.getClipWidth();
		int height = g.getClipHeight();

		Widget[] children = getChildren();
		int firstVisibleChildIndex = this.firstVisibleChildIndex;
		int lastVisibleChildIndex = this.lastVisibleChildIndex;
		for (int i = firstVisibleChildIndex; i <= lastVisibleChildIndex; i++) {
			Widget child = children[i];
			assert child != null;
			renderChild(child, g);
			if (i < lastVisibleChildIndex) {
				// Don't need to reset after the last widget.
				g.setTranslation(translateX, translateY);
				g.setClip(x, y, width, height);
			}
		}
	}

	@Override
	protected void setShownChildren() {
		int firstVisibleChildIndex = this.firstVisibleChildIndex;
		int lastVisibleChildIndex = this.lastVisibleChildIndex;
		if (lastVisibleChildIndex == 0) {
			lastVisibleChildIndex = getChildrenCount() - 1;
		}
		for (int i = firstVisibleChildIndex; i <= lastVisibleChildIndex; i++) {
			setShownChild(getChild(i));
		}
	}

	@Override
	public void initializeViewport(int width, int height) {
		this.viewportWidth = width;
		this.viewportHeight = height;
	}

	@Override
	public void updateViewport(int x, int y) {
		int viewportWidth = this.viewportWidth;
		int viewportHeight = this.viewportHeight;
		if (isShown()) {
			removeNoLongerVisibleItems(x, y, viewportWidth, viewportHeight);
		}
		addNewlyVisibleItems(x, y, viewportWidth, viewportHeight);
	}

	/**
	 * Adds newly visible items.
	 *
	 * @param x
	 *            the new viewport x coordinate.
	 * @param y
	 *            the new viewport y coordinate.
	 * @param width
	 *            the new viewport width.
	 * @param height
	 *            the new viewport height.
	 */
	private void addNewlyVisibleItems(int x, int y, int width, int height) {
		// Add newly visible items.
		Widget[] children = getChildren();
		int size = children.length;
		if (size > 0) {
			boolean shown = isShown();
			int firstVisible = getFirstVisible(x, y);
			boolean horizontal = (getOrientation() == LayoutOrientation.HORIZONTAL);
			int i = firstVisible;
			for (; i < size; i++) {
				Widget child = children[i];
				int childX = child.getX();
				int childY = child.getY();
				if (horizontal ? (childX + x > width) : (childY + y > height)) {
					// The widget is not in the window, stop recursion.
					break;
				} else if (shown) {
					// The widget is in the window.
					setShownChild(child);
				}
			}
			this.lastVisibleChildIndex = i - 1;
		}
	}

	/**
	 * Removes no more visible items.
	 *
	 * @param x
	 *            the new viewport x coordinate.
	 * @param y
	 *            the new viewport y coordinate.
	 * @param width
	 *            the new viewport width.
	 * @param height
	 *            the new viewport height.
	 */
	private void removeNoLongerVisibleItems(int x, int y, int width, int height) {
		Widget[] children = getChildren();
		int size = children.length;
		if (size > 0) {
			int firstVisibleChildIndex = this.firstVisibleChildIndex;
			int lastVisible = size;
			boolean horizontal = (getOrientation() == LayoutOrientation.HORIZONTAL);
			for (int i = firstVisibleChildIndex - 1; ++i < lastVisible;) {
				Widget child = children[i];
				if (!child.isShown()) {
					break;
				}
				if (horizontal) {
					int childX = child.getX();
					if (childX + x > width || childX + child.getWidth() < -x) {
						setHiddenChild(child);
					}
				} else {
					int childY = child.getY();
					if (childY + y > height || childY + child.getHeight() < -y) {
						setHiddenChild(child);
					}
				}
			}
		}
	}

	private int getFirstVisible(int x, int y) {
		int index = this.firstVisibleChildIndex;
		Widget previousFirstVisibleChild = getChild(index);
		if (getOrientation() == LayoutOrientation.HORIZONTAL) {
			getFirstVisibleHorizontally(x, previousFirstVisibleChild);
		} else {
			getFirstVisibleVertically(y, previousFirstVisibleChild);
		}

		return this.firstVisibleChildIndex;
	}

	private void getFirstVisibleVertically(int y, Widget previousFirstVisibleChild) {
		// Be aware that the code is quite the same as getFirstVisibleHorizontally method.

		int childY = previousFirstVisibleChild.getY();
		int childHeight = previousFirstVisibleChild.getHeight();
		boolean stillFirst = childY <= -y && childY + childHeight >= -y;
		if (!stillFirst) {
			boolean searchForward = childY + childHeight < -y;
			Widget[] children = getChildren();
			int size = children.length;
			int firstCandidate = searchForward ? size - 1 : 0;
			for (int i = this.firstVisibleChildIndex; searchForward ? ++i < size : --i >= 0;) {
				Widget child = children[i];
				int candidateY = child.getY();
				int candidateHeight = child.getHeight();
				if (candidateY <= -y && candidateY + candidateHeight >= -y) {
					firstCandidate = i;
					break;
				}
			}
			this.firstVisibleChildIndex = firstCandidate;
		}
	}

	private void getFirstVisibleHorizontally(int x, Widget previousFirstVisibleChild) {
		// Be aware that the code is quite the same as getFirstVisibleVertically method.

		int childX = previousFirstVisibleChild.getX();
		int childWidth = previousFirstVisibleChild.getWidth();
		boolean stillFirst = childX <= -x && childX + childWidth >= -x;
		if (!stillFirst) {
			boolean searchForward = childX + childWidth < -x;
			Widget[] children = getChildren();
			int size = children.length;
			int firstCandidate = searchForward ? size - 1 : 0;
			for (int i = this.firstVisibleChildIndex; searchForward ? ++i < size : --i >= 0;) {
				Widget child = children[i];
				int candidateX = child.getX();
				int candidateWidth = child.getWidth();
				if (candidateX <= -x && candidateX + candidateWidth >= -x) {
					firstCandidate = i;
					break;
				}
			}
			this.firstVisibleChildIndex = firstCandidate;
		}
	}

}
