/*
 * Java
 *
 * Copyright 2018-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.example.mock.style;

import com.microej.example.mock.widget.Connected;
import com.microej.example.mock.widget.Light;
import com.microej.example.mock.widget.TemperatureWidget;
import com.microej.example.mock.widget.TextReceiver;
import com.microej.example.mock.widget.Toggle;

import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.style.outline.border.RoundedBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.basic.ImageWidget;
import ej.widget.basic.Label;
import ej.widget.keyboard.Key;
import ej.widget.keyboard.Keyboard;
import ej.widget.keyboard.TextField;
import ej.widget.util.font.StrictFontLoader;

/**
 * Class responsible for initializing the demo styles.
 */
public class StylesheetPopulator {
	/** Form color ID. */
	/* package */ static final int FORM = 9;
	/** Result label ID. */
	/* package */ static final int RESULT_LABEL = 10;
	/** Page content rolor ID. */
	/* package */ static final int CONTENT = 301;

	private static final int FOREGROUND = MicroEJColors.CONCRETE_BLACK_75;
	private static final int BACKGROUND = MicroEJColors.WHITE;
	private static final int CHECKED_FOREGROUND = MicroEJColors.CORAL;
	private static final int ACTIVE_FOREGROUND = MicroEJColors.POMEGRANATE;


	private static final int KEYBOARD_BACKGROUND_COLOR = MicroEJColors.CONCRETE_WHITE_75;
	private static final int KEYBOARD_KEY_COLOR = MicroEJColors.CONCRETE;
	private static final int KEYBOARD_HIGHLIGHT_COLOR = MicroEJColors.CORAL;
	private static final int TEXT_PLACEHOLDER_COLOR = MicroEJColors.CONCRETE_WHITE_25;
	private static final int TEXT_SELECTION_COLOR = MicroEJColors.CONCRETE_WHITE_50;

	private static final int SEND_TEXT_BUTTON_INACTIVE_COLOR = 0xeaeaea;
	private static final int SEND_TEXT_BUTTON_ACTIVE_COLOR = 0xd6d6d6;

	private static final int KEY_CORNER_RADIUS = 10;

	// Prevents initialization.
	private StylesheetPopulator() {
	}

	/**
	 * Populates the stylesheet.
	 */
	public static void initialize(Desktop desktop) {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		// Sets the default style.
		EditableStyle defaultStyle = stylesheet.getDefaultStyle();
		defaultStyle.setColor(FOREGROUND);
		defaultStyle.setBackground(new RectangularBackground(BACKGROUND));
		StrictFontLoader fontLoader = new StrictFontLoader();
		Font defaultFontProfile = fontLoader.getFont(FontFamilies.SOURCE_SANS_PRO, 32,
				Font.STYLE_PLAIN);

		defaultStyle.setFont(defaultFontProfile);
		defaultStyle.setHorizontalAlignment(Alignment.LEFT);
		defaultStyle.setVerticalAlignment(Alignment.VCENTER);

		TypeSelector labelTypeSelector = new TypeSelector(Label.class);

		// Sets the label style.
		EditableStyle style = stylesheet.getSelectorStyle(labelTypeSelector);
		style.setBackground(NoBackground.NO_BACKGROUND);

		style = stylesheet.getSelectorStyle(new TypeSelector(Connected.class));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setMargin(new FlexibleOutline(0, 0, 0, 27));
		style = stylesheet.getSelectorStyle(new TypeSelector(Light.class));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setMargin(new FlexibleOutline(0, 0, 0, 27));
		style = stylesheet.getSelectorStyle(new TypeSelector(TemperatureWidget.class));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setMargin(new FlexibleOutline(0, 0, 0, 27));
		style = stylesheet.getSelectorStyle(new TypeSelector(TextReceiver.class));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setMargin(new FlexibleOutline(0, 0, 0, 27));

		// Sets the inactive send text button style
		style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setBackground(new RectangularBackground(KEYBOARD_BACKGROUND_COLOR));
		style.setBorder(new RectangularBorder(FOREGROUND, 2));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);
		style.setMargin(new FlexibleOutline(6, 25, 6, 25));


