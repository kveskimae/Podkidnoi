/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import com.littlech.cl.Utils;
import com.littlech.cl.gui.podkidnoy.button.IActionButtonType;


/**
 * 
 * Handles button events for Podkidnoy buttons panel
 * 
 * @author Kristjan Veskim�e
 *
 */
public class ButtonsPanelButtonHandler extends AbstractButton implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source instanceof IActionButtonType) {
			IActionButtonType cb = (IActionButtonType) source;
			String cmd;
			if (cb.getType() != null) {
				cmd = Utils.marshallButton(cb.getType());
			} else {
				cmd = null;
			}
			ActionEvent ae = new ActionEvent(this, arg0.getID(), cmd);
			fireActionPerformed(ae);
		} else {
			throw new IllegalStateException(
					// "ButtonsPanel was listening action events from "
					"To "
					+ source.getClass().getCanonicalName()
					// + ", which is not among the buttons"
					);
		}
	}
	
}