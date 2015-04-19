/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod;

import java.util.List;
import java.util.Observable;
import java.util.concurrent.ScheduledExecutorService;

import com.littlech.cl.Utils;
import com.littlech.gen.a.A1;
import com.littlech.gen.a.A12;
import com.littlech.gen.a.A13;
import com.littlech.gen.a.A16;
import com.littlech.gen.a.A5;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B26;
import com.littlech.gen.c.C1;
import com.littlech.gen.e.E19;
import com.littlech.gen.e.E28;
import com.littlech.gen.f.F1;
import com.littlech.gs.GameServer;
import com.littlech.gs.game.GameCommandImpl;
import com.littlech.gs.game.IBotTask;
import com.littlech.gs.game.ISeat;
import com.littlech.gs.game.pod.task.*;
import com.littlech.gs.test.Test;
import com.littlech.gs.user.ServerUser;


// TODO: Auto-generated Javadoc
/**
 * The Class PodkidnoySeat.
 */
public class PodkidnoySeat extends Observable implements ISeat {

	/** The m id. */
	private final F1 mID;

	/** The m game. */
	private Podkidnoy mGame;

	/** The m user. */
	private ServerUser mUser;

	/** The new game start. */
	private boolean newGameStart = false;

	/** The buttons. */
	private List<A5> buttons;

	/** The cards. */
	private B26 cards = new B26();

	/**
	 * Instantiates a new podkidnoy seat.
	 *
	 * @param _id the _id
	 * @param _game the _game
	 */
	public PodkidnoySeat(final F1 _id, final Podkidnoy _game) {
		if (_id == null) {
			throw new NullPointerException("Seat ID code is null");
		}
		if (_game == null) {
			throw new NullPointerException("Game is null");
		}
		mID = _id;
		mGame = _game;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PodkidnoySeat{");
		sb.append("" + mID);
		/*
		 * 
		 * XXX iterating must be synchronized
		sb.append(", cards=" + getCards());
		sb.append(", but=" + getButtons());
		sb.append(", start=" + isNewGameStart());
		sb.append(", user=" + mUser);
		*/
		sb.append("}");
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see server.ISeat#getID()
	 */
	@Override
	public F1 getID() {
		return mID;
	}

	/* (non-Javadoc)
	 * @see server.ISeat#sitUser(server.ServerUser)
	 */
	@Override
	public void sitUser(final ServerUser _user) {
		if (!isEmpty()) {
			throw new IllegalStateException("Trying to sit " + _user
					+ " on seat " + this + " while seat already has user");
		}
		if (_user == null) {
			throw new NullPointerException("Server user is null");
		}
		mUser = _user;
		notifyListeners();
	}

	/* (non-Javadoc)
	 * @see server.ISeat#removeUser()
	 */
	@Override
	public void removeUser() {
		if (isEmpty()) {
			throw new IllegalStateException("Seat is already empty");
		}
		mUser = null;
		notifyListeners();
		// XXX create bot task if needed!
	}

	/* (non-Javadoc)
	 * @see server.ISeat#getServerUser()
	 */
	public ServerUser getServerUser() {
		return mUser;
	}

	/* (non-Javadoc)
	 * @see server.ISeat#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return getServerUser() == null;
	}

	/**
	 * Notify listeners.
	 */
	public void notifyListeners() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Check if the player has requested a new game start.
	 *
	 * @return True if the player requested a new game start, false otherwise
	 */
	public boolean isNewGameStart() {
		return newGameStart;
	}

	/**
	 * Set the value of the player new game start request.
	 *
	 * @param newGameStart True if a new game start was requested
	 */
	public void setNewGameStart(boolean newGameStart) {
		this.newGameStart = newGameStart;
	}

	/* (non-Javadoc)
	 * @see server.ISeat#writeCommand(com.littlech.gen.e.E19)
	 */
	public void writeCommand(final E19 command) {
		Utils.checkCommand(command, C1.C_11);
		if (!isEmpty()) {
			try {
			getServerUser().write(command);
			} catch (Exception e) {
e.printStackTrace();
// XXX make bot task and remove player
			}
		} else {
			C1 code = command.getC13();
			if (C1.C_11.equals(code)) {
				E28 gameContent = (E28) command
						.getE20();
				A12 ar = gameContent.getE30();
				if (ar != null) {
					A1 type = ar.getType();
					if (ar instanceof A13) {
						A13 onTurnAR = (A13) ar;
						Podkidnoy podGame = mGame;
						PodkidnoyData podData = podGame.getPodkidnoyData();
						
						ScheduledExecutorService mBotsService; 
						
						if (mGame.isTest()) {
							mBotsService = Test.getService();
						} else {
						mBotsService = GameServer
								.getInstance().getService();
						}

						B26 sof = new B26();
						sof.getB27().addAll(getCards());
						// System.out.println(getID() + " is doing "+type+ " on cards " + sof);
						switch (type) {
						case A_4:
							BotAddTask addTask = new BotAddTask(
									getID(), sof, podData.getTrumpCard(),
									onTurnAR.getA14(), podData
											.getTableCards(), onTurnAR.getA15());
							// mBotsService.submit(addTask);
							
							queueTaskInGame(addTask);
							
							break;
						case A_3:
							BotDefendTask defendTask = new BotDefendTask(
									 getID(), sof, podData
											.getTrumpCard(), onTurnAR
											.getA14(), podData
											.getTableCards(), onTurnAR.getA15());

							queueTaskInGame(defendTask);
							break;
						case A_2:
							BotFirstTask firstTask = new BotFirstTask(
									getID(), sof, podData.getTrumpCard(),
									onTurnAR.getA14(), podData
											.getTableCards(), onTurnAR.getA15());

							queueTaskInGame(firstTask);
							break;
						default:
							break;
						}
					} else if (ar instanceof A16) {
						// System.out.println("OTHERS id=" + getSeatId() +
						// ", state=" + type);
					} else {
						throw new IllegalArgumentException(
								"Unknown action request type: "
										+ ar.getClass().getCanonicalName());
					}
				}
			}
		}
	}

	private void queueTaskInGame(IBotTask _task) {
		mGame.getCommandHandler().queueGameCommand(new GameCommandImpl(_task));
	}

	/**
	 * Gets the num of cards.
	 *
	 * @return the num of cards
	 */
	public int getNumOfCards() {
		return getCards().size();
	}

	/**
	 * Gets the cards.
	 *
	 * @return the cards
	 */
	public List<B20> getCards() {
		return cards.getB27();
	}
	
	/**
	 * Gets the sets the of cards.
	 *
	 * @return the sets the of cards
	 */
	public B26 getSetOfCards() {
		return cards;
	}

	/**
	 * Gets the buttons.
	 *
	 * @return the buttons
	 */
	public List<A5> getButtons() {
		return buttons;
	}

}
