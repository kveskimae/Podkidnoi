/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.util;

import java.util.List;


import com.littlech.cl.Utils;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B23;

/**
 * 
 * Memorizer for player and table cards
 * <p>
 * Use to revert to previous state after player has changed table cards in client and picks up
 * 
 * @author Kristjan Veskim�e
 *
 */
public class DefendingStateMemory {

	/**
	 * Player cards
	 */
	private final List<B20> cards;
	
	/**
	 * Table cards
	 */
	private final List<B23> table;

	/**
	 * Constructor
	 * @param _cards Player cards before player starts defending
	 * @param _table Table cards before player starts defending
	 */
	public DefendingStateMemory(List<B20> _cards, List<B23> _table) {
		cards = Utils.getCopy(_cards);
		table = Utils.getCopyOfTable(_table);
	}
	
	/**
	 * Returns snapshot of player cards, taken before player started defending table
	 * @return Cards
	 */
	public List<B20> getCards() {
		return cards;
	}

	/**
	 * Returns snapshot of table cards, taken before player started defending table
	 * @return Table cards
	 */
	public List<B23> getTable() {
		return table;
	}

}
