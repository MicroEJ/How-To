/*
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.kernel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.microej.Led;

import ej.kf.AlreadyLoadedFeatureException;
import ej.kf.Feature;
import ej.kf.IncompatibleFeatureException;
import ej.kf.InvalidFormatException;
import ej.kf.Kernel;
import ej.kf.UnknownFeatureException;

/**
 * A kernel waiting for an {@link InputStream} and loading it.
 */
public class SimpleKernel implements Runnable {

	private final List<Feature> features;
	private Thread kernelThread;
	private boolean load;

	/**
	 * Entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		Led.switchLed(true);
		System.out.println("Hello.main()");
		SimpleKernel kernel = new SimpleKernel();
		System.out.println("Start kernel");
		kernel.start();

	}

	/**
	 * Instantiates a {@link SimpleKernel}.
	 */
	public SimpleKernel() {
		features = new ArrayList<>();
		load = false;
	}

	/**
	 * Starts the kernel.
	 */
	public synchronized void start() {
		load = true;
		if (kernelThread == null) {
			kernelThread = new Thread(this, "Kernel");
			kernelThread.start();
		}
	}

	@Override
	public void run() {
		while (load) {
			System.out.println("[K] get inputstream...");
			try (InputStream is = FeatureInputStream.getInputStream()) {
				System.out.println("[K] load&start app");
				Feature feature = ej.kf.Kernel.load(is);
				features.add(feature);
				System.out.println("[K] app loaded");
			} catch (IOException | InvalidFormatException | IncompatibleFeatureException
					| AlreadyLoadedFeatureException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Stops the Kernel.
	 */
	public synchronized void stop() {
		load = false;
		Thread kernelThread = this.kernelThread;
		this.kernelThread = null;
		if (kernelThread != null) {
			kernelThread.interrupt();
		}
		for (Feature feature : features) {
			try {
				Kernel.unload(feature);
			} catch (UnknownFeatureException e) {
				// Sanity
			}
		}
	}
}
