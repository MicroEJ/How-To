/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.kernel.security;

import java.security.Permission;

import com.microej.LedPermission;

import ej.kf.Feature;

/**
 * Checker for the {@link LedPermission}. Authorize only the feature with the
 * name "Hello".
 */
public class LedPermissionChecker implements FeaturePermissionChecker {

	@Override
	public void checkPermission(Permission p, Feature f) throws SecurityException {
		String name = f.getName();
		if (name == null || !name.equals("Hello")) {
			throw new SecurityException();
		}
	}

}
