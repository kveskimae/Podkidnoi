/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.tabs;

import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import com.littlech.cl.gui.lobby.constants.*;

public class LobbyTabbedPane extends JTabbedPane {

	private AbsoluteConstraints constraint = new AbsoluteConstraints(LobbyLocations.LOBBY_TABS, LobbySizes.LOBBY_TAB);

	public LobbyTabbedPane() {
		setUI(new LobbyTabbedPaneUI());
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setFont(LobbyFonts.LOBBY_TABS);
	}

	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

}
