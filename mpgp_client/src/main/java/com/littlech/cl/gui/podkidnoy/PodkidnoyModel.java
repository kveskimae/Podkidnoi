/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy;

import java.util.*;


import com.littlech.cl.Utils;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.cl.gui.podkidnoy.util.Self;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B23;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F10;
import com.littlech.gen.f.F14;
import com.littlech.gen.f.F17;
import com.littlech.gen.f.F6;
import com.littlech.gen.f.F9;
import com.littlech.gen.g.G1;

/**
 * 
 * Model for Podkidnoy GUI
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class PodkidnoyModel {

	/* Seats */

	/**
	 * Players on seats (including self)
	 */
	private Map<F1, ObservableValue<F9>> seatUsers;

	/**
	 * Seat states
	 */
	private Map<F1, ObservableValue<F14>> seatStates;

	/*General game state*/
	
	/**
	 * Game state (either playing or waiting)
	 */
	private ObservableValue<G1> state = new ObservableValue<G1>();

	/**
	 * Number of cards left in the card pack
	 */
	private ObservableValue<Integer> pack = new ObservableValue<Integer>();

	/**
	 * Trump card
	 */
	private ObservableValue<B20> trump = new ObservableValue<B20>();

	/**
	 * Table cards
	 */
	private ObservableValue<List<B23>> table = new ObservableValue<List<B23>>();

	/* Player-specific */

	/**
	 * Player cards
	 */
	private ObservableValue<List<B20>> cards = new ObservableValue<List<B20>>();

	/**
	 * Self user and allowed actions
	 */
	private final ObservableValue<Self> self = new ObservableValue<Self>();
	
	/* General on turn */

	/**
	 * Seat currently on turn in playing game state
	 */
	private ObservableValue<F1> onTurn = new ObservableValue<F1>();

	private ObservableValue<Boolean> isSitting = new ObservableValue<Boolean>();

	/* Self on turn */
	
	/**
	 * Maximum number of bottom cards that can be laid (for first and adding state)
	 */
	private ObservableValue<Integer> max = new ObservableValue<Integer>();

	public PodkidnoyModel(final F6 _selfUser) {
		Self selfValue = new Self(_selfUser);
		self.setValue(selfValue);
	}

	/**
	 * Returns user sitting on parameter seat
	 * @param arg Seat ID code
	 * @return User
	 */
	public ObservableValue<F9> getUser(final F1 arg) { // , final boolean checkSelf) {
		// if (checkSelf) {
		checkSelf(arg);
		// }
		F1 _id = getCode(arg);
		ObservableValue<F9> ret = getSeatUsers().get(_id);
		if (ret == null) {
			throw new IllegalArgumentException("Unsupported: " + _id);
		}
		return ret;
	}

	/**
	 * Returns all playing seat states, i.e. states for seats that have not finished the game yet;
	 * returned states do not contain self seat's state 
	 * @return Map of seat ID codes and corresponding playing seat state objects
	 */
	public Map<F1, F17> seatsInPlayingState() {
		List<F1> ids = new ArrayList<F1>(Arrays.asList(F1.values()));
		if (self.getValue() != null && self.getValue().getId() != null) {
			ids.remove(self.getValue().getId());
		}
		Map<F1, F17> ret = new HashMap<F1, F17>();
		for (F1 cur : ids) {
			ObservableValue<F14> curSeat = getSeat(cur);
			if (curSeat.getValue() != null && curSeat.getValue() instanceof F17) {
				ret.put(cur, (F17) curSeat.getValue());
			}
		}
		return ret;
	}

	/**
	 * Returns seat state for parameter seat
	 * @param arg Seat ID code
	 * @return Seat state
	 */
	public ObservableValue<F14> getSeat(final F1 arg) {
		checkSelf(arg);
		F1 _id = getCode(arg);
		ObservableValue<F14> ret = getSeatStates().get(_id);
		if (ret == null) {
			throw new IllegalArgumentException("Unsupported: " + _id);
		}
		return ret;
	}

	/**
	 * Checks if the parameter seat is self seat and throws IllegalArgumentException if it is
	 * @param _id Seat ID code
	 * @throws IllegalArgumentException Parameter seat is self seat
	 */
	private void checkSelf(F1 _id) {
		if (self.getValue() != null && self.getValue().getId() != null && self.getValue().getId().equals(_id)) {
			throw new IllegalArgumentException(); // "Asking user with self id");
		}
	}

	/**
	 * Returns self
	 * @return Self
	 */
	public ObservableValue<Self> getSelf() {
		return self;
	}
	
	public ObservableValue<Boolean> getIsSitting() {
		return isSitting;
	}

	/**
	 * Returns player cards
	 * @return Cards
	 */
	public ObservableValue<List<B20>> getCards() {
		return cards;
	}

	/**
	 * Returns number of cards left in pack
	 * @return Number of cards
	 */
	public ObservableValue<Integer> getPack() {
		return pack;
	}

	/**
	 * Returns trump card
	 * @return Card
	 */
	public ObservableValue<B20> getTrump() {
		return trump;
	}

	/**
	 * Returns table cards
	 * @return Table cards
	 */
	public ObservableValue<List<B23>> getTable() {
		return table;
	}

	/**
	 * Returns seat on turn
	 * @return Seat ID code
	 */
	public ObservableValue<F1> getOnTurn() {
		return onTurn;
	}

	/**
	 * Returns seat users sitting on seats  
	 * @return Map of seat ID codes and corresponding seat users
	 */
	private Map<F1, ObservableValue<F9>> getSeatUsers() {
		if (seatUsers == null) {
			seatUsers = new HashMap<F1, ObservableValue<F9>>();
			seatUsers.put(F1.F_2, new ObservableValue<F9>());
			seatUsers.put(F1.F_3, new ObservableValue<F9>());
			seatUsers.put(F1.F_4, new ObservableValue<F9>());
			seatUsers.put(F1.F_5, new ObservableValue<F9>());
		}
		return seatUsers;
	}

	/**
	 * Returns seat states
	 * @return Map of seat ID codes and corresponding seat states
	 */
	private Map<F1, ObservableValue<F14>> getSeatStates() {
		if (seatStates == null) {
			seatStates = new HashMap<F1, ObservableValue<F14>>();
			seatStates.put(F1.F_2, new ObservableValue<F14>());
			seatStates.put(F1.F_3, new ObservableValue<F14>());
			seatStates.put(F1.F_4, new ObservableValue<F14>());
			seatStates.put(F1.F_5, new ObservableValue<F14>());
		}
		return seatStates;
	}

	/**
	 * Maps seat ID code from server view to client view
	 * <p>
	 * In client, the leftmost user is always self user and other seats are rearranged accordingly 
	 * @param arg Seat ID code in server view
	 * @return Seat ID code in client view
	 */
	public F1 getCode(F1 arg) {
		if (getSelf().getValue() == null || getSelf().getValue().getId() == null) {
			return arg;
		}
		F1 selfID = getSelf().getValue().getId();
		int rankSelf = Utils.mapIDToRank(selfID);
		int rankArg = Utils.mapIDToRank(arg);
		if (rankArg > rankSelf) {
			int retRank = rankArg - rankSelf;
			return Utils.mapRankToSeatID(retRank);
		} else {
			int retRank = 4 + rankArg - rankSelf;
			return Utils.mapRankToSeatID(retRank);
		}
	}

	/**
	 * Returns self user
	 * @return Seat
	 */
	public ObservableValue<F9> getSelfUser() {
		F1 selfID = self.getValue().getId();
		if (selfID == null) {
			throw new NullPointerException(); // "Self id is null");
		}
		ObservableValue<F9> ret = getSeatUsers().get(F1.F_2);
		return ret;
	}

	/**
	 * Returns self seat
	 * @return Seat
	 */
	public ObservableValue<F14> getSelfSeat() {
		F1 selfID = self.getValue().getId();
		if (selfID == null) {
			throw new NullPointerException(); // "Self id is null");
		}
		ObservableValue<F14> ret = getSeatStates().get(F1.F_2); // _selfID);
		return ret;
	}

	/**
	 * Returns Podkidnoy game state code
	 * <p>
	 * {@link G1}
	 * @return State code
	 */
	public ObservableValue<G1> getState() {
		return state;
	}

	/**
	 * Returns the maximum number of bottom cards that player is allowed to lay on the table (in first and adding state or while sending)
	 * @return Maximum number
	 */
	public ObservableValue<Integer> getMax() {
		return max;
	}

	/**
	 * Resets (TODO Unimplemented)
	 */
	public void reset() {
	}

	public void updateSelf() {
		F10 hsu = new F10();
		hsu.setF11(getSelf().getValue().getUser());
		getSelfUser().setValue(hsu);
	}

}
