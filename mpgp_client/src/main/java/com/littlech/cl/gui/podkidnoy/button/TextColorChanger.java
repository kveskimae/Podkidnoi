/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.button;

import java.awt.Color;

import javax.swing.AbstractButton;
import javax.swing.DefaultButtonModel;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TextColorChanger implements ChangeListener {

	private final JLabel mLabel;
	private final Color mColorNormal, mColorDisabled;

	public TextColorChanger(final JLabel _label, final Color _colorNormal, final Color _colorDisabled) {
		mLabel = _label;
		mColorNormal = _colorNormal;
		mColorDisabled = _colorDisabled;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		AbstractButton sourceButton= (AbstractButton) e.getSource();
    DefaultButtonModel model = (DefaultButtonModel) sourceButton.getModel();
    if (model.isEnabled()) {
    	mLabel.setForeground(mColorNormal);
    } else {
    	mLabel.setForeground(mColorDisabled);
    }
	}

}