		// Sets the active send text button style

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new TypeSelector(Button.class), new StateSelector(Button.ACTIVE)));
		style.setBackground(new RectangularBackground(SEND_TEXT_BUTTON_ACTIVE_COLOR));


		// Sets the image style.
		style = stylesheet.getSelectorStyle(new TypeSelector(ImageWidget.class));
		// Align with back button size.
		style.setPadding(new FlexibleOutline(0, 0, 0, 5));

		// Sets the image style.
		style = stylesheet.getSelectorStyle(new TypeSelector(Toggle.class));
		// Align with back button size.
		style.setPadding(new FlexibleOutline(0, 0, 0, 5));

		initializeKeyboardStyle(stylesheet);

		desktop.setStylesheet(stylesheet);
	}

	private static void initializeKeyboardStyle(CascadingStylesheet stylesheet) {

		// Keyboard
		TypeSelector keyboardSelector = new TypeSelector(Keyboard.class);
		EditableStyle keyboardStyle = stylesheet.getSelectorStyle(keyboardSelector);
		keyboardStyle.setBackground(new RectangularBackground(KEYBOARD_BACKGROUND_COLOR));

		Selector labelSelector = new TypeSelector(Label.class);
		EditableStyle labelStyle = stylesheet.getSelectorStyle(labelSelector);
		labelStyle.setBackground(NoBackground.NO_BACKGROUND);

		Selector keySelector = new TypeSelector(Key.class);
		EditableStyle keyStyle = stylesheet.getSelectorStyle(keySelector);
		keyStyle.setColor(KEYBOARD_KEY_COLOR);
		keyStyle.setBackground(NoBackground.NO_BACKGROUND);
		keyStyle.setHorizontalAlignment(Alignment.HCENTER);
		keyStyle.setVerticalAlignment(Alignment.VCENTER);
		keyStyle.setMargin(new FlexibleOutline(3, 2, 3, 2));

		Selector keyBackground = new ClassSelector(Keyboard.KEY_BACKGROUND);
		EditableStyle keyBackgroundStyle = stylesheet.getSelectorStyle(keyBackground);
		keyBackgroundStyle.setBackground(new RectangularBackground(KEYBOARD_BACKGROUND_COLOR));

		StateSelector activeSelector = new StateSelector(Key.ACTIVE);
		AndCombinator activeKeySelector = new AndCombinator(keySelector, activeSelector);
		EditableStyle activeKeyStyle = stylesheet.getSelectorStyle(activeKeySelector);
		activeKeyStyle.setColor(Colors.BLACK);
		activeKeyStyle.setBackground(new RoundedBackground(KEYBOARD_HIGHLIGHT_COLOR, KEY_CORNER_RADIUS));
		activeKeyStyle.setBorder(new RoundedBorder(KEYBOARD_HIGHLIGHT_COLOR, KEY_CORNER_RADIUS, 2));

		ClassSelector spaceKeySelector = new ClassSelector(Keyboard.SPACE_KEY_SELECTOR);
		EditableStyle spaceKeyStyle = stylesheet.getSelectorStyle(spaceKeySelector);
		spaceKeyStyle.setBackground(new RoundedBackground(KEYBOARD_KEY_COLOR, KEY_CORNER_RADIUS));
		spaceKeyStyle.setBorder(new RoundedBorder(KEYBOARD_KEY_COLOR, KEY_CORNER_RADIUS, 2));

		ClassSelector specialKeySelector = new ClassSelector(Keyboard.SPECIAL_KEY_SELECTOR);
		EditableStyle specialKeyStyle = stylesheet.getSelectorStyle(specialKeySelector);
		specialKeyStyle.setColor(Colors.WHITE);
		specialKeyStyle.setBackground(new RoundedBackground(MicroEJColors.CORAL, KEY_CORNER_RADIUS));
		specialKeyStyle.setBorder(new RoundedBorder(MicroEJColors.CORAL, KEY_CORNER_RADIUS, 2));


		specialKeySelector = new ClassSelector(Keyboard.INITIAL_SPECIAL_KEY_SELECTOR);
		specialKeyStyle = stylesheet.getSelectorStyle(specialKeySelector);
		specialKeyStyle.setColor(Colors.WHITE);


		// Text fields
		TypeSelector textSelector = new TypeSelector(TextField.class);
		EditableStyle textStyle = stylesheet.getSelectorStyle(textSelector);

		textStyle.setBackground(new RectangularBackground(TEXT_PLACEHOLDER_COLOR));
		textStyle.setHorizontalAlignment(Alignment.LEFT);
		textStyle.setVerticalAlignment(Alignment.VCENTER);
		textStyle.setMargin(new FlexibleOutline(4, 5, 5, 5));
		textStyle.setPadding(new FlexibleOutline(0, 1, 1, 1));

		textStyle.setExtraInt(TextField.SELECTION_COLOR, TEXT_SELECTION_COLOR);

		activeSelector = new StateSelector(TextField.ACTIVE);
		AndCombinator focusedTextSelector = new AndCombinator(textSelector, activeSelector);
		EditableStyle focusedTextStyle = stylesheet.getSelectorStyle(focusedTextSelector);
		focusedTextStyle.setBackground(new RectangularBackground(TEXT_PLACEHOLDER_COLOR));

		StateSelector emptySelector = new StateSelector(TextField.EMPTY);
		AndCombinator placeholderTextSelector = new AndCombinator(textSelector, emptySelector);
		EditableStyle placeholderTextStyle = stylesheet.getSelectorStyle(placeholderTextSelector);
		placeholderTextStyle.setBackground(NoBackground.NO_BACKGROUND);
		placeholderTextStyle.setColor(TEXT_PLACEHOLDER_COLOR);
		placeholderTextStyle.setBorder(new FlexibleRectangularBorder(TEXT_PLACEHOLDER_COLOR, 1, 1, 1, 1));

		ClassSelector resultLabelSelector = new ClassSelector(RESULT_LABEL);
		EditableStyle resultLabelStyle = stylesheet.getSelectorStyle(resultLabelSelector);
		resultLabelStyle.setHorizontalAlignment(Alignment.LEFT);
		resultLabelStyle.setVerticalAlignment(Alignment.VCENTER);
		resultLabelStyle.setColor(Colors.WHITE);
		resultLabelStyle.setBackground(NoBackground.NO_BACKGROUND);
		resultLabelStyle.setMargin(new FlexibleOutline(4, 5, 4, 5));

		ClassSelector formSelector = new ClassSelector(FORM);
		EditableStyle formStyle = stylesheet.getSelectorStyle(formSelector);
		formStyle.setMargin(new FlexibleOutline(5, 10, 5, 10));
	}

}
