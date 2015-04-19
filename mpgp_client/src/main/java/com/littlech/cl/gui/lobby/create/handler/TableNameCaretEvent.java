/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create.handler;

import javax.swing.event.CaretEvent;

public class TableNameCaretEvent extends CaretEvent {

	public TableNameCaretEvent(Object source) {
		super(source);
	}

	@Override
	public int getDot() {
		return 0;
	}

	@Override
	public int getMark() {
		return 0;
	}

}
