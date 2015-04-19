/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.login.constants;

import java.awt.Color;

public interface LoginColors {
	
	Color
	
	/**
	 *  Info messages
	 */
	INFO = Color.white,
	
	/**
	 *  Fatal errors
	 */
	ERROR = new Color(
			// 245, 150, 150, 
			246, 135, 65,
			255),
	
	/**
	 * Cancel button foreground
	 */
	CANCEL_FG = Color.white,
	
	/**
	 * Loading bar bubble, opaque
	 */
	BUBBLE_STRONGEST = new Color(255, 200, 0, 255),
	
	/**
	 * Loading bar bubble, strongly visible
	 */
	BUBBLE_STRONG = new Color(255, 200, 0, 160),
	
	/**
	 * Loading bar bubble, weakly visible
	 */
	BUBBLE_WEAK = new Color(255, 200, 0, 80),
	
	/**
	 * Loading bar bubble, transparent
	 */
	BUBBLE_WEAKEST = new Color(255, 200, 0, 0);

}
