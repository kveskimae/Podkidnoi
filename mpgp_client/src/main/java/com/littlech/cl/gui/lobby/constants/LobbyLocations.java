/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.constants;

import java.awt.Point;

import com.littlech.cl.constants.Locations;
import com.littlech.cl.constants.Sizes;

public interface LobbyLocations {

	/**
	 * Gaps
	 */
	int 
	/**
	 * Lobby
	 */
	// Horizontal shift between label and combo box
	LOBBY_PARAMETER_INPUT_GAP = 10;
	
	Point
			
			/**
			 * ==============================
			 * 
			 * ========= LOBBY ==========
			 * 
			 * ==============================
			 */

			/**
			 * Tabs
			 */
			// Tabbed pane
			LOBBY_TABS = new Point(20, 20),

			/**
			 * Create tab; locations are relative to the create tab
			 */
			// Games combo
			CREATE_COMBO_GAMES = new Point(100 + LobbySizes.CREATE_POD_PLAYERS_LABEL.width + LOBBY_PARAMETER_INPUT_GAP, 100),
			// Table name field
			CREATE_TABLE_NAME = new Point(330 + 120, 100),
			// Create label
			CREATE_LABEL = new Point(320, 40),
			// Base panel
			CREATE_CONF = new Point(10, 140),
			// Create button
			CREATE_BUTTON = new Point(330, 440),
			
			/**
			 * Podkidnoy configuration panel
			 */
			/* Number of players */
			// Label
			CREATE_POD_PLAYERS_LABEL = new Point(280, 30),
			// Combo
			CREATE_POD_PLAYERS_COMBO = new Point(CREATE_POD_PLAYERS_LABEL.x + LobbySizes.CREATE_POD_PLAYERS_LABEL.width + LOBBY_PARAMETER_INPUT_GAP,
					CREATE_POD_PLAYERS_LABEL.y),

			/* Cards on turn */
			// Label
			CREATE_POD_ON_TURN_LABEL = new Point(120, 120),
			// Combo
			CREATE_POD_ON_TURN_COMBO = new Point(CREATE_POD_ON_TURN_LABEL.x + LobbySizes.CREATE_POD_ON_TURN_LABEL.width + LOBBY_PARAMETER_INPUT_GAP,
					CREATE_POD_ON_TURN_LABEL.y - 10),

			/* Sending */
			CREATE_POD_SENDING = new Point(450, CREATE_POD_ON_TURN_LABEL.y + 5),

			/* Card pack */
			// Label
			CREATE_POD_PACK_LABEL = new Point(CREATE_POD_PLAYERS_LABEL.x, 210),
			// Combo
			CREATE_POD_PACK_COMBO = new Point(CREATE_POD_PACK_LABEL.x + LobbySizes.CREATE_POD_PACK_LABEL.width + LOBBY_PARAMETER_INPUT_GAP,
					CREATE_POD_PACK_LABEL.y - 10),
					
			/**
			 * Podkidnoy tab
			 */
			/**
			 * Join button
			 */
			// Text
			LOBBY_JOIN_TEXT = new Point(7, 4),
			// Button
			LOBBY_JOIN = new Point(626, 12),
			/**
			 * Table
			 */
			// Scroll pane
			LOBBY_TABLE_SCROLLPANE = new Point(12, 20);

	Point COPYRIGHT = new Point(Locations.UPPER_LEFT.x + Sizes.APPLET.width - LobbySizes.COPYRIGHT.width - 20,
			Locations.UPPER_LEFT.y + Sizes.APPLET.height - LobbySizes.COPYRIGHT.height - 20		
	);

}
