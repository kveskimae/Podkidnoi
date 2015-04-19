/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.littlech.cl.gui.lobby.create.CreateTab;
import com.littlech.gen.g.G13;

public class GamesComboHandler implements ActionListener {

	private CreateTab mCreate;

	public GamesComboHandler(final CreateTab _create) {
		mCreate = _create;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println("-2-");
		G13 selected = mCreate.getGames().getSelectedGame();
		mCreate.setSelectedConf(selected);
		boolean createButtonVisible = true;
		if (selected == null) {
			createButtonVisible = false;
		}
		mCreate.getCreateButton().setVisible(createButtonVisible);
	}

}
