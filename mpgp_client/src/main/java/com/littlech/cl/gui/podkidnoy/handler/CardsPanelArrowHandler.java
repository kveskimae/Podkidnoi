/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.littlech.cl.gui.podkidnoy.component.CardsPanel;

/**
 * 
 * Handles Podkidnoy player cards panel's arrow action events
 * 
 * @author Kristjan Veskim�e
 *
 */
public class CardsPanelArrowHandler implements ActionListener {
	
	/**
	 * Type; left or right
	 */
	private final CardsPanelArrowHandlerType type;
	
	/**
	 * Player cards panel where action is forwarded
	 */
	private final CardsPanel panel;
	
	/**
	 * Constructor
	 * @param panel Player cards panel that adds this arrow action events handler to one of its arrows (either left or right)
	 * @param _type Type
	 */
	public CardsPanelArrowHandler(final CardsPanel panel, final CardsPanelArrowHandlerType _type) {
		this.type = _type;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (getType()) {
		case LEFT:
			getPanel().leftButtonClicked();
			break;
		case RIGHT:
			getPanel().rightButtonClicked();
			break;
		default:
			throw new IllegalStateException(
					// "Arrow type was: " + 
					"Unsupported: " + 
					getType());
		}
	}
	
	/**
	 * Retrieves the type of this arrow handler; one of left or right
	 * @return Type
	 */
	public CardsPanelArrowHandlerType getType() {
		return type;
	}
	
	/**
	 * Retrieves the player cards panel where the arrow associated with this action handler is located
	 * @return Player cards panel
	 */
	public CardsPanel getPanel() {
		return panel;
	}
	
}

