/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create;

import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.explodingpixels.macwidgets.HudWidgetFactory;
import com.littlech.cl.constants.*;
import com.littlech.gen.g.G13;
import com.littlech.gen.g.G17;
import com.littlech.gen.g.G21;
import com.littlech.gen.g.G9;
import com.littlech.cl.gui.lobby.constants.*;

public class PodkidnoyConfiguration implements IConfigurationPanel {

	/**
	 * Base panel
	 */
	private final JPanel panel;
	/**
	 * Models for combo boxes
	 */
	private DefaultComboBoxModel
	/* Number of players */
	playersModel = new javax.swing.DefaultComboBoxModel(new Integer[] { 2, 3, 4 }),
	/* Card pack */
	cardPackModel = new javax.swing.DefaultComboBoxModel(new Integer[] { 36, 52 }),
	/* Turn cards */
	onTurnModel = new javax.swing.DefaultComboBoxModel(new Integer[] { 1, 6 });

	/**
	 * Combos
	 */
	private JComboBox playersCombo, cardPackCombo, cardsOnTurnList;

	/**
	 * Check boxes
	 */
	private JCheckBox sendingBox;

	/**
	 * Constraints for combo boxes, labels and check box
	 */
	private final AbsoluteConstraints
	/* Number of players */
	// Combo
			constraintPlayersCombo = new AbsoluteConstraints(LobbyLocations.CREATE_POD_PLAYERS_COMBO, LobbySizes.CREATE_POD_COMBO_PLAYERS),
			// Label
			constraintPlayersLabel = new AbsoluteConstraints(LobbyLocations.CREATE_POD_PLAYERS_LABEL, LobbySizes.CREATE_POD_PLAYERS_LABEL),

			/* Turn cards */
			// Combo
			constraintOnTurnCombo = new AbsoluteConstraints(LobbyLocations.CREATE_POD_ON_TURN_COMBO, LobbySizes.CREATE_POD_ON_TURN_COMBO),
			// Label
			constraintOnTurnLabel = new AbsoluteConstraints(LobbyLocations.CREATE_POD_ON_TURN_LABEL, LobbySizes.CREATE_POD_ON_TURN_LABEL),

			/* Card pack */
			// Combo
			constraintPackCombo = new AbsoluteConstraints(LobbyLocations.CREATE_POD_PACK_COMBO, LobbySizes.CREATE_POD_PACK_COMBO),
			// Label
			constraintPackLabel = new AbsoluteConstraints(LobbyLocations.CREATE_POD_PACK_LABEL, LobbySizes.CREATE_POD_PACK_LABEL),

			/* Sending box */
			constraintSending = new AbsoluteConstraints(LobbyLocations.CREATE_POD_SENDING, LobbySizes.CREATE_POD_SENDING);

	public PodkidnoyConfiguration() {
		panel = new JPanel();
		panel.setLayout(new AbsoluteLayout());
		panel.setBackground(Color.blue);
		panel.setOpaque(false);

		/* Number of players */
		// Combo
		playersCombo = HudWidgetFactory.createHudComboBox(playersModel);
		playersCombo.setName(Names.CONF_POD_PLAYERS);
		panel.add(playersCombo, constraintPlayersCombo);
		// Label
		JLabel playersLabel = HudWidgetFactory.createHudLabel("Players");
		panel.add(playersLabel, constraintPlayersLabel);

		/* Cards on turn */
		// Combo
		cardsOnTurnList = HudWidgetFactory.createHudComboBox(onTurnModel);
		playersCombo.setName(Names.CONF_POD_ON_TURN);
		panel.add(cardsOnTurnList, constraintOnTurnCombo);
		// Label
		JLabel cardsOnTurn = HudWidgetFactory.createHudLabel("<html>Number of<br> cards per turn");
		panel.add(cardsOnTurn, constraintOnTurnLabel);

		/* Card pack */
		// Combo
		cardPackCombo = HudWidgetFactory.createHudComboBox(cardPackModel);
		playersCombo.setName(Names.CONF_POD_PACK);
		panel.add(cardPackCombo, constraintPackCombo);
		// Label
		JLabel cardPack = HudWidgetFactory.createHudLabel("Card pack");
		panel.add(cardPack, constraintPackLabel);

		/* Sending */
		sendingBox = HudWidgetFactory.createHudCheckBox("Sending");
		playersCombo.setName(Names.CONF_POD_SENDING);
		panel.add(sendingBox, constraintSending);
	}
	
	public JComboBox getPlayersCombo() {
		return playersCombo;
	}

	public G17 getGameConf() {
		G17 gameConf = new G17();
		gameConf.setG18(G13.G_14);
		G21 ac = new G21();
		ac.setG25(getPackType());
		ac.setG22(getPlayers());
		ac.setG23(isSending());
		ac.setG24(getTurnCards());
		gameConf.setG19(ac);
		return gameConf;
	}

	private int getTurnCards() {
		return ((Integer) cardsOnTurnList.getSelectedItem()).intValue();
	}

	private boolean isSending() {
		return sendingBox.isSelected();
	}

	private int getPlayers() {
		return ((Integer) playersCombo.getSelectedItem()).intValue();
	}

	private G9 getPackType() {
		Integer selected = (Integer) cardPackCombo.getSelectedItem();
		switch (selected) {
		case 20:
			return G9.G_10;
		case 36:
			return G9.G_11;
		case 52:
			return G9.G_12;
		default:
			throw new IllegalStateException("Cannot map: " + selected);
		}
	}

	/*
	 * public AbsoluteConstraints getConstraint() { return constraint; }
	 */

	public JPanel getPanel() {
		return panel;
	}

	public G13 getGameType() {
		return G13.G_14;
	}

	@Override
	public void reset() {
		playersCombo.setSelectedIndex(0);
		cardPackCombo.setSelectedIndex(0);
		cardsOnTurnList.setSelectedIndex(0);
		sendingBox.setSelected(false);
	}

	@Override
	public void setEnabled(boolean b) {
		playersCombo.setEnabled(b);
		cardPackCombo.setEnabled(b);
		cardsOnTurnList.setEnabled(b);
		sendingBox.setEnabled(b);
	}

}
