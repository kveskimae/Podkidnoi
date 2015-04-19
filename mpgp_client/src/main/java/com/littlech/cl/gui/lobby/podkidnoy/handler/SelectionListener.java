/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.podkidnoy.handler;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.littlech.cl.gui.lobby.podkidnoy.JoinButton;
import com.littlech.cl.gui.lobby.podkidnoy.PodkidnoyTable;

public class SelectionListener implements ListSelectionListener {

	private PodkidnoyTable table;
	private JoinButton join;

	public SelectionListener(final PodkidnoyTable _table, final JoinButton _join) {
		this.table = _table;
		this.join = _join;
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == table.getSelectionModel() && table.getRowSelectionAllowed()) {
			int first = e.getFirstIndex();
			if (table.isEnabled()) {
				if (first == -1 && join.isEnabled()) {
					join.setEnabled(false);
				}
				if (first != -1 && !join.isEnabled()) {
					join.setEnabled(true);
				}
			}
		}
	}
}