/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.TreeMap;

import com.littlech.gen.e.E19;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F23;
import com.littlech.gen.f.F6;
import com.littlech.gs.game.IPlayerStore;
import com.littlech.gs.gate.Gateway;
import com.littlech.gs.user.ServerUser;
import static com.littlech.gs.gate.Gateway.*;
// TODO: Auto-generated Javadoc
/**
 * The Class PodkidnoyPlayerStore.
 */
public class PodkidnoyPlayerStore implements IPlayerStore {

	/** The viewers. */
	private final List<ServerUser> viewers = new ArrayList<ServerUser>();

	/** The seats. */
	private final TreeMap<F1, PodkidnoySeat> seats = new TreeMap<F1, PodkidnoySeat>();

	/* Random numbers generator */
	/** The random. */
	private static Random random = new Random();

	/* All players in store */
	// protected final LinkedList<ServerSeat> seats = new
	// LinkedList<PodkidnoySeat>();

	/* Winners of current game */
	/** The winners all. */
	private LinkedList<PodkidnoySeat> winnersAll = new LinkedList<PodkidnoySeat>();

	/* Players of current game round */
	/** The players round. */
	private LinkedList<PodkidnoySeat> playersRound = new LinkedList<PodkidnoySeat>();

	/* Winners of current game round */
	/** The winners round. */
	private LinkedList<PodkidnoySeat> winnersRound = new LinkedList<PodkidnoySeat>();

	/* Players still playing in current game */
	/** The players current. */
	private LinkedList<PodkidnoySeat> playersCurrent = new LinkedList<PodkidnoySeat>();

	/* First attacker in current game */
	/** The first. */
	private PodkidnoySeat first;

	/* Beginner in the last game */
	/** The last game first. */
	private PodkidnoySeat lastGameFirst;

	/* Winner of the last game */
	/** The last game winner. */
	private PodkidnoySeat lastGameWinner;

	/* Player currently on move */
	/** The actor. */
	private PodkidnoySeat actor;

	/** The game. */
	private final Podkidnoy game;

	public Podkidnoy getGame() {
		return game;
	}

	/* Format of the prefix used in printing the player store */
	/** The format. */
	private static String format = "%25s";

	/**
	 * Instantiates a new podkidnoy player store.
	 *
	 * @param _players the _players
	 * @param _game the _game
	 */
	public PodkidnoyPlayerStore(final int _players, final Podkidnoy _game) {
		game = _game;
		if (_players < 1 || _players > 4) {
			throw new IllegalArgumentException("Illegal number of players: "
					+ _players);
		}
		for (int i = 0; i < _players; i++) {
			F1 curID = F1.values()[i];
			PodkidnoySeat curSeat = new PodkidnoySeat(curID, _game);
			seats.put(curID, curSeat);
			curSeat.addObserver(_game);
		}
	}

	/**
	 * Gets the viewers.
	 *
	 * @return the viewers
	 */
	public List<ServerUser> getViewers() {
		return viewers;
	}

	/* (non-Javadoc)
	 * @see server.IPlayerStore#remove(server.ServerUser)
	 */
	@Override
	public void remove(ServerUser user) {
		/*
		System.out.println("Trying to remove " + user + " from game " +  game.getId());
		System.out.println("trhead: " + Thread.currentThread().getStackTrace()[3]);
		*/
		if (getViewers().contains(user)) {
			/* User is a viewer */
			getViewers().remove(user);
			return;
		}
		boolean found = false;
		forSeats : for (PodkidnoySeat ps : getAllSeats()) {
			/* User is seated as a player */
			if (!ps.isEmpty()) {
				ServerUser pssu = ps.getServerUser();
				if (pssu == null) {
					throw new NullPointerException(
							"There was no player sitting on a non-empty seat");
				}
				if (pssu.equals(user)) {
					ps.removeUser();
					found = true;
					break forSeats;
					// return;
				}
			}
		}
		if (!found) {

			throw new IllegalArgumentException("User " + user
					+ " was not found in player store " + this);
		}
		// XXX check if last human player & end game if so!!!
		boolean allEmpty = true;
		for (PodkidnoySeat ps : getAllSeats()) {
			if (!ps.isEmpty()) {
				allEmpty = false;
			}
		}
		if (allEmpty) {
			getGame().reset();
		}
	}

