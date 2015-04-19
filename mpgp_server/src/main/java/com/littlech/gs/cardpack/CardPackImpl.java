/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.cardpack;

import java.util.ArrayList;
import java.util.Iterator;

import com.littlech.gen.b.*;


// TODO: Auto-generated Javadoc
/**
 * Implementation of a card pack of 36 cards.
 *
 * @author Kristjan Veskim�e
 */
public class CardPackImpl implements ICardPack {

	

	/** Cards as an array list. */
	protected ArrayList<B20> cards = new ArrayList<B20>();

	/**
	 * Method for retrieving the next card in the pack.
	 *
	 * @return Next card in the pack
	 */
	public B20 getCard() {
		int leftInPack = cards.size();
		if (leftInPack < 1) {
			throw new IllegalStateException(
					"Trying to get a card from an empty pack");
		}
		int idx = leftInPack - 1;
		B20 ret = cards.get(idx);
		cards.remove(idx);
		return ret;
	}

	/**
	 * Checks if there are more cards left in the pack.
	 *
	 * @return true, if successful
	 */
	public boolean hasMoreCards() {
		return !this.cards.isEmpty();
	}

	/**
	 * Constructs a string representation of the pack.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<B20> it = cards.iterator();
		while (it.hasNext()) {
			String nextString = it.next().toString();
			sb.append(nextString + ";");
		}
		String ret = "[" + sb.toString() + "]";
		return ret;
	}

	/* (non-Javadoc)
	 * @see s.cardpack.ICardPack#size()
	 */
	@Override
	public int size() {
		return cards.size();
	}

	/* (non-Javadoc)
	 * @see s.cardpack.ICardPack#peekLast()
	 */
	@Override
	public B20 peekLast() {
		return cards.get(0);
	}

}
