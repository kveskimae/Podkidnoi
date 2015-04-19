/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.littlech.cl.gui.podkidnoy.PodkidnoyController;
import com.littlech.cl.gui.podkidnoy.util.ControlButtonType;
import com.littlech.cl.gui.podkidnoy.button.IControlButtonType;

/**
 * 
 * Handles Podkidnoy player cards panel's button events
 * 
 * @author Kristjan Veskim�e
 *
 */
public class ControlPanelButtonHandler 
// extends AbstractButton 
implements ActionListener {
	
	private final PodkidnoyController mController;
	
	/**
	 * Do not use!
	 */
	private ControlPanelButtonHandler() {
		mController = null;
	}

	public ControlPanelButtonHandler(final PodkidnoyController _controller) {
		mController = _controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source instanceof IControlButtonType) {
			IControlButtonType cb = (IControlButtonType) source;
			ControlButtonType ct = cb.getType();
			switch (ct) {
			case CLOSE:
				mController.handleClose();
				break;
			case OPTIONS:
				// Unimplemented
				break;
			default:
				
				throw new IllegalArgumentException("Unsupported" +
						// " control button type" +
						": " + ct);
			}
		} else {
			throw new IllegalStateException(
					// "ControlPanelButtonHandler was listening to " 
					"To "
					+ source.getClass().getCanonicalName());
		}
	}
	
}
