/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.podkidnoy;

import javax.swing.JScrollPane;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

import com.explodingpixels.macwidgets.IAppWidgetFactory;
import com.littlech.cl.gui.lobby.constants.*;

/**
 * 
 * Scroll pane for lobby Podkidnoy tables
 * 
 * @author Kristjan Veskim�e
 *
 */
public class PodkidnoyTableScrollPane extends JScrollPane {

	/**
	 * Constraint
	 */
	private AbsoluteConstraints constraint = new AbsoluteConstraints(LobbyLocations.LOBBY_TABLE_SCROLLPANE, LobbySizes.LOBBY_TABLE_SCROLLPANE);

	/**
	 * Constructor
	 * @param table Lobby Podkidnoy table 
	 */
	public PodkidnoyTableScrollPane(final PodkidnoyTable table) {
		super(table);
		IAppWidgetFactory.makeIAppScrollPane(this);
		setSize(LobbySizes.LOBBY_TABLE_SCROLLPANE);
		setPreferredSize(LobbySizes.LOBBY_TABLE_SCROLLPANE);
		setBackground(LobbyColors.LOBBY_BLUE);
	}

	/**
	 * Returns constraint for this scroll pane
	 * @return Constraint
	 */
	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

}
