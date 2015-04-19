/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.podkidnoy.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.littlech.cl.gui.lobby.LobbyController;

/**
 * 
 * Handles Podkidnoy lobby join button
 * 
 * @author Kristjan Veskim�e
 *
 */
public class JoinButtonHandler implements ActionListener {
	
	/**
	 * Lobby controller
	 */
	private LobbyController mController;

	/**
	 * Constructor
	 * @param _controller Lobby controller
	 */
	public JoinButtonHandler(final LobbyController _controller) {
		mController = _controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			mController.pressedJoin();
	}
	
}
