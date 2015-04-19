/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod;


import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReentrantLock;


import com.littlech.cl.Utils;
import com.littlech.gen.a.A18;
import com.littlech.gen.a.A5;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B23;
import com.littlech.gen.b.B26;
import com.littlech.gen.b.B28;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D1;
import com.littlech.gen.d.D19;
import com.littlech.gen.d.D22;
import com.littlech.gen.d.D24;
import com.littlech.gen.d.D4;
import com.littlech.gen.e.E1;
import com.littlech.gen.e.E11;
import com.littlech.gen.e.E19;
import com.littlech.gen.e.E24;
import com.littlech.gen.e.E37;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F15;
import com.littlech.gen.f.F20;
import com.littlech.gen.g.G1;
import com.littlech.gen.g.G13;
import com.littlech.gs.GameServer;
import com.littlech.gs.game.ICommandHandler;
import com.littlech.gs.game.IGameCommand;
import com.littlech.gs.game.RulesCheckResult;
import com.littlech.gs.test.Test;
import com.littlech.gs.user.ServerUser;

import static com.littlech.gs.gate.Gateway.*;
// TODO: Auto-generated Javadoc
/**
 * The Class PodkidnoyCommandHandler.
 */
public class PodkidnoyCommandHandler implements ICommandHandler, Runnable {

	private ConcurrentLinkedQueue<IGameCommand> commandQueue = new ConcurrentLinkedQueue<IGameCommand>();
	private Podkidnoy game;
	private ReentrantLock lock = new ReentrantLock();
	
	/**
	 * Invariant: if the task for this handler is executed or scheduled to be executed 
	 * then beingProcessed must be true
	 */
	private volatile boolean beingProcessed = false;

	public PodkidnoyCommandHandler(final Podkidnoy _game) {
		game = _game;
	}

	public ReentrantLock getLock() {
		return lock;
	}

	private ConcurrentLinkedQueue<IGameCommand> getCommandQueue() {
		return commandQueue;
	}

	@Override
	public Runnable getTask() {
		return this;
	}

	@Override
	public void queueGameCommand(IGameCommand command) {
		getCommandQueue().add(command);
		getLock().lock();
		if (!beingProcessed) {
			getExecutor().execute(getTask());
		}
		getLock().unlock();
	}

	private Executor getExecutor() {
		ScheduledExecutorService mBotsService;
		if (game.isTest()) {
			mBotsService = Test.getService();
		} else {
			mBotsService = GameServer.getInstance().getService();
		}
		return mBotsService;
	}

	@Override
	public void run() {
		String oldName = Thread.currentThread().getName();
		
		Thread.currentThread().setName(getThreadID());
		try {
		beingProcessed = true;
		game.getLock().lock();
		int processedCommands = 0;
		while (processedCommands++ < 20 && !getCommandQueue().isEmpty()) {
			IGameCommand cur = getCommandQueue().poll();
			if (cur.isFromUser()) {
				processUserCommand(cur);
			} else if (cur.isBotMove()) {
				processBotCommand(cur);
			} else if (cur.isFromFramework()) {
				throw new UnsupportedOperationException(
						"Framework commands are not supported yet");
			} else {
				throw new IllegalArgumentException("Unsupported game command: "
						+ cur);
			}
		}
		game.getLock().unlock();

		getLock().lock();
		if (getCommandQueue().isEmpty()) {
			beingProcessed = false;
		} else {
			getExecutor().execute(getTask());
		}
		getLock().unlock();
		} catch (Exception e) {
e.printStackTrace();
		}
		Thread.currentThread().setName(oldName);
	}

	private String getThreadID() {
		String ret = "" + game.getConfiguration().getG18() + "-" + 
	game.getId();
		return ret;
	}
	
	public static String mapGameIDToName(final G13 id) {
		switch (id) {
		case G_14:
			return "Pod";

		default:
			throw new IllegalArgumentException("Unknown id: " + id);
		}
	}

	private void processBotCommand(IGameCommand gameCommand) {
		D4 received = gameCommand.getCommand();
		F1 botSeat = gameCommand.getBotSeatID();
		game.handleGameCommand(botSeat, received);
	}

