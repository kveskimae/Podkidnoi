/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.constants;

import java.awt.Color;

public interface PodkidnoyColors {

	Color

			/**
			 * ==============================
			 * 
			 * ========= PODKIDNOY ==========
			 * 
			 * ==============================
			 */
			/**
			 * Action button
			 */
			// Text label foreground
			ACTION_FG = Color.white,

			/**
			 * Player box
			 */
			// Player's cards number foreground
			PLAYERBOX_CARDS_NUMBER = new java.awt.Color(255, 51, 0),

			/**
			 * Game udpate messages text area
			 */
			// Background color
			PODKIDNOY_MESSAGES_BG = new Color(0xeedb00),
			// Foreground for info type messages
			PODKIDNOY_MESSAGES_INFO = Color.white,
			// Foreground for general type messages
			PODKIDNOY_MESSAGES_GENERAL = new Color(0x89dcf1);
	Color PLAYERBOX_NAME_BG = Color.black;
	Color PLAYERBOX_NAME_FG = Color.white;
	Color CONTROL_FG = Color.white;
	Color CONTROL_FG_DISABLED = new Color(0x7a7a7a);
}
