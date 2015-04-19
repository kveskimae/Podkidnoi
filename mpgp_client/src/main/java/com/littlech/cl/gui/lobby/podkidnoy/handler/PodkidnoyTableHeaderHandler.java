/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.podkidnoy.handler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.littlech.cl.gui.lobby.podkidnoy.PodkidnoyTab;

/**
 * 
 * Handles Podkidnoy table header clicks
 * 
 * @author Kristjan Veskim�e
 *
 */
public class PodkidnoyTableHeaderHandler implements MouseListener {
	
	/**
	 * Podkidnoy tab
	 */
	private PodkidnoyTab mTab;

	/**
	 * Constructor
	 * @param _tab Podkidnoy tab
	 */
	public PodkidnoyTableHeaderHandler(final PodkidnoyTab _tab) {
		mTab = _tab;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mTab.headerClicked();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
