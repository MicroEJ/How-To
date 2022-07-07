/*
 * Copyright 2015-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package ej.widget.keyboard;

import ej.annotation.Nullable;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.bon.XMath;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.EventHandler;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Command;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.widget.listener.OnFocusListener;
import ej.widget.util.render.StringPainter;

/**
 * A text field is a widget that holds a string that can be modified by the user.
 */
public class TextField extends Widget implements EventHandler, Animation, OnFocusListener {

	/**
	 * Active state.
	 */
	public static final int ACTIVE = 1;
	/**
	 * Empty state.
	 */
	public static final int EMPTY = 2;

	/** The extra field ID for the selection background color. */
	public static final int SELECTION_BACKGROUND = 0;
	/** The extra field ID for the selection text. */
	public static final int SELECTION_COLOR = 1;
	/** The extra field ID for the clear button font. */
	public static final int CLEAR_BUTTON_FONT = 2;
	/** Empty string. */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	/** Cursor blink period in milliseconds. */
	private static final long BLINK_PERIOD = 500;
	/** Default maximum text length. */
	private static final int DEFAULT_MAX_TEXT_LENGTH = 50;
	/** Pixels for double click detection or dragging validation. */
	private static final int FINGER_AREA = 5;
	/** Scroll change direction minimum offset. */
	private static final int SCROLL_CHANGE_DIRECTION = 20;
	/** Cursor width in pixels. */
	private static final int CURSOR_WIDTH = 2;
	/** Double click maximum interval in milliseconds. */
	private static final int DOUBLE_CLICK_TIME = 500;
	/** Long pressed minimum time in milliseconds. */
	private static final int LONG_PRESSED_TIME = 500;
	/** Cursor animation step in milliseconds. */
	private static final int ANIM_SPEED = 20;
	private final Timer timer;
	/** Text buffer. */
	private final StringBuilder buffer;
	/** Text placeholder. */
	private String placeHolder;
	/** Maximum text length in characters. */
	private int maxTextLength;
	/** Maximum field length in pixels. */
	private final int fieldLength;
	/** Caret start position. */
	private int caretStart;
	/** Caret end position. */
	private int caretEnd;
	/** Text field is active or not. */
	private boolean active;
	/** Caret blinking task. */
	private @Nullable TimerTask blinkTask;
	/** Caret visibility. */
	private boolean showCaret;
	/** Text field width in pixels. */
	private int fieldWidth;
	/** Text content width in pixels. */
	private int textWidth;
	/** Location of last pressed event. */
	private int pressedX;
	/** Scrolling sequence, it ends when there is a release event. */
	private boolean scrolledSequence;
	/** Scrolling event active. */
	private boolean isScrolled;
	/** Scrolling direction, true for left, false for right. */
	private boolean scrollDirectionLeft;
	/** Dragging event active (select text). */
	private boolean isDragged;
	/** Scrolling start time. */
	private long startTime;
	/**
	 * Current offset left position, is zero on left of the text or negative if scrolling more text on the right side.
	 */
	private int startX;
	/** Current scrolled distance. */
	private int scrolledDistance;
	/** Last distance on scrolling. */
	private int lastDistance;
	/** Counter for change direction while scrolling. */
	private int counterScrolled;
	/** Scrolling animation period. */
	private int animPeriod;
	/** Computed pressed time, it can start the scrolling if it's more than LONG_PRESSED_TIME. */
	private long pressedTime;
	/** Double click time, it must be less than DOUBLE_CLICK_TIME. */
	private long doubleClickTime;

	private OnFocusListener onFocusListener;
	/**
	 * Creates a text field with an empty text.
	 *
	 * @param timer
	 *            the timer used to blink the caret
	 */
	public TextField(Timer timer) {
		this(EMPTY_STRING, EMPTY_STRING, DEFAULT_MAX_TEXT_LENGTH, timer);
	}

