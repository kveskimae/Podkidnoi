/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.button;

import javax.swing.JToggleButton;

import com.littlech.cl.gui.podkidnoy.util.ControlButtonType;

/**
 * 
 * Toggle button that can provide its control type
 * 
 * @author Kristjan Veskim�e
 *
 */
public class ControlButtonTypeImpl extends JToggleButton  implements IControlButtonType {
	
	/**
	 * Control type assigned to this button
	 */
	private ControlButtonType type;

	/**
	 * 
	 * Sets control type for this button
	 * 
	 * @param _type Action type
	 */
	public void setType(final ControlButtonType _type) {
		this.type = _type;
	}

	/**
	 * Retrieves control associated with this button
	 */
	public ControlButtonType getType() {
		return type;
	}

}
