/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import java.awt.Container;
import java.util.Observer;

import org.netbeans.lib.awtextra.AbsoluteConstraints;

/**
 * 
 * Podkidnoy screen panel for grouping similar functionality
 * 
 * @author Kristjan Veskim�e
 *
 */
public interface IScreenPanel extends Observer {

	/**
	 * Retrieves constraint, i.e. the area where this panel is to be drawn
	 * @return Constraint for this panel
	 */
	AbsoluteConstraints getConstraint();

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

	/**
	 * Shows or hides this panel
	 * @param b True if this panel is to be shown, false otherwise
	 */
	void setVisible(boolean b);

}
