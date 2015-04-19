/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.constants;

import java.awt.Dimension;

public interface LobbySizes {
int
			/**
			 * ==============================
			 * 
			 * ========= LOBBY ==========
			 * 
			 * ==============================
			 */
			// Row height
			LOBBY_ROW_HEIGHT = 25;

Dimension

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
			// Tab
			LOBBY_TAB = new Dimension(760, 530),

			/**
			 * Podkidnoy tab
			 */
			// Join button
			LOBBY_JOIN = new Dimension(144, 23),
			// Join button text
			LOBBY_JOIN_TEXT = new Dimension(LOBBY_JOIN.width - 14, LOBBY_JOIN.height - 8),
			// Table scroll pane
			LOBBY_TABLE_SCROLLPANE = new Dimension(735, 470),

			/**
			 * Create tab
			 */
			// Game-specific configuration area
			LOBBY_CREATE_CONF = new Dimension(740, 260),
			// Games combo
			CREATE_COMBO_GAMES = new Dimension(100, 20),
			// Label "Create new table"
			CREATE_LABEL_CREATE = new Dimension(100, 25),
			// Create button
			CREATE_BUTTON_CREATE = new Dimension(100, 25),
			// Table name
			CREATE_TABLE_NAME = new Dimension(100, 25),

			/**
			 * Podkidnoy configuration
			 */
			// Number of players label
			CREATE_POD_PLAYERS_LABEL = new Dimension(80, 20),
			// Number of players combo
			CREATE_POD_COMBO_PLAYERS = new Dimension(40, 30),
			// Card pack label
			CREATE_POD_PACK_LABEL = CREATE_POD_PLAYERS_LABEL,
			// Card pack combo
			CREATE_POD_PACK_COMBO = new Dimension(50, 50),
			// On turn cards label
			CREATE_POD_ON_TURN_LABEL = new Dimension(100, 30),
			// On turn cards combo
			CREATE_POD_ON_TURN_COMBO = new Dimension(60, 50),
			// Sending check box
			CREATE_POD_SENDING = new Dimension(100, 25);

Dimension COPYRIGHT = new Dimension(150, 12);;

}
