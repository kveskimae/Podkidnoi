/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.tabs;

import java.awt.Graphics;
import java.awt.Insets;

/**
 * 
 * UI for lobby tabbed pane without borders
 * 
 * @author Kristjan Veskim�e
 *
 */
public class LobbyTabbedPaneUI extends javax.swing.plaf.basic.BasicTabbedPaneUI {
	
	@Override
	protected Insets getContentBorderInsets(int tabPlacement) {
		return new Insets(0, 0, 0, 0);
	}

	@Override
	protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
	}
}