	/**
	 * Creates a text field with an initial text and a place holder.
	 * <p>
	 * The place holder is displayed when the text is empty.
	 *
	 * @param text
	 *            the text to set.
	 * @param placeHolder
	 *            the place holder to set.
	 * @param fieldLength
	 *            how many characters are shown
	 * @param timer
	 *            the timer used to blink the caret
	 */
	public TextField(String text, String placeHolder, int fieldLength, Timer timer) {
		this.timer = timer;
		this.buffer = new StringBuilder(text);
		this.placeHolder = placeHolder;
		this.maxTextLength = DEFAULT_MAX_TEXT_LENGTH;
		this.fieldLength = fieldLength;
	}

	/**
	 * Gets the place holder.
	 *
	 * @return the place holder.
	 */
	public String getPlaceHolder() {
		return this.placeHolder;
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

		if (this.buffer.length() > maxTextLength) {
			this.buffer.setLength(maxTextLength);
		}
	}

	/**
	 * Gets the text of the text field.
	 *
	 * @return the text.
	 */
	public String getText() {
		return this.buffer.toString();
	}

	/**
	 * Gets the text or the place holder, depending on whether or not the text is empty.
	 *
	 * @return the text if it is not empty, the place holder otherwise.
	 */
	private String getTextOrPlaceHolder() {
		if (isEmpty()) {
			return this.placeHolder;
		} else {
			return getText();
		}
	}

	/**
	 * Gets the length of the text of the text field.
	 *
	 * @return the text length.
	 */
	public int getTextLength() {
		return this.buffer.length();
	}

