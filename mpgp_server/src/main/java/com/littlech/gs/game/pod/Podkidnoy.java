/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.xml.DOMConfigurator;

import com.littlech.cl.Utils;
import com.littlech.gen.a.A1;
import com.littlech.gen.a.A12;
import com.littlech.gen.a.A13;
import com.littlech.gen.a.A16;
import com.littlech.gen.a.A18;
import com.littlech.gen.a.A5;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B26;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D1;
import com.littlech.gen.d.D17;
import com.littlech.gen.d.D19;
import com.littlech.gen.d.D23;
import com.littlech.gen.d.D4;
import com.littlech.gen.e.E1;
import com.littlech.gen.e.E11;
import com.littlech.gen.e.E14;
import com.littlech.gen.e.E19;
import com.littlech.gen.e.E24;
import com.littlech.gen.e.E28;
import com.littlech.gen.e.E31;
import com.littlech.gen.e.E34;
import com.littlech.gen.e.E37;
import com.littlech.gen.e.E40;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F10;
import com.littlech.gen.f.F12;
import com.littlech.gen.f.F14;
import com.littlech.gen.f.F15;
import com.littlech.gen.f.F17;
import com.littlech.gen.f.F19;
import com.littlech.gen.f.F20;
import com.littlech.gen.f.F26;
import com.littlech.gen.f.F6;
import com.littlech.gen.f.F9;
import com.littlech.gen.g.G1;
import com.littlech.gen.g.G13;
import com.littlech.gen.g.G17;
import com.littlech.gs.cardpack.ICardPack;
import com.littlech.gs.game.GameCommandImpl;
import com.littlech.gs.game.ICommandHandler;
import com.littlech.gs.game.IGame;
import com.littlech.gs.game.IGameData;
import com.littlech.gs.game.IPlayerStore;
import com.littlech.gs.game.IRules;
import com.littlech.gs.game.IStateMachine;
import com.littlech.gs.game.RulesCheckResult;
import com.littlech.gs.game.pod.task.PodkidnoyUtils;
import com.littlech.gs.test.Test;
import com.littlech.gs.user.ServerUser;

import static com.littlech.gs.gate.Gateway.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Podkidnoy.
 */
public class Podkidnoy implements IGame, Observer {

	/** The state machine. */
	private final PodkidnoyStateMachine stateMachine;

	/** The rules. */
	private final PodkidnoyRules rules;

	/** The configuration. */
	private final G17 configuration;

	/** The data. */
	private final PodkidnoyData data;

	/** The player store. */
	private final PodkidnoyPlayerStore playerStore;

	/** The lock. */
	private final ReentrantLock lock = new ReentrantLock();

	/* Logger */
	/** The logger. */
	// public final Logger game = Logger.getLogger(Podkidnoy.class);

	/* Keeps track of the games that current instance has played */
	/** The games counter. */
	private int gamesCounter = 1;

	/** The test. */
	private final boolean test;

	/** The id. */
	private final String id;
	
	/** The m number of test games. */
	private 
	// volatile long 
	AtomicLong
	mNumberOfTestGames = new AtomicLong(-1);

