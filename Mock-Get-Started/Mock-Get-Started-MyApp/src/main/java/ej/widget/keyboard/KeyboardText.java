/*
 * Java
 *
 * Copyright 2018-2019 MicroEJ Corp. All rights reserved. 
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package ej.widget.keyboard;

import com.microej.example.mock.style.ClassSelectors;

import ej.giml.annotation.Element;
import ej.giml.annotation.ElementAttribute;
import ej.giml.annotation.ElementConstructor;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.microui.util.EventHandler;
import ej.style.Style;
import ej.style.container.AlignmentHelper;
import ej.style.container.Rectangle;
import ej.style.text.TextManager;
import ej.style.util.ElementAdapter;
import ej.style.util.StyleHelper;
import ej.widget.basic.Text;
import ej.widget.listener.OnFocusListener;

/**
 * A text is a widget that holds a string that can be modified by the user.
 */
@Element
public class KeyboardText extends Text implements EventHandler {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final OnFocusListener[] EMPTY_FOCUS_LISTENERS = new OnFocusListener[0];

	private static final String CLEAR_BUTTON_STRING = "\u00D7"; //$NON-NLS-1$

	private static final int DEFAULT_MAX_TEXT_LENGTH = 25;

	private final ElementAdapter selectionElement;
	private final ElementAdapter clearButtonElement;

	private int maxTextLength;

	private OnFocusListener[] onFocusListeners;

	/**
	 * Creates an empty text.
	 */
	public KeyboardText() {
		this(EMPTY_STRING);
	}

	/**
	 * Creates a text with an initial content.
	 *
	 * @param text
	 *            the text to set.
	 * @throws NullPointerException
	 *             if the given text is <code>null</code>.
	 */
	public KeyboardText(String text) {
		this(text, EMPTY_STRING);
	}

	/**
	 * Creates a text with an initial content and a place holder.
	 * <p>
	 * The place holder is displayed when the text is empty.
	 *
	 * @param text
	 *            the text to set.
	 * @param placeHolder
	 *            the place holder to set.
	 * @throws NullPointerException
	 *             if one or both the given text and place holder are <code>null</code>.
	 */
	@ElementConstructor
	public KeyboardText(@ElementAttribute(defaultValue = EMPTY_STRING) String text,
			@ElementAttribute(defaultValue = EMPTY_STRING) String placeHolder) {
		super(text, placeHolder);
		this.selectionElement = new ElementAdapter(this);
		this.selectionElement.addClassSelector(ClassSelectors.CLASS_SELECTOR_SELECTION);
		this.clearButtonElement = new ElementAdapter(this);
		this.clearButtonElement.addClassSelector(ClassSelectors.CLASS_SELECTOR_CLEAR_BUTTON);
		this.onFocusListeners = EMPTY_FOCUS_LISTENERS;
		setMaxTextLength(DEFAULT_MAX_TEXT_LENGTH);
	}

	/**
	 * Gets the max text length of the text field.
	 *
	 * @return the max text length.
	 */
	public int getMaxTextLength() {
		return this.maxTextLength;
	}

