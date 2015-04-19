/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod;

import com.littlech.gen.g.G21;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerPodConfigType.
 */
public class ServerPodConfigType extends G21 {
	
    /** The first policy. */
    private FIRST_POLICY firstPolicy = FIRST_POLICY.RANDOM;
  
    /** The num of player cards. */
    private int maxRounds = 50, numOfTableCards = 6, numOfPlayerCards = 6;
    
    
    

	/**
	 * Gets the max rounds.
	 *
	 * @return the max rounds
	 */
	public int getMaxRounds() {
		return maxRounds;
	}

	/**
	 * Sets the max rounds.
	 *
	 * @param maxRounds the new max rounds
	 */
	public void setMaxRounds(int maxRounds) {
		this.maxRounds = maxRounds;
	}

	/**
	 * Gets the num of table cards.
	 *
	 * @return the num of table cards
	 */
	public int getNumOfTableCards() {
		return numOfTableCards;
	}

	/**
	 * Sets the num of table cards.
	 *
	 * @param numOfTableCards the new num of table cards
	 */
	public void setNumOfTableCards(int numOfTableCards) {
		this.numOfTableCards = numOfTableCards;
	}

	/**
	 * Gets the num of player cards.
	 *
	 * @return the num of player cards
	 */
	public int getNumOfPlayerCards() {
		return numOfPlayerCards;
	}

	/**
	 * Sets the num of player cards.
	 *
	 * @param numOfPlayerCards the new num of player cards
	 */
	public void setNumOfPlayerCards(int numOfPlayerCards) {
		this.numOfPlayerCards = numOfPlayerCards;
	}

	/**
	 * Gets the first policy.
	 *
	 * @return the first policy
	 */
	public FIRST_POLICY getFirstPolicy() {
		return firstPolicy;
	}

	/**
	 * Sets the first policy.
	 *
	 * @param firstPolicy the new first policy
	 */
	public void setFirstPolicy(FIRST_POLICY firstPolicy) {
		this.firstPolicy = firstPolicy;
	}
	
	

}
