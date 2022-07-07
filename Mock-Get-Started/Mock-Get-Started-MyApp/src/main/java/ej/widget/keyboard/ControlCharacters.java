/*
 * Copyright 2016-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package ej.widget.keyboard;

/**
 * Represents all the control characters (the first 32 codes + SPACE and DELETE).
 */
public interface ControlCharacters {

	/**
	 * Originally used to allow gaps to be left on paper tape for edits. Later used for padding after a code that might
	 * take a terminal some time to process (e.g. a carriage return or line feed on a printing terminal). Now often used
	 * as a string terminator.
	 */
	char NULL = 0x00;

	/**
	 * First character of message header.
	 */
	char START_OF_HEADING = 0x01;

	/**
	 * First character of message text, and may be used to terminate the message heading.
	 */
	char START_OF_TEXT = 0x02;

	/**
	 * Often used as a "break" character (Ctrl-C) to interrupt or terminate a program or process.
	 */
	char END_OF_TEXT = 0x03;

	/**
	 * Used on Unix to signal end-of-file condition on, or to log out from a terminal.
	 */
	char END_OF_TRANSMISSION = 0x04;

	/**
	 * Signal intended to trigger a response at the receiving end, to see if it is still present.
	 */
	char ENQUIRY = 0x05;

	/**
	 * Response to an Enquiry, or an indication of successful receipt of a message.
	 */
	char ACKNOWLEDGEMENT = 0x06;

	/**
	 * Originally used to sound a bell on the terminal. Later used for a beep on systems that didn't have a physical
	 * bell. May also quickly turn on and off inverse video (a visual bell).
	 */
	char BELL = 0x07;

	/**
	 * Move the cursor one position leftwards. On input, this may delete the character to the left of the cursor. On
	 * output, where in early computer technology a character once printed could not be erased, the backspace was
	 * sometimes used to generate accented characters in ASCII. For example, à could be produced using the three
	 * character sequence a BS ` (or, using the characters’ hex values, 0x61 0x08 0x60). This usage is now deprecated
	 * and generally not supported. To provide disambiguation between the two potential uses of backspace, the cancel
	 * character control code was made part of the standard C1 control set.
	 */
	char BACK_SPACE = 0x08;

	/**
	 * Position to the next character tab stop.
	 */
	char HORIZONTAL_TAB = 0x09;

	/**
	 * On typewriters, printers, and some terminal emulators, moves the cursor down one row without affecting its column
	 * position. On Unix, used to mark end-of-line. In DOS, Windows, and various network standards, LF is used following
	 * CR as part of the end-of-line mark.
	 */
	char LINE_FEED = 0x0a;

	/**
	 * Position the form at the next line tab stop.
	 */
	char VERTICAL_TAB = 0x0b;

	/**
	 * On printers, load the next page. Treated as whitespace in many programming languages, and may be used to separate
	 * logical divisions in code. In some terminal emulators, it clears the screen. It still appears in some common
	 * plain text files as a page break character, such as the RFCs published by IETF.
	 */
	char FORM_FEED = 0x0c;

	/**
	 * Originally used to move the cursor to column zero while staying on the same line. On Mac OS (pre-OS X), as well
	 * as in earlier systems such as the Apple II and Commodore 64, used to mark end-of-line. In DOS, Windows, and
	 * various network standards, it is used preceding LF as part of the end-of-line mark. The Enter or Return key on a
	 * keyboard will send this character, but it may be converted to a different end-of-line sequence by a terminal
	 * program.
	 */
	char CARRIAGE_RETURN = 0x0d;

	/**
	 * Switch to an alternative character set.
	 */
	char SHIFT_OUT = 0x0e;

	/**
	 * Return to regular character set after Shift Out.
	 */
	char SHIFT_IN = 0x0f;

	/**
	 * Cause the following octets to be interpreted as raw data, not as control codes or graphic characters. Returning
	 * to normal usage would be implementation dependent.
	 */
	char DATA_LINK_ESCAPE = 0x10;

	/**
	 * These four control codes are reserved for device control, with the interpretation dependent upon the device they
	 * were connected. DC1 and DC2 were intended primarily to indicate activating a device while DC3 and DC4 were
	 * intended primarily to indicate pausing or turning off a device. In actual practice DC1 and DC3 (known also as XON
	 * and XOFF respectively in this usage) quickly became the de facto standard for software flow control.
	 */
	char DEVICE_CONTROL1 = 0x11;

