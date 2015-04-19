/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.login.constants;

import java.awt.Point;

public interface LoginLocations {

	/**
	 * Gap between loading bar and text, and text and cancel button
	 */
	int GAP_BIG = 30,
			/**
			 * Gap between loading bar left edge and its first bubble
			 */
			GAP_BUBBLE1_X = 5,
			/**
			 * Vertical shift for bubble relative to loading bar top edge
			 */
			GAP_BUBBLE1_Y = (int) Math
					.round((LoginSizes.LOADING_BAR.height - LoginSizes.BUBBLE.height) / 2.0);

	Point
	/**
	 * Text label
	 */
	LOGIN_TEXT = new Point(100, 350),
			/**
			 * Loading bar background image
			 */
			LOADING_BAR = new Point(
			// X-coordinate
					LOGIN_TEXT.x
							+ (int) Math
									.round((LoginSizes.LOGIN_TEXT.width - LoginSizes.LOADING_BAR.width) / 2.0),
					// Y-coordinate
					LOGIN_TEXT.y - (LoginSizes.LOADING_BAR.height + GAP_BIG)),
			/**
			 * Cancel button
			 */
			CANCEL = new Point(
					LOGIN_TEXT.x
							+ (int) Math
									.round((LoginSizes.LOGIN_TEXT.width - LoginSizes.CANCEL.width) / 2.0),
					LOGIN_TEXT.y + LoginFonts.LOGIN_TEXT.getSize() + GAP_BIG),

			/**
			 * Text in cancel button relative to button
			 */
			CANCEL_TEXT = new Point(
			// X
					0,
					// Y
					(int) Math
							.round((LoginSizes.CANCEL.height - LoginFonts.CANCEL_TEXT
									.getSize()) / 2.0)),
			/**
			 * Leftmost bubble in loading bar
			 */
			BUBBLE1 = new Point(LOADING_BAR.x + GAP_BUBBLE1_X, LOADING_BAR.y
					+ GAP_BUBBLE1_Y);

}
