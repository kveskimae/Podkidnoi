/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;

import com.littlech.gen.e.E19;
import com.littlech.gen.f.F1;
import com.littlech.gs.user.ServerUser;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISeat.
 */
public interface ISeat {
	
	/**
	 * Gets the iD.
	 *
	 * @return the iD
	 */
	F1 getID();
	
	/**
	 * Sit user.
	 *
	 * @param _user the _user
	 */
	void sitUser(ServerUser _user);
	
	/**
	 * Gets the server user.
	 *
	 * @return the server user
	 */
	ServerUser getServerUser();
	
	/**
	 * Removes the user.
	 */
	void removeUser();
	
	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	boolean isEmpty();
	
	/**
	 * Write command.
	 *
	 * @param command the command
	 * @throws Exception the exception
	 */
	void writeCommand(E19 command) throws Exception;

}
