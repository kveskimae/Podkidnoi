/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.cardpack;

import java.util.ArrayList;
import java.util.List;

import com.littlech.cl.Utils;
import com.littlech.gen.b.*;
import com.littlech.gen.g.G9;


// TODO: Auto-generated Javadoc
/**
 * A factory for creating CardPack objects.
 */
public class CardPackFactory {

	/**
	 * Gets the shuffled.
	 *
	 * @param ranks the ranks
	 * @return the shuffled
	 */
	protected static ICardPack getShuffled(List<B6> ranks) {
		CardPackImpl ret = new CardPackImpl();
		List<B20> tmpCards = new ArrayList<B20>();
		List<B1> suits = CardPackFactory.getSuits();
		for (B1 suit : suits) {
			for (B6 rank : ranks) {
				tmpCards.add(
						Utils.getCard(rank, suit)
						// new CardType(rank, suit)
						);
			}
		}
		while (tmpCards.size() > 0) {
			double step = 1 / (double) tmpCards.size();
			int idx = (int) Math.floor(Math.random() / step);
			ret.cards.add(tmpCards.get(idx));
			tmpCards.remove(idx);
		}
		return ret;
	}

	/**
	 * Creates a new CardPack object.
	 *
	 * @param type the type
	 * @return the i card pack
	 */
	public static ICardPack createShuffled(G9 type) {
		List<B6> ranks; // = CardPackFactory.getRanks20();
		switch (type) {
		case G_10:
			ranks = CardPackFactory.getRanks20();
			break;
		case G_11:
			ranks = CardPackFactory.getRanks36();
			break;
		case G_12:
			ranks = CardPackFactory.getRanks52();
			break;
		default:
			throw new IllegalArgumentException("Unsupported card pack type "
					+ type);
		}
		ICardPack ret = CardPackFactory.getShuffled(ranks);
		return ret;
	}

	/**
	 * Returns all suits One-dimensional array of card suits.
	 *
	 * @return the suits
	 */
	protected static List<B1> getSuits() {
		List<B1> ret = new ArrayList<B1>();
		for (B1 s : B1.values()) {
			ret.add(s);
		}
		return ret;
	}

	/**
	 * Returns all values for a card pack of 52 cards.
	 *
	 * @return 1D-array of ranks
	 */
	private static List<B6> getRanks52() {
		List<B6> ret = new ArrayList<B6>();
		ret.add(B6.B_7);
		ret.add(B6.B_8);
		ret.add(B6.B_9);
		ret.add(B6.B_10);
		ret.add(B6.B_11);
		ret.add(B6.B_12);
		ret.add(B6.B_13);
		ret.add(B6.B_14);
		ret.add(B6.B_15);
		ret.add(B6.B_16);
		ret.add(B6.B_17);
		ret.add(B6.B_18);
		ret.add(B6.B_19);
		return ret;
	}

	/**
	 * Returns all card ranks for a pack of 36 cards.
	 *
	 * @return 1D-array of ranks
	 */
	private static List<B6> getRanks36() {
		List<B6> ret = new ArrayList<B6>();
		ret.add(B6.B_11);
		ret.add(B6.B_12);
		ret.add(B6.B_13);
		ret.add(B6.B_14);
		ret.add(B6.B_15);
		ret.add(B6.B_16);
		ret.add(B6.B_17);
		ret.add(B6.B_18);
		ret.add(B6.B_19);
		return ret;
	}

	/**
	 * Returns all card ranks for a pack of 36 cards.
	 *
	 * @return 1D-array of ranks
	 */
	private static List<B6> getRanks20() {
		List<B6> ret = new ArrayList<B6>();
		ret.add(B6.B_15);
		ret.add(B6.B_16);
		ret.add(B6.B_17);
		ret.add(B6.B_18);
		ret.add(B6.B_19);
		return ret;
	}

}
