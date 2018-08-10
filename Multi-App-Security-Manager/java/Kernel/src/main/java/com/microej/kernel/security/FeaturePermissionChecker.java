/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
 */
package com.microej.kernel.security;

import java.security.Permission;

import ej.kf.Feature;

/**
 * API for checking {@link Feature} permissions.
 */
public interface FeaturePermissionChecker{

	/**
	 * Called by {@link KernelSecurityManager} when the current thread context
	 * requesting for a Permission check is owned by a Feature. This method is
	 * called in Kernel mode.
	 *
	 * @throws SecurityException
	 *             when the feature doesn't have the permission.
	 */
	public void checkPermission(Permission p, Feature f) throws SecurityException;
}