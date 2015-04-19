/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.littlech.cl.gui.lobby.LobbyController;

public class CreateButtonHandler implements ActionListener {
	
	private final LobbyController mLobbyController;

	public CreateButtonHandler(final LobbyController _lobbyController) {
		mLobbyController = _lobbyController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mLobbyController.sendCreateTableCommand();
	}

}
