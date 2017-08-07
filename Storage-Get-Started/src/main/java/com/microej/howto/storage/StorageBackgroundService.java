/*
 * Java
 *
 * Copyright 2017 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.howto.storage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.wadapps.app.BackgroundService;
import ej.wadapps.storage.Storage;

/**
 * BackgroundService storing then loading some data.
 */
public class StorageBackgroundService implements BackgroundService {
	private static final String KEY = "MY_DATA"; //$NON-NLS-1$
	private static final String DATA = "DATA"; //$NON-NLS-1$

	@Override
	public void onStart() {
		// Get the storage service.
		Storage storage = ServiceLoaderFactory.getServiceLoader().getService(Storage.class);
		if(storage==null){
			System.out.println("Storage unavailable.");
			return;
		}

		// Store a data with the storage.
		try (ByteArrayInputStream bais = new ByteArrayInputStream(DATA.getBytes())) {
			System.out.println("Store: " + KEY);
			storage.store(KEY, bais);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Retrieve the stored data.
		System.out.println("Load: " + KEY);
		try (InputStream stream = storage.load(KEY)) {
			// Do something with the input stream.
			int r = stream.read();
			while(r != -1) {
				r = stream.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// List all stored data.
		try {
			for (String k : storage.getIds()) {
				System.out.println("Data available: " + k);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Remove the stored data.
		try {
			System.out.println("Remove: " + KEY);
			storage.remove(KEY);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onStop() {
		// Not used

	}

}
