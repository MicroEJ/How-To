/*
 * Java
 *
 * Copyright 2018 IS2T. All rights reserved.
 * For demonstration purpose only.
 * IS2T PROPRIETARY. Use is subject to license terms.
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
