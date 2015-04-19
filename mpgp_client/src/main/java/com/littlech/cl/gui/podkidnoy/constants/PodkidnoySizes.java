/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.constants;

import java.awt.Dimension;

import com.littlech.cl.constants.Sizes;
import com.littlech.cl.gui.podkidnoy.component.ButtonsPanel;
import com.littlech.cl.gui.podkidnoy.component.CardsPanel;
import com.littlech.cl.gui.podkidnoy.component.TablePanel;

public interface PodkidnoySizes {

	int
	/**
	 * ==============================
	 * 
	 * ========= PODKIDNOY ==========
	 * 
	 * ==============================
	 */
	// Gap between action buttons
			ACTION_BUTTONS_SHIFT = 120,
			// Player cards panel and arrow
			PLAYER_CARDS_ARROW_GAP = 10,
			// Player cards
			PLAYER_CARDS_SHIFT = 25,
			// Vertical shift for arrows? XXX
			PLAYER_CARDS_ARROW_GAP_V = 40,
			// Shift for consecutive table card pairs
			TABLE_CARDS_SHIFT = 90,
			// Horizontal and vertical shift for defending card (relative to bottom)
			TABLE_CARDS_TOP_SHIFT_H = 15, TABLE_CARDS_TOP_SHIFT_V = 10,
			// Gap between control buttons
			CONTROL_BUTTONS_SHIFT = 20,
			// Vertical shift for control buttons
			CONTROL_GAP_V = 10
			;

	Dimension

			/**
			 * ==============================
			 * 
			 * ========= PODKIDNOY ==========
			 * 
			 * ==============================
			 */
			/**
			 * Action buttons
			 */
			// Button
			ACTION = new Dimension(105, 44),
			// Button text label
			ACTION_TEXT = new Dimension(80, 20),
			// Your turn label
			YOUR_TURN = new Dimension(310, 52),
			// Buttons panel
			ACTIONS = new Dimension(PodkidnoySizes.ACTION.width * ButtonsPanel.BUTTONS + (ButtonsPanel.BUTTONS - 1) * ACTION_BUTTONS_SHIFT, PodkidnoySizes.ACTION.height
					+ YOUR_TURN.height),

			/**
			 * Player cards panel
			 */
			// Card
			CARD = new Dimension(60, 85),
			// Arrow
			ARROW = new Dimension(15, 15),
			// Panel
			PLAYERCARDS = new Dimension(2 * (PodkidnoySizes.ARROW.width + PLAYER_CARDS_ARROW_GAP) + (CardsPanel.PLAYER_CARDS - 1) * PLAYER_CARDS_SHIFT + CARD.width,
					CARD.height),

			/**
			 * Pack and trump panel
			 */
			PACK_AND_TRUMP = new Dimension(125, 100),

			/**
			 * Table cards
			 */
			TABLE_CARDS = new Dimension((TablePanel.TABLE_CARDS - 1) * TABLE_CARDS_SHIFT + PodkidnoySizes.CARD.width + 15, CARD.height + 10),

			/**
			 * Player box
			 */
			// Player box
			PLAYERBOX = new Dimension(115, 115),
			// Player's cards number
			PLAYERBOX_CARDS_NUMBER = new Dimension(25, 20),
			// Name label size
			PLAYERBOX_NAME = new Dimension(105, 20),

			/**
			 * Podkidnoy start and leave buttons
			 */
			// Base panel XXX is height really applet width?
			STARTANDLEAVE = new Dimension(300, Sizes.APPLET.width),
			// Leave button
			STARTANDLEAVE_LEAVE = new Dimension(123, 48),
			// Start button
			STARTANDLEAVE_START = new Dimension(65, 65),

			/**
			 * Podkidnoy info text
			 */
			// Text area
			PODKIDNOY_INFO = new Dimension(210, 150),

	/**
	 * Control buttons
	 */
	// Background image
	CONTROL_BG = new Dimension(180, 40),
	// Button
	 CONTROL_BUTTON = new Dimension(60, 20),
	 // Text
	 CONTROL_TEXT = CONTROL_BUTTON;

	Dimension COPYRIGHT = new Dimension(150, 12),
	SIT = new Dimension(70, 58);

}
