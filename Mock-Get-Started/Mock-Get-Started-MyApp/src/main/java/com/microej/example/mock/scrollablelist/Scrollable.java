/*
 * Copyright 2014-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.scrollablelist;

/**
 * This interface may be implemented by any widget that could be set in a scroll container.
 * <p>
 * The widget will be notified when the viewport of the scroll container change. It can then optimize its rendering (or
 * hierarchy for a container) to avoid drawing elements outside the viewport.
 */
public interface Scrollable {

	/**
	 * Called when the scroll container updates its size.
	 *
	 * @param width
	 *            the new width of the viewport.
	 * @param height
	 *            the new height of the viewport.
	 */
	void initializeViewport(int width, int height);

	/**
	 * Called when the scroll container updates its viewport.
	 *
	 * @param x
	 *            the new x coordinate of the viewport.
	 * @param y
	 *            the new y coordinate of the viewport.
	 */
	void updateViewport(int x, int y);

}
