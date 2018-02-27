/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mycompany;

/**
 * @see javadoc API
 */
public class MyLibNatives {

	public static int factorial(int number) {
		if (number == 0) {
			return 1;
		}
		int fact = 1; // this will be the result
		for (int i = 1; i <= number; i++) {
			fact *= i;
		}
		return fact;
	}
}