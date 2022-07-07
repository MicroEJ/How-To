/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.is2t.hil.HIL;
import com.is2t.hil.StopListener;
import com.microej.example.mock.MySNI;

/**
 *
 */
public class MyMockFrame extends JFrame {

	private static final long serialVersionUID = -5647085267195160276L;

	public static final MyMockFrame INSTANCE = new MyMockFrame();

	private final JPanel panel;
	private final Thermostat thermostat;

	private final Light light;

	private final JTextField textField;

	private final JLabel textReceived;

	private final Connected connected;

	/**
	 * Forbid instantiation.
	 */
	private MyMockFrame() {
		HIL.getInstance().addStopListener(new StopListener() {

			@Override
			public void stop(boolean isEvalVersion) {
				dispose();
			}
		});

		// Sets the UI look and feel.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		// Setup the window frame
		this.setTitle("My Mock");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		this.setContentPane(panel);

		// Add the widgets
		panel.add(new JLabel(" Connected"));
		connected = new Connected();
		panel.add(connected);

		panel.add(new JLabel(" Light"));
		light = new Light();
		panel.add(light);

		JLabel thermostatLabel = new JLabel();
		panel.add(thermostatLabel);
		thermostat = new Thermostat(thermostatLabel);
		panel.add(thermostat);

		textField = new JTextField(" Text to send");
		JButton textButton = new JButton("Send text");
		panel.add(textButton);
		panel.add(textField);
		textButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MySNI.sendText();
			}
		});

		panel.add(new JLabel(" Text received:"));
		textReceived = new JLabel("");
		panel.add(textReceived);

		this.setSize(250, 310);
	}

	public int getTemperature() {
		return thermostat.getValue();
	}

	public static void main(String[] args) {
		INSTANCE.getTemperature();
		boolean on = true;
		while (true) {
			on = !on;
			INSTANCE.switchLight(on);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void switchLight(boolean on) {
		this.light.switchLight(on);
	}

	public void setText(String text) {
		textReceived.setText(text);
	}

	public String getText() {
		return textField.getText();
	}

	public boolean isConnected() {
		return connected.isSelected();
	}
}
