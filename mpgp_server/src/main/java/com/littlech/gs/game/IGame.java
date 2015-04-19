/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;

import java.util.concurrent.locks.Lock;

import com.littlech.gen.g.G17;

// TODO: Auto-generated Javadoc
/**
 * An abstraction for multiplayer games, e.g. Podkidnoy.
 */
public interface IGame {
	
	// void queueGameCommand(IGameCommand _command);
	
	ICommandHandler getCommandHandler();

	/**
	 * Handle command.
	 *
	 * @param _user the _user
	 * @param received the received
	 */
	// void handleCommand(ServerUser _user, D4 received);

	/**
	 * Handles one bot move.
	 *
	 * @param seatID the seat id
	 * @param _cmd the _cmd
	 */
	// void handleBotMove( F1 seatID, D4 _cmd);

	/**
	 * Handle game command.
	 *
	 * @param seatID the seat id
	 * @param _cmd the _cmd
	 */
	// void handleGameCommand( F1 seatID, D4 _cmd);

	/**
	 * Start new game.
	 */
	void startNewGame();
	
	/**
	 * Gets the lock.
	 *
	 * @return the lock
	 */
	Lock getLock();

	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	G17 getConfiguration();

	/**
	 * Gets the rules.
	 *
	 * @return the rules
	 */
	IRules getRules();

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	IGameData getData();

	/**
	 * Gets the state machine.
	 *
	 * @return the state machine
	 */
	IStateMachine getStateMachine();

	/**
	 * Gets the player store.
	 *
	 * @return the player store
	 */
	IPlayerStore getPlayerStore();

	/**
	 * Checks if is test.
	 *
	 * @return true, if is test
	 */
	boolean isTest();
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Resets the game
	 * <br />
	 * Use example: the last human player has left the game table
	 */
	void reset();

}
