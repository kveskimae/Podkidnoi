/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.littlech.cl.gui.podkidnoy.PodkidnoyController;
import com.littlech.gen.f.F1;

public class SitButtonHandler implements ActionListener {
	
	private final PodkidnoyController mController;
	private final F1 mID;
	
	/**
	 * Do not use!
	 */
	private SitButtonHandler() {
		mController = null;
		mID = null;
	}

	public SitButtonHandler(final PodkidnoyController _controller, final F1 id) {
		mController = _controller;
		mID = id;
	}
	
	public PodkidnoyController getController() {
		return mController;
	}
	
public F1 getID() {
	return mID;
}

	@Override
	public void actionPerformed(ActionEvent e) {
		getController().handleSit(getID());
	}

}