	/**
	 * @see ControlCharacters#DEVICE_CONTROL1
	 */
	char DEVICE_CONTROL2 = 0x12;

	/**
	 * @see ControlCharacters#DEVICE_CONTROL1
	 */
	char DEVICE_CONTROL3 = 0x13;

	/**
	 * @see ControlCharacters#DEVICE_CONTROL1
	 */
	char DEVICE_CONTROL4 = 0x14;

	/**
	 * Sent by a station as a negative response to the station with which the connection has been set up. In binary
	 * synchronous communication protocol, the NAK is used to indicate that an error was detected in the previously
	 * received block and that the receiver is ready to accept retransmission of that block. In multipoint systems, the
	 * NAK is used as the not-ready reply to a poll.
	 */
	char NEGATIVE_ACKNOWLEDGEMENT = 0x15;

	/**
	 * Used in synchronous transmission systems to provide a signal from which synchronous correction may be achieved
	 * between data terminal equipment, particularly when no other character is being transmitted.
	 */
	char SYNCHRONOUS_IDLE = 0x16;

	/**
	 * Indicates the end of a transmission block of data when data are divided into such blocks for transmission
	 * purposes.
	 */
	char END_OF_TRANSMISSION_BLOCK = 0x17;

	/**
	 * Indicates that the data preceding it are in error or are to be disregarded.
	 */
	char CANCEL = 0x18;

	/**
	 * Intended as means of indicating on paper or magnetic tapes that the end of the usable portion of the tape had
	 * been reached.
	 */
	char END_OF_MEDIUM = 0x19;

	/**
	 * Originally intended for use as a transmission control character to indicate that garbled or invalid characters
	 * had been received. It has often been put to use for other purposes when the in-band signaling of errors it
	 * provides is unneeded, especially where robust methods of error detection and correction are used, or where errors
	 * are expected to be rare enough to make using the character for other purposes advisable.
	 */
	char SUBSTITUTE = 0x1a;

	/**
	 * The Esc key on the keyboard will cause this character to be sent on most systems. It can be used in software user
	 * interfaces to exit from a screen, menu, or mode, or in device-control protocols (e.g., printers and terminals) to
	 * signal that what follows is a special command sequence rather than normal text. In systems based on ISO/IEC 2022,
	 * even if another set of C0 control codes are used, this octet is required to always represent the escape
	 * character.
	 */
	char ESCAPE = 0x1b;

	/**
	 * Can be used as delimiters to mark fields of data structures. If used for hierarchical levels, US is the lowest
	 * level (dividing plain-text data items), while RS, GS, and FS are of increasing level to divide groups made up of
	 * items of the level beneath it.
	 */
	char FILE_SEPARATOR = 0x1c;

	/**
	 * Same as {@link ControlCharacters#FILE_SEPARATOR}.
	 */
	char GROUP_SEPARATOR = 0x1d;

	/**
	 * Same as {@link ControlCharacters#FILE_SEPARATOR}.
	 */
	char RECORD_SEPARATOR = 0x1e;

	/**
	 * Same as {@link ControlCharacters#FILE_SEPARATOR}.
	 */
	char UNIT_SEPARATOR = 0x1f;

	/**
	 * Space is a graphic character. It has a visual representation consisting of the absence of a graphic symbol. It
	 * causes the active position to be advanced by one character position. In some applications, Space can be
	 * considered a lowest-level "word separator" to be used with the adjacent separator characters.
	 */
	char SPACE = 0x20;

	/**
	 * Not technically part of the C0 control character range, this was originally used to mark deleted characters on
	 * paper tape, since any character could be changed to all ones by punching holes everywhere. On VT100 compatible
	 * terminals, this is the character generated by the key labelled ⌫, usually called backspace on modern machines,
	 * and does not correspond to the PC delete key.
	 */
	char DELETE = 0x7f;

	/**
	 * Move left.
	 */
	char VK_LEFT = 0xe04b;

	/**
	 * Move right.
	 */
	char VK_RIGHT = 0xe04d;

	/**
	 * Move down.
	 */
	char VK_DOWN = 0xE050;

	/**
	 * Move up.
	 */
	char VK_UP = 0xE048;
}
