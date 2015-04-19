/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.create.handler;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.littlech.cl.gui.lobby.LobbyController;
import com.littlech.cl.gui.lobby.util.CreateState;

public class TableNameFieldHandler implements CaretListener {
	
	private LobbyController mCreateTab;

	public TableNameFieldHandler(final LobbyController _CreateTab) {
		mCreateTab = _CreateTab;
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		boolean enabled = false;
		if (mCreateTab.isEligibleTableName() && CreateState.IDLE.equals(mCreateTab.getModel().getState().getValue())) {
			enabled = true;
		}
		mCreateTab.getScreen().getCreateTab().getCreateButton().setEnabled(enabled);
		/*
		if (mCreateTab.bingo()) {
			mCreateTab.getPodkidnoy().reset();
		}
		*/
	}

}
