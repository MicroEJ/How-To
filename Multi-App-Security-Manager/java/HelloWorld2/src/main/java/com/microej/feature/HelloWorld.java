/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.feature;

import com.microej.Led;

/**
 * Entry point, outputing an Hello World and switching a Led.
 */
public class HelloWorld {

	/**
	 * Entry Point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		System.out.println("Hello World !"); //$NON-NLS-1$
		boolean on = false;
		while (true) {
			Led.switchLed(on);
			on = !on;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
