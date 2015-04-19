/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.constants;

import java.awt.Point;

import com.littlech.cl.constants.Sizes;

public interface PodkidnoyLocations {

	Point
			/**
			 * ==============================
			 * 
			 * ========= PODKIDNOY ==========
			 * 
			 * ==============================
			 */
			/**
			 * Action buttons panel
			 */
			// Text in action button relative to button
			ACTION_TEXT = new Point(12, 12),
			// Location of action buttons panel's upper left corner on applet
			ACTIONS_PANEL = new Point(455, 450),
			// Location of your turn label relative to action buttons panel
			YOUR_TURN = new Point(20, 40),

			/**
			 * Player cards panel
			 */
			PLAYER_CARDS = new Point(140, 470),

			/**
			 * Pack and trump panel
			 */
			PACK_AND_TRUMP = new Point(340, 10),

			/**
			 * Table cards
			 */
			TABLE_CARDS = new Point(140, 340),

			/**
			 * Player boxes
			 */
			// Player's cards number square
			PLAYERBOX_CARDS_NUMBER = new Point(40, 80),
			// Player's cards number text (relative to square)
			PLAYERBOX_CARDS_NUMBER_TEXT = new Point(PLAYERBOX_CARDS_NUMBER.x + 5, PLAYERBOX_CARDS_NUMBER.y + 3),
			// Player's name
			PLAYERBOX_NAME = new Point(5, 60),
			// XXX
			PLAYERBOX_SIT = new Point((int)Math.round((PodkidnoySizes.PLAYERBOX_NAME.width  - PodkidnoySizes.SIT.width)/2.0), 0),
			// Leftmost player; self if the player has seated in game
			PLAYERBOX_SELF = new Point(10, 450),
			// Other three players from left to right
			PLAYERBOX_OTHER1 = new Point(70, 190), PLAYERBOX_OTHER2 = new Point(350, 150), PLAYERBOX_OTHER3 = new Point(640, 190),

			/**
			 * Podkidnoy start and leave buttons
			 */
			// Base panel
			STARTANDLEAVE = new Point(Sizes.APPLET.width - PodkidnoySizes.STARTANDLEAVE.width, 0),
			// Leave button absolute
			STARTANDLEAVE_LEAVE_ABS = new Point(630, 20),
			// Leave button relative to start and leave panel
			STARTANDLEAVE_LEAVE = new Point(STARTANDLEAVE_LEAVE_ABS.x - STARTANDLEAVE.x, STARTANDLEAVE_LEAVE_ABS.y - STARTANDLEAVE.y),
			// Start button absolute
			STARTANDLEAVE_START_ABS = new Point(670, 520),
			// Start button relative to start and leave panel
			STARTANDLEAVE_START = new Point(STARTANDLEAVE_START_ABS.x - STARTANDLEAVE.x, STARTANDLEAVE_START_ABS.y - STARTANDLEAVE.y),

			/**
			 * Podkidnoy info text
			 */
			// Text area
			PODKIDNOY_INFO = new Point(20, 10),

	/**
	 * Control buttons
	 */
	// Background image
	CONTROL_BG = new Point(Sizes.APPLET.width - PodkidnoySizes.CONTROL_BG.width, Sizes.APPLET.height - PodkidnoySizes.CONTROL_BG.height),
	// Leftmost control button
	// CONTROL_1 = new Point(CONTROL_BG.x + PodkidnoySizes.CONTROL_BUTTONS_SHIFT, CONTROL_BG.y + PodkidnoySizes.CONTROL_GAP_V),
	// Control button that is second from left
	// CONTROL_2 = new Point(CONTROL_BG.x + 2 * PodkidnoySizes.CONTROL_BUTTONS_SHIFT + PodkidnoySizes.CONTROL_BUTTON.width, CONTROL_BG.y + PodkidnoySizes.CONTROL_GAP_V),
	// Text in control button relative to button
	CONTROL_TEXT = new Point(0, 0)
	;
	Point COPYRIGHT = new Point(300, 300);

}
