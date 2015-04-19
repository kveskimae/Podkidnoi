/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.tabs.handler;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.littlech.cl.gui.lobby.constants.*;
import com.littlech.cl.gui.lobby.LobbyController;
import com.littlech.cl.gui.lobby.LobbyScreen;

public class TabsHandler implements ChangeListener {

	private int lastSelectedIndex = -1;

	private final LobbyScreen mScreen;

	public TabsHandler(final LobbyScreen _screen) {
		mScreen = _screen;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JTabbedPane tabs = mScreen.getTabs();

		/* Checks if there is really a change */
		int sIdx = tabs.getSelectedIndex();
		if (lastSelectedIndex == sIdx) {
			return;
		}
		lastSelectedIndex = sIdx;

		/* Showing or hiding join button */
		boolean visible = false;
		if (LobbyController.TAB_PODKIDNOY.equals(mScreen.getSelectedTab())) {
			visible = true;
		}
		mScreen.getJoinButton().setVisible(visible);

		/* Setting tab colors */
		/*
		 * if (lastSelectedIndex != -1) { tabs.setForegroundAt(lastSelectedIndex,
		 * Colors.LOBBY_TABS_UNSELECTED_FG); }
		 */
		for (int i = 0; i < tabs.getTabCount(); i++) {
			if (i != sIdx) {
				tabs.setForegroundAt(i, LobbyColors.LOBBY_TABS_UNSELECTED_FG);
				// Component cur = tabs.getTabComponentAt(i);
				// cur.setForeground(Colors.LOBBY_TABS_UNSELECTED_FG);
			}
		}
		tabs.setForegroundAt(sIdx, LobbyColors.LOBBY_TABS_SELECTED_FG);
	}

}