	/**
	 * Sets the place holder of the text field.
	 *
	 * @param placeHolder
	 *            the place holder to set.
	 */
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}

	/**
	 * Sets the text of the text field.
	 *
	 * @param text
	 *            the text to set.
	 */
	public void setText(String text) {
		boolean wasEmpty = isEmpty();
		this.buffer.setLength(0);
		this.buffer.append(text);
		setCaret(text.length());
		updateEmptyState(wasEmpty, isEmpty());
	}

	/**
	 * Removes the character just before the caret.
	 * <p>
	 * If a part of the text is selected, it is removed instead of the character.
	 */
	public void back() {
		if (!removeSelection()) {
			int caret = this.caretEnd;
			if (caret > 0) {
				boolean wasEmpty = isEmpty();
				int newCaret = caret - 1;
				this.buffer.deleteCharAt(newCaret);
				setCaret(newCaret);
				updateTextLength();
				updateEmptyState(wasEmpty, isEmpty());
			}
		}
	}

	/**
	 * Inserts a character in the text at the caret position.
	 * <p>
	 * If a part of the text is selected, it is removed and replaced by the given character.
	 *
	 * @param c
	 *            character to be inserted at caret position
	 */
	public void insert(char c) {
		if (this.buffer.length() < this.maxTextLength) {
			boolean wasEmpty = isEmpty();
			removeSelection();
			int caret = this.caretEnd;
			this.buffer.insert(caret, c);
			int newCaret = caret + 1;
			setSelection(newCaret, newCaret, 2);
			updateTextLength();
			updateEmptyState(wasEmpty, false);
		}
	}

	private void updateEmptyState(boolean wasEmpty, boolean isEmpty) {
		if (wasEmpty != isEmpty) {
			updateStyle();
			requestRender();
		}
	}

	/**
	 * Gets whether or not the text is empty.
	 *
	 * @return <code>true</code> if the text is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return this.buffer.length() == 0;
	}

	/**
	 * Gets the part of the text that is selected.
	 *
	 * @return the selected text.
	 */
	public String getSelection() {
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		char[] selectedChars = new char[selectionEnd - selectionStart + 1];
		this.buffer.getChars(selectionStart, selectionStart, selectedChars, 0);
		return new String(selectedChars);
	}

	/**
	 * Gets the caret position in the text.
	 *
	 * @return the caret position.
	 */
	public int getCaret() {
		return this.caretEnd;
	}

	/**
	 * Gets the start index of the selection.
	 *
	 * @return the start index of the selection.
	 */
	public int getSelectionStart() {
		return Math.min(this.caretStart, this.caretEnd);
	}

	/**
	 * Gets the end index of the selection.
	 *
	 * @return the end index of the selection.
	 */
	public int getSelectionEnd() {
		return Math.max(this.caretStart, this.caretEnd);
	}

	/**
	 * Sets the selection range in the text.
	 *
	 * @param start
	 *            the selection start index.
	 * @param end
	 *            the selection end index.
	 * @param characterMove
	 *            0 for click, -1 for move one character right, 1 for move one character left, 2 for insert new
	 *            character
	 */
	public void setSelection(int start, int end, int characterMove) {
		if (start != this.caretStart || end != this.caretEnd) {
			int length = this.buffer.length();
			start = XMath.limit(start, 0, length);
			end = XMath.limit(end, 0, length);
			this.caretStart = start;
			this.caretEnd = end;

			// set correct caret, expand beyond visible limits

			if (start == 0) {
				// cursor reset for first text position
				this.startX = 0;
			} else {
				// move cursor
				moveCursor(start, characterMove);
			}

			// blinking
			if (this.active) {
				this.showCaret = true;
				if (start == end) {
					startBlink();
				} else {
					stopBlink();
				}
			}

		}
		requestRender();
	}

	private void moveCursor(int start, int characterMove) {
		Font font = getStyle().getFont();
		String text = getText();
		int currentCharacterWidth = getX(text, font, start - 1, 1);
		int currentWidth = getX(text, font, 0, start);
		int maxWidth = this.fieldWidth - CURSOR_WIDTH;

		int currentLeftX = currentWidth + this.startX;
		if (currentWidth >= maxWidth) {
			if (characterMove > 0 || characterMove == 2) {
				// while moving right one character we adjust startX on right margin or,
				// while inserting character we move left if we hit the right margin
				this.startX -= currentCharacterWidth;
			}
		} else {
			// move left one character, adjust startX if we hit the left margin
			if (this.startX < 0 && currentLeftX < 0) {
				this.startX += currentCharacterWidth;
				this.startX = this.startX > 0 ? 0 : this.startX;
			}
		}
	}

	/**
	 * Sets the caret position in the text.
	 *
	 * @param position
	 *            the caret index.
	 */
	public void setCaret(int position) {
		setSelection(position, position, 0);
	}

	/**
	 * Gets whether the caret is currently shown or not.
	 * <p>
	 * This state changes repeatedly when the text is active and the caret is blinking.
	 *
	 * @return <code>true</code> if the caret is shown, <code>false</code> otherwise.
	 */
	protected boolean isShownCaret() {
		return this.showCaret;
	}

	private boolean removeSelection() {
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		if (selectionStart != selectionEnd) {
			this.buffer.delete(selectionStart, selectionEnd);
			setCaret(selectionStart);
			return true;
		}
		return false;
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		int horizontalAlignment = style.getHorizontalAlignment();
		int verticalAlignment = style.getVerticalAlignment();
		Font font = style.getFont();
		this.fieldWidth = contentWidth;

		String text = getTextOrPlaceHolder();
		if (this.textWidth == 0) {
			updateTextLength();
		}

		if (this.isScrolled) {
			computeScrollX();
		}

		// Draw text.
		int startAt = this.startX + this.scrolledDistance;
		g.disableEllipsis();
		g.setColor(style.getColor());
		StringPainter.drawStringInArea(g, text, font, startAt, 0, contentWidth, contentHeight, horizontalAlignment,
				verticalAlignment);

		// Compute selection bounds and draw it.
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();

		// Draw selection or single caret.
		if (this.showCaret) {
			// background
			int selectionColor = style.getExtraInt(SELECTION_BACKGROUND, style.getColor());
			g.setColor(selectionColor);
			Rectangle selection = getBounds(selectionStart, selectionEnd, text, font, contentWidth, contentHeight,
					horizontalAlignment, verticalAlignment);
			int w = selectionStart != selectionEnd ? selection.getWidth() : 1;
			int selectionX = selection.getX();
			int selectionY = selection.getY();
			selectionX += this.startX;

			Painter.fillRectangle(g, selectionX, selectionY, w + 1, selection.getHeight());
			g.setBackgroundColor(selectionColor);

			// selected text
			String selectedString = text.substring(selectionStart, selectionEnd);
			g.setColor(style.getExtraInt(SELECTION_COLOR, Colors.BLACK));
			StringPainter.drawStringInArea(g, selectedString, font, selectionX + 1, selectionY, w, contentHeight,
					Alignment.VCENTER, verticalAlignment);
		}

	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Font font = getStyle().getFont();
		char[] newStr = new char[this.fieldLength];
		for (int i = 0; i < this.fieldLength; i++) {
			newStr[i] = 'A';
		}
		StringPainter.computeOptimalSize(new String(newStr), font, size);
	}

	private Rectangle getBounds(int startIndex, int endIndex, String text, Font font, int areaWidth, int areaHeight,
			int horizontalAlignment, int verticalAlignment) {
		// Shift to beginning of the text.
		int xLeft = Alignment.computeLeftX(font.stringWidth(text), 0, areaWidth, horizontalAlignment);
		int yTop = Alignment.computeTopY(font.getHeight(), 0, areaHeight, verticalAlignment);
		int top = yTop;
		int left = xLeft + getX(startIndex, 0, font, text);
		int right = xLeft + getX(endIndex, 0, font, text);
		return new Rectangle(left, top, right - left, font.getHeight());
	}

	private static int getX(int index, int currentIndex, Font font, String line) {
		if (index < currentIndex) {
			return font.stringWidth(line);
		} else {
			return getX(line, font, 0, index - currentIndex);
		}
	}

	private static int getX(String line, Font font, int offset, int length) {
		return font.substringWidth(line, offset, length);
	}

	/**
	 * Activates or deactivates the text field.
	 * <p>
	 * When the text field is active the cursor blinks, otherwise the cursor is not visible.
	 *
	 * @param active
	 *            <code>true</code> to activate the text field, <code>false</code> otherwise.
	 * @since 2.3.0
	 */
	public void setActive(boolean active) {
		if (active != this.active) {
			this.active = active;
			if (active) {
				if (this.caretStart == this.caretEnd) {
					startBlink();
				} else {
					this.showCaret = true;
				}
			} else {
				stopBlink();
				this.showCaret = false;
			}
			updateStyle();
			requestRender();
		}
	}

	private void startBlink() {
		if (this.blinkTask == null) {
			TimerTask blinkTask = new TimerTask() {
				@Override
				public void run() {
					TextField.this.showCaret = !TextField.this.showCaret;
					requestRender();
				}
			};
			this.blinkTask = blinkTask;
			this.timer.schedule(blinkTask, 0, BLINK_PERIOD);
		}
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		stopBlink();
	}

	private void stopBlink() {
		TimerTask blinkTask = this.blinkTask;
		if (blinkTask != null) {
			blinkTask.cancel();
			this.blinkTask = null;
		}
	}

	@Override
	public boolean isInState(int state) {
		return (state == ACTIVE && this.active) || (state == EMPTY && isEmpty()) || super.isInState(state);
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);

		switch (type) {
		case Command.EVENT_TYPE:
			int data = Event.getData(event);
			if (onCommand(data)) {
				return true;
			}
			break;
		case KeyboardEventGenerator.EVENTGENERATOR_ID:
			handleKeyboard(event);
			return true;
		case Pointer.EVENT_TYPE:
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX() - this.startX;
			int action = Buttons.getAction(event);
			switch (action) {
			case Buttons.PRESSED:
				onPointerPressed(pointerX);
				break;
			case Buttons.RELEASED:
				onPointerReleased(pointerX);
				return true;
			case Pointer.DRAGGED:
				onPointerDragged(pointerX);
				return true;
			default:
				break;
			}
			break;
		}
		return super.handleEvent(event);
	}

	private void handleKeyboard(int event) {
		KeyboardEventGenerator keyboard = (KeyboardEventGenerator) Event.getGenerator(event);
		char c = keyboard.getChar(event);
		switch (c) {
		case ControlCharacters.BACK_SPACE:
			back();
			break;
		case ControlCharacters.VK_LEFT:
			onCommand(Command.LEFT);
			break;
		case ControlCharacters.VK_RIGHT:
			onCommand(Command.RIGHT);
			break;
		default:
			insert(c);
			break;
		}

	}

	private void onPointerPressed(int pointerX) {
		int newCaret = getCaret(pointerX);
		// reset selection and caret to current position
		setCaret(newCaret);

		// double click start/reset
		long currentTime = System.currentTimeMillis();
		if (currentTime - this.pressedTime > DOUBLE_CLICK_TIME) {
			this.pressedTime = currentTime;
		}
		this.pressedX = pointerX;
		this.isDragged = false;
		this.isScrolled = false;
	}

	private void onPointerDragged(int pointerX) {
		if (!this.isDragged && !this.isScrolled && !this.scrolledSequence
				&& System.currentTimeMillis() - this.pressedTime > LONG_PRESSED_TIME) {
			// scroll
			this.scrollDirectionLeft = this.pressedX > pointerX;
			this.isScrolled = true; // do not allow multiple parallel animations
			this.lastDistance = pointerX;
			scroll();
		} else if (this.scrolledSequence) {
			// dragged while scrolling, check dragging direction
			boolean currentDirection = this.lastDistance > pointerX;
			int draggedWhileScrolling = Math.abs(this.lastDistance - pointerX);
			this.lastDistance = pointerX;

			if (this.scrollDirectionLeft == currentDirection) {
				this.counterScrolled = 0;
			} else {
				this.counterScrolled += draggedWhileScrolling;
				if (this.counterScrolled > SCROLL_CHANGE_DIRECTION) {
					this.scrollDirectionLeft = currentDirection;
					setCaret(getCaret(pointerX));
					this.startX += this.scrolledDistance;
					initScroll();
				}
			}
		} else if (!this.isScrolled && Math.abs(this.pressedX - pointerX) > FINGER_AREA) {
			// drag
			this.isDragged = true;
			int newCaret = getCaret(pointerX);
			// Update selection.
			setSelection(this.caretStart, newCaret, 0);
		}
	}

	private int getCaret(int pointerX) {
		int x = pointerX - getAbsoluteX();
		Style style = getStyle();
		Font font = style.getFont();
		Rectangle contentBounds = getContentBounds();
		return getIndex(x - contentBounds.getX(), getText(), font, contentBounds.getWidth(),
				style.getHorizontalAlignment());
	}

	private int getIndex(int x, String text, Font font, int areaWidth, int horizontalAlignment) {
		int textLength = text.length();
		if (textLength == 0) {
			return 0;
		}

		// Shift to beginning of the text.
		int xLeft = Alignment.computeLeftX(font.stringWidth(text), 0, areaWidth, horizontalAlignment);
		x -= xLeft;

		int min = 0;
		int max = textLength;
		int left = 0;

		// Search the character under the given position.
		// Use dichotomy.
		while (max - min > 1) {
			int half = (max - min) >> 1;
			int halfWidth = font.substringWidth(text, min, half);
			if (left + halfWidth > x) {
				// Select first part.
				max = min + half;
			} else {
				// Select second part.
				min += half;
				left += halfWidth;
			}
		}

		// Select the right side of the selected character.
		if ((left + font.charWidth(text.charAt(min)) / 2) < x) {
			min++;
		}
		return min;
	}

	private void onPointerReleased(int pointerX) {
		// double click detection
		long currentTime = System.currentTimeMillis();
		if (currentTime - this.doubleClickTime > DOUBLE_CLICK_TIME) {
			// reset double click
			this.doubleClickTime = currentTime;
		} else if (currentTime - this.pressedTime < DOUBLE_CLICK_TIME
				&& Math.abs(this.pressedX - pointerX) < FINGER_AREA) {
			onPointerDoubleClicked(pointerX);
		}

		// scrolling clean-up
		if (this.scrolledSequence) {
			this.scrolledSequence = false;
			int rightScrollTarget = -computeExtraWidth();
			this.startX += this.scrolledDistance;
			if (this.startX > 0) {
				this.startX = 0;
			} else if (this.startX < rightScrollTarget) {
				this.startX = rightScrollTarget;
			}
			this.caretStart = getCaret(pointerX - this.scrolledDistance);
			finishScroll();
			this.caretEnd = this.caretStart;
			setCaret(this.caretStart);
		}
		this.isDragged = false;
	}

	/**
	 * Select word under double click position.
	 *
	 * @param pointerX
	 *            double click position
	 */
	private void onPointerDoubleClicked(int pointerX) {
		this.doubleClickTime = System.currentTimeMillis();
		char space = 0x20;
		int clickedOn = getCaret(pointerX);
		String text = getText();
		int wordRight = text.indexOf(space, clickedOn);
		if (wordRight == -1) {
			wordRight = text.length();
		}

		text = text.substring(0, wordRight);
		int wordLeft = text.lastIndexOf(space, wordRight - 1);
		wordLeft = wordLeft == -1 ? 0 : wordLeft + 1;
		setSelection(wordLeft, wordRight, 0);
	}

	private void scroll() {
		if (this.textWidth > this.fieldWidth) {
			Animator animator = getDesktop().getAnimator();
			initScroll();
			this.scrolledSequence = true;
			this.scrolledDistance = 0;
			animator.startAnimation(this);
		}
	}

	private void initScroll() {
		this.startTime = System.currentTimeMillis();
		this.animPeriod = (ANIM_SPEED * this.textWidth);
		this.counterScrolled = 0;
	}

	/**
	 * Update start X for text, use scroll if necessary.
	 * <p>
	 * Detects begin and end of text and stops scrolling on edges.
	 */
	private void computeScrollX() {
		if (this.isScrolled && this.animPeriod != 0) {
			int elapsedTime = (int) (System.currentTimeMillis() - this.startTime) % this.animPeriod;
			int direction = this.scrollDirectionLeft ? 1 : -1;
			int allowedScrollWidth = this.textWidth + FINGER_AREA;

			this.scrolledDistance = direction * (allowedScrollWidth * elapsedTime / this.animPeriod);

			// on edges, stop animation
			if (this.scrollDirectionLeft) {
				if (this.startX + this.scrolledDistance >= 0) {
					this.startX = 0;
					finishScroll();
				}
			} else {
				int rightScrollTarget = -computeExtraWidth();
				int totalOffset = this.startX + this.scrolledDistance;
				if (totalOffset < rightScrollTarget) {
					this.isScrolled = false;
				}
			}
		}
	}

	private void finishScroll() {
		this.animPeriod = 0;
		this.scrolledDistance = 0;
		this.isScrolled = false;
	}

	private int computeExtraWidth() {
		return this.textWidth - this.fieldWidth;
	}

	private void updateTextLength() {
		this.textWidth = getStyle().getFont().stringWidth(this.getText());
	}

	private boolean onCommand(int command) {
		int caret = this.caretEnd;
		if (command == Command.LEFT && caret > 0) {
			int newCaret = caret - 1;
			setSelection(newCaret, newCaret, -1);
			return true;
		} else if (command == Command.RIGHT && caret < this.buffer.length()) {
			int newCaret = caret + 1;
			setSelection(newCaret, newCaret, +1);
			return true;
		}
		return false;
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		if (this.isScrolled) {
			requestRender();
		}
		return this.isScrolled;
	}

	/**
	 * @param onFocusListener
	 */
	public void addOnFocusListener(OnFocusListener onFocusListener) {
		// TODO Auto-generated method stub
		this.onFocusListener = onFocusListener;

	}

	@Override
	public void onGainFocus() {
		// TODO Auto-generated method stub
		onFocusListener.onGainFocus();

	}

	@Override
	public void onLostFocus() {
		// TODO Auto-generated method stub
		onFocusListener.onLostFocus();
	}
}
