/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui;

import java.awt.Container;

import com.littlech.gen.e.E19;

/**
 * 
 * Screen controller
 * 
 * @author Kristjan Veskim�e
 * 
 */
public interface IScreenController {
	
	void finishLayout();

	/**
	 * Processes command received from server
	 * 
	 * @param cmd
	 *          Command from server to client
	 * @return True if screen processed parameter command, false otherwise
	 */
	boolean handleSC(E19 cmd);

	/**
	 * Returns type of screen
	 * 
	 * @return Screen type
	 */
	ScreenControllerType getType();

	/**
	 * Called before this screen is set as the currently shown screen
	 */
	void startShow();

	/**
	 * Called after this screen is replaced from the currently shown screen
	 */
	void stopShow();

	/**
	 * Returns base panel for this screen where all its components are added
	 * 
	 * @return Container for screen
	 */
	Container getPanel();

	void reset();
	
	void enable();

}
