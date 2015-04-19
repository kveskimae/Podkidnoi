/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.button;

import javax.swing.JToggleButton;

import com.littlech.gen.a.A5;

/**
 * 
 * Toggle button that can provide its action type
 * 
 * @author Kristjan Veskim�e
 *
 */
public class ActionButtonTypeImpl extends JToggleButton  implements IActionButtonType {
	
	/**
	 * Action type assigned to this button
	 */
	private A5 type;

	/**
	 * 
	 * Sets action for this button
	 * 
	 * @param _type Action type
	 */
	public void setType(final A5 _type) {
		this.type = _type;
	}

	/**
	 * Retrieves action associated with this button
	 */
	public A5 getType() {
		return type;
	}

}
