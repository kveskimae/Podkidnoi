/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.netbeans.lib.awtextra.AbsoluteConstraints;

import com.littlech.cl.constants.Names;
import com.littlech.cl.gui.lobby.constants.*;
import com.littlech.gen.g.G13;

public class Games {

	public static final String GAME_EMPTY = " ";

	public static final String GAME_POD = "Podkidnoy";

	public static final String GAME_BRIDGE = "Bridge";

	private final DefaultComboBoxModel model;

	private final AbsoluteConstraints constraint = new AbsoluteConstraints(LobbyLocations.CREATE_COMBO_GAMES,
			LobbySizes.CREATE_COMBO_GAMES);

	private final JComboBox combo;

	public Games() {
		model = new javax.swing.DefaultComboBoxModel();
		model.addElement(GAME_EMPTY);
		model.addElement(GAME_POD);
		// model.addElement(GAME_BRIDGE);
		combo = new JComboBox(model); // HudWidgetFactory.createHudComboBox(model);
		combo.setName(Names.CREATE_GAMES);
	}

	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

	public JComboBox getCombo() {
		return combo;
	}

	public void removeSelectedGame() {
		model.removeElement(model.getSelectedItem());
		combo.setSelectedItem(GAME_EMPTY);
	}

	public void removeGame(final G13 _type) {
		if (_type == null) {
			throw new NullPointerException(); // "Game type is null");
		}
		if (G13.G_14.equals(_type)) {
			model.removeElement(GAME_POD);
		}
	}

	/**
	 * Returns game type code for selected item or null if no game has been
	 * selected
	 * 
	 * @return Game type
	 */
	public G13 getSelectedGame() {
		String selected = (String) combo.getSelectedItem();
		if (GAME_POD.equals(selected)) {
			return G13.G_14;
		} else if (GAME_BRIDGE.equals(selected)) {
			return G13.G_15;
		}
		return null;
	}

	public void setSelectedGame(final G13 _type) {
		if (_type == null) {
			combo.setSelectedItem(GAME_EMPTY);
			return;
		}
		switch (_type) {
		case G_15:
			combo.setSelectedItem(GAME_BRIDGE);
			break;
		case G_14:
			combo.setSelectedItem(GAME_POD);
			break;

		default:
			throw new IllegalArgumentException("Unsupported: " + _type);
		}
	}

	public int getSize() {
		int ret = model.getSize() - 1;
		// System.out.println("games=" + ret);
		return ret;
	}

	public void setEnabled(boolean b) {
		combo.setEnabled(b);
	}

}
