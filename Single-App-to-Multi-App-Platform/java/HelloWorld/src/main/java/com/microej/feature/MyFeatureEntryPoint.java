/*
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
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
