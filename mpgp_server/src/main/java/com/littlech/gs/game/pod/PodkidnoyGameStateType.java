/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod;

import java.util.ArrayList;
import java.util.List;

import com.littlech.gs.game.IGameState;


// TODO: Auto-generated Javadoc
/**
 * Enumeration of Durak game state types.
 *
 * @author Kristjan Veskim�e
 */
public enum PodkidnoyGameStateType implements IGameState {
	
	/** Waiting rounds' first cards. */
	GS_DURAK_ROUND_STARTED,
	/**
	 * Defending the table for the first time (i.e. can send)
	 */
	GS_DURAK_DEFENDING_FIRST,
	
	/** Waiting more cards after the table has been defended. */
	GS_DURAK_WAITING_MORE_CARDS,
	
	/** Defending the table again (cannot send). */
	GS_DURAK_DEFENDING_AGAIN,
	
	/** Picked table cards up, waiting add cards. */
	GS_DURAK_PICKED_UP,
	
	/** Round has ended, new round has not begun. */
	GS_DURAK_ROUND_ENDED,
	
	/** Game has finished. */
	GS_DURAK_FINISHED,
	
	/** Game has not been initialized yet. */
	GS_DURAK_NOT_INITIALIZED;

	/**
	 * Check if the state is Durak game playing state.
	 *
	 * @return true if the state is a playing state, false otherwise
	 */
	@Override
	public boolean isPlayingState() {
		boolean ret = PodkidnoyGameStateType.getPlayingStates().contains(this);
		return ret;
	}
	
	/**
	 * Get the playing states.
	 *
	 * @return States
	 */
	private static List<PodkidnoyGameStateType> getPlayingStates() {
		ArrayList<PodkidnoyGameStateType> ret = new ArrayList<PodkidnoyGameStateType>();
		ret.add(GS_DURAK_ROUND_STARTED);
		ret.add(GS_DURAK_DEFENDING_FIRST);
		ret.add(GS_DURAK_WAITING_MORE_CARDS);
		ret.add(GS_DURAK_DEFENDING_AGAIN);
		ret.add(GS_DURAK_PICKED_UP);
		return ret;
	}
}