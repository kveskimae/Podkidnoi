/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.littlech.cl.gui.podkidnoy.PodkidnoyController;

public class StartButtonHandler implements ActionListener {
	
	private final PodkidnoyController mController;
	
	/**
	 * Do not use!
	 */
	private StartButtonHandler() {
		mController = null;
	}

	public StartButtonHandler(final PodkidnoyController _controller) {
		mController = _controller;
	}
	
	public PodkidnoyController getController() {
		return mController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getController().handleStart();
	}

}
