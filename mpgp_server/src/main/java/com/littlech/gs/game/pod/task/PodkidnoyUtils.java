/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.littlech.cl.Utils;
import com.littlech.gen.b.*;


// TODO: Auto-generated Javadoc
/**
 * Durak game utils.
 *
 * @author Kristjan Veskim�e
 */
public class PodkidnoyUtils {

	/**
	 * Calculates cards that can be used to send defending the table forward.
	 *
	 * @param nextToDefenderCards Cards of the player seated next to defender
	 * @param table Table cards
	 * @param defenderCards Defender cards
	 * @return Cards that can be used to send the table forward, null if sending
	 * is impossible
	 */
	public static B26 calcSendForwardCards(
			B26 nextToDefenderCards, B28 table,
			B26 defenderCards) {

		return PodkidnoyUtils.calcSendForwardCards1(nextToDefenderCards.getB27().size(),
				table, defenderCards);
	}

	/**
	 * Calculates cards that can be used to send the table forward.
	 *
	 * @param numOfNextToDefenderCards Number of cards that player seated next to the defender
	 * possesses
	 * @param table Table cards
	 * @param playerCards Defender cards
	 * @return Cards that can be used to send the table forward, null if sending
	 * is impossible
	 */
	public static B26 calcSendForwardCards1(
			int numOfNextToDefenderCards, B28 table,
			B26 playerCards) {

		// Checking if the player that seats next to defender has enough cards
		// for sending
		int nextCards = numOfNextToDefenderCards;
		int onTable = table.getB29().size();
		if (onTable + 1 > nextCards) {
			return null;
		}

		// Creating list of possible send cards
		List<B20> cardsOptions = playerCards.getB27();
		if (cardsOptions.size() < 1) {
			throw new IllegalStateException("Defender did not have any cards");
		}
		B6 rank = table.getB29().get(0).getB25().getB22();
		List<B20> sameValueCards = new ArrayList<B20>();
		Iterator<B20> it = cardsOptions.iterator();
		while (it.hasNext()) {
			B20 option = it.next();
			if (option.getB22().equals(rank)) {
				sameValueCards.add(option);
			}
		}

		// checking if defender has at least one card that can be used for
		// sending
		if (sameValueCards.size() > 0) {
			B26 ret = new B26();
			Iterator<B20> itSVC = sameValueCards.iterator();
			do {
				B20 sendCard = itSVC.next();
				ret.getB27().add(sendCard);
			} while (itSVC.hasNext() && ret.getB27().size() + onTable < nextCards);
			return ret;
		}
		return null;
	}

	/**
	 * Calculates cards that can be used to send the table forward.
	 *
	 * @param max Maximum number of cards that can be added to table cards when
	 * sending
	 * @param table Table cards
	 * @param playerCards Defender cards
	 * @return Cards that can be used to send the table forward, null if sending
	 * is impossible
	 */
	public static B26 calcSendForwardCards(int max, B28 table,
			B26 playerCards) {

		// Checking if the player that seats next to defender has enough cards
		// for sending
		if (max < 1) {
			return null;
		}

		// Creating list of possible send cards
		List<B20> cardsOptions = playerCards.getB27();
		if (cardsOptions.size() < 1) {
			throw new IllegalStateException("Defender was empty-handed");
		}
		B6 rank = table.getB29().get(0).getB25().getB22();
		List<B20> sameValueCards = new ArrayList<B20>();
		Iterator<B20> it = cardsOptions.iterator();
		while (it.hasNext()) {
			B20 option = it.next();
			if (option.getB22().equals(rank)) {
				sameValueCards.add(option);
			}
		}

		// checking if defender has at least one card that can be used for
		// sending
		if (sameValueCards.size() > 0) {
			B26 ret = new B26();
			Iterator<B20> itSVC = sameValueCards.iterator();
			do {
				B20 sendCard = itSVC.next();
				ret.getB27().add(sendCard);
			} while (itSVC.hasNext() && ret.getB27().size() < max);
			return ret;
		}
		return null;
	}

	/**
	 * Calculates cards that can be added to the table.
	 *
	 * @param table Table cards
	 * @param defenderCards Defender's cards
	 * @param attackerCards Current attacker's cards
	 * @return Cards that can be added to the table, null if no cards can be
	 * added
	 */
	public static B26 calcAddCards(B28 table,
			B26 defenderCards, B26 attackerCards) {

		// defender must have more cards than bottom cards on table to be able
		// to add
		int numOfDefenderCards = defenderCards.getB27().size()
				+ 
				
				Utils.getDefendingCards(table.getB29()).size()
				// table.getDefendingCards().size() XXX
				;
		int onTable = 
			// table.keySet().size() XXX
			table.getB29().size()
			;
		if (onTable >= numOfDefenderCards) {
			return null;
		}

		// creating list of attacker cards that can be added
		List<B20> cardsOptions = attackerCards.getB27();
		if (cardsOptions.size() < 1) {
			return null;
		}
		List<B6> tableValues = 
			// table.getRanks() XXX
			Utils.getRanksList(table.getB29())
			;
		List<B20> sameValueCards = new ArrayList<B20>();
		Iterator<B20> it = cardsOptions.iterator();
		while (it.hasNext()) {
			B20 option = it.next();
			if (tableValues.contains(option.getB22())) {
				sameValueCards.add(option);
			}
		}

		if (sameValueCards.size() > 0) {
			B26 ret = new B26();
			Iterator<B20> itSVC = sameValueCards.iterator();

			// returning as many cards as can be added
			do {
				B20 addCard = itSVC.next();
				ret.getB27().add(addCard);
			} while (itSVC.hasNext()
					&& ret.getB27().size() + onTable < numOfDefenderCards);
			return ret;
		}
		return null;
	}

