/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;

import com.littlech.gen.g.G1;


// TODO: Auto-generated Javadoc
/**
 * The Interface IStateMachine.
 *
 * @param <T> the generic type
 */
public  interface IStateMachine <T extends IGameState> {
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	T getState();
	
	/**
	 * Sets the state.
	 *
	 * @param newState the new state
	 */
	void setState(T newState);
	
	/**
	 * Gets the table state.
	 *
	 * @return the table state
	 */
	G1 getTableState();
	
	/**
	 * Sets the test table state.
	 *
	 * @param code the new test table state
	 */
	void setTestTableState(G1 code);

	/**
	 * Resets this state machine
	 * <br />
	 * Use case example: the last human player has left the game table
	 */
	void reset();

}

