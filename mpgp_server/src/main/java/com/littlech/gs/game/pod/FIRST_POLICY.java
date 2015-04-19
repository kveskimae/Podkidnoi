/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod;

// TODO: Auto-generated Javadoc
/**
 * Enumeration of the first policies.
 *
 * @author Kristjan Veskim�e
 */
public enum FIRST_POLICY {
	
	
	/** Gives the game's very first attacker to random player. */
	RANDOM,
	
	/** Gives the game's very first attacker to last game's winner. */
	LAST_WINNER,
	
	/** Gives the game's very first attacker to player seated next to the last game's very first attacker. */
	ROUND
	;
}