	/* (non-Javadoc)
	 * @see server.IPlayerStore#add(server.ServerUser)
	 */
	@Override
	public void add(ServerUser user) {
		if (logGame.isDebugEnabled()) {
		logGame.debug("Adding " + user + " to viewers in game " + game.getId());
		}
		getViewers().add(user);
	}

	/* (non-Javadoc)
	 * @see server.IPlayerStore#sit(server.ServerUser, com.littlech.gen.f.F1)
	 */
	@Override
	public void sit(ServerUser user, F1 code) {
		PodkidnoySeat cur = seats.get(code);
		cur.sitUser(user);
		getViewers().remove(user);
	}

	/* (non-Javadoc)
	 * @see server.IPlayerStore#addCreator(server.ServerUser)
	 */
	@Override
	public void addCreator(ServerUser u) {
seats.get(F1.F_2).sitUser(u);
	}

	/* (non-Javadoc)
	 * @see server.IPlayerStore#writeToAll(com.littlech.gen.e.E19)
	 */
	@Override
	public void writeToAll(E19 command) {
		List<ServerUser> failedToWrite = new ArrayList<ServerUser>();
		for (ServerUser su : getViewers()) {
			try {
				su.write(command);
			} catch (Exception e) {
				failedToWrite.add(su);
				logCommunication.error("Could not write to " + su, e);
			}
		}
		for (PodkidnoySeat ps : getAllSeats()) {
			ps.writeCommand(command);
		}
	}

