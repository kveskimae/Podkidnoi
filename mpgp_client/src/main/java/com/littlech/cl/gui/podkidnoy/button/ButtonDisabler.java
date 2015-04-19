/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

public class ButtonDisabler implements ActionListener {

	private final AbstractButton mButton;

	public ButtonDisabler(final AbstractButton _button) {
		mButton= _button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    mButton.setEnabled(false);
    /*
    if (mButton.isEnabled()) {
      mButton.setEnabled(false);
    } else {
      mButton.setEnabled(true);
    }
    */
	}

}
