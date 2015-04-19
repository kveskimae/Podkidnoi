/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.util;

/**
 * 
 * Lobby create table state
 * 
 * @author Kristjan Veskim�e
 *
 */
public enum CreateState {
	
	/**
	 * Tables can be created by the user
	 */
	IDLE,
	
	/**
	 * User has created a new table, waiting server response 
	 */
	WAITING_RESPONSE

}