	private PodkidnoyCommandHandler commandHandler;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Podkidnoy[");
		sb.append("id=").append(getId());
		/*
		sb.append(", ");
		sb.append("name=").append(getConfiguration().get);
		*/
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Instantiates a new podkidnoy.
	 *
	 * @param _configuration the _configuration
	 * @param _id the _id
	 * @param _test the _test
	 */
	public Podkidnoy(final G17 _configuration, String _id, boolean _test) {
		G13 gameType = _configuration.getG18();
		if (!G13.G_14.equals(gameType)) {
			throw new IllegalArgumentException("Not Podkidnoy configuration: "
					+ gameType);
		}
		configuration = _configuration;
		id = _id;
		test = _test;
		if (isTest()) {
			DOMConfigurator.configure("logconf.xml"); // initializing log4j
		}

		rules = new PodkidnoyRules(this);
		stateMachine = new PodkidnoyStateMachine();
		data = new PodkidnoyData();
		playerStore = new PodkidnoyPlayerStore(getPodkidnoyConf().getG22(),
				this);
		commandHandler = new PodkidnoyCommandHandler(this);
	}

	/* (non-Javadoc)
	 * @see server.IGame#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see server.IGame#isTest()
	 */
	@Override
	public boolean isTest() {
		return test;
	}

	/* (non-Javadoc)
	 * @see server.IGame#getRules()
	 */
	@Override
	public IRules getRules() {
		return rules;
	}

	/* (non-Javadoc)
	 * @see server.IGame#getStateMachine()
	 */
	@Override
	public IStateMachine getStateMachine() {
		return stateMachine;
	}

	/* (non-Javadoc)
	 * @see server.IGame#handleCommand(server.ServerUser, com.littlech.gen.d.D4)
	 */
	/**
	 * Lock must already be held by calling thread.
	 */
	public void handleCommand(ServerUser user, D4 cmd) {
		if (user == null) {
			throw new NullPointerException("User is null");
		}
		if (cmd == null) {
			throw new NullPointerException("Command is null");
		}
		// getLock().lock();

		C1 commandType = cmd.getC13();
		if (commandType == null) {
			throw new NullPointerException("Command type is null");
		}
		switch (commandType) {
		case C_11:
			/*
			 * RulesCheckResult checkResult = getRules().isAllowed(user, cmd);
			 * if (checkResult.isAllowed()) {
			 */
			F1 seatID = getPlayerStore().getSeatIDByUser(user);
			handleGameCommand(seatID, cmd);
			/*
			 * } else { // Send all to client SeatIDCode seatID =
			 * getPlayerStore().getSeatIDByUser(user); SCType allCmd =
			 * getAllCommand(seatID); writeCommandToUser(user, allCmd); }
			 */
			break;
		case C_7:
			/*
			 * RulesCheckResult checkResult3 = getRules().isAllowed(user, cmd);
			 * if (checkResult3.isAllowed()) {
			 */
			// SeatIDCode seatID = getPlayerStore().getSeatIDByUser(user);
			handleJoinCommand(user, cmd);
			/*
			 * 
			 * } else { // Send all to client }
			 */
			break;
		case C_8:
			// XXX compose bot task if player is sitting and game is in playing
			// state
			// getPodkidnoyPlayerStore().remove(user);

			user.removeFromGame();
			break;
		case C_9:
			RulesCheckResult checkResult2 = getRules().isAllowed(user, cmd);
			if (checkResult2.isAllowed()) {
				D17 sitDownContent = (D17) cmd
						.getD5();
				F1 seatID2 = sitDownContent.getD18();
				try {
					// XXX
					getPodkidnoyPlayerStore().sit(user, seatID2);
					E19 allCmd = getAllCommand(seatID2);
					user.write(allCmd);
				} catch (Exception e) {
					logCommunication.error("Unable to write sit down success response", e);
					getPodkidnoyPlayerStore().getSeatByID(seatID2).removeUser();
				}
			} else {
				E19 sitFailure = new E19();
				sitFailure.setC13(C1.C_9);
				sitFailure.setE20(new E24());
				sitFailure.setE21(E11.E_13);
				sitFailure.setE22(checkResult2.getReason());
				writeCommandToUser(user, sitFailure);
			}
			break;
		default:
			break;
		}
		// getLock().unlock();
	}

	/**
	 * Handle join command.
	 *
	 * @param user the user
	 * @param cmd the cmd
	 */
	private void handleJoinCommand(ServerUser user, D4 cmd) {
		try {
			getPodkidnoyPlayerStore().add(user);
			user.write(getJoinOK());
			user.write(getAllCommand());
			user.setGame(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the join ok.
	 *
	 * @return the join ok
	 */
	private static E19 getJoinOK() {
		E19 joinOK = new E19();
		joinOK.setC13(C1.C_7);
		joinOK.setE21(E11.E_12);
		joinOK.setE20(new E24());
		return joinOK;
	}

	/**
	 * Write command to user.
	 *
	 * @param user the user
	 * @param allCmd the all cmd
	 */
	private void writeCommandToUser(ServerUser user, E19 allCmd) {
		try {
			user.write(allCmd);
		} catch (Exception e) {
			logCommunication.error("Unable to write command " + allCmd + " to user " + user);
			getPodkidnoyPlayerStore().remove(user);
		}
	}

	/* (non-Javadoc)
	 * @see server.IGame#handleBotCommand(com.littlech.gen.f.F1, com.littlech.gen.d.D4)
	 */
	/*
	private void handleBotMove(F1 seatID, D4 cmd) {
		getLock().lock();
		// Lock still has to be acquired
		handleGameCommand(seatID, cmd);
		getLock().unlock();
	}
	*/

	/* (non-Javadoc)
	 * @see server.IGame#handleGameCommand(com.littlech.gen.f.F1, com.littlech.gen.d.D4)
	 */
	public void handleGameCommand(F1 seatID, D4 cmd) {
		try {
		PodkidnoyCommandHandler.handleGameCommand(this, seatID, cmd);
		} catch (Exception e) {
e.printStackTrace();
StringWriter sw = new StringWriter();
PrintWriter pw = new PrintWriter(sw);
e.printStackTrace(pw);
if (logGame.isDebugEnabled()) {
logGame.debug("Podkidnoy: Exception while handling game command " + sw.toString());
}
/*
try { // XXX why's that??
	// Let logger write 
	Thread.currentThread().wait(1000);
} catch (InterruptedException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
System.exit(0);
*/
		}
	}

	/**
	 * Finishes current round and starts the new round or finishes the game.
	 */
	void finishRound() {
		logGame.debug("$$$ Round has ended");
		boolean maxRounds = isMaxRounds();
		if (!maxRounds && startNewRound()) {
			checkWinners("finishRound");
			// sendGameUpdates("New round has started"); // XXX
			return;
		}
		finishGame(maxRounds);
	}

	/**
	 * Checks if is max rounds.
	 *
	 * @return true, if is max rounds
	 */
	public boolean isMaxRounds() {
		boolean ret = getPodkidnoyData().getRounds() >= getPodkidnoyConf()
				.getMaxRounds();
		return ret;
	}

	/**
	 * Finishes current game.
	 *
	 * @param _maxRoundsReached the _max rounds reached
	 */
	private void finishGame(final boolean _maxRoundsReached) {
		checkWinners("finishGame");

		getPodkidnoyPlayerStore().endGame();
		for (PodkidnoySeat player : getPodkidnoyPlayerStore().getAllSeats()) {
			player.setNewGameStart(false);
		}
		getPodkidnoyStateMachine().setState(
				PodkidnoyGameStateType.GS_DURAK_FINISHED);

		StringBuilder sb = new StringBuilder();
		sb.append("Game " + gamesCounter + " has ended");
		if (isMaxRounds()) {
			sb.append(" (maximum rounds reached)");
		}
		sb.append(". " + getWinnersToClient());

		// XXX
		sendGameEndUpdates();
		logGame.info("$$$ " + sb.toString());
		getPodkidnoyPlayerStore().reset();
		getPodkidnoyData().reset(getPodkidnoyConf().getG25());

		if (isTest()) {
			if ((mNumberOfTestGames.get() < 0 || mNumberOfTestGames.get() > gamesCounter)) {
			driveNewTestRound();
			} else {
				String s = "Playing " + mNumberOfTestGames + " test games done";
				logGame.info(s);
				Test.getService().shutdown();
			}
		}
		gamesCounter++;
	}

	/**
	 * Check winners.
	 *
	 * @param s the s
	 */
	private void checkWinners(String s) {
		if (getPodkidnoyPlayerStore().getWinnersAll().size() > 0
				&& getPodkidnoyData().getPack().hasMoreCards()) {

			logGame.error("Winners check failed");
			logGame.error("player store=" + getPodkidnoyPlayerStore());
			logGame.error("data=" + getPodkidnoyData());
			logGame.error("state machine=" + getPodkidnoyStateMachine());
			throw new IllegalStateException(s);
		}
	}

	/**
	 * Starts new game round. Called after previous round has ended, i.e. not
	 * before the first round has ended
	 * 
	 * @return true if starting the round game was successful, false if the game
	 *         has ended
	 */
	private boolean startNewRound() {
		getPodkidnoyData().incrementRounds();
		if (!isMoreThanOne()) {
			return false;
		}
		checkWinners("startNewRound");
		boolean tableDefended = Utils.isTableDefended(data.getTableCards()
				.getB29());
		if (!tableDefended) {
			getPodkidnoyPlayerStore().getDefender().getCards().addAll(
					Utils.getAllTableCards(data.getTableCards()));
		}
		dealCards();
		getPodkidnoyPlayerStore().putEmptyHandedToTmp();
		if (!isMoreThanOne()) {
			return false;
		}
		boolean turnToNext = true;
		if (tableDefended && getPodkidnoyPlayerStore().isDefenderHasCards()) {
			turnToNext = false;
		}
		getPodkidnoyPlayerStore().endRound(turnToNext);
		if (!isMoreThanOne()) {
			return false;
		}
		data.getTableCards().getB29().clear();
		getPodkidnoyStateMachine().setState(
				PodkidnoyGameStateType.GS_DURAK_ROUND_STARTED);

		// XXX
		sendNewRoundUpdates(); // true);
		return true;
	}

	/**
	 * Deals out cards to players if there are any left in the pack.
	 */
	public void dealCards() {
		int firstIdx = getPodkidnoyPlayerStore().getRoundPlayers().indexOf(
				getPodkidnoyPlayerStore().getFirst());
		int size = getPodkidnoyPlayerStore().getRoundPlayers().size();
		if (firstIdx == 0) {
			for (int i = 0; i < size; i++) {
				fillNonDefenderCards(i);
			} // for i
		} else {
			for (int i = firstIdx; i < size; i++) {
				fillNonDefenderCards(i);
			}
			for (int i = 0; i < firstIdx; i++) {
				fillNonDefenderCards(i);
			}
		}
		fillPlayerCards(getPodkidnoyPlayerStore().getDefender(
		false // XXX		
		).getCards());
	}

	/**
	 * Deals cards to non-defending player appointed by index.
	 *
	 * @param idx Index of the player in the current round's players list
	 */
	private void fillNonDefenderCards(int idx) {
		PodkidnoySeat p = getPodkidnoyPlayerStore().getRoundPlayers().get(idx);
		if (!p.equals(getPodkidnoyPlayerStore().getDefender(false))) {
			fillPlayerCards(p.getCards());
		} 
		/*
		else {
			throw new IllegalStateException("On idx=" + idx + " of round players is " + p + ", who is also the defender");
		}
		*/
	}

	/**
	 * Fills parameter cards from pack (and trump card, if it has not been added
	 * yet).
	 *
	 * @param cards Player cards to be filled
	 */
	private void fillPlayerCards(List<B20> cards) {
		ICardPack pack = getPodkidnoyData().getPack();
		while (cards.size() < getPodkidnoyConf().getNumOfPlayerCards()
				&& pack.hasMoreCards()) {
			cards.add(pack.getCard());
		}
	}

	/**
	 * Check if there are at least two players with cards (otherwise the round
	 * is to be finished).
	 *
	 * @return true if there are at least two players in the game, false
	 * otherwise
	 */
	public boolean isMoreThanOne() {
		getPodkidnoyPlayerStore().putEmptyHandedToTmp();
		int size = getPodkidnoyPlayerStore().getSizeOfPlayersWithCards();
		boolean ret = size > 1;
		return ret;
	}

	/**
	 * Throws an exception when current game state does not accept parameter
	 * command type.
	 *
	 * @param durakGS Podkidnoy game state
	 * @param butCode Command type
	 */
	void throwIllegalArgumentException(PodkidnoyGameStateType durakGS,
			A5 butCode) {
		throw new IllegalArgumentException("State " + durakGS + " got command "
				+ butCode);

	}

	/**
	 * Gets the podkidnoy conf.
	 *
	 * @return the podkidnoy conf
	 */
	public ServerPodConfigType getPodkidnoyConf() {
		return (ServerPodConfigType) configuration.getG19();
	}

	/**
	 * Gets the podkidnoy state machine.
	 *
	 * @return the podkidnoy state machine
	 */
	public PodkidnoyStateMachine getPodkidnoyStateMachine() {
		return stateMachine;
	}

	/* (non-Javadoc)
	 * @see server.IGame#getConfiguration()
	 */
	@Override
	public G17 getConfiguration() {
		return configuration;
	}

	/* (non-Javadoc)
	 * @see server.IGame#getData()
	 */
	@Override
	public IGameData getData() {
		return data;
	}

	/* (non-Javadoc)
	 * @see server.IGame#getPlayerStore()
	 */
	@Override
	public IPlayerStore getPlayerStore() {
		return playerStore;
	}

	/* (non-Javadoc)
	 * @see server.IGame#getLock()
	 */
	@Override
	public Lock getLock() {
		return lock;
	}

	/**
	 * Gets the podkidnoy data.
	 *
	 * @return the podkidnoy data
	 */
	public PodkidnoyData getPodkidnoyData() {
		return data;
	}

	/**
	 * Gets the podkidnoy player store.
	 *
	 * @return the podkidnoy player store
	 */
	public PodkidnoyPlayerStore getPodkidnoyPlayerStore() {
		return playerStore;
	}

	/**
	 * Returns all the cards that can be used for defending the bottom table
	 * cards.
	 *
	 * @return Defending cards
	 */
	public B26 getKillers() {
		B26 ret = new B26();
		ret.getB27().addAll(
				Utils.getDefendingCards(getPodkidnoyData().getTableCards()
						.getB29()));
		ret.getB27().addAll(playerStore.getDefender().getCards());
		return ret;
	}

	/**
	 * Drive new test round.
	 */
	public void driveNewTestRound() {
		if (!isTest()) {
			throw new IllegalStateException("Test is false");
		}
		LinkedList<PodkidnoySeat> all = getPodkidnoyPlayerStore().getAllSeats();
		if (all.size() < 2) {
			throw new IllegalStateException("Less than 2 bots");
		}
		setBotsStart(true);
		PodkidnoySeat first = all.getFirst();
		D4 startCommand = getStartCommand();
		// handleBotMove(first.getID(), startCommand);
		getCommandHandler().queueGameCommand(new GameCommandImpl(startCommand, first.getID()));
	}

	/**
	 * Gets the start command.
	 *
	 * @return the start command
	 */
	public static D4 getStartCommand() {
		D4 ret = new D4();
		ret.setC13(C1.C_11);

		D19 gameContent = new D19();
		gameContent.setD20(D1.D_2);
		ret.setD5(gameContent);

		D23 emptyContent = new D23();
		gameContent.setD21(emptyContent);
		return ret;
	}

	/**
	 * Sets the bots start.
	 *
	 * @param b the new bots start
	 */
	private void setBotsStart(boolean b) {
		Iterator<PodkidnoySeat> itAll = getPodkidnoyPlayerStore().getAllSeats()
				.iterator();
		while (itAll.hasNext()) {
			PodkidnoySeat cur = itAll.next();
			if (cur.isEmpty()) {
				cur.setNewGameStart(true);
			}
		}
	}

	/**
	 * Checks if is all humans start.
	 *
	 * @return true, if is all humans start
	 */
	boolean isAllHumansStart() {
		boolean allHumansStart = true;
		boolean allBots = true;
		for (PodkidnoySeat player : getPodkidnoyPlayerStore().getAllSeats()) {
			if (!player.isEmpty()) {
				allBots = false;
				if (!player.isNewGameStart()) {
					allHumansStart = false;
				}
			}
		}
		return !allBots && allHumansStart;
	}

	/**
	 * Gets the winners to client.
	 *
	 * @return the winners to client
	 */
	private String getWinnersToClient() {
		String ret;
		if (getPodkidnoyPlayerStore().getWinnersAll().size() > 0) {
			StringBuilder sb = new StringBuilder();
			int size = getPodkidnoyPlayerStore().getAllSeats().size();
			switch (size) {
			case 0:
			case 1:
				throw new IllegalStateException("Number of players is " + size);
			default:
				int winnersSize = getPodkidnoyPlayerStore().getWinnersAll()
						.size();
				if (winnersSize > 0) {
					for (int i = 0; i < winnersSize; i++) {
						PodkidnoySeat winner = getPodkidnoyPlayerStore()
								.getWinnersAll().get(i);
						int rank = i + 1;
						sb.append(rank + ". " + winner.getID());
						if (i < winnersSize - 1) {
							sb.append(", ");
						}
					}
				} else {
					throw new IllegalStateException("Number of winners is "
							+ winnersSize);
				}
				break;
			}
			ret = "Winners are " + sb.toString();
		} else {
			ret = "All tied.";
		}
		return ret;
	}

	/**
	 * Starts a new game . To be called after all players in game have requested
	 * for start. Game updates are to be sent separately.
	 */
	@Override
	public void startNewGame() {
		getPodkidnoyPlayerStore().reset();
		// getPodkidnoyData().getTableCards().getPairs().clear();

		getPodkidnoyData().reset(getPodkidnoyConf().getG25());
		getPodkidnoyStateMachine().setState(
				PodkidnoyGameStateType.GS_DURAK_ROUND_STARTED);

		FIRST_POLICY firstPolicy = getPodkidnoyConf().getFirstPolicy();
		switch (firstPolicy) {
		case RANDOM:
			getPodkidnoyPlayerStore().firstToRandom();
			break;
		case LAST_WINNER:
			getPodkidnoyPlayerStore().firstToLastWinner();
			break;
		case ROUND:
			getPodkidnoyPlayerStore().firstToRound();
			break;
		default:
			throw new IllegalArgumentException("Unknown first policy "
					+ firstPolicy);
		}

		getPodkidnoyPlayerStore().turnToFirstAttacker();
		dealCards();
		logGame.info("$$$ New game has started");
		checkWinners("startNewGame");
	}

	/**
	 * Checks if any more bottom cards can be added to table.
	 *
	 * @return true if more bottom cards can be added, false otherwise
	 */
	public boolean isMoreCanBeAdded() {
		if (playerStore.getSizeOfPlayersWithCards() < 2) {
			return false;
		}
		int numOfBottoms = Utils.getBottomCards(
				getPodkidnoyData().getTableCards().getB29()).size();
		boolean bottomsFull = (getPodkidnoyConf().getNumOfTableCards() == numOfBottoms);
		boolean lastAttacker = playerStore.isLastAttacker();
		boolean defenderEmptyHanded = (playerStore.getDefender()
				.getNumOfCards() < 1);
		boolean nonDefendersHaveNoCards = !(playerStore
				.isNonDefendersHaveCards());
		boolean killersUsed = (numOfBottoms >= getKillers().getB27().size());
		boolean fourSameRanks = Utils.isFourSameRanks(getPodkidnoyData()
				.getTableCards().getB29()); // .isFourSameRanks();
		boolean ret = !(bottomsFull || lastAttacker || defenderEmptyHanded
				|| nonDefendersHaveNoCards || killersUsed || fourSameRanks);
		return ret;
	}

	/**
	 * Check that last actor is not on turn.
	 *
	 * @param _lastActorID the _last actor id
	 */
	public void checkThatLastActorIsNotOnTurn(final F1 _lastActorID) {
		F1 curActor = getPodkidnoyPlayerStore().getActor().getID();
		if (_lastActorID.equals(curActor)) {
			throw new IllegalStateException("After " + _lastActorID + " has acted the actor is still " + curActor
					+"\n"+ Thread.currentThread().getStackTrace()[3]		
			);
		}
	}
	
	/**
	 * Send game updates.
	 *
	 * @param action the action
	 * @param _lastActorID the _last actor id
	 */
	void sendGameUpdates(final A18 action,
			final F1 _lastActorID) { // , MessageType update) {
		if (action == null) {
			throw new NullPointerException("Action is null\nStack:\n"
					+ Thread.currentThread().getStackTrace()[3]);
		}

		// checkThatLastActorIsNotOnTurn(_lastActorID);
		
		PodkidnoyGameStateType durakGS = getPodkidnoyStateMachine().getState();

		E19 confirm = new E19();
		confirm.setC13(C1.C_11);
		confirm.setE21(E11.E_12);
		E28 confirmContent = new E28();
		confirmContent.setE29(E1.E_7);
		confirm.setE20(confirmContent);

		switch (durakGS) {
		case GS_DURAK_ROUND_STARTED:
			throw new IllegalStateException(); // XXX ?
		case GS_DURAK_DEFENDING_FIRST:
		case GS_DURAK_DEFENDING_AGAIN:
		case GS_DURAK_WAITING_MORE_CARDS:
		case GS_DURAK_PICKED_UP:

				/* self couldn't be on turn right after acting */
				confirmContent.setE30(getOthersAR());
				sendCommandToSeat(_lastActorID, confirm);
				
			if (true) {
				/* Updating actor */
				E19 gameUpd = getGameCommandStub();
				E34 content = new E34();
				content.setE36(action);
				content.setE35(_lastActorID);
				content.setE29(E1.E_6);
				gameUpd.setE20(content);

				content.setE30(getOnTurnAR());
				sendCommandToActor(gameUpd);
			}

			if (true) {	
				/* Updating players that are not-on-turns and not-last-actors */
				E19 gameUpd = getGameCommandStub();
				E34 content = new E34();
				content.setE36(action);
				content.setE35(_lastActorID);
				content.setE29(E1.E_6);
				gameUpd.setE20(content);

				A16 othersAR = getOthersAR();
				content.setE30(othersAR);
				sendCommandToNonActors(gameUpd, _lastActorID);

			}
			break;
		case GS_DURAK_ROUND_ENDED:

			// confirm action request is null
			sendCommandToSeat(_lastActorID, confirm);
			
			E19 roundLastActionUpd = getGameCommandStub();
			E34 actionUpd = new E34();
			actionUpd.setE36(action);
			actionUpd.setE35(_lastActorID);
			actionUpd.setE29(E1.E_6);
			roundLastActionUpd.setE20(actionUpd);

			actionUpd.setE30(null);
			sendCommandToAll(roundLastActionUpd, _lastActorID);

			break;

		// Falls through
		default:
			throw new IllegalStateException(
					"Continue round method must be used only in non-roundended playing state");
		} // switch (durakGS)
	}

	/**
	 * Send command to seat.
	 *
	 * @param lastActorID the last actor id
	 * @param confirm the confirm
	 */
	private void sendCommandToSeat(F1 lastActorID, E19 confirm) {
		getPodkidnoyPlayerStore().getSeatByID(lastActorID)
				.writeCommand(confirm);
	}

	/**
	 * Gets the others ar.
	 *
	 * @return the others ar
	 */
	private A16 getOthersAR() {
		A16 ret = new A16();
		ret.setA17(getPodkidnoyPlayerStore().getActor().getID());
		A1 state;
		switch (getPodkidnoyStateMachine().getState()) {
		case GS_DURAK_ROUND_STARTED:
			state = A1.A_2;
			break;
		case GS_DURAK_DEFENDING_FIRST:
		case GS_DURAK_DEFENDING_AGAIN:
			state = A1.A_3;
			break;
		case GS_DURAK_PICKED_UP:
		case GS_DURAK_WAITING_MORE_CARDS:
			state = A1.A_4;
			break;
		default:
			 // XXX Something happens in here
			throw new IllegalStateException("There is no actor in state "
					+ getPodkidnoyStateMachine().getState());
		}
		ret.setType(state);
		return ret;
	}

	/**
	 * Gets the on turn ar.
	 *
	 * @return the on turn ar
	 */
	private A13 getOnTurnAR() {
		A13 ret = new A13();
		A1 state;
		boolean sendOK = false;
		int max = -1;
		switch (getPodkidnoyStateMachine().getState()) {
		case GS_DURAK_ROUND_STARTED:
			state = A1.A_2;
			int numOfDefenderCards = getPodkidnoyPlayerStore().getDefender()
					.getCards().size();
			max = (numOfDefenderCards >= 4) ? 4 : numOfDefenderCards;
			if (max > getPodkidnoyConf().getNumOfTableCards()
			// getNumOfTableCards()
			) {
				max = getPodkidnoyConf().getNumOfTableCards();
			}
			break;
		case GS_DURAK_DEFENDING_FIRST:
			if (getPodkidnoyConf().isG23()) {
				int numOfNextToDefenderCards = getPodkidnoyPlayerStore()
						.getNextToDefender().getCards().size();

				if (numOfNextToDefenderCards > getPodkidnoyConf()
						.getNumOfPlayerCards()) {
					numOfNextToDefenderCards = getPodkidnoyConf()
							.getNumOfPlayerCards();
				}
				int cardsOnTable = Utils.getBottomCards(data.getTableCards())
						.size();
				int canBeAdded = numOfNextToDefenderCards - cardsOnTable;
				if (cardsOnTable < getPodkidnoyConf().getNumOfTableCards()
						&& canBeAdded > 0) {
					sendOK = (PodkidnoyUtils
							.calcSendForwardCards(getPodkidnoyPlayerStore()
									.getNextToDefender().getSetOfCards(), data
									.getTableCards(), getPodkidnoyPlayerStore()
									.getDefenderWithCards(true).getSetOfCards()) != null);
					if (sendOK) {
						max = (canBeAdded >= 4) ? 4 : canBeAdded;
						if (max + cardsOnTable > getPodkidnoyConf()
								.getNumOfTableCards()) {
							max = getPodkidnoyConf().getNumOfTableCards()
									- cardsOnTable;
						}
					}
				}
			}
		case GS_DURAK_DEFENDING_AGAIN:
			if (PodkidnoyUtils.calcDefendedTable(getKillers(), data
					.getTableCards(), data.getTrumpCard()) != null) {
				ret.getA14().add(A5.A_8);
			}
			if (sendOK) {
				ret.getA14().add(A5.A_7);
			}
			state = A1.A_3;
			break;
		case GS_DURAK_PICKED_UP:
		case GS_DURAK_WAITING_MORE_CARDS:
			B26 addCards = PodkidnoyUtils.calcAddCards(data
					.getTableCards(), getPodkidnoyPlayerStore().getDefender()
					.getSetOfCards(), getPodkidnoyPlayerStore().getActor()
					.getSetOfCards());
			if (addCards != null) {
				ret.getA14().add(A5.A_10);
				int numOfKillers = getKillers().getB27().size();
				int numOfBottoms = Utils.getBottomCards(data.getTableCards())
						.size();
				int numOfCanBeAdded = numOfKillers - numOfBottoms;
				max = (numOfCanBeAdded >= 4) ? 4 : numOfCanBeAdded;
				if (max + numOfBottoms > getPodkidnoyConf()
						.getNumOfTableCards()) {
					max = getPodkidnoyConf().getNumOfTableCards()
							- numOfBottoms;
				}
			}
			state = A1.A_4;
			if (getPodkidnoyPlayerStore().getActor() == getPodkidnoyPlayerStore()
					.getDefender(true)) {
				throw new IllegalStateException(
						"Defender is adding cards to himself");
			}
			break;
		default:
			throw new IllegalStateException("There is no actor in state "
					+ getPodkidnoyStateMachine().getState());
		}
		ret.setType(state);
		int turnCards = getPodkidnoyConf().getG24();
		if (max > turnCards) {
			max = turnCards;
		}
		ret.setA15(new Integer(max));
		return ret;
	}

	/**
	 * Send new game updates.
	 */
	void sendNewGameUpdates() {
		E19 sc = getGameCommandStub();
		E28 content = new E28();
		sc.setE20(content);
		content.setE29(E1.E_4);
		sendCommandToAll(sc);
	}

	/**
	 * Send game end updates.
	 */
	private void sendGameEndUpdates() {
		E19 sc = getGameCommandStub();
		E28 content = new E28();
		sc.setE20(content);
		content.setE29(E1.E_9);
		sendCommandToAll(sc);
	}

	/**
	 * Send new round updates.
	 *
	 */
	void sendNewRoundUpdates() { // final boolean _includeGameState) {
		if (logGame.isDebugEnabled()) {
		logGame.debug("$$$ New round has started");
		}
		E19 sc = new E19();
		sc.setC13(C1.C_11);
		sc.setE21(E11.E_12);
		E37 content = new E37();
		content.getE39().addAll(getSeatUpdates());
		sc.setE20(content);

		E14 gs = new E14();
		// if (_includeGameState) {
		gs.setE15(new Integer(getPodkidnoyData().getPack().size()));
		gs.setE16(data.getTrumpCard());
		content.setE38(gs);
		// }

		content.setE29(E1.E_5);
		PodkidnoySeat actor = getPodkidnoyPlayerStore().getActor();
		if (actor == null) {
			throw new IllegalStateException("Actor is null");
		}

		A1 stateCode = A1.A_2;

		// Players not on turn and viewers
		A16 other = new A16();
		content.setE30(other);
		other.setA17(actor.getID());
		other.setType(stateCode);

		for (PodkidnoySeat ss : getPodkidnoyPlayerStore().getAllSeats()) {
			if (ss != actor) {
				// if (_includeGameState) {
				gs.setE17(ss.getSetOfCards());
				// }
				ss.writeCommand(sc);
			}
		}
		sendToViewers(sc);

		// Player on turn
		A13 onturn = new A13();
		content.setE30(onturn);
		onturn.setType(stateCode);
		int defenderCards = getPodkidnoyPlayerStore().getDefender().getCards()
				.size();
		int onTurnCards = getPodkidnoyConf().getG24();
		int max = Math.min(defenderCards, onTurnCards);
		onturn.setA15(max);

		// if (_includeGameState) {
		gs.setE17(actor.getSetOfCards());
		// }
		actor.writeCommand(sc);
	}

	/**
	 * Gets the seat updates.
	 *
	 * @return the seat updates
	 */
	private List<F20> getSeatUpdates() {
		List<F20> ret = new ArrayList<F20>();
		PodkidnoyPlayerStore ps = getPodkidnoyPlayerStore();
		LinkedList<PodkidnoySeat> players = ps.getAllSeats();
		Iterator<PodkidnoySeat> it = players.iterator();
		List<F1> seatIDs = new ArrayList<F1>(Arrays
				.asList(F1.values()));
		while (it.hasNext()) {
			PodkidnoySeat cur = it.next();
			F20 st = new F20();
			/* seat ID code */
			F1 curID = cur.getID();
			st.setF21(curID);
			seatIDs.remove(curID);

			/* seat state */
			F14 seatState;
			if (ps.getWinnersAll().contains(cur)) {
				F15 iss = new F15();
				iss.setF16(false);
				seatState = iss;
			} else {
				F17 pss = new F17();
				pss.setF18(cur.getNumOfCards());
				seatState = pss;
			}
			st.setF22(seatState);

			ret.add(st);
		} // while

		return ret;
	}

	/**
	 * Send command to all.
	 *
	 * @param _cmd the _cmd
	 * @param _excludes the _excludes
	 */
	void sendCommandToAll(final E19 _cmd, F1... _excludes) {
		List<F1> excludesList = new ArrayList<F1>(Arrays
				.asList(_excludes));
		for (PodkidnoySeat ss : getPodkidnoyPlayerStore().getAllSeats()) {
			if (!excludesList.contains(ss.getID())) {
				ss.writeCommand(_cmd);
			}
		}
		// to viewers too!
		sendToViewers(_cmd);
	}

	/**
	 * Send to viewers.
	 *
	 * @param _cmd the _cmd
	 */
	private void sendToViewers(E19 _cmd) {
		List<ServerUser> toRemove = new ArrayList<ServerUser>();
		for (ServerUser su : getPodkidnoyPlayerStore().getViewers()) {
			try {
				su.write(_cmd);
			} catch (Exception e) {
				e.printStackTrace();
				toRemove.add(su);
			}
		}
		for (ServerUser cur : toRemove) {
			getPodkidnoyPlayerStore().remove(cur);
		}
	}

	/**
	 * Send command to actor.
	 *
	 * @param sc the sc
	 */
	private void sendCommandToActor(E19 sc) {
		PodkidnoySeat actor = getPodkidnoyPlayerStore().getActor();
		if (actor == null) {
			throw new IllegalStateException("Actor is null");
		}
		actor.writeCommand(sc);
	}

	/**
	 * Send command to non actors.
	 *
	 * @param sc the sc
	 * @param lastActorID the last actor id
	 */
	private void sendCommandToNonActors(E19 sc, F1... lastActorID) {
		List<F1> excludesList = new ArrayList<F1>(Arrays
				.asList(lastActorID));
		PodkidnoySeat actor = getPodkidnoyPlayerStore().getActor();
		if (actor == null) {
			throw new IllegalStateException("Actor is null");
		}
		for (PodkidnoySeat ss : getPodkidnoyPlayerStore().getAllSeats()) {
			if (ss != actor) {
				if (!excludesList.contains(ss.getID())) {
					ss.writeCommand(sc);
				}
			}
		}
		// to viewers too!
		sendToViewers(sc);
	}

	/**
	 * Gets the all command.
	 *
	 * @return the all command
	 */
	private E19 getAllCommand() {
		return getAllCommand(null);
	}

	/**
	 * Gets the all command.
	 *
	 * @param _selfID the _self id
	 * @return the all command
	 */
	private E19 getAllCommand(final F1 _selfID) {
		E19 ret = new E19();
		ret.setC13(C1.C_11);
		ret.setE21(E11.E_12);
		E40 allContent = new E40();
		allContent.setE29(E1.E_10);
		G1 tableStateCode = getPodkidnoyStateMachine()
				.getTableState();
		G1 gameStateCode;
		switch (tableStateCode) {
		case G_3:
			gameStateCode = G1.G_3;
			break;
		default:
			gameStateCode = G1.G_2;
			break;
		}
		allContent.setE45(true);
		allContent.setE44(_selfID);
		allContent.setE41(gameStateCode);
		switch (gameStateCode) {
		case G_3:
			allContent.setE30(getAROthersForAll());
			allContent.setE42(getGameState());
			allContent.getE43().addAll(getPlayingSeatStates());
			break;
		case G_2:
			List<F26> waitingSeats = getWaitingStateSeats();
			allContent.getE43().addAll(waitingSeats);
			break;
		default:
			throw new IllegalStateException(
					"Cannot compose all command in current game state: "
							+ gameStateCode);
		}
		ret.setE20(allContent);
		return ret;
	}

	/**
	 * Gets the aR others for all.
	 *
	 * @return the aR others for all
	 */
	private A12 getAROthersForAll() {
		A1 state = null;
		switch (getPodkidnoyStateMachine().getState()) {
		case GS_DURAK_ROUND_STARTED:
			state = A1.A_2;
			break;
		case GS_DURAK_DEFENDING_FIRST:
		case GS_DURAK_DEFENDING_AGAIN:
			state = A1.A_3;
			break;
		case GS_DURAK_PICKED_UP:
		case GS_DURAK_WAITING_MORE_CARDS:
			state = A1.A_4;
			break;
		default:
			break;
		}
		if (state != null) {
			A16 ret = new A16();
			ret.setA17(getPodkidnoyPlayerStore().getActor().getID());
			ret.setType(state);
			return ret;
		} else {
			return null;
		}
	}

	/**
	 * Gets the game state.
	 *
	 * @return the game state
	 */
	private E14 getGameState() {

		E14 ret = new E14();
		ret.setE15(new Integer(getPodkidnoyData().getPack().size()));
		ret.setE17(null);
		ret.setE18(data.getTableCards());
		ret.setE16(data.getTrumpCard());
		return ret;
	}

	/**
	 * Gets the playing seat states.
	 *
	 * @return the playing seat states
	 */
	private List<F26> getPlayingSeatStates() {
		List<F26> ret = new ArrayList<F26>();
		PodkidnoyPlayerStore ps = getPodkidnoyPlayerStore();
		LinkedList<PodkidnoySeat> players = ps.getAllSeats();
		Iterator<PodkidnoySeat> it = players.iterator();
		List<F1> seatIDs = new ArrayList<F1>(Arrays
				.asList(F1.values()));
		while (it.hasNext()) {
			PodkidnoySeat cur = it.next();
			F26 st = new F26();
			/* seat ID code */
			F1 curID = cur.getID();
			st.setF27(curID);
			seatIDs.remove(curID);

			/* seat state */
			F14 seatState;
			if (ps.getWinnersAll().contains(cur)) {
				F15 iss = new F15();
				iss.setF16(false);
				seatState = iss;
			} else {
				F17 pss = new F17();
				pss.setF18(cur.getNumOfCards());
				seatState = pss;
			}
			st.setF29(seatState);

			/* seat user */
			F9 sut;
			if (!cur.isEmpty()) {
				F10 hsu = new F10();
				hsu.setF11(cur.getServerUser().getF11());
				sut = hsu;
			} else {
				F12 bsu = new F12();
				sut = bsu;
			}
			st.setF28(sut);

			ret.add(st);
		} // while

		/* unused seats */
		Iterator<F1> itUnused = seatIDs.iterator();
		while (itUnused.hasNext()) {
			F26 st = new F26();
			F1 cur = itUnused.next();
			F19 uss = new F19();
			st.setF27(cur);
			st.setF29(uss);
			ret.add(st);
		}
		return ret;
	}

	/**
	 * Gets the waiting state seats.
	 *
	 * @return the waiting state seats
	 */
	private List<F26> getWaitingStateSeats() {
		List<F26> ret = new ArrayList<F26>();
		PodkidnoyPlayerStore ps = getPodkidnoyPlayerStore();
		LinkedList<PodkidnoySeat> players = ps.getAllSeats();
		Iterator<PodkidnoySeat> it = players.iterator();
		List<F1> seatIDs = new ArrayList<F1>(Arrays
				.asList(F1.values()));
		while (it.hasNext()) {
			PodkidnoySeat cur = it.next();
			F26 st = new F26();
			/* seat ID code */
			F1 curID = cur.getID();
			st.setF27(curID);
			seatIDs.remove(curID);

			/* seat state */
			F15 iss = new F15();
			iss.setF16(cur.isNewGameStart());
			st.setF29(iss);

			/* seat user */
			F9 sut;
			if (!cur.isEmpty()) {
				F10 hsu = new F10();
				hsu.setF11(cur.getServerUser().getF11());
				sut = hsu;
			} else {
				F12 bsu = new F12();
				sut = bsu;
			}
			st.setF28(sut);

			ret.add(st);
		} // while
		/* unused seats */
		Iterator<F1> itUnused = seatIDs.iterator();
		while (itUnused.hasNext()) {
			F26 st = new F26();
			F1 cur = itUnused.next();
			F19 uss = new F19();
			st.setF27(cur);
			st.setF29(uss);
			ret.add(st);
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof PodkidnoySeat) {
			PodkidnoySeat s = (PodkidnoySeat) o;
			E19 update = new E19(); // SCType.SEAT_CHANGE);
			update.setC13(C1.C_11);
			update.setE21(E11.E_12);
			E31 userUpd = new E31();
			userUpd.setE29(E1.E_2);
			F1 curCode = s.getID();
			userUpd.setE32(curCode);
			ServerUser serverUser = s.getServerUser();
			F9 newUser;
			if (serverUser == null) {
				F12 bsu = new F12();
				newUser = bsu;
			} else {
				F10 hsu = new F10();
				newUser = hsu;
				hsu.setF11(serverUser.getF11());
			}
			userUpd.setE33(newUser);
			update.setE20(userUpd);
			sendCommandToAll(update);
		} else {
			throw new IllegalStateException(""
					+ this.getClass().getCanonicalName() + " was listening to "
					+ o.getClass().getCanonicalName());
		}
	}

	/**
	 * TODO Add player cards to actor.
	 *
	 * @return the all
	 */
	public synchronized E19 getAll() {
		E19 ret = getGameCommandStub();
		E40 all = new E40();
		all.setE29(E1.E_10);
		ret.setE20(all);
		G1 state = getPodkidnoyStateMachine().getTableState();
		LinkedList<PodkidnoySeat> PodkidnoySeats = getPodkidnoyPlayerStore()
				.getAllSeats();
		Iterator<PodkidnoySeat> it = PodkidnoySeats.iterator();
		while (it.hasNext()) {
			PodkidnoySeat cur = it.next();
			F26 st = new F26();
			st.setF27(cur.getID());
			ServerUser su = cur.getServerUser();
			all.getE43().add(st);
			if (su != null) {
				F6 u = su.getF11();
				F10 hsu = new F10();
				hsu.setF11(u);
				st.setF28(hsu);
				break;
			} else {
				F12 bsu = new F12();
				st.setF28(bsu);
			}
			F14 ss;
			if (getPodkidnoyPlayerStore().getRoundPlayers().contains(cur)) {
				F17 playSeatState = new F17();
				playSeatState.setF18(cur.getNumOfCards());
				ss = playSeatState;
			} else {
				F15 idleState = new F15();
				if (G1.G_3.equals(state)) {
					idleState.setF16(false);
				} else {
					idleState.setF16(cur.isNewGameStart());
				}
				ss = idleState;
			}
			st.setF29(ss);
		}

		all.setE45(true);
		all.setE44(null);
		switch (state) {
		case G_3:
			E14 gameState = new E14();
			gameState.setE15(data.getPack().size());
			gameState.setE17(new B26());
			gameState.setE18(data.getTableCards());
			gameState.setE16(data.getTrumpCard());
			all.setE42(gameState);
			all.setE41(G1.G_3);
			break;

		default:
			all.setE41(G1.G_2);
			break;
		}
		return ret;
	}

	/**
	 * Gets the game command stub.
	 *
	 * @return the game command stub
	 */
	public E19 getGameCommandStub() {
		E19 ret = new E19();
		ret.setC13(C1.C_11);
		ret.setE21(E11.E_12);
		return ret;
	}

	/**
	 * Gets the seat.
	 *
	 * @param rnd the rnd
	 * @return the seat
	 */
	public PodkidnoySeat getSeat(int rnd) {
		int numOfPlayers = getPodkidnoyConf().getG22();
		if (rnd < 0) {
			throw new IllegalArgumentException("Illegal seat index: " + rnd);
		}
		if (rnd >= numOfPlayers) {
			int maxIndex = numOfPlayers - 1;
			throw new IllegalArgumentException(
					"Seat index out of allowed range [0-" + maxIndex + "]: "
							+ rnd);
		}
		F1 id = F1.values()[rnd];
		PodkidnoySeat ret = getPodkidnoyPlayerStore().getSeatByID(id);
		return ret;
	}

	/**
	 * Drive test games.
	 *
	 * @param _numberOfGames the _number of games
	 */
	public void driveTestGames(final int _numberOfGames) {
		if (!isTest()) {
			throw new IllegalStateException("Test is false");
		}
		LinkedList<PodkidnoySeat> all = getPodkidnoyPlayerStore().getAllSeats();
		if (all.size() < 2) {
			throw new IllegalStateException("Less than 2 bots");
		}

		mNumberOfTestGames.set(_numberOfGames);
		
		String s = "Starting to play " + mNumberOfTestGames + " test games...";
		logGame.info(s);

		setBotsStart(true);
		PodkidnoySeat first = all.getFirst();
		D4 startCommand = getStartCommand();
		// handleBotMove(first.getID(), startCommand);
		getCommandHandler().queueGameCommand(new GameCommandImpl(startCommand, first.getID()));
	}

	@Override
	public ICommandHandler getCommandHandler() {
		return commandHandler;
	}

	@Override
	public void reset() {
		getPodkidnoyPlayerStore().reset();
		getPodkidnoyData().reset(getPodkidnoyConf().getG25());
		getPodkidnoyStateMachine().reset();
		/*
		reset();
//game.getData().reset(); done when game begins??
		game.getStateMachine().setState(PodkidnoyGameStateType.GS_DURAK_NOT_INITIALIZED);
		// endGame();
		 * 
		 */
	}

}
