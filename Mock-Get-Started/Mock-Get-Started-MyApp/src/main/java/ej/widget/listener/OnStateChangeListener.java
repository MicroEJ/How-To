/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package ej.widget.listener;

/**
 * Defines an object which listens to state change events.
 */
public interface OnStateChangeListener {

	/**
	 * Invoked when the target of the listener has changed its state.
	 *
	 * @param newState the new state of the listened object.
	 */
	void onStateChange(boolean newState);
}
