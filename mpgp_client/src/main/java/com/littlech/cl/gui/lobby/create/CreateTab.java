/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create;

import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import com.explodingpixels.macwidgets.HudWidgetFactory;
import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.*;
import com.littlech.cl.gui.lobby.LobbyController;
import com.littlech.cl.gui.lobby.create.handler.GamesComboHandler;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D12;
import com.littlech.gen.d.D4;
import com.littlech.gen.g.G13;
import com.littlech.gen.g.G17;
import com.littlech.cl.gui.lobby.constants.*;

public class CreateTab {

	private final JPanel panel = new JPanel();

	private final IImageLoader mImageLoader;

	public static final String GAME_EMPTY = " ";

	public static final String GAME_POD = "Podkidnoy";

	public static final String GAME_BRIDGE = "Bridge";

	private final TableName tableName;

	private final Games games;

	private final PodkidnoyConfiguration podkidnoyConf;

	private final BridgeConfiguration bridgeConf;

	private CardLayout cl;

	private JPanel emptyConf;

	/**
	 * Constraints 
	 */
	private AbsoluteConstraints
	// Base panel
	createConstraint = new AbsoluteConstraints(LobbyLocations.CREATE_CONF, LobbySizes.LOBBY_CREATE_CONF),
	// Create label
	createLabelConstr = new AbsoluteConstraints(LobbyLocations.CREATE_LABEL, LobbySizes.CREATE_LABEL_CREATE),
	// Create button
	createButtonConstr = new AbsoluteConstraints(LobbyLocations.CREATE_BUTTON, LobbySizes.CREATE_BUTTON_CREATE);
	
	private JPanel confPanel;

	private JButton createButton;

	public CreateTab(final IImageLoader _imageLoader) {
		mImageLoader = _imageLoader;
		panel.setBackground(LobbyColors.LOBBY_TABS_CREATE_CONTENT_BG);
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		tableName = new TableName();
		tableName.setName(Names.CREATE_TABLE_NAME);
		panel.add(tableName, tableName.getConstraint());

		games = new Games();
		panel.add(games.getCombo(), games.getConstraint());

		podkidnoyConf = new PodkidnoyConfiguration();
		bridgeConf = new BridgeConfiguration();

		confPanel = new JPanel();

		emptyConf = new JPanel();
		emptyConf.setOpaque(false);

		cl = new CardLayout();
		confPanel.setLayout(cl);
		confPanel.setOpaque(false);

		confPanel.add(podkidnoyConf.getPanel(), podkidnoyConf.getGameType().name());
		confPanel.add(bridgeConf.getPanel(), bridgeConf.getGameType().name());
		confPanel.add(emptyConf, GAME_EMPTY);

		panel.add(confPanel, createConstraint);

		games.getCombo().addActionListener(new GamesComboHandler(this));

		JLabel createLabel = HudWidgetFactory.createHudLabel("Create table");
		createLabel.setFont(LobbyFonts.LOBBY_CELL);
		panel.add(createLabel, createLabelConstr);

		createButton = HudWidgetFactory.createHudButton("Create");
		createButton.setName(Names.CREATE_BUTTON);
		panel.add(createButton, createButtonConstr);


		// setSelectedGame(GameTypeCode.BRIDGE);
	}
	
	public TableName getTableName() {
		return tableName;
	}

	public D4 getCreateCommand() {
		D12 content = new D12();
		content.setD14(getTrimmedTableName());
		G17 gameConf = getCurrentGamePanel().getGameConf();
		content.setD13(gameConf);
		
		D4 sc = new D4();
		sc.setC13(C1.C_6);
		sc.setD5(content);
		return sc;
	}
	
	private IConfigurationPanel getCurrentGamePanel() {
		G13 cur = games.getSelectedGame();
		switch (cur) {
		case G_14:
			return podkidnoyConf;
		case G_15:
			return bridgeConf;
		default:
			throw new IllegalStateException("" + cur);
		}
	}

	public void setSelectedGame(final G13 type) {
		games.setSelectedGame(type);
	}

	public Games getGames() {
		return games;
	}

	public JPanel getPanel() {
		return panel;
	}

	public String getName() {
		return LobbyController.TAB_CREATE;
	}

	public void setSelectedConf(final G13 type) {
		if (type == null) {
			cl.show(confPanel, GAME_EMPTY);
			return;
		}
		switch (type) {
		case G_15:
		case G_14:
			cl.show(confPanel, type.name());
			break;
		default:
			throw new IllegalArgumentException("Unsupported: " + type);
		}
	}

	public JButton getCreateButton() {
		return createButton;
	}
	
	public String getTrimmedTableName() {
		String _text = tableName.getText();
		String ret = _text.trim();
		return ret;
	}
	
	public PodkidnoyConfiguration getPodkidnoy() {
		return podkidnoyConf;
	}

	public void resetCurrent() {
		getCurrentGamePanel().reset();
	}

	public void setEnabled(boolean b) {
		tableName.setEnabled(b);
		games.setEnabled(b);
		podkidnoyConf.setEnabled(b);
		bridgeConf.setEnabled(b);
	}

	public void reset() {
		games.setSelectedGame(null);
		tableName.setText("");
		tableName.fireCaretChange();
	}

}
