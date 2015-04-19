/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod.task;

import com.littlech.cl.Utils;
import com.littlech.gen.g.G17;
import com.littlech.gen.g.G27;
import com.littlech.gen.g.G30;
import com.littlech.gs.game.IGame;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerLobbyTable.
 */
public class ServerLobbyTable {

	/** The table. */
	private final G30 table;

	/** The game. */
	private final IGame game;
	
	/**
	 * Instantiates a new server lobby table.
	 *
	 * @param _game the _game
	 * @param _table the _table
	 */
	private ServerLobbyTable(final IGame _game, final G30 _table) {
		game = _game;
		table = _table;
	}

	/**
	 * Instantiates a new server lobby table.
	 *
	 * @param _id the _id
	 * @param _name the _name
	 * @param _def the _def
	 * @param _configuration the _configuration
	 * @param _game the _game
	 */
	public ServerLobbyTable(final String _id, final String _name,
			final boolean _def, final G17 _configuration,
			final IGame _game) {
		game = _game;

		table = new G30();
		table.setG33(_configuration.getG19());
		table.setG35(_def);
		table.setG31(_id);
		table.setG32(_name);
		table.setG34(getTableState());
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public IGame getGame() {
		return game;
	}

	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public G30 getTable() {
		table.setG34(getTableState());
		return table;
	}

	/**
	 * Gets the table state.
	 * 
	 * XXX synchronization here, maybe needs improving
	 * 
	 * @return the table state
	 */
	public G27 getTableState() {
		getGame().getLock().lock();
		G27 ret = new G27();
		ret.setG28(getGame().getStateMachine().getTableState());
		ret.getG29().addAll(getGame().getPlayerStore().getLobbySeats());
		getGame().getLock().unlock();
		return ret;
	}

	/**
	 * Gets the copy.
	 *
	 * @return the copy
	 */
	public ServerLobbyTable getCopy() {
		G30 copyTable = Utils.getCopy(getTable());
		ServerLobbyTable ret = new ServerLobbyTable(getGame(), copyTable);
		return ret;
	}

}
