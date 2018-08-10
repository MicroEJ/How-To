/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.feature;

import ej.kf.FeatureEntryPoint;

/**
 * Feature entry point.
 */
public class MyFeatureEntryPoint implements FeatureEntryPoint {

	@Override
	public void start() {
		System.out.println("feature started"); //$NON-NLS-1$
		HelloWorld.main(null);
	}

	@Override
	public void stop() {
		System.out.println("feature stopped"); //$NON-NLS-1$
	}

}
