/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.cardpack;

import com.littlech.gen.b.B20;
 
// TODO: Auto-generated Javadoc
/**
 * Interface for a card pack.
 *
 * @author Kristjan Veskim�e
 */
public interface ICardPack {

	/**
	 * Method for retrieving the next card in the pack.
	 *
	 * @return next card in the pack
	 */
	B20 getCard();

	/**
	 * Checks if there are more cards in the pack.
	 *
	 * @return True if there still cards left in the pack, false otherwise
	 */
	boolean hasMoreCards();

	/**
	 * Constructs string representation of the card pack.
	 *
	 * @return String representation of the pack
	 */
	String toString();

	/**
	 * Size.
	 *
	 * @return the int
	 */
	int size();

	/**
	 * Peek last.
	 *
	 * @return the b20
	 */
	B20 peekLast();

}
