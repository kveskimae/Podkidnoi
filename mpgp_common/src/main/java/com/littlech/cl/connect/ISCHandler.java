/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.connect;

import com.littlech.gen.e.E19;

/**
 * 
 * Decorator for handling commands received from server
 * 
 * @author Kristjan Veskim�e
 *
 */
public interface ISCHandler {

	/**
	 * 
	 * Handles parameter command received from game server
	 * 
	 * @param cmd Command from server 
	 */
	void handleSC(E19 sc);

}
