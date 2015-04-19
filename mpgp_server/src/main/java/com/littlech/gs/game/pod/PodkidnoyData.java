/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod;

import com.littlech.gen.b.B20;
import com.littlech.gen.b.B28;
import com.littlech.gen.g.G9;
import com.littlech.gs.cardpack.CardPackFactory;
import com.littlech.gs.cardpack.ICardPack;
import com.littlech.gs.game.IGameData;


// TODO: Auto-generated Javadoc
/**
 * The Class PodkidnoyData.
 */
public class PodkidnoyData implements IGameData {

	/* Card pack */
	/** The cp. */
	private ICardPack cp;

	/* The trump card */
	/** The trump card. */
	private B20 trumpCard = null;

	/* Table cards variable */
	/** The table cards. */
	private B28 tableCards = new B28();

	/*
	 * Consecutive rounds playing in the game. When maximum number from
	 * configuration is reached, the game is finished
	 */
	/** The rounds. */
	private int rounds = 0;

	/**
	 * Constructor.
	 *
	 */
	public PodkidnoyData() {
	}

	/**
	 * Resets the current game's rounds counter.
	 */
	private void resetRounds() {
		rounds = 0;
	}

	/**
	 * Increases the current game's rounds counter by one.
	 */
	public void incrementRounds() {
		rounds++;
	}

	/**
	 * Retrieves the number of rounds played in the current game.
	 *
	 * @return Number of rounds
	 */
	public int getRounds() {
		return rounds;
	}

	/**
	 * Get the current game card pack.
	 *
	 * @return Card pack
	 */
	public ICardPack getPack() {
		return cp;
	}

	/**
	 * Returns current table cards.
	 *
	 * @return Table cards
	 */
	public B28 getTableCards() {
		return tableCards;
	}

	/**
	 * Sets the trump card for current game data.
	 *
	 * @param trumpCard Trump card
	 */
	public void setTrumpCard(B20 trumpCard) {
		this.trumpCard = trumpCard;
	}

	/**
	 * Retrieves the trump card.
	 *
	 * @return Trump card
	 */
	public B20 getTrumpCard() {
		return trumpCard;
	}

	/**
	 * Shuffles the card pack.
	 *
	 * @param _packType the _pack type
	 */
	public void reset(final G9 _packType) {
		cp = CardPackFactory.createShuffled(_packType);
		setTrumpCard(cp.peekLast());
		resetRounds();
		getTableCards().getB29().clear();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Trump = " + getTrumpCard() + "\n");
		sb.append("Table = " + getTableCards() + "\n");
		sb.append("Pack = " + getPack() + "\n");
		sb.append("Rounds = " + getRounds() + "\n");
		return sb.toString();
	}

}