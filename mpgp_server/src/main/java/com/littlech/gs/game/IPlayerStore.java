/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;

import java.util.List;

import com.littlech.gen.e.E19;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F23;
import com.littlech.gs.user.ServerUser;


// TODO: Auto-generated Javadoc
/**
 * The Interface IPlayerStore.
 */
public interface IPlayerStore {

	/**
	 * Removes the.
	 *
	 * @param user the user
	 */
	void remove(ServerUser user);

    /**
     * Adds the.
     *
     * @param _user the _user
     */
    void add (ServerUser _user);

	/**
	 * Adds the creator.
	 *
	 * @param u the u
	 */
	void addCreator(ServerUser u);

    /**
     * Sit.
     *
     * @param _user the _user
     * @param _code the _code
     */
    void sit (ServerUser _user, F1 _code);

	/**
	 * Write to all.
	 *
	 * @param command the command
	 */
	void writeToAll(E19 command);

	/**
	 * Gets the lobby seats.
	 *
	 * @return the lobby seats
	 */
	List<F23> getLobbySeats();

	/**
	 * Checks if is seats turn.
	 *
	 * @param id the id
	 * @return true, if is seats turn
	 */
	boolean isSeatsTurn(F1 id);

	/**
	 * Finds seat that corresponds to parameter user and returns its id code.
	 *
	 * @param user User
	 * @return ID code for the seat where player is sitting or null if player is not found sitting
	 */
	F1 getSeatIDByUser(ServerUser user);

	/**
	 * Reset.
	 */
	void reset();

}
 
