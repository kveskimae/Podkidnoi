/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.littlech.cl.Controller;
import com.littlech.cl.gui.IScreenController;
import com.littlech.cl.gui.ScreenControllerType;
import com.littlech.cl.gui.lobby.create.handler.CreateButtonHandler;
import com.littlech.cl.gui.lobby.create.handler.TableNameFieldHandler;
import com.littlech.cl.gui.lobby.podkidnoy.PodkidnoyTable;
import com.littlech.cl.gui.lobby.podkidnoy.handler.JoinButtonHandler;
import com.littlech.cl.gui.lobby.util.CreateState;
import java.awt.event.ActionListener;
import java.util.Arrays;

import com.littlech.gen.d.D4;
import com.littlech.gen.e.E11;
import com.littlech.gen.e.E19;
import com.littlech.gen.e.E25;
import com.littlech.gen.g.G13;
import com.littlech.gen.g.G30;
import com.littlech.gen.g.G4;

/**
 * 
 * Controller for Podkidnoy GUI
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class LobbyController implements IScreenController, ActionListener {

	public static final String TAB_PODKIDNOY = "PODKIDNOY", TAB_CREATE = "CREATE";

	/**
	 * Client application controller
	 */
	private final Controller mController;

	/**
	 * Podkidnoy screen
	 */
	private LobbyScreen screen;

	/**
	 * Podkidnoy model
	 */
	private LobbyModel model;

	private final List<String> badWords;
	private final List<String> disallowedTableNames;

	/**
	 * Constructor
	 * 
	 * @param _controller
	 *          Client application controller
	 */
	public LobbyController(final Controller _controller) {
		mController = _controller;
		screen = new LobbyScreen(mController.getImageLoader());
		model = new LobbyModel();

		badWords = new ArrayList<String>(Arrays.asList("fuck", "shit", "dick", "cunt"));
		disallowedTableNames = new ArrayList<String>();
		
		// disallowedTableNames.add("Table777"); // XXX delete this

		screen.getJoinButton().addActionListener(new JoinButtonHandler(this));

		screen.setSelectedTab(TAB_PODKIDNOY);
		screen.getCreateTab().getCreateButton().setEnabled(false);
		screen.getCreateTab().getCreateButton().addActionListener(new CreateButtonHandler(this));

		getModel().getState().addObserver(getScreen());

		getScreen().getCreateTab().getTableName().addCaretListener(new TableNameFieldHandler(this));

		getModel().getState().setValue(CreateState.IDLE);
		setSelectedConf(null);
	}
	
	@Override
	public void enable() {
		// System.out.println("enabling lobby");
		getModel().getState().setValue(CreateState.IDLE);
PodkidnoyTable table = getScreen().getPodkidnoyTab().getTable();
		int selectedRow = table.getSelectedRow();
		if (selectedRow >= 0) {
			table.removeRowSelectionInterval(selectedRow, selectedRow);
		}
		screen.getJoinButton().setEnabled(false);
	}

	public LobbyScreen getScreen() {
		return screen;
	}

	public LobbyModel getModel() {
		return model;
	}

	@Override
	public Container getPanel() {
		return screen.getPanel();
	}

	@Override
	public ScreenControllerType getType() {
		return ScreenControllerType.LOBBY;
	}

	@Override
	public boolean handleSC(E19 cs) {
		switch (cs.getC13()) {
		case C_4:
			getScreen().getPodkidnoyTab().getTableModel().processAll(cs);
			return true;
		case C_5:
			String selectedTableID = getScreen().getPodkidnoyTab().getSelectedTableID();
			
			getScreen().getPodkidnoyTab().getTableModel().updateTables(cs);
			
			int modelRow = getScreen().getPodkidnoyTab().getTableModel().getTableIndexByTableID(selectedTableID);
			if (modelRow >= 0) {
				int converted = getScreen().getPodkidnoyTab().getTable().convertRowIndexToView(modelRow);
				getScreen().getPodkidnoyTab().getTable().setRowSelectionInterval(converted, converted);
			}
			return true;
		case C_6:
			handleCreateTable(cs);
			return true;
		case C_7:
			handleJoinTable(cs);
			return true;
		default:
			return false;
		}
	}

	private void handleJoinTable(E19 cs) {
		E11 ec = cs.getE21();
		System.out.println("handle join with " + ec);
		switch (ec) {
		case E_12:
			// Ignore, screen has already been disabled
			// XXX
			// PodkidnoyController pC = ((PodkidnoyController)mController.getPodkidnoy());
			// pC.get
			// OK
			// mController.enableSitButtons(true);
			break;
		case E_13:
			// FAIL
			mController.addDialog(cs.getE22());
			getModel().getState().setValue(CreateState.IDLE);
			break;
		default:
			throw new IllegalArgumentException(); // "Unknown error code: " + ec);
		}
	}

	private void handleCreateTable(final E19 cs) {
		E25 creationResultContent = (E25) cs.getE20();
		G4 resultCode = creationResultContent.getE26();
		switch (resultCode) {
		case G_7:
			getScreen().getCreateTab().resetCurrent();
			mController.addDialog(creationResultContent.getE27());
			break;
		case G_6:
			// XXX Add to disallowed list
			getDisallowedTableNames().add(getScreen().getCreateTab().getTrimmedTableName().toLowerCase());
			getScreen().getCreateTab().getTableName().setText("");
			mController.addDialog(creationResultContent.getE27());
			break;
		case G_5:
			/// System.out.println("HEUREKA!");
			mController.setScreenController(ScreenControllerType.PODKIDNOY); // XXX Maybe should be in controller?
			break;
		case G_8:
			getScreen().getCreateTab().getGames().removeSelectedGame();
			if (getScreen().getCreateTab().getGames().getSize() < 1) {
				getScreen().removeTab(LobbyController.TAB_CREATE);
			}
			mController.addDialog(creationResultContent.getE27());
			break;
		default:
			throw new IllegalArgumentException("Unsupported: " + resultCode);
		}
		getModel().getState().setValue(CreateState.IDLE);
		// update();
	}

	@Override
	public void startShow() {
	}

	@Override
	public void stopShow() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public void pressedJoin() {
		// System.out.println("join pressed");
		getModel().getState().setValue(CreateState.WAITING_RESPONSE);
		D4 cmd = getScreen().getPodkidnoyTab().getJoinCommand();
		mController.sendCS(cmd);
	}

	public void sendCreateTableCommand() {
		// System.out.println("create pressed");
		getModel().getState().setValue(CreateState.WAITING_RESPONSE);
		D4 cmd = getScreen().getCreateTab().getCreateCommand();
		mController.sendCS(cmd);
	}

	public void setSelectedConf(final G13 _type) {
		screen.getCreateTab().setSelectedGame(_type);
	}

	public boolean isEligibleTableName() {
		String trimmedTableName = getScreen().getCreateTab().getTrimmedTableName();
		if (trimmedTableName == null) {
			return false;
		}
		if (trimmedTableName.length() < 1) {
			return false;
		}
		String inLower = trimmedTableName.toLowerCase();
		for (String s : getBadWords()) {
			if (inLower.contains(s)) {
				return false;
			}
		}
		for (String s : getDisallowedTableNames()) {
			String disallowedInLower = s.toLowerCase();
			if (inLower.equals(disallowedInLower)) {
				return false;
			}
		}
		for (G30 t : getScreen().getPodkidnoyTab().getTableModel().getTables()) {
			if (trimmedTableName.equals(t.getG32())) {
				return false;
			}
		}
		return true;
	}

	public List<String> getBadWords() {
		return badWords;
	}

	public List<String> getDisallowedTableNames() {
		return disallowedTableNames;
	}

	@Override
	public void reset() {
		getScreen().getPodkidnoyTab().getTableModel().reset();
		getScreen().getCreateTab().reset(); // XXX ADDED
		getScreen().getCreateTab().getPodkidnoy().reset();
		getModel().getState().setValue(CreateState.IDLE);
	}

	@Override
	public void finishLayout() {
		getScreen().finishLayout();
	}

	/* START OF TEST METHODS */

	/* END OF TEST METHODS */

}