	private void processUserCommand(IGameCommand gameCommand) {
		ServerUser su = gameCommand.getUser();
		D4 received = gameCommand.getCommand();
		RulesCheckResult checkResult1 = game.getRules().isAllowed(su, received);
		if (checkResult1.isAllowed()) {
			game.handleCommand(su, received);
		} else {
			handleFail(checkResult1);
			E19 failureCommand = getGameFailureCommand(received.getC13(),
					checkResult1.getReason());
			try {
				su.write(failureCommand);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handle fail.
	 * 
	 * @param checkResult
	 *            the check result
	 */
	private void handleFail(RulesCheckResult checkResult) {
		checkResult.getException().printStackTrace();
	}

	/**
	 * Gets the game failure command.
	 * 
	 * @param _type
	 *            the _type
	 * @param _msg
	 *            the _msg
	 * @return the game failure command
	 */
	private E19 getGameFailureCommand(final C1 _type, final String _msg) {
		E19 ret = new E19();
		ret.setC13(_type);
		ret.setE21(E11.E_13);
		ret.setE22(_msg);
		ret.setE20(new E24());
		return ret;
	}

	/**
	 * Handle game command.
	 * 
	 * @param _game
	 *            the _game
	 * @param seatID
	 *            the seat id
	 * @param cmd
	 *            the cmd
	 */
	public static void handleGameCommand(Podkidnoy _game, F1 seatID, D4 cmd) {
		// System.out.println("-3-");
		D19 gameContent = (D19) cmd.getD5();
		D1 gameCommandCode = gameContent.getD20();
		if (gameCommandCode.equals(D1.D_3)) {
			G1 gameState = _game.getStateMachine().getTableState();
			if (gameState.equals(G1.G_3)) {
				D22 gameContentContent = gameContent.getD21();
				D24 podAction = (D24) gameContentContent;
				StringBuilder sbInfo = new StringBuilder();
				PodkidnoyGameStateType durakGS = _game
						.getPodkidnoyStateMachine().getState();
				A18 podActionAction = podAction.getD25();
				A5 butCode = podActionAction.getA19();

				A18 action = new A18();
				action.setA19(butCode);

				switch (durakGS) {
				case GS_DURAK_ROUND_STARTED:
					if (butCode.equals(A5.A_6)) {
						B26 firstCards = podActionAction.getA21();
						PodkidnoySeat roundFirst = _game
								.getPodkidnoyPlayerStore().getFirst();
						Utils.removeCardsFromList(roundFirst.getCards(),
								firstCards.getB27());
						for (B20 ct : firstCards.getB27()) {
							B23 p = new B23();
							p.setB25(ct);
							_game.getPodkidnoyData().getTableCards().getB29()
									.add(p);
						}
						action.setA21(firstCards);
						sbInfo
								.append(seatID
										+ " laid first "
										+ Utils
												.getPresentableFormatForCards(firstCards));

						if (_game.isMoreThanOne()) {
							// System.out.println("ZZZ");
							_game.getPodkidnoyPlayerStore().turnToDefender();
							_game
									.getPodkidnoyStateMachine()
									.setState(
											PodkidnoyGameStateType.GS_DURAK_DEFENDING_FIRST);
							break;
						} else {
							_game
									.getPodkidnoyStateMachine()
									.setState(
											PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED);
						}
						break;
					}
				case GS_DURAK_DEFENDING_FIRST:
					if (butCode.equals(A5.A_7)) {
						B26 sendingCards = podActionAction.getA21();
						Utils.removeCardsFromList(_game
								.getPodkidnoyPlayerStore().getDefender()
								.getCards(), sendingCards.getB27());

						for (B20 ct : sendingCards.getB27()) {
							B23 p = new B23();
							p.setB25(ct);
							_game.getPodkidnoyData().getTableCards().getB29()
									.add(p);
						}
						action.setA21(sendingCards);
						sbInfo
								.append(seatID
										+ " sent with "
										+ Utils
												.getPresentableFormatForCards(sendingCards));

						/*
						 * 
						 * if (butCode.equals(PodButtonCode.SEND)) { if
						 * (isMoreThanOne()) { if
						 * (data.playerStore.getDefender(false
						 * ).getCards().size() > 0) {
						 * data.playerStore.firstToDefender(true); }
						 * data.playerStore.turnToDefenderForSend(); break; } }
						 */

						if (_game.isMoreThanOne()) {
							// XXX sth wrong here!
							// System.out.println("VKVKVK 1 " +
							// _game.getPodkidnoyPlayerStore());
							if (_game.getPodkidnoyPlayerStore().getDefender(
									false).getCards().size() > 0) {
								// System.out.println("VKVKVK 2 " +
								// _game.getPodkidnoyPlayerStore());
								_game.getPodkidnoyPlayerStore()
										.firstToDefender(true);
							}
							// System.out.println("VKVKVK 3 " +
							// _game.getPodkidnoyPlayerStore());
							_game.getPodkidnoyPlayerStore()
									.turnToDefenderForSend();
							// System.out.println("VKVKVK 3.5 " +
							// _game.getPodkidnoyPlayerStore());
							// _game.checkThatLastActorIsNotOnTurn(seatID);
							// System.out.println("VKVKVK 3.7 " +
							// _game.getPodkidnoyPlayerStore());
						} else {
							// System.out.println("VKVKVK 4");
							_game
									.getPodkidnoyStateMachine()
									.setState(
											PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED);
						}
						break;
					}
					// falls through
				case GS_DURAK_DEFENDING_AGAIN:
					if (butCode.equals(A5.A_8)) {
						B28 defendedTable = podActionAction.getA20();
						B26 killers = _game.getKillers();
						Utils.removeCardsFromList(killers.getB27(), Utils
								.getDefendingCards(defendedTable.getB29()));
						List<B20> defendersCards = _game
								.getPodkidnoyPlayerStore().getDefender()
								.getCards();
						defendersCards.clear();
						defendersCards.addAll(killers.getB27());
						B28 serverTable = _game.getPodkidnoyData()
								.getTableCards();
						serverTable.getB29().clear();
						serverTable.getB29().addAll(defendedTable.getB29());
						action.setA20(defendedTable);
						sbInfo.append(seatID
								// _game.getPodkidnoyData().playerStore.getDefender().getInfoName()
								+ " defended table to "
								+ Utils.getPresentableFormat(defendedTable
										.getB29()));

						if (_game.isMoreCanBeAdded() && _game.isMoreThanOne()) {
							_game.getPodkidnoyPlayerStore()
									.turnToFirstAttacker();
							_game
									.getPodkidnoyStateMachine()
									.setState(
											PodkidnoyGameStateType.GS_DURAK_WAITING_MORE_CARDS);
						} else {
							_game
									.getPodkidnoyStateMachine()
									.setState(
											PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED);
						}
						break;
					}
					if (butCode.equals(A5.A_9)) {
						sbInfo.append(seatID
								+ " picked "
								+ Utils.getPresentableFormat(_game
										.getPodkidnoyData().getTableCards()
										.getB29()));

						if (_game.isMoreCanBeAdded() && _game.isMoreThanOne()) {
							_game.getPodkidnoyPlayerStore()
									.turnToFirstAttacker();
							_game.getPodkidnoyStateMachine().setState(
									PodkidnoyGameStateType.GS_DURAK_PICKED_UP);
						} else {
							_game
									.getPodkidnoyStateMachine()
									.setState(
											PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED);
						}
						break;
					}
					_game.throwIllegalArgumentException(durakGS, butCode);
				case GS_DURAK_WAITING_MORE_CARDS:
					if (butCode.equals(A5.A_10)) {
						B26 cards = podActionAction.getA21();
						Utils.removeCardsFromList(_game
								.getPodkidnoyPlayerStore().getActor()
								.getCards(), cards.getB27());

						for (B20 c : cards.getB27()) {
							B23 p = new B23();
							p.setB25(c);
							_game.getPodkidnoyData().getTableCards().getB29()
									.add(p);
						}
						action.setA21(cards);
						sbInfo.append(seatID + " added bottom cards "
								+ Utils.getPresentableFormatForCards(cards));

						if (_game.isMoreThanOne()) {
							// Should not go into S_DURAK_PICKED_UP ->
							// CMD_DURAK_ADD, as
							// it has stronger check
							_game.getPodkidnoyPlayerStore().turnToDefender();
							_game
									.getPodkidnoyStateMachine()
									.setState(
											PodkidnoyGameStateType.GS_DURAK_DEFENDING_AGAIN);
							break;
						} else {
							_game
									.getPodkidnoyStateMachine()
									.setState(
											PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED);
						}
						break;
					}
				case GS_DURAK_PICKED_UP:
					if (butCode.equals(A5.A_10)) {
						B26 cards = podActionAction.getA21();
						Utils.removeCardsFromList(_game
								.getPodkidnoyPlayerStore().getActor()
								.getCards(), cards.getB27());

						for (B20 c : cards.getB27()) {
							B23 p = new B23();
							p.setB25(c);
							_game.getPodkidnoyData().getTableCards().getB29()
									.add(p);
						}
						action.setA21(cards);
						sbInfo.append(seatID + " added bottom cards "
								+ Utils.getPresentableFormatForCards(cards));

						if (_game.isMoreCanBeAdded() && _game.isMoreThanOne()) {
							_game.getPodkidnoyPlayerStore().increaseAttacker();
							break;
						} else {
							_game
									.getPodkidnoyStateMachine()
									.setState(
											PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED);
						}
						break;
					} else if (butCode.equals(A5.A_11)) {
						sbInfo.append(seatID + " passed");
						if (_game.isMoreCanBeAdded() && _game.isMoreThanOne()) {
							_game.getPodkidnoyPlayerStore().increaseAttacker();
						} else {
							_game
									.getPodkidnoyStateMachine()
									.setState(
											PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED);
						}
						break;
					}
					_game.throwIllegalArgumentException(durakGS, butCode);
				default:
					_game.throwIllegalArgumentException(durakGS, butCode);
				} // switch (durakGS)
				String inf = sbInfo.toString();
				if (logGame.isDebugEnabled()) {
				logGame.info(inf);
				}
				if (action == null) {
					throw new NullPointerException("Action is null for "
							+ butCode);
				}
				// XXX
				// try {

				_game.sendGameUpdates(action, seatID);
				/*
				 * } catch (RuntimeException e) {
				 * System.out.println("An error occured while propagating update "
				 * + action + " from seat " + seatID); throw e; }
				 */

				PodkidnoyGameStateType dgs = _game.getPodkidnoyStateMachine()
						.getState();
				if (dgs.equals(PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED)) {
					_game.finishRound();
				}

			} // if (gameState.equals(GameStateType.PLAYING))
			else {
				throw new IllegalArgumentException("Game in state " + gameState
						+ " got game play command " + cmd);
			}
		} // if (commandType.isDurakPlayCommand())
		else if (gameCommandCode.equals(D1.D_2)) {
			PodkidnoySeat s = _game.getPodkidnoyPlayerStore().getSeatByID(
					seatID);
			s.setNewGameStart(true);

			E37 seatStateUpd = new E37();
			seatStateUpd.setE29(E1.E_3);
			F20 cur = new F20();
			cur.setF21(seatID);
			F15 pss = new F15();
			pss.setF16(true);
			cur.setF22(pss);
			seatStateUpd.getE39().add(cur);
			E19 seatStateUpdCmd = new E19();
			seatStateUpdCmd.setC13(C1.C_11);
			seatStateUpdCmd.setE20(seatStateUpd);
			seatStateUpdCmd.setE21(E11.E_12);
			_game.sendCommandToAll(seatStateUpdCmd);

			boolean allStart = true;
			for (PodkidnoySeat player : _game.getPodkidnoyPlayerStore()
					.getAllSeats()) {
				if (!player.isNewGameStart()) {
					allStart = false;
					break;
				}
			}
			if (_game.isAllHumansStart()
					|| (allStart && !_game.getPodkidnoyStateMachine()
							.getTableState().equals(G1.G_3))) {
				_game.startNewGame();
				// XXX
				_game.sendNewGameUpdates();
				_game.sendNewRoundUpdates(); // true);
			}
		}

	}

}
