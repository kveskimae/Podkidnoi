/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.connect;

import com.littlech.gen.d.D4;

/**
 * 
 * Decorator for sending commands from client to server
 * 
 * @author Kristjan Veskim�e
 *
 */
public interface ICSHandler {

	/**
	 * 
	 * Sends parameter command to game server
	 * 
	 * @param cmd Command from client to server 
	 */
	void sendCS(D4 cs);

}
