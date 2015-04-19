/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import com.littlech.cl.Utils;
import com.littlech.cl.gui.podkidnoy.button.CardButton;
import com.littlech.gen.b.B20;

/**
 * 
 * Handles Podkidnoy table cards panel's button events
 * 
 * @author Kristjan Veskim�e
 *
 */
public class TablePanelButtonHandler extends AbstractButton implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof CardButton) {
			CardButton cb = (CardButton) source;
			String cmd;
			B20 ct = cb.getCard();
			if (ct != null) {
				cmd = Utils.marshallCard(ct);
			} else {
				cmd = null;
			}
			ActionEvent ae = new ActionEvent(this, e.getID(), cmd);
			fireActionPerformed(ae);
		}
	}
	
}
