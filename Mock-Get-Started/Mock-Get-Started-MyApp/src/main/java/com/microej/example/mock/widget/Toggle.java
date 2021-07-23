/*
 * Copyright 2020-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.widget;

import ej.annotation.Nullable;
import ej.bon.XMath;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.microui.display.ResourceImage;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.listener.OnStateChangeListener;
import ej.widget.util.color.GradientHelper;

/**
 * A toggle is a widget which displays a text and a switch that can be checked
 * or unchecked.
 */
public class Toggle extends Widget implements Animation {

	/** The extra field ID for the color of the toggle when it is checked. */
	public static final int CHECKED_COLOR_FIELD = 0;
	/** The extra field ID for the color of the toggle when it is unchecked. */
	public static final int UNCHECKED_COLOR_FIELD = 1;

	private static final int DEFAULT_CHECKED_COLOR = Colors.GREEN;
	private static final int DEFAULT_UNCHECKED_COLOR = Colors.RED;

	private static final String BACKGROUND_IMAGE = "/images/toggle-background.png"; //$NON-NLS-1$
	private static final String CURSOR_ON_IMAGE = "/images/toggle-cursor-on.png"; //$NON-NLS-1$
	private static final String CURSOR_OFF_IMAGE = "/images/toggle-cursor-off.png"; //$NON-NLS-1$

	private static final long ANIM_DURATION = 150;

	private final String text;

	@Nullable
	private ResourceImage backgroundImage;
	@Nullable
	private ResourceImage cursorOnImage;
	@Nullable
	private ResourceImage cursorOffImage;
	@Nullable
	private OnStateChangeListener onStateChangeListener;


	private boolean checked;
	private long animEndTime;

	/**
	 * Creates a toggle with the given text to display.
	 *
	 * @param text the text to display.
	 */
	public Toggle(String text) {
		this.text = text;
		// this.checked = false; VM_DONE
		// this.animEndTime = 0; VM_DONE
	}


	@Override
	protected void onAttached() {
		this.backgroundImage = ResourceImage.loadImage(BACKGROUND_IMAGE);
		this.cursorOnImage = ResourceImage.loadImage(CURSOR_ON_IMAGE);
		this.cursorOffImage = ResourceImage.loadImage(CURSOR_OFF_IMAGE);
	}

	@Override
	protected void onDetached() {
		final ResourceImage backgroundImage = this.backgroundImage;
		if (backgroundImage != null) {
			backgroundImage.close();
		}
		final ResourceImage cursorOnImage = this.cursorOnImage;
		if (cursorOnImage != null) {
			cursorOnImage.close();
		}
		final ResourceImage cursorOffImage = this.cursorOffImage;
		if (cursorOffImage != null) {
			cursorOffImage.close();
		}
	}

	@Override
	public void onShown() {
		setEnabled(true);
	}

	@Override
	protected void onHidden() {
		getDesktop().getAnimator().stopAnimation(this);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		Font font = style.getFont();
		int horizontalAlignment = style.getHorizontalAlignment();
		int verticalAlignment = style.getVerticalAlignment();

		final ResourceImage backgroundImage = this.backgroundImage;
		final ResourceImage cursorOnImage = this.cursorOnImage;
		final ResourceImage cursorOffImage = this.cursorOffImage;
		assert backgroundImage != null;
		assert cursorOnImage != null;
		assert cursorOffImage != null;

		// compute checked ratio (1 = checked, 0 = unchecked, 0.5 = middle)
		float ratio = (float) (this.animEndTime - System.currentTimeMillis()) / ANIM_DURATION;
		ratio = XMath.limit(ratio, 0.0f, 1.0f);
		if (this.checked) {
			ratio = 1.0f - ratio;
		}

		// draw background
		int backgroundX = Alignment.computeLeftX(computeWidth(this.text, font, backgroundImage), 0, contentWidth,
				horizontalAlignment);
		int backgroundY = Alignment.computeTopY(backgroundImage.getHeight(), 0, contentHeight, verticalAlignment);
		g.setColor(GradientHelper.blendColors(getUncheckedColor(style), getCheckedColor(style), ratio));
		Painter.drawImage(g, backgroundImage, backgroundX, backgroundY);

		// draw cursor
		Image cursorImage = this.checked ? cursorOnImage : cursorOffImage;
		int cursorTranslation = backgroundImage.getWidth() - cursorImage.getWidth();
		int cursorX = backgroundX + (int) (ratio * cursorTranslation);
		int cursorY = Alignment.computeTopY(cursorImage.getHeight(), 0, contentHeight, verticalAlignment);
		g.setColor(style.getColor());
		Painter.drawImage(g, cursorImage, cursorX, cursorY);

		// draw text
		int textX = backgroundX + backgroundImage.getWidth() + computeSpacing(font);
		int textY = Alignment.computeTopY(font.getHeight(), 0, contentHeight, verticalAlignment);
		Painter.drawString(g, this.text, font, textX, textY);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		final ResourceImage backgroundImage = this.backgroundImage;
		final ResourceImage cursorOnImage = this.cursorOnImage;
		final ResourceImage cursorOffImage = this.cursorOffImage;
		assert backgroundImage != null;
		assert cursorOnImage != null;
		assert cursorOffImage != null;

		Font font = getStyle().getFont();
		int width = computeWidth(this.text, font, backgroundImage);
		int height = computeHeight(font, backgroundImage, cursorOnImage, cursorOffImage);
		size.setSize(width, height);
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			int action = Buttons.getAction(event);
			if (action == Buttons.RELEASED) {
				this.checked = !this.checked;
				this.animEndTime = System.currentTimeMillis() + ANIM_DURATION;
				requestRender();
				stateChanged();
				Animator animator = getDesktop().getAnimator();
				animator.stopAnimation(this);
				animator.startAnimation(this);
				return true;
			}
		}

		return super.handleEvent(event);
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		requestRender();
		return (isShown() && currentTimeMillis < this.animEndTime);
	}

	private static int computeWidth(String text, Font font, Image backgroundImage) {
		return backgroundImage.getWidth() + computeSpacing(font) + font.stringWidth(text);
	}

	private static int computeHeight(Font font, Image backgroundImage, Image cursorOnImage, Image cursorOffImage) {
		int max1 = Math.max(font.getHeight(), backgroundImage.getHeight());
		int max2 = Math.max(cursorOnImage.getHeight(), cursorOffImage.getHeight());
		return Math.max(max1, max2);
	}

	private static int computeSpacing(Font font) {
		return font.getHeight() / 2;
	}

	public boolean isChecked() {
		return checked;
	}
	private static int getCheckedColor(Style style) {
		return style.getExtraInt(CHECKED_COLOR_FIELD, DEFAULT_CHECKED_COLOR);
	}

	private static int getUncheckedColor(Style style) {
		return style.getExtraInt(UNCHECKED_COLOR_FIELD, DEFAULT_UNCHECKED_COLOR);
	}

	private void stateChanged() {
		if (onStateChangeListener != null) {
			onStateChangeListener.onStateChange(checked);
		}
	}
	public void addOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
		this.onStateChangeListener = onStateChangeListener;
	}
}
