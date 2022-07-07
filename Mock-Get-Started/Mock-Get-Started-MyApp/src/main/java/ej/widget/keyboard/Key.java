/*
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package ej.widget.keyboard;

import ej.annotation.Nullable;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;
import ej.widget.basic.Label;
import ej.widget.basic.OnClickListener;

/**
 * Represents one of the keys of a keyboard.
 */
public class Key extends Label {

	/**
	 * Active state.
	 */
	public static final int ACTIVE = 1;

	private static final int REPEAT_DELAY = 600;
	private static final int REPEAT_PERIOD = 60;

	private final KeyboardEventGenerator keyboard;
	private final Timer timer;
	private @Nullable OnClickListener onClickListener;
	private @Nullable TimerTask repeatTask;
	private boolean repeatable;
	private boolean pressed;

	/**
	 * Constructor.
	 *
	 * @param keyboardEventGenerator
	 *            the keyboard event generator
	 * @param timer
	 *            the timer used to repeat events when the user keeps pressing the key
	 */
	public Key(KeyboardEventGenerator keyboardEventGenerator, Timer timer) {
		this.keyboard = keyboardEventGenerator;
		this.timer = timer;
		this.onClickListener = null;
		this.repeatable = false;
		this.repeatTask = null;
	}

	/**
	 * Sets the key as a standard character input key
	 *
	 * @param character
	 *            the character printed by the key
	 */
	public void setStandard(final char character) {
		setEnabled(true);

		setText(String.valueOf(character));
		this.onClickListener = new OnClickListener() {
			@Override
			public void onClick() {
				Key.this.keyboard.send(character);
			}
		};
		this.repeatable = true;
		removeAllClassSelectors();
	}

	/**
	 * Sets the key as a standard character input key
	 *
	 * @param character
	 *            the character printed by the key
	 * @param classSelector
	 *            the class selector to set
	 */
	public void setStandard(final char character, int classSelector) {
		setStandard(character);
		addClassSelector(classSelector);
	}

	/**
	 * Sets the key as a special key
	 *
	 * @param text
	 *            the text to draw on the key
	 * @param listener
	 *            the action to execute when the key is pressed
	 */
	public void setSpecial(String text, OnClickListener listener) {
		setEnabled(true);
		setText(text);
		this.onClickListener = listener;
		this.repeatable = false;
		removeAllClassSelectors();
	}

	/**
	 * Sets the key as a special key
	 *
	 * @param text
	 *            the text to draw on the key
	 * @param listener
	 *            the action to execute when the key is pressed
	 * @param classSelector
	 *            the class selector to set
	 */
	public void setSpecial(String text, OnClickListener listener, int classSelector) {
		setSpecial(text, listener);
		addClassSelector(classSelector);
		requestLayOut();
	}

	/**
	 * Sets the key as a blank key
	 */
	public void setBlank() {
		setEnabled(false);
		setText(""); //$NON-NLS-1$
		this.onClickListener = null;
		this.repeatable = false;
		removeAllClassSelectors();
	}

	@Override
	public boolean isInState(int state) {
		return (this.pressed && state == ACTIVE) || super.isInState(state);
	}

	@Override
	public boolean handleEvent(int event) {
		switch (Event.getType(event)) {
		case Pointer.EVENT_TYPE:
			int action = Buttons.getAction(event);
			switch (action) {
			case Buttons.PRESSED:
				setPressed(true);
				OnClickListener clickListener = this.onClickListener;
				if (clickListener != null) {
					clickListener.onClick();
				}
				startRepeatTask();
				break;
			case Buttons.RELEASED:
				if (this.pressed) {
					// Update button state & style before external handling.
					setPressed(false);
					stopRepeatTask();
					return true;
				}
				// Don't exit when the button is dragged, because the user can drag inside the button.
				// case Pointer.DRAGGED:
			}
			break;
		case DesktopEventGenerator.EVENT_TYPE:
			action = DesktopEventGenerator.getAction(event);
			if (action == PointerEventDispatcher.EXITED) {
				if (this.pressed) {
					setPressed(false);
					stopRepeatTask();
				}
			}
			break;
		default:
			break;
		}
		return super.handleEvent(event);
	}

	private void setPressed(boolean pressed) {
		this.pressed = pressed;
		// for image buttons change style if active
		updateStyle();
		requestRender();
	}

	private void startRepeatTask() {
		if (this.repeatable && this.onClickListener != null) {
			TimerTask repeatTask = new TimerTask() {
				@Override
				public void run() {
					OnClickListener clickListener = Key.this.onClickListener;
					if (clickListener != null) {
						clickListener.onClick();
					}
				}
			};
			Timer timer = this.timer;
			this.repeatTask = repeatTask;
			timer.schedule(repeatTask, REPEAT_DELAY, REPEAT_PERIOD);
		}
	}

	private void stopRepeatTask() {
		TimerTask repeatTask = this.repeatTask;
		if (repeatTask != null) {
			repeatTask.cancel();
			this.repeatTask = null;
		}
	}

}
