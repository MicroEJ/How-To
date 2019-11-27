/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package ej.widget.listener;

/**
 * Defines an object which listens to focus events.
 */
public interface OnFocusListener {

	/**
	 * Invoked when the target of the listener has gained focus.
	 */
	abstract public void onGainFocus();

	/**
	 * Invoked when the target of the listener has lost focus.
	 */
	abstract public void onLostFocus();
}
