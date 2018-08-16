/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
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