	/**
	 * Sets the max text length of the text field.
	 *
	 * @param maxTextLength
	 *            the max text length.
	 */
	public void setMaxTextLength(int maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

	@Override
	public void insert(char c) {
		if (getTextLength() < this.maxTextLength) {
			super.insert(c);
		}
	}

	/**
	 * Adds a listener on the focus events of the text.
	 *
	 * @param onFocusListener
	 *            the focus listener to add.
	 */
	public void addOnFocusListener(OnFocusListener onFocusListener) {
		OnFocusListener[] onFocusListeners = this.onFocusListeners;
		int listenersLength = onFocusListeners.length;
		OnFocusListener[] newArray = new OnFocusListener[listenersLength + 1];
		System.arraycopy(onFocusListeners, 0, newArray, 0, listenersLength);
		newArray[listenersLength] = onFocusListener;
		this.onFocusListeners = newArray;
	}

	/**
	 * Removes a listener on the focus events of the text.
	 *
	 * @param onFocusListener
	 *            the focus listener to remove.
	 */
	public void removeOnFocusListener(OnFocusListener onFocusListener) {
		OnFocusListener[] onFocusListeners = this.onFocusListeners;
		int listenersLength = onFocusListeners.length;
		for (int i = listenersLength; --i >= 0;) {
			OnFocusListener candidate = onFocusListeners[i];
			if (candidate.equals(onFocusListener)) {
				if (listenersLength == 1) {
					this.onFocusListeners = EMPTY_FOCUS_LISTENERS;
				} else {
					OnFocusListener[] newArray = new OnFocusListener[listenersLength - 1];
					System.arraycopy(onFocusListeners, 0, newArray, 0, i);
					System.arraycopy(onFocusListeners, i + 1, newArray, i, listenersLength - i - 1);
					this.onFocusListeners = newArray;
				}
			}
		}
	}

	private void notifyOnGainFocusListeners() {
		for (OnFocusListener onFocusListener : this.onFocusListeners) {
			onFocusListener.onGainFocus();
		}
	}

	private void notifyOnLostFocusListeners() {
		for (OnFocusListener onFocusListener : this.onFocusListeners) {
			onFocusListener.onLostFocus();
		}
	}

	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		Font font = StyleHelper.getFont(style);
		TextManager textManager = style.getTextManager();
		// Keep call to getText() for subclasses (such as Password).
		String text = getText();
		int alignment = style.getAlignment();
		int foregroundColor = style.getForegroundColor();

		// Remove selection thickness.
		bounds.decrementSize(1, 1);

		// Compute selection bounds and draw it.
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		if (selectionStart != selectionEnd || isShownCaret()) {
			Style selectionStyle = this.selectionElement.getStyle();
			Rectangle[] selection = textManager.getBounds(selectionStart, selectionEnd, text, font, bounds, alignment);
			g.setColor(selectionStyle.getForegroundColor());
			for (Rectangle rectangle : selection) {
				int w = (selectionStart != selectionEnd ? rectangle.getWidth() : 1);
				g.fillRect(rectangle.getX() + 1, rectangle.getY(), w, rectangle.getHeight());
			}
		}
		// handling placeholder case
		if (isEmpty()) {
			text = getPlaceHolder();
		}

		// Shift selection thickness.
		g.translate(1, 1);
		textManager.drawText(g, text, font, foregroundColor, bounds, alignment);
		g.translate(-1, -1);
		// super.renderContent(g, style, bounds);

		// Draw clear button.
		Style clearButtonStyle = this.clearButtonElement.getStyle();
		Font clearButtonFont = StyleHelper.getFont(clearButtonStyle);
		textManager.drawText(g, CLEAR_BUTTON_STRING, clearButtonFont, clearButtonStyle.getForegroundColor(), bounds,
				clearButtonStyle.getAlignment());
	}

	@Override
	public void gainFocus() {
		super.gainFocus();
		notifyOnGainFocusListeners();
	}

	@Override
	public void lostFocus() {
		super.lostFocus();
		notifyOnLostFocusListeners();
		// this.caretEnd = this.caretStart = this.buffer.length();
	}

	@Override
	public boolean handleEvent(int event) {
		boolean result = super.handleEvent(event);
		int type = Event.getType(event);
		switch (type) {
		case Event.POINTER:
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX();
			int pointerY = pointer.getY();
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				onPointerPressed(pointerX, pointerY);
				return true;
			}
			break;
		}
		return result;
	}

	private void onPointerPressed(int pointerX, int pointerY) {
		// check clear button event
		Rectangle bounds = this.getContentBounds();
		Style style = this.clearButtonElement.getStyle();
		int clearButtonWidth = StyleHelper.getFont(style).stringWidth(CLEAR_BUTTON_STRING);
		int clearButtonX = AlignmentHelper.computeXLeftCorner(clearButtonWidth, bounds.getX(), bounds.getWidth(),
				style.getAlignment());
		int pX = getRelativeX(pointerX);
		if (pX >= clearButtonX && pX < clearButtonX + clearButtonWidth) {
			setText(EMPTY_STRING);
			return;
		}
	}

}
