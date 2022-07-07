/*
 * Java
 *
 * Copyright 2017-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.howto.storage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.wadapps.app.BackgroundService;
import ej.wadapps.storage.Storage;

/**
 * A example showing a basic use of the Storage API.
 *
 */
public class StorageBackgroundService implements BackgroundService {

	/** Logger */
	private static final Logger LOGGER = Logger.getLogger(StorageBackgroundService.class.getName());

	/** * A key example. */
	private static final String KEY = "MY_DATA"; //$NON-NLS-1$

	/** A value example. **/
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
			LOGGER.log(Level.SEVERE, e.getMessage());
		}

		// Retrieve the stored data.
		System.out.println("Load: " + KEY);
		StringBuffer buffer = new StringBuffer();
		try (InputStream stream = storage.load(KEY)) {
			// Do something with the input stream.
			int byteReaded;
			while((byteReaded = stream.read()) != -1) {
				buffer.append((char) byteReaded);
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		System.out.println("Value : ."+ buffer.toString()+".");

		// List all stored data.
		try {
			for (String k : storage.getIds()) {
				System.out.println("Data available: " + k);
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}

		// Remove the stored data.
		try {
			System.out.println("Remove: " + KEY);
			storage.remove(KEY);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	@Override
	public void onStop() {
		// Not used
	}

}
