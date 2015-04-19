/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui;

/**
 * 
 * Screen controller type
 * 
 * @author Kristjan Veskim�e
 * 
 */
public enum ScreenControllerType {

	/**
	 * Login screen; presented while client application connects to the game
	 * server and loads images
	 */
	LOGIN,

	/**
	 * Lobby screen; presented after login and before game screen; player can join
	 * tables or create new tables from lobby screen
	 */
	LOBBY,

	/**
	 * Podkidnoy game screen
	 */
	PODKIDNOY

}
