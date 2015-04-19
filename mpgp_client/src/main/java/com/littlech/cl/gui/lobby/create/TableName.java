/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.netbeans.lib.awtextra.AbsoluteConstraints;

import com.littlech.cl.gui.lobby.constants.*;
import com.littlech.cl.gui.lobby.create.handler.TableNameCaretEvent;
import com.littlech.cl.gui.lobby.create.handler.TableNameLimit;

public class TableName extends JTextField {
	
	private AbsoluteConstraints constraint = new org.netbeans.lib.awtextra.AbsoluteConstraints(LobbyLocations.CREATE_TABLE_NAME,
			LobbySizes.CREATE_TABLE_NAME);
	
	public static final int MAX_LENGTH = 15;
	
	public TableName() {
		setDocument(new TableNameLimit(MAX_LENGTH));
		setBorder(BorderFactory.createEmptyBorder());
	}
	
	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

	public void fireCaretChange() {
		// XXX postActionEvent(); ? 
		
		ArrayList<CaretListener> al = new ArrayList<CaretListener>(Arrays.asList(getCaretListeners()));
		Iterator<CaretListener> it = al.iterator();
		if (it.hasNext()) {
			CaretEvent ce = new TableNameCaretEvent(this);
			while (it.hasNext()) {
				it.next().caretUpdate(ce);
			}
		}
	}

}
