/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.util;

import java.util.ArrayList;
import java.util.List;

import com.littlech.gen.a.A1;
import com.littlech.gen.a.A5;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F6;

/**
 * 
 * Holds user and allowed actions for self
 * 
 * @author Kristjan Veskim�e
 *
 */
public class Self {
	
	/**
	 * User
	 */
	private F6 user;
	
	/**
	 * Seat ID code
	 */
	private F1 id;

	/**
	 * On turn state; can be null
	 */
	private A1 onTurnState = null;

	/**
	 * Allowed actions in on turn state
	 */
	private final List<A5> buttons = new ArrayList<A5>();

	/**
	 * Constructor 
	 * @param selfUser User for self player
	 */
	public Self(F6 selfUser) {
		user = selfUser;
	}

	/**
	 * Returns user for self player
	 * @return User
	 */
	public F6 getUser() {
		return user;
	}
	
	/**
	 * Returns Seat ID code for self player
	 * @return Seat ID code or null if user is a viewer
	 */
	public F1 getId() {
		return id;
	}

	/**
	 * Sets ID code for self player; update after player has sat down on table
	 * @param id Seat ID code
	 */
	public void setId(F1 id) {
		// System.out.println("id=" + id);
		this.id = id;
	}

	/**
	 * Returns allowed actions for self player on turn
	 * @return Podkidnoy button codes
	 */
	public List<A5> getButtons() {
		return buttons;
	}

	/**
	 * Sets on turn state for self player; set null if self player is not on turn
	 * @param onTurnState Podkidnoy state code
	 */
	public void setOnTurnState(A1 onTurnState) {
		this.onTurnState = onTurnState;
	}

	/**
	 * Returns Podkidnoy on turn state for self player
	 * @return Podkidnoy state code or null if self player is not on turn
	 */
	public A1 getOnTurnState() {
		return onTurnState;
	}
	
	public void setUser(F6 user) {
		this.user = user;
	}
	
	/*
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Self[user=");
		if (user == null) {
		sb.append("null");
		} else {
			sb.append("{name="+user.getF7()+", avatar="+user.getF8()+"}");
		}
		sb.append(", id=" + id);
		sb.append("]");
		return sb.toString();
	}
	*/

}