	/**
	 * Calculates cards that can be added to the table.
	 *
	 * @param table Table cards
	 * @param maxAdd Maximum number of cards that can be added
	 * @param playerCards Current attacker's cards
	 * @return Cards that can be added to the table, null if no cards can be
	 * added
	 */
	public static B26 calcAddCards(B28 table, int maxAdd,
			B26 playerCards) {

		// creating list of attacker cards that can be added
		List<B20> cardsOptions = playerCards.getB27();
		if (cardsOptions.size() < 1) {
			return null;
		}
		List<B6> tableValues = 
			// table.getRanks() XXX
			
			Utils.getRanksList(table.getB29())
			;
		List<B20> sameValueCards = new ArrayList<B20>();
		Iterator<B20> it = cardsOptions.iterator();
		while (it.hasNext()) {
			B20 option = it.next();
			if (tableValues.contains(option.getB22())) {
				sameValueCards.add(option);
			}
		}

		if (sameValueCards.size() > 0) {
			B26 ret = new B26();
			Iterator<B20> itSVC = sameValueCards.iterator();

			// returning as many cards as can be added
			do {
				B20 addCard = itSVC.next();
				ret.getB27().add(addCard);
			} while (itSVC.hasNext() && ret.getB27().size() < maxAdd);
			return ret;
		}
		return null;
	}

	/**
	 * Calculates defended table.
	 *
	 * @param killers Possible defending cards
	 * @param table Table cards
	 * @param trump Trump card
	 * @return Defended table if the table can be defended, null otherwise
	 */
	public static B28 calcDefendedTable(B26 killers,
			B28 table, B20 trump) {
		List<B20> bottomCards = 
			Utils.getBottomCards(table.getB29())
			// table.getBottoms() XXX
			;
		B28 defendedTable = new B28();
		B28 ret = calcDefendedTableRecursively(killers.getB27(),
				bottomCards, defendedTable, trump);
		return ret;
	}

	/**
	 * Accompanying method for calculating the defended table.
	 *
	 * @param killers Possible defending cards
	 * @param bottomCards Undefended bottom cards
	 * @param defendedTable Helping variable
	 * @param trump Trump card
	 * @return Defended table if the table can be defended, null otherwise
	 */
	private static B28 calcDefendedTableRecursively(List<B20> killers,
			List<B20> bottomCards, B28 defendedTable, B20 trump) {
		Iterator<B20> itBottom = bottomCards.iterator();
		while (itBottom.hasNext()) {
			B20 cardBottom = itBottom.next();
			Iterator<B20> itKillers = killers.iterator();
			while (itKillers.hasNext()) {
				B20 cardKiller = itKillers.next();
				if (
						Utils.kills(trump, cardKiller, cardBottom)
						// Killer.kills(cardKiller, cardBottom, trump) XXX
						
				) {
					List<B20> tmpKillers = new ArrayList<B20>();
					tmpKillers.addAll(killers);
					ArrayList<B20> tmpBottomCards = new ArrayList<B20>();
					tmpBottomCards.addAll(bottomCards);
					
					B28 tmpDefendedTable = new B28();
						tmpDefendedTable.getB29().addAll(Utils.getCopyOfTable(defendedTable.getB29()))
						// defendedTable.getCopy() XXX
						;
						
					tmpKillers.remove(cardKiller);
					tmpBottomCards.remove(cardBottom);
					
					// tmpDefendedTable.put(cardBottom, cardKiller); XXX
					B23 p = new B23();
					p.setB25(cardBottom);
					p.setB24(cardKiller);
					tmpDefendedTable.getB29().add(p);
					
					if (tmpBottomCards.isEmpty()) {
						return tmpDefendedTable;
					} else {
						B28 recursiveResult = calcDefendedTableRecursively(
								tmpKillers, tmpBottomCards, tmpDefendedTable,
								trump);
						if (recursiveResult != null) {
							return recursiveResult;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Calculates first cards that can be laid.
	 *
	 * @param playerCards Player cards
	 * @param max Maximum number of bottom cards that can be laid
	 * @return First cards
	 */
	public static B26 calcFirstCards(B26 playerCards, int max) {
		List<B20> possibilities = playerCards.getB27();
		List<B20> tmpMax = new ArrayList<B20>();
		for (B20 c : possibilities) {
			B6 rank = c.getB22();
			List<B20> tmp = new ArrayList<B20>();
			tmp.add(c);
			for (B20 d : possibilities) {
				if (d.getB22().equals(rank) && !c.equals(d)) {
					tmp.add(d);
				}
			}
			if (tmp.size() > tmpMax.size()) {
				tmpMax = tmp;
			}
		}
		B26 ret = new B26();
		Iterator<B20> it = tmpMax.iterator();
		while (it.hasNext() && ret.getB27().size() < max) {
			ret.getB27().add(it.next());
		}
		return ret;
	}

}
