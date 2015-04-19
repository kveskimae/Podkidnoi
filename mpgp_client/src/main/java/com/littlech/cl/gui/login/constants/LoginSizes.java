/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.login.constants;

import java.awt.Dimension;

public interface LoginSizes {

	Dimension
	/**
	 * Text label
	 */
	LOGIN_TEXT = new Dimension(300, LoginFonts.LOGIN_TEXT.getSize() + 5),
	/**
	 * Cancel button
	 */
	CANCEL = new Dimension(100, 34),
			/**
			 * Text on cancel button
			 */
			CANCEL_TEXT = new Dimension(CANCEL.width, LoginFonts.CANCEL_TEXT
					.getSize()),
			/**
			 * Loading bar
			 */
			LOADING_BAR = new Dimension(170, 23),
			/**
			 * Loading bar bubble
			 */
			BUBBLE = new Dimension(16, 16);

}
