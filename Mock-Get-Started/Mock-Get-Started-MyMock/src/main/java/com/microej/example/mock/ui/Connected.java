/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.ui;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.microej.example.mock.MySP;

/**
 *
 */
public class Connected extends JCheckBox {

	private static final long serialVersionUID = -4759326086681450752L;
	private boolean last;
	/**
	 *
	 */
	public Connected() {
		// Init the state to the contrary to force the update of the initial
		// state.
		last = !isSelected();
		ChangeListener listener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				boolean selected = isSelected();
				if (last != selected) {
					last = selected;
					MySP.sendConnected(selected);
				}
			}
		};
		addChangeListener(listener);
		// Initialize the SP.
		listener.stateChanged(null);
	}

}
