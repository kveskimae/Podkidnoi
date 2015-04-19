/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create;

import java.awt.Container;

import com.littlech.gen.g.G13;
import com.littlech.gen.g.G17;

/**
 * 
 * Podkidnoy screen panel for grouping similar functionality
 * 
 * @author Kristjan Veskim�e
 *
 */
public interface IConfigurationPanel {

	/**
	 * Retrieves constraint, i.e. the area where this panel is to be drawn
	 * @return Constraint for this panel
	 */
	// AbsoluteConstraints getConstraint();

	/**
	 * Retrieves the base panel where this panel's all components are located
	 * @return Base panel
	 */
	Container getPanel();

	/**
	 * Enables or disables this panel
	 * @param b True if this panel is to be enabled, false otherwise
	 */
	void setEnabled(boolean b);

	void reset();
	
	G13 getGameType();
	
	public G17 getGameConf();

}