	/* (non-Javadoc)
	 * @see server.IPlayerStore#getSeatIDByUser(server.ServerUser)
	 */
	@Override
	public F1 getSeatIDByUser(ServerUser user) {
		for (PodkidnoySeat ps : getAllSeats()) {
			if (!ps.isEmpty()) {
				ServerUser pssu = ps.getServerUser();
				if (pssu == null) {
					throw new NullPointerException(
							"There was no player sitting on a non-empty seat");
				}
				if (pssu.equals(user)) {
					return ps.getID();
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see server.IPlayerStore#getLobbySeats()
	 */
	@Override
	public List<F23> getLobbySeats() {
		Iterator<PodkidnoySeat> it = getSeats().iterator();
		List<F23> ret = new ArrayList<F23>();
		while (it.hasNext()) {
			PodkidnoySeat cur = it.next();
			F23 lobbySeat = new F23();
			ret.add(lobbySeat);
			lobbySeat.setF24(cur.getID());
			if (!cur.isEmpty()) {
				F6 curUserUser = cur.getServerUser().getF11();
				lobbySeat.setF25(curUserUser);
			}
		}
		return ret;
	}

	/**
	 * Gets the index in all.
	 *
	 * @param oldPlayer the old player
	 * @return the index in all
	 */
	private int getIndexInAll(ServerUser oldPlayer) {
		List<PodkidnoySeat> seatsList = getSeats();
		Iterator<PodkidnoySeat> it = seatsList.iterator();
		while (it.hasNext()) {
			PodkidnoySeat cur = it.next();
			if (cur.getServerUser() != null) {
				if (cur.getServerUser().equals(oldPlayer)) {
					return seatsList.indexOf(cur);
				}
			}
		}
		throw new IllegalArgumentException("Player not found " + oldPlayer);
	}

	/**
	 * Call when finishing the Durak game.
	 */
	public void endGame() {
		Iterator<PodkidnoySeat> it = winnersRound.iterator();
		while (it.hasNext()) {
			winnersAll.add(it.next());
		}
	}

	/**
	 * Call when ending current Durak game round.
	 *
	 * @param turnToNext Boolean indicating if the first attacker in the next round is
	 * finishing round's defender (false) or next to defender (true)
	 */
	public void endRound(boolean turnToNext) {
		// putEmptyHandedToTmp(); // is it necessary?
		if (playersCurrent.size() >= 2) {
			first = (turnToNext) ? getNextToDefender() : getDefender();
		}
		Iterator<PodkidnoySeat> it = winnersRound.iterator();
		while (it.hasNext()) {
			PodkidnoySeat cur = it.next();
			// XXX
			if (cur.getNumOfCards() < 1) {
				winnersAll.add(cur);
				playersCurrent.remove(cur);
			} /*
			 * else { playersRound.add(cur); }
			 */
		}
		playersRound.clear();
		winnersRound.clear();
		playersRound.addAll(playersCurrent);

		if (playersCurrent.size() >= 2) {
			turnToFirstAttacker();
		}
	}

	/**
	 * Put empty-handed players to current game round's winners.
	 */
	public void putEmptyHandedToTmp() {
		PodkidnoySeat emptyHanded = null;
		for (PodkidnoySeat player : playersCurrent) {
			if (player.getCards().size() < 1) {
				if (emptyHanded == null) {
					emptyHanded = player;
				} else {
					throw new IllegalStateException(
							"More than one player was empty-handed");
				}
			}
		}
		if (emptyHanded != null) {
			winnersRound.add(emptyHanded);
			playersCurrent.remove(emptyHanded);
			Gateway.logGame.info("Adding to winners " + emptyHanded);
		}
	}

	/**
	 * Get player on move.
	 *
	 * @return Player
	 */
	public PodkidnoySeat getActor() {
		if (actor == null) {
			throw new IllegalStateException("Asking actor when actor is null");
		} else if (!playersCurrent.contains(actor)) {
			throw new IllegalStateException("Current players " + playersCurrent
					+ " do not contain current actor " + actor);
		}
		return actor;
	}

	/**
	 * Get player on move.
	 *
	 * @param mustContain the must contain
	 * @return Player
	 */
	public PodkidnoySeat getActor(boolean mustContain) {
		if (actor == null) {
			throw new IllegalStateException("Asking actor when actor is null");
		} else if (mustContain && !playersCurrent.contains(actor)) {
			throw new IllegalStateException("Current players " + playersCurrent
					+ " do not contain current actor " + actor);
		}
		return actor;
	}

	/**
	 * Check if the is on move.
	 *
	 * @param _id the _id
	 * @return True if the player is currently on move, false otherwise
	 */
	/*
	 * public boolean isPlayersTurn(ServerUser player) { return (getActor() !=
	 * null && getActor().getUser().equals(player)); }
	 */

	/**
	 * Check if the is on move
	 * 
	 * @param player
	 *            Player
	 * @return True if the player is currently on move, false otherwise
	 */
	public boolean isSeatsTurn(final F1 _id) {
		PodkidnoySeat curActor = getActor();
		if (curActor == null) {
			throw new NullPointerException("Currently there is no actor");
		}
		F1 curID = curActor.getID();
		boolean ret = _id.equals(curID);
		return ret;
	}

	/**
	 * Gives move to the next attacker.
	 */
	public void increaseAttacker() {
		// XXX
		PodkidnoySeat newActor = getNext(getActor(false), true);
		if (newActor.equals(first)) {
			throw new IllegalStateException("New attacker " + newActor
					+ " is first attacker");
		} else if (newActor.equals(getActor(false))) {
			throw new IllegalStateException("New attacker " + newActor
					+ " is previous attacker");
		} else if (newActor.equals(getDefender())) {
			throw new IllegalStateException("New attacker " + newActor
					+ " is defender");
		}
		actor = newActor;
	}

	/**
	 * Gives turn to the current round's defender.
	 */
	public void turnToDefender() {
		PodkidnoySeat defender = getDefender();
		if (!playersCurrent.contains(defender)) {
			throw new IllegalStateException(
					"Current players do not contain any longer defender "
							+ defender);
		}
		actor = defender;
	}

	/**
	 * Gives turn to the current round's first attacker (or the next
	 * non-defending player if the first attacker has no cards).
	 */
	public void turnToFirstAttacker() {
		int size = playersCurrent.size();
		if (size < 2) {
			throw new IllegalStateException(
					"Cannot give turn to first when there are left only "
							+ size);
		}
		if (playersCurrent.contains(first)) {
			actor = first;
		} else {
			actor = getNext(first, true);
		}
	}

	/**
	 * Get all the winners in the game.
	 *
	 * @return All winners
	 */
	public LinkedList<PodkidnoySeat> getWinnersAll() {
		return winnersAll;
	}

	/**
	 * Get the last attacker in current game round.
	 *
	 * @return Last attacker
	 */
	public PodkidnoySeat getLastAttacker() {
		return getPrevious(first, true);
	}

	/**
	 * Get the first attacker in current game round.
	 *
	 * @return First attacker
	 */
	public PodkidnoySeat getFirst() {
		if (playersCurrent.contains(first)) {
			return first;
		} else {
			return getNext(first, true);
		}
	}

	/**
	 * Get the defender in current game round.
	 *
	 * @return Defender
	 */
	public PodkidnoySeat getDefender() {
		return
		// getDefender(true);
		getDefenderWithCards(true);
	}

	/**
	 * Get the player that is seated next after the defender.
	 *
	 * @return Player next to defender
	 */
	public PodkidnoySeat getNextToDefender() {
		int size = playersRound.size();
		if (size < 2) {
			throw new IllegalStateException(
					"Asking next to defender when left " + size);
		}
		return getNext(getDefender(false), true);
	}

	/**
	 * Turn to defender for send.
	 */
	public void turnToDefenderForSend() {
		PodkidnoySeat defender = getDefenderWithCards(true);
		if (!playersCurrent.contains(defender)) {
			throw new IllegalStateException(
					"Current players do not contain any longer defender "
							+ defender);
		}
		actor = defender;
	}

	/**
	 * Gets the defender with cards.
	 *
	 * @param currentMustContain the current must contain
	 * @return the defender with cards
	 */
	public PodkidnoySeat getDefenderWithCards(boolean currentMustContain) {
		int size = playersCurrent.size();
		if (size < 2) {
			throw new IllegalStateException("Asking defender when left " + size);
		}

		int firstIdx = playersCurrent.indexOf(first);

		if (firstIdx < 0) {
			// FSeatnners
			ListIterator<PodkidnoySeat> it = playersRound
					.listIterator(playersRound.indexOf(first));
			it.next();
			PodkidnoySeat potentialDefender = null;

			whileIt: while (it.hasNext()) {
				PodkidnoySeat smoke = it.next();
				if (playersCurrent.contains(smoke)) {
					potentialDefender = smoke;
					break whileIt;
				}
			}
			if (potentialDefender == null) {
				// Defender with cards was not found from current players list
				// after
				// first
				potentialDefender = playersCurrent.get(0);
			}

			if (!currentMustContain
					|| playersCurrent.contains(potentialDefender)) {
				return potentialDefender;
			}
			throw new IllegalStateException(
					"Current players do not contain defender "
							+ potentialDefender + " any more");

		} else {

			ListIterator<PodkidnoySeat> it = playersCurrent
					.listIterator(firstIdx);
			it.next();
			PodkidnoySeat potentialDefender;
			if (it.hasNext()) {
				potentialDefender = it.next();
			} else {
				potentialDefender = playersCurrent.getFirst();
			}
			if (!currentMustContain
					|| playersCurrent.contains(potentialDefender)) {
				return potentialDefender;
			}
			throw new IllegalStateException(
					"Current players do not contain defender "
							+ potentialDefender + " any more");
		}
	}

	/**
	 * Get the defender.
	 *
	 * @param currentMustContain Boolean indicating if the current players list must contain
	 * the defender
	 * @return Defender
	 */
	public PodkidnoySeat getDefender(boolean currentMustContain) {
		int size = playersRound.size();
		if (size < 2) {
			throw new IllegalStateException("Asking defender when left " + size);
		}
		ListIterator<PodkidnoySeat> it = playersRound.listIterator(playersRound
				.indexOf(first));
		it.next();
		PodkidnoySeat potentialDefender;
		if (it.hasNext()) {
			potentialDefender = it.next();
		} else {
			potentialDefender = playersRound.getFirst();
		}
		if (!currentMustContain || playersCurrent.contains(potentialDefender)) {
			return potentialDefender;
		}
		throw new IllegalStateException(
				"Current players do not contain defender " + potentialDefender
						+ " any more");
	}

	/**
	 * Gives first attack to random player. To be called before a new game
	 * starts
	 */
	public void firstToRandom() {
		first = playersRound.get(random.nextInt(playersRound.size()));
	}

	/**
	 * Gives first attack to the winner of the last game. To be called before a
	 * new game starts
	 */
	public void firstToLastWinner() {
		if (lastGameWinner != null) {
			first = lastGameWinner;
		} else {
			firstToRandom();
		}
	}

	/**
	 * Gives first attack to the player seated next to the last game's very
	 * first attacker. To be called before a new game starts
	 */
	public void firstToRound() {
		throw new UnsupportedOperationException();
		/*
		 * if (lastGameFirst != null) { // XXX Must be chosen from all! first =
		 * getNext(lastGameFirst, false); } else { firstToRandom(); }
		 * lastGameFirst = first;
		 */
	}

	/**
	 * Gives first attack to the currently defending player. Use after sending
	 * forward
	 * 
	 * @param mustBeInCurrent
	 *            Boolean indicating if the currently defending player must be
	 *            in current players list
	 */
	public void firstToDefender(boolean mustBeInCurrent) {
		PodkidnoySeat defender = getDefender(false);
		if (mustBeInCurrent && !playersCurrent.contains(defender)) {
			throw new IllegalStateException(
					"Current players do not contain defender " + defender);
		}
		first = defender;
	}

	/**
	 * Gives first attack to the player seated next to currently defending
	 * player. Use e.g. after the defender has picked up
	 */
	public void firstToNextToDefender() {
		first = getNextToDefender();
	}

	/**
	 * Get the player seated next to the parameter player.
	 *
	 * @param player Player after whom the returned player seats
	 * @param skipDefender if true, defender will not be returned
	 * @return Next player
	 */
	private PodkidnoySeat getNext(PodkidnoySeat player, boolean skipDefender) {
		int size = playersCurrent.size();
		if (size < 2) {
			throw new IllegalStateException("Asking next to " + player
					+ " when current players list has only " + size);
		}
		// System.out.println("-0- player=" + player + ", skip=" +
		// skipDefender);
		if (playersCurrent.contains(player)) {
			// System.out.println("-1-");
			ListIterator<PodkidnoySeat> it = playersCurrent
					.listIterator(playersCurrent.indexOf(player));
			it.next();
			PodkidnoySeat potentialDefender;
			if (it.hasNext()) {
				// System.out.println("-3-");
				potentialDefender = it.next();
			} else {
				// System.out.println("-4-");
				potentialDefender = playersCurrent.getFirst();
			}
			if (skipDefender && potentialDefender.equals(getDefender(false))) {
				return getNext(potentialDefender, skipDefender);
			} else {
				return potentialDefender;
			}
		} else if (playersRound.contains(player)) {
			// System.out.println("-a-");

			ListIterator<PodkidnoySeat> it = playersRound
					.listIterator(playersRound.indexOf(player));
			it.next();
			while (it.hasNext()) {
				PodkidnoySeat cur = it.next();
				// System.out.println("-b- " + cur);
				if (playersCurrent.contains(cur)) {
					boolean notDefender = !cur.equals(getDefender(false));
					if (!skipDefender || notDefender) {
						// System.out.println("-c- " + cur + ", skip=" +
						// skipDefender +
						// ", not defender=" + notDefender);
						return cur;
					}
				}
			}

			PodkidnoySeat playersRoundFirstElement = playersRound.getFirst();
			// System.out.println("-d-");

			if (playersCurrent.contains(playersRoundFirstElement)) {
				boolean notDefender = !playersRoundFirstElement
						.equals(getDefender(false));
				if (!skipDefender || notDefender) {
					// System.out.println("-e- " + playersRoundFirstElement +
					// ", skip=" +
					// skipDefender + ", not defender=" + notDefender);
					return playersRoundFirstElement;
				}
			}

			ListIterator<PodkidnoySeat> itFirst = playersRound
					.listIterator(playersRound
							.indexOf(playersRoundFirstElement));
			itFirst.next();
			while (itFirst.hasNext()) {
				PodkidnoySeat cur = itFirst.next();
				// System.out.println("-f- " + cur);
				if (playersCurrent.contains(cur)) {
					boolean notDefender = !cur.equals(getDefender(false));
					if (!skipDefender || notDefender) {
						// System.out.println("-g- " + cur + ", skip=" +
						// skipDefender +
						// ", not defender=" + notDefender);
						return cur;
					}
				}
			}

			/*
			 * if (playersCurrent.contains(playersRoundFirstElement)) {
			 * System.out.println("-h- " + playersRoundFirstElement); return
			 * playersRoundFirstElement; }
			 */

			throw new IllegalStateException("Unable to find next to " + player);
		}
		throw new IllegalStateException(
				"Asking next when neither current players nor rounds players list contains "
						+ player);
	}

	/**
	 * Retrieves the player that seats before the parameter player.
	 *
	 * @param player Player seating after the player to be returned
	 * @param skipDefender If true, the defender will be skipped and not be returned
	 * @return Previous player
	 */
	private PodkidnoySeat getPrevious(PodkidnoySeat player, boolean skipDefender) {
		int size = playersCurrent.size();
		if (size < 2) {
			throw new IllegalStateException("Asking previous to " + player
					+ " when current players list has only " + size);
		}
		if (playersCurrent.contains(player)) {
			ListIterator<PodkidnoySeat> it = playersCurrent
					.listIterator(playersCurrent.indexOf(player));
			PodkidnoySeat potentialDefender;
			if (it.hasPrevious()) {
				potentialDefender = it.previous();
			} else {
				potentialDefender = playersCurrent.getLast();
			}
			if (skipDefender && potentialDefender.equals(getDefender(false))) {
				return getPrevious(potentialDefender, skipDefender);
			} else {
				return potentialDefender;
			}
		} else if (playersRound.contains(player)) {
			ListIterator<PodkidnoySeat> it = playersRound
					.listIterator(playersRound.indexOf(player));
			while (it.hasPrevious()) {
				PodkidnoySeat cur = it.previous();
				if (playersCurrent.contains(cur)) {
					if (skipDefender || !cur.equals(getDefender(false))) {
						return cur;
					}
				}
			}
			PodkidnoySeat playersRoundLastElement = playersRound.getLast();
			if (playersCurrent.contains(playersRoundLastElement)) {
				return playersRoundLastElement;
			}

			throw new IllegalStateException("Unable to find next to " + player);
		}
		throw new IllegalStateException(
				"Asking previous when neither current players nor rounds players list contains "
						+ player);
	}

	/**
	 * Resets the player store. (Clears players and winners lists.)
	 */
	public void reset() {
		if (winnersAll.size() > 0) {
			lastGameWinner = winnersAll.get(0);
		}
		winnersAll.clear();
		playersRound.clear();
		playersRound.addAll(getAllSeats());
		winnersRound.clear();
		playersCurrent.clear();
		playersCurrent.addAll(getAllSeats());
		for (PodkidnoySeat player : getAllSeats()) {
			player.getCards().clear();
			player.setNewGameStart(false);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String prefixPlayersAll = String.format(format, "All players = ");
		String prefixPlayersRound = String.format(format, "Round players = ");
		String prefixPlayersCurrent = String.format(format,
				"Current players = ");
		String prefixWinnersAll = String.format(format, "All winners = ");
		String prefixWinnersRound = String.format(format, "Round winners = ");
		String prefixViewers = String.format(format, "Viewers = ");
		sb.append(prefixPlayersAll + getAllSeats() + "\n");
		sb.append(prefixWinnersAll + winnersAll + "\n");
		sb.append(prefixPlayersRound + playersRound + "\n");
		sb.append(prefixWinnersRound + winnersRound + "\n");
		sb.append(prefixPlayersCurrent + playersCurrent + "\n");
		sb.append(prefixViewers + getViewers() + "\n");
		sb.append("first: " + first + "\n");
		sb.append("actor: " + actor + "\n");
		return sb.toString();
	}

	/**
	 * Gives first to the first player in the currently playing players list.
	 * Use for testing purposes
	 */
	public void testFirstToFirst() {
		first = playersCurrent.get(0);
	}

	/**
	 * Check if attackers have cards left.
	 *
	 * @return True if attackers have cards left, false otherwise
	 */
	public boolean isNonDefendersHaveCards() {
		for (PodkidnoySeat player : playersCurrent) {
			if (!player.equals(getDefender()) && player.getCards().size() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get players still playing in game.
	 *
	 * @return Players
	 */
	public LinkedList<PodkidnoySeat> getPlayers() {
		return playersCurrent;
	}

	/**
	 * Get all players in game, including those that have finished.
	 *
	 * @return All players
	 */
	public LinkedList<PodkidnoySeat> getAllSeats() {
		LinkedList<PodkidnoySeat> ret = new LinkedList<PodkidnoySeat>();
		for (F1 id : F1.values()) {
			if (seats.containsKey(id)) {
				PodkidnoySeat cur = seats.get(id);
				if (cur == null) {
					throw new IllegalStateException(
							"All seats map cointained null value");
				}
				ret.add(cur);
			}
		}
		return ret;
		/*
		 * 
		 * LinkedList<AbstractPodkidnoyPlayer> ret = new
		 * LinkedList<AbstractPodkidnoyPlayer>(); Iterator<Seat> it =
		 * seats.iterator(); while (it.hasNext()) {
		 * ret.add(it.next().getPlayer()); } return ret;
		 */
	}

	/**
	 * Gets the all players.
	 *
	 * @return the all players
	 */
	public LinkedList<ServerUser> getAllPlayers() {
		LinkedList<ServerUser> ret = new LinkedList<ServerUser>();
		for (PodkidnoySeat cur : getAllSeats()) {
			if (cur == null) {
				throw new IllegalStateException(
						"All seats map cointained null value");
			}
			if (!cur.isEmpty()) {
				ret.add(cur.getServerUser());
			}
		}
		return ret;
	}

	/**
	 * Get number of players with cards left in game.
	 *
	 * @return Number of players with cards
	 */
	public int getSizeOfPlayersWithCards() {
		int counter = 0;
		for (PodkidnoySeat player : playersCurrent) {
			if (player.getCards().size() > 0) {
				counter++;
			}
		}
		return counter;
	}

	/**
	 * Check if the defender has cards left.
	 *
	 * @return True if defender has cards, false otherwise
	 */
	public boolean isDefenderHasCards() {
		PodkidnoySeat def = getDefender(false);
		if (playersCurrent.contains(def) && def.getCards().size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Get current round winners.
	 *
	 * @return Round winners
	 */
	public LinkedList<PodkidnoySeat> getRoundWinners() {
		return winnersRound;
	}

	/**
	 * Check if the player on move is the last attacker.
	 *
	 * @return True if player currently on move is the last attacker, false
	 * otherwise
	 */
	public boolean isLastAttacker() {
		PodkidnoySeat defender = getDefender(false);
		if (defender != null && defender.equals(actor)) {
			return false;
		}
		PodkidnoySeat currentActor = getActor();
		return currentActor.equals(getLastAttacker());
	}

	/**
	 * Get players that started the round.
	 *
	 * @return Players as the round began
	 */
	public LinkedList<PodkidnoySeat> getRoundPlayers() {
		return playersRound;
	}

	/**
	 * Gets the seats.
	 *
	 * @return the seats
	 */
	public List<PodkidnoySeat> getSeats() {
		List<PodkidnoySeat> ret = new ArrayList<PodkidnoySeat>();
		ret.addAll(seats.values());
		return ret;
	}

	/*
	 * public HumanPodkidnoyPlayer getPlayerByUser(User u) { for (Seat p :
	 * getAll()) { if (p != null) { HumanPodkidnoyPlayer hp =
	 * (HumanPodkidnoyPlayer) p.getPlayer(); User potential = hp.getUser(); if
	 * (potential.equals(u)) { return hp; } } } throw new
	 * IllegalArgumentException("User not found: " + u); }
	 */

	/*
	 * public ServerUser getPlayerByID(String sessionID) { for (PodkidnoySeat s
	 * : getAll()) { if (s.getUser() != null) { if
	 * (s.getServerUser().getSessionId().equals(sessionID)) { return
	 * s.getServerUser(); } } } throw new
	 * IllegalArgumentException("Player not found, session id = " + sessionID);
	 * }
	 */

	/**
	 * Gets the seat by id.
	 *
	 * @param _id the _id
	 * @return the seat by id
	 */
	public PodkidnoySeat getSeatByID(final F1 _id) {
		for (PodkidnoySeat s : getAllSeats()) {
			if (s.getID().equals(_id)) {
				return s;
			}
			/*
			 * if (s.getUser() != null) { if
			 * (s.getServerUser().getSessionId().equals(sessionID)) { return
			 * s.getServerUser(); } }
			 */
		}
		throw new IllegalArgumentException("Server seat not found: " + _id);
	}

	/**
	 * Gets the seat by user.
	 *
	 * @param u the u
	 * @return the seat by user
	 */
	public PodkidnoySeat getSeatByUser(final ServerUser u) {
		for (PodkidnoySeat s : getAllSeats()) {
			if (s.getServerUser() != null) {
				ServerUser pot = s.getServerUser();
				if (u.equals(pot)) {
					return s;
				}
			}
		}
		throw new IllegalArgumentException("User not found " + u);
	}

}
