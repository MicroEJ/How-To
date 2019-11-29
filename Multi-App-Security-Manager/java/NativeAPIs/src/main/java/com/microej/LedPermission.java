/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej;

import java.security.BasicPermission;

/**
 * Permission to access to the Led.
 */
public class LedPermission extends BasicPermission {

	private static final String LED = "LED";

	/**
	 * Get a singleton to a {@link LedPermission}.
	 */
	public static final LedPermission INSTANCE = new LedPermission();

	private LedPermission() {
		super(LED);
	}

}
