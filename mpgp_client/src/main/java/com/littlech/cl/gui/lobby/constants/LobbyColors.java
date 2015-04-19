/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.constants;

import java.awt.Color;

public interface LobbyColors {

	Color

	/**
	 * ==============================
	 * 
	 * ========= LOBBY ==============
	 * 
	 * ==============================
	 */
	// Lobby screen bg
			LOBBY_BG = new java.awt.Color(76, 74, 74),
			// Lobby blue (e.g. unselected tabs bg)
			LOBBY_BLUE = new Color(0x00a5cb),
			/**
			 * Join button
			 */
			// Join button fg
			LOBBY_JOIN_FG = new java.awt.Color(255, 255, 255),
			/**
			 * Tabs pane
			 */
			// Lobby tabs pane content background
			LOBBY_TABS_GAME_CONTENT_BG = Color.white,
			// Selected tab fg
			LOBBY_TABS_SELECTED_FG = Color.black,
			// Unselected tab fg
			LOBBY_TABS_UNSELECTED_FG = Color.white,
			/**
			 * Table
			 */
			// Header fg
			LOBBY_HEADER = Color.white,
			// Cell fg
			LOBBY_TABLE_CELL_TEXT = Color.black,
			// White table row
			LOBBY_TABLE_ROW_WHITE = Color.white,
			// Yellow table row
			LOBBY_TABLE_ROW_YELLOW = new java.awt.Color(253, 243, 195),
			// Players column fg in empty or waiting state
			LOBBY_TABLE_PLAYERS_FG = Color.white,
			// Players column bg in waiting state
			LOBBY_TABLE_PLAYERS_BG_WAITING = new Color(0x52a707),
			// Players column bg in empty state
			LOBBY_TABLE_PLAYERS_BG_EMPTY = new Color(0xa0a0a0),
			// Selected row bg
			LOBBY_TABLE_SELECTED_BG = new Color(0xec4d0e),
			// Selected row fg
			LOBBY_TABLE_SELECTED_FG = Color.white,
			// Create tab content bg
			LOBBY_TABS_CREATE_CONTENT_BG = new Color(0x404040),
			// Create tab content fg
			LOBBY_TABS_CREATE_CONTENT_FG = Color.white;
}
