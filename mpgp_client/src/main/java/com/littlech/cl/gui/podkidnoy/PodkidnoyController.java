/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.littlech.cl.Controller;
import com.littlech.cl.Utils;
import com.littlech.cl.gui.IScreenController;
import com.littlech.cl.gui.ScreenControllerType;
import com.littlech.cl.gui.podkidnoy.component.SeatBox;
import com.littlech.cl.gui.podkidnoy.component.StartButton;
import com.littlech.cl.gui.podkidnoy.component.TablePanel;
import com.littlech.cl.gui.podkidnoy.handler.ButtonsPanelButtonHandler;
import com.littlech.cl.gui.podkidnoy.handler.CardsPanelButtonHandler;
import com.littlech.cl.gui.podkidnoy.handler.ControlPanelButtonHandler;
import com.littlech.cl.gui.podkidnoy.handler.SitButtonHandler;
import com.littlech.cl.gui.podkidnoy.handler.StartButtonHandler;
import com.littlech.cl.gui.podkidnoy.handler.TablePanelButtonHandler;
import com.littlech.cl.gui.podkidnoy.util.DefendingStateMemory;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.cl.gui.podkidnoy.util.Self;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;

import com.littlech.gen.a.A1;
import com.littlech.gen.a.A12;
import com.littlech.gen.a.A13;
import com.littlech.gen.a.A16;
import com.littlech.gen.a.A18;
import com.littlech.gen.a.A5;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B23;
import com.littlech.gen.b.B26;
import com.littlech.gen.b.B28;
import com.littlech.gen.b.B6;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D1;
import com.littlech.gen.d.D17;
import com.littlech.gen.d.D19;
import com.littlech.gen.d.D23;
import com.littlech.gen.d.D24;
import com.littlech.gen.d.D4;
import com.littlech.gen.d.D8;
import com.littlech.gen.e.*;
import com.littlech.gen.f.*;
import com.littlech.gen.g.G1;

/**
 * 
 * Controller for Podkidnoy GUI
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class PodkidnoyController implements IScreenController, ActionListener {

	/**
	 * Client application controller
	 */
	private final Controller mController;

	/**
	 * Podkidnoy screen
	 */
	private PodkidnoyScreen screen;

	/**
	 * Podkidnoy model
	 */
	private PodkidnoyModel model;

	/**
	 * Keeps count of cards added to table cards by player; e.g. when player is
	 * adding cards to table after defender has picked up and there were
	 * undefended bottom cards lying on the table, cards added helps to
	 * differentiate between previous undefended bottom cards and added cards
	 */
	private final List<B20> cardsAdded = new ArrayList<B20>();

	/**
	 * Memorizes initial table cards before player starts defending table; used to
	 * revert to previous table cards after player decides to pick up
	 */
	private DefendingStateMemory m;

	/**
	 * Constructor
	 * 
	 * @param _controller
	 *          Client application controller
	 */
	public PodkidnoyController(final Controller _controller) {
		mController = _controller;
		screen = new PodkidnoyScreen(mController.getImageLoader());

		F6 selfUser = new F6();
		selfUser.setF8("actor.png");
		// XXX
		selfUser.setF7("ToBeReplaced");

		model = new PodkidnoyModel(selfUser);
		initObservers();
		initActionListeners();

		getScreen().getControlPanel().getOptionsButton().setEnabled(false);
	}

	/**
	 * Returns Podkidnoy screen
	 * 
	 * @return Podkidnoy screen
	 */
	public PodkidnoyScreen getScreen() {
		return screen;
	}

	/**
	 * Returns Podkidnoy screen's model
	 * 
	 * @return Podkidnoy model
	 */
	private PodkidnoyModel getModel() {
		return model;
	}

	/**
	 * Returns start button
	 * 
	 * @return Button
	 */
	public StartButton getStart() {
		return getScreen().
		// getStartAndLeave().
				getStartButton();
	}

	/**
	 * Returns leave button
	 * 
	 * @return Button
	 */
	/*
	 * public JButton getLeave() { return
	 * getScreen().getStartAndLeave().getLeave(); }
	 */

	/**
	 * Initializes observers
	 */
	private void initObservers() {
		model.getCards().addObserver(getScreen().getCards());
		model.getTable().addObserver(getScreen().getTable());
		model.getState().addObserver(getScreen().getButtons());
		model.getSelf().addObserver(getScreen().getButtons());

		// model.getState().addObserver(getScreen().getStartAndLeave());
		model.getState().addObserver(getScreen().getPackAndTrump());
		model.getState().addObserver(getScreen().getCards());
		model.getState().addObserver(getScreen().getTable());
		ObservableValue<Self> modelSelf = model.getSelf();
		ObservableValue<G1> gameState = model.getState();
		ObservableValue<Boolean> isSitting = model.getIsSitting();
		for (F1 id : F1.values()) {
			ObservableValue<F9> user = model.getUser(id);
			ObservableValue<F14> state = model.getSeat(id);

			SeatBox cur = new SeatBox(mController.getImageLoader(), id, user, state, gameState, modelSelf, isSitting);
			getScreen().getSeats().put(id, cur);

			// / PlayerBox cur = getScreen().getSeat(id);
			user.addObserver(cur.getUserPart());
			state.addObserver(cur.getStatePart());
			// modelSelf.addObserver(cur); XXX ? what to do
			user.addObserver(cur);
			state.addObserver(cur);
			gameState.addObserver(cur);
		}
		/*
		 * model.getOnTurn().addObserver(getScreen().getSeat(SeatIDCode.ONE));
		 * model.getOnTurn().addObserver(getScreen().getSeat(SeatIDCode.TWO));
		 * model.getOnTurn().addObserver(getScreen().getSeat(SeatIDCode.THREE));
		 * model.getOnTurn().addObserver(getScreen().getSeat(SeatIDCode.FOUR));
		 */
		model.getOnTurn().addObserver(getScreen().getSeat(F1.F_2).getStatePart());
		model.getOnTurn().addObserver(getScreen().getSeat(F1.F_3).getStatePart());
		model.getOnTurn().addObserver(getScreen().getSeat(F1.F_4).getStatePart());
		model.getOnTurn().addObserver(getScreen().getSeat(F1.F_5).getStatePart());

		model.getPack().addObserver(getScreen().getPackAndTrump());
		model.getTrump().addObserver(getScreen().getPackAndTrump().getTrump());
	}

	/**
	 * Initializes action listeners
	 */
	private void initActionListeners() {
		/*
		 * getScreen().getStartAndLeave().getStart().addActionListener(this);
		 * getScreen().getStartAndLeave().getLeave().addActionListener(this);
		 */
		getScreen().getCards().getButtonHandler().addActionListener(this);
		getScreen().getTable().getButtonHandler().addActionListener(this);
		getScreen().getButtons().getButtonHandler().addActionListener(this);

		getScreen().getControlPanel().addActionListenerToButtons(new ControlPanelButtonHandler(this));
		getScreen().getStartButton().addActionListener(new StartButtonHandler(this));
		for (F1 id : F1.values()) {
			getScreen().getSeats().get(id).getSitButton().addActionListener(new SitButtonHandler(this, id));
		}
	}

	@Override
	public Container getPanel() {
		return getScreen().getPanel();
	}

	@Override
	public ScreenControllerType getType() {
		return ScreenControllerType.PODKIDNOY;
	}

	@Override
	public void startShow() {
		getScreen().setVisible(true);
		reset();
		getScreen().getControlPanel().getCloseButton().setEnabled(true);
	}

	@Override
	public void stopShow() {
	}

	// TODO add info
	@Override
	public boolean handleSC(E19 sc) {
		switch (sc.getC13()) {
		case C_2:
			E46 loginContent = (E46) sc.getE20();
			F6 u = loginContent.getE47();
			getModel().getSelf().getValue().setUser(u);
			return true;
		case C_11:
			return handleGameCommand(sc);
		case C_9:
			return handleSitDownCommand(sc);
		default:
			return false;
		}
	}

	private boolean handleSitDownCommand(final E19 sc) {
		E11 c = sc.getE21();
		switch (c) { // error code
		case E_13: // fail
			mController.addDialog(sc.getE22());
			break;
		case E_12:
			// XXX if sit down is OK, then reset sit down buttons
			getScreen().setEnabledToSitButtons(true);
break;
		default:
			throw new IllegalArgumentException("Unknown: " + c);
		}
		return true;
	}

	private boolean handleGameCommand(E19 sc) {
		E28 content = (E28) sc.getE20();
		E1 gameCode = content.getE29();
		// boolean ended = false;
		switch (gameCode) {
		case E_2:
			E31 upd = (E31) content;
			F1 id = upd.getE32();
			F9 nu = upd.getE33();
			F1 selfId3 = model.getSelf().getValue().getId();
			G1 gameState3 = model.getState().getValue();
			boolean waiting = (gameState3 != null && gameState3.equals(G1.G_2));
				if (!id.equals(selfId3)) {
					model.getUser(id).setValue(nu);
					if (waiting) {
					ObservableValue<F14> curSeatState = model.getSeat(id);
					F15 idle = new F15();
					idle.setF16(false);
					curSeatState.setValue(idle);
					}
				} else {
					model.getSelfUser().setValue(nu);
					if (waiting) {
						ObservableValue<F14> curSeatState = model.getSelfSeat();
						F15 idle = new F15();
						idle.setF16(false);
						curSeatState.setValue(idle);
						}
				}
			break;
		case E_3:
			E37 seatStateUpd = (E37) content;
			Iterator<F20> it = seatStateUpd.getE39().iterator();
			F1 selfId4 = model.getSelf().getValue().getId();
			while (it.hasNext()) {
				F20 cur = it.next();
				if (!cur.getF21().equals(selfId4)) {
					model.getSeat(cur.getF21()).setValue(cur.getF22());
				} else {
					model.getSelfSeat().setValue(cur.getF22());
				}
			}
			break;
		case E_4:
			model.getState().setValue(G1.G_3);
			model.getOnTurn().setValue(null);
			model.getCards().setValue(new ArrayList<B20>());
			model.getTable().setValue(new ArrayList<B23>());
			model.getTable().notifyListeners();
			// System.out.println("self = " + selfId);
			for (F1 idSeat : F1.values()) {
				// System.out.println("idSeat = " + idSeat);
				F17 pl = new F17();
				pl.setF18(0);
				if (!isSelfUser(idSeat)) { // idSeat.equals(selfId)) {
					ObservableValue<F14> cur = model.getSeat(idSeat);
					System.out.println("cur value = " + cur.getValue());
					if (!(cur.getValue() instanceof F19)) {
						// System.out.println("pl");
						cur.setValue(pl);
					}
				} else {
					model.getSelfSeat().setValue(pl);
				}
			}
			break;
		case E_6:
			E34 contentActionUpdate = (E34) content;

			B26 playerCardsAU = contentActionUpdate.getE36().getA21();
			B28 tableCardsAU = contentActionUpdate.getE36().getA20();
			F1 actor = contentActionUpdate.getE35();
			if (!isSelfUser(actor)) {
				/* Not a self action confirmation */
			if (playerCardsAU != null) {
				Utils.addBottomsToTable(model.getTable().getValue(), playerCardsAU.getB27());
				model.getTable().notifyListeners();
				ObservableValue<F14> actorSeat = model.getSeat(actor);
				F14 actorSeatState = actorSeat.getValue();
				F17 actorPlayingSeatState = (F17) actorSeatState;
				int newCardsNum = actorPlayingSeatState.getF18() - playerCardsAU.getB27().size();
				actorPlayingSeatState.setF18(newCardsNum);
				actorSeat.notifyListeners();
			} else if (tableCardsAU != null) {
				int previousDefenders = (model.getTable().getValue() == null) ? 0 : Utils.getDefendingCards(model.getTable().getValue()).size();
				model.getTable().setValue(tableCardsAU.getB29());
				ObservableValue<F14> actorSeat = model.getSeat(actor);
				F14 actorSeatState = actorSeat.getValue();
				F17 actorPlayingSeatState = (F17) actorSeatState;
				int newCardsNum = actorPlayingSeatState.getF18() + previousDefenders - Utils.getDefendingCards(tableCardsAU.getB29()).size();
				actorPlayingSeatState.setF18(newCardsNum);
				actorSeat.notifyListeners();
			}
			}
			/* Action request may be updated only with memory snap after action */
			A12 ar3 = contentActionUpdate.getE30();
			B26 memoryPlayer = new B26();
			memoryPlayer.getB27().addAll(Utils.getCopy(model.getCards().getValue()));
			B28 memoryTable = new B28();
			memoryTable.getB29().addAll(Utils.getCopyOfTable(model.getTable().getValue()));
			updateActionRequest(ar3, memoryPlayer, memoryTable);
			break;
		case E_5:
			getCardsAdded().clear();
			E37 gameUpd1 = (E37) content;
			updateState(gameUpd1);
			// XXX
			updateIdles();
			break;
		case E_8:
			/* Emptying table */
			model.getTable().setValue(new ArrayList<B23>());
			/* Updating seats if the defender picked up */
			E37 contentGame1 = (E37) content;
			Iterator<F20> it4 = contentGame1.getE39().iterator();
			while (it4.hasNext()) {
				F20 cur = it4.next();
				model.getSeat(cur.getF21()).setValue(cur.getF22());
			}
			break;
		case E_7:
			E28 contentGame = content;
			A12 ar2 = contentGame.getE30();
			updateActionRequest(ar2, null, null);
			break;
		case E_9:
			// ended = true;
			model.getState().setValue(G1.G_2);
			model.getOnTurn().setValue(null);
			model.getCards().setValue(new ArrayList<B20>());
			model.getTable().setValue(new ArrayList<B23>());
			F1 selfId2 = model.getSelf().getValue().getId();
			for (F1 idSeat : F1.values()) {
				F15 pl = new F15();
				if (!idSeat.equals(selfId2)) {
					ObservableValue<F14> cur = model.getSeat(idSeat);
					if (!(cur.getValue() instanceof F19)) {
						cur.setValue(pl);
					}
				} else {
					// What if self is not sitting?
					model.getSelfSeat().setValue(pl);
				}
			}
			break;
		case E_10:
			getCardsAdded().clear();

			E40 allUpd = (E40) content;

			Self self = model.getSelf().getValue();
			F1 selfID = allUpd.getE44();
			self.setId(selfID);
			model.getSelf().setValue(self); // for updating
			model.getIsSitting().setValue(allUpd.isE45());

			/* start of seats */
			Iterator<F26> itSeats = allUpd.getE43().iterator();
			List<F1> unusedIDs = new ArrayList<F1>(Arrays.asList(F1.values()));
			if (selfID != null) {
				unusedIDs.remove(selfID);
			}
			while (itSeats.hasNext()) {
				F26 cur = itSeats.next();
				F1 curId = cur.getF27();
				if (!curId.equals(selfID)) {
					model.getUser(curId).setValue(cur.getF28());
					model.getSeat(curId).setValue(cur.getF29());
					unusedIDs.remove(curId);
				} else if (curId.equals(selfID)) {
					model.getSelfSeat().setValue(cur.getF29());
				}
			}
			Iterator<F1> unusedIt = unusedIDs.iterator();
			while (unusedIt.hasNext()) {
				F1 cur = unusedIt.next();
				model.getSeat(cur).setValue(new F19());
			}

			if (getModel().getSelf().getValue().getId() != null) {
				model.updateSelf();
			}
			/* end of seats */

			/* start of state */
			G1 stateCode = allUpd.getE41();
			switch (stateCode) {
			case G_3:
				/* game state */
				E14 gameState = allUpd.getE42();
				model.getPack().setValue(gameState.getE15());
				model.getTrump().setValue(gameState.getE16());
				B26 playerCards = gameState.getE17();
				if (playerCards != null) {
					model.getCards().setValue(playerCards.getB27());
				} else {
					model.getCards().setValue(
							new ArrayList<B20>()
							// null
							);
				}
				B28 tableCards = gameState.getE18();
				if (tableCards != null) {
					model.getTable().setValue(gameState.getE18().getB29());
				} else {
					List<B23> emptyTable = new ArrayList<B23>();
					model.getTable().setValue(emptyTable);
				}
				/* on turn */
				A12 ar = allUpd.getE30();
				if (ar != null) {
					updateActionRequest(ar, playerCards, tableCards);
				} else {
					model.getOnTurn().setValue(null);
				}
				break;
			case G_2:
				// XXX
				// getScreen().getStartButton().setVisible(true);
				break;
			default:
				break;
			}

			model.getState().setValue(stateCode);
			break;
		default:
			throw new IllegalArgumentException("Unsupported: " + gameCode);
		}

		boolean selfPlaying = model.getSelf().getValue().getId() != null;
		if (selfPlaying) {
			switch (gameCode) {
			/*
			 * case SEAT_USER_CHANGE: case SEAT_STATE_CHANGE: case ACTION_UPDATE: case
			 * ROUND_STARTED: case ROUND_ENDED: case ACTION: // ignore break;
			 */
			// XXX make start button visibility dependant on if user is a player
			case E_4:
				getScreen().getStartButton().setVisible(false);
				break;
			case E_9:
				getScreen().getStartButton().setVisible(true);
				break;
			case E_10:
				E40 allUpd = (E40) content;
				G1 stateCode2 = allUpd.getE41();
				switch (stateCode2) {
				case G_3:
					getScreen().getStartButton().setVisible(false);
					break;
				case G_2:
					getScreen().getStartButton().setVisible(true);
					break;
				default:
					break;
				}
			}
			if (getScreen().getStartButton().isVisible()) {
				if (E1.E_9.equals(gameCode)) {
					getScreen().getStartButton().setEnabled(true);
				} else {
					if (getModel().getSelfSeat().getValue() instanceof F15) {
						F15 idleState = (F15) getModel().getSelfSeat().getValue();
						boolean toEnableStart = !idleState.isF16();
						if (toEnableStart) {
							getScreen().getStartButton().setEnabled(true);
						} else {
							getScreen().getStartButton().setEnabled(false);
						}
					} else {
						// System.out.println("not idle seat state");
						getScreen().getStartButton().setEnabled(false);
					}
				}
			}
		} else {
			if (getScreen().getStartButton().isVisible()) {
				getScreen().getStartButton().setVisible(false);
			}
		}

		update();
		return true;
	}

	private boolean isSelfUser(F1 idSeat) {
		F1 selfId = model.getSelf().getValue().getId();
		boolean ret = selfId != null && selfId.equals(idSeat); 
		return ret;
	}

	/**
	 * Moves seats without cards into idle state; call just after a new round has
	 * started
	 */
	private void updateIdles() {
		if (model.getCards().getValue() == null || model.getCards().getValue().size() == 0) {
			model.getSelfSeat().setValue(new F15());
		}
		Map<F1, F17> otherSeats = model.seatsInPlayingState();
		List<F1> toIdle = new ArrayList<F1>();
		for (F1 cur : otherSeats.keySet()) {
			F17 curSeat = otherSeats.get(cur);
			if (curSeat.getF18() < 1) {
				toIdle.add(cur);
			}
		}
		for (F1 cur : toIdle) {
			model.getSeat(cur).setValue(new F15());
		}
	}

	/**
	 * Updates Podkidnoy screen's model with parameter command
	 * 
	 * @param gameUpd
	 */
	private void updateState(final E37 gameUpd) {
		/* game state */
		E14 gameState = gameUpd.getE38();
		B28 tableCards = null;
		B26 playerCards = null;
		if (gameState != null) {
			B20 trump = gameState.getE16();
			if (trump != null) {
				model.getTrump().setValue(trump);
			}
			model.getPack().setValue(gameState.getE15());
			playerCards = gameState.getE17();
			if (playerCards != null) {
				model.getCards().setValue(playerCards.getB27());
			} else {
				model.getCards().setValue(null); // XXX WHY THAT?
			}
			tableCards = gameState.getE18();
			if (tableCards != null) {
				model.getTable().setValue(gameState.getE18().getB29());
			} else {
				List<B23> emptyTable = new ArrayList<B23>();
				model.getTable().setValue(emptyTable);
			}
		}
		/* on turn */
		Iterator<F20> it3 = gameUpd.getE39().iterator();
		whileIt3 : while (it3.hasNext()) {
			F20 cur = it3.next();
			F1 curID = cur.getF21();

			F1 selfId = model.getSelf().getValue().getId();
			if (selfId != null && selfId.equals(curID)) {
				continue whileIt3;
			}
			F14 newState = cur.getF22();
			/// System.out.println("cur id = " + curID + ", new State = " + newState);
			model.getSeat(curID).setValue(newState);
		}

		A12 ar = gameUpd.getE30();
		if (ar != null) {
			// System.out.println("11");
			updateActionRequest(ar, playerCards, tableCards);
		} else {
			// System.out.println("22");
			model.getOnTurn().setValue(null);
		}
	}

	/**
	 * Switches player on turn with parameter action request. Action request
	 * contains Podkidnoy action state code and additionally:
	 * <ul>
	 * <li>On turn seat's ID code if the player is not on turn
	 * <li>Allowed actions if player is on turn
	 * </ul>
	 * <p>
	 * Player and table cards are used for reverting to their previous values if
	 * player is defending and picks up after altering table cards
	 * 
	 * @param ar
	 *          Action request
	 * @param playerCards
	 *          Initial player cards
	 * @param tableCards
	 *          Initial table cards
	 */
	private void updateActionRequest(A12 ar, B26 playerCards, B28 tableCards) {
		getCardsAdded().clear();

		if (ar instanceof A13) {
			A13 turn = (A13) ar;
			A1 selfTurn = turn.getType();
			model.getSelf().getValue().getButtons().clear();
			model.getSelf().getValue().getButtons().addAll(turn.getA14());
			model.getSelf().getValue().setOnTurnState(selfTurn);
			model.getSelf().notifyListeners();
			model.getMax().setValue(turn.getA15());
			model.getOnTurn().setValue(F1.F_2); // no need for mapping
			switch (selfTurn) {
			case A_3:
				m = new DefendingStateMemory(playerCards.getB27(), tableCards.getB29());
				break;

			default:
				break;
			}
		} else if (ar instanceof A16) {
			A16 others = (A16) ar;
			F1 seatOnTurn = others.getA17();
			Self selfValue = model.getSelf().getValue();
			if (selfValue != null && selfValue.getId() != null && selfValue.getId().equals(seatOnTurn)) {
				throw new IllegalArgumentException(); // "Action request to others was sent with self ID");
			}
			F1 mappedCode = model.getCode(others.getA17());
			model.getOnTurn().setValue(mappedCode);
			model.getSelf().getValue().setOnTurnState(null);
			model.getSelf().setValue(model.getSelf().getValue()); // for updating only
		} else if (ar == null) {
			model.getOnTurn().setValue(null);
			model.getSelf().getValue().setOnTurnState(null);
		} else {
			throw new IllegalArgumentException("Unsupported: " + ar.getClass().getCanonicalName());
		}
	}

	/**
	 * Returns cards added to table cards by player
	 * <p>
	 * Helps for example when player is adding cards to table after defender has
	 * picked up and there were undefended bottom cards lying on the table. Then
	 * added cards help to differentiate between previous undefended bottom cards
	 * and added cards
	 * 
	 * @return Added cards
	 */
	public List<B20> getCardsAdded() {
		return cardsAdded;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		/*
		 * if (source.equals(getLeave())) { getLeave().setEnabled(false);
		 */
		// System.out.println("leave");
		/*
		 * CSType leaveCmd = new CSType(); leaveCmd.setCode(CommandCode.LEAVETABLE);
		 * CSContentEmptyType emptyContent = new CSContentEmptyType();
		 * leaveCmd.setContent(emptyContent); mController.sendCS(leaveCmd);
		 */
		/*
		 * throw new
		 * IllegalStateException("Old leave button was replaced by close button"); }
		 * else
		 */
		/*
		 * if (source.equals(getStart())) { // System.out.println("start"); CSType
		 * startCmd = new CSType(); startCmd.setCode(CommandCode.GAMECOMMAND);
		 * CSContentGameType gameContent = new CSContentGameType();
		 * gameContent.setGameCommandCode(CSGameCode.START); CSGameEmptyType
		 * emptyGame = new CSGameEmptyType(); gameContent.setContent(emptyGame);
		 * startCmd.setContent(gameContent); mController.sendCS(startCmd);
		 * 
		 * } else
		 */
		/* CARDS */
		if (source instanceof CardsPanelButtonHandler) {
			A1 selfState = model.getSelf().getValue().getOnTurnState();
			switch (selfState) {
			case A_2:
				String cmdFirst = arg0.getActionCommand();
				B20 cardFirst = Utils.unmarshallCard(cmdFirst);
				if (!(cardFirst == null)) {
					Utils.removeCardFromList(model.getCards().getValue(), cardFirst);
					B23 p = new B23();
					p.setB25(cardFirst);
					p.setB24(null);
					model.getTable().getValue().add(p);
					getCardsAdded().add(cardFirst);
					model.getCards().notifyListeners();
					model.getTable().notifyListeners();
				}

				break;
			case A_3:
				String cmdDef = arg0.getActionCommand();
				B20 cardDef = Utils.unmarshallCard(cmdDef);
				if (!(cardDef == null)) {
					boolean sendOK = model.getSelf().getValue().getButtons().contains(A5.A_7) && Utils.isAllUndefended(model.getTable().getValue());
					if (getScreen().getTable().getSelectedBottoms().size() == 1) {
						B20 bottom = getScreen().getTable().getSelectedBottoms().get(0);
						Utils.removeCardFromList(model.getCards().getValue(), cardDef);
						Utils.defendBottomCard(model.getTable().getValue(), bottom, cardDef);
						model.getCards().notifyListeners();
						model.getTable().notifyListeners();
					} else if (sendOK) {
						Utils.removeCardFromList(model.getCards().getValue(), cardDef);
						B23 p = new B23();
						p.setB25(cardDef);
						p.setB24(null);
						model.getTable().getValue().add(p);
						getCardsAdded().add(cardDef);
						model.getCards().notifyListeners();
						model.getTable().notifyListeners();
					}
				}
				break;
			case A_4:
				String cmdAdd = arg0.getActionCommand();
				B20 cardAdd = Utils.unmarshallCard(cmdAdd);
				if (!(cardAdd == null)) {
					Utils.removeCardFromList(model.getCards().getValue(), cardAdd);
					B23 p = new B23();
					p.setB25(cardAdd);
					p.setB24(null);
					model.getTable().getValue().add(p);
					getCardsAdded().add(cardAdd);
					model.getCards().notifyListeners();
					model.getTable().notifyListeners();
				}
				break;
			default:
				throw new IllegalStateException("" + selfState);
			}
			update();
			/* BUTTONS */
		} else if (source instanceof ButtonsPanelButtonHandler) {
			String cmd = arg0.getActionCommand();
			A5 but = Utils.unmarshallButton(cmd);
			switch (but) {
			case A_9:
				model.getCards().setValue(m.getCards());
				model.getTable().setValue(m.getTable());
				break;
			default:
				break;
			}
			D4 playCmd = this.getPlayCommand(but);
			mController.sendCS(playCmd);
			model.reset();
			getScreen().disableAll();

			/* TABLE PANEL */
		} else if (source instanceof TablePanelButtonHandler) {

			A1 selfState = model.getSelf().getValue().getOnTurnState();
			switch (selfState) {
			case A_2:
				String cmdFirst = arg0.getActionCommand();
				B20 cardFirst = Utils.unmarshallCard(cmdFirst);
				if (!(cardFirst == null)) {
					Utils.removePairByBottom(model.getTable().getValue(), cardFirst);
					model.getCards().getValue().add(cardFirst);
					Utils.removeCardFromList(getCardsAdded(), cardFirst);
					model.getCards().notifyListeners();
					model.getTable().notifyListeners();
				}
				break;
			case A_3:
				String cmdDef = arg0.getActionCommand();
				B20 cardDef = Utils.unmarshallCard(cmdDef);
				if (Utils.cardsContain(getCardsAdded(), cardDef)) {
					// Removing a send card
					Utils.removePairByBottom(model.getTable().getValue(), cardDef);
					model.getCards().getValue().add(cardDef);
					Utils.removeCardFromList(getCardsAdded(), cardDef);
					model.getCards().notifyListeners();
					model.getTable().notifyListeners();
				} else if (!(cardDef == null) && Utils.tableContainsDefender(model.getTable().getValue(), cardDef)) {
					// Removing a defending card
					Utils.removeDefender(model.getTable().getValue(), cardDef);
					model.getCards().getValue().add(cardDef);
					model.getCards().notifyListeners();
					model.getTable().notifyListeners();
				}
				break;
			case A_4:
				String cmd = arg0.getActionCommand();
				B20 card = Utils.unmarshallCard(cmd);
				boolean addedCard = Utils.cardsContain(getCardsAdded(), card);
				if (addedCard) {
					Utils.removePairByBottom(model.getTable().getValue(), card);
					model.getCards().getValue().add(card);
					Utils.removeCardFromList(getCardsAdded(), card);
					model.getCards().notifyListeners();
					model.getTable().notifyListeners();
				}
				break;
			default:
				break;
			}
			update();
		} // source is tablepanel
	}

	/**
	 * Returns model buttons from Podkidnoy model
	 * 
	 * @return Buttons
	 */
	public List<A5> getButtons() {
		List<A5> ret = model.getSelf().getValue().getButtons();
		return ret;
	}

	/**
	 * Updates Podkidnoy screen after handling commands and user actions
	 */
	private void update() {
		A1 selfState = model.getSelf().getValue().getOnTurnState();
		if (selfState != null) {
			switch (selfState) {
			case A_2:
				updateFirst();
				break;
			case A_3:
				updateDefending();
				break;
			case A_4:
				updateAdding();
				break;
			default:
				throw new IllegalArgumentException("Unsupported: " + selfState);
			}
		} else {
			// PASSIVE ALSO HERE NOW
			getScreen().disableAll();
		}
	}

	/**
	 * Updates adding state
	 */
	private void updateAdding() {
		List<A5> buttonsToEnable = new ArrayList<A5>();
		int added = getCardsAdded().size();
		// System.out.println("added=" + added);
		if (added > 0) {
			buttonsToEnable.add(A5.A_10);
		} else {
			buttonsToEnable.add(A5.A_11);
		}
		Integer max = model.getMax().getValue();
		if (max == null) {
			max = new Integer(-1);
		}
		int noOfSelected = getCardsAdded().size();
		int numOfTableCards = model.getTable().getValue().size();
		if (numOfTableCards < TablePanel.TABLE_CARDS && (max == -1 || noOfSelected < max)) {
			List<B6> ranks = Utils.getRanksList(model.getTable().getValue());
			getScreen().getCards().setEnabledForRanks(ranks);
		} else {
			getScreen().getCards().setEnabled(false);
		}
		getScreen().getTable().enableCards(getCardsAdded());
		getScreen().getButtons().enableTypes(buttonsToEnable);
	}

	/**
	 * Updates defending state
	 */
	private void updateDefending() {
		List<B20> selectedBottoms = getScreen().getTable().getSelectedBottoms();
		int size = selectedBottoms.size();
		ArrayList<A5> buttonsToEnable = new ArrayList<A5>();
		buttonsToEnable.add(A5.A_9);
		switch (size) {
		case 0:
			boolean defendOK = getButtons().contains(A5.A_8);
			if (getCardsAdded().size() == 0 && defendOK) {
				ArrayList<B20> tableCardsToEnable = new ArrayList<B20>();
				List<B20> undefendedBottoms = Utils.getUndefendedBottoms(model.getTable().getValue());
				List<B20> defendingCards = Utils.getDefendingCards(model.getTable().getValue());
				tableCardsToEnable.addAll(undefendedBottoms);
				tableCardsToEnable.addAll(defendingCards);
				getScreen().getTable().enableCards(tableCardsToEnable);
			} else {
				getScreen().getTable().enableCards(getCardsAdded());
			}
			int selected = getCardsAdded().size();
			Integer max = model.getMax().getValue();
			if (max == null) {
				max = new Integer(-1);
			}
			boolean sendOK = getButtons().contains(A5.A_7) && Utils.isAllUndefended(model.getTable().getValue()); // .isAllUndefended();

			int bottoms = Utils.getBottomCards(model.getTable().getValue()).size();

			if (sendOK) {
				// System.out.println("--1-- bottoms = "+bottoms+" < max=" + max);
				if (max == -1 || 
						// bottoms 
						selected
						< max) {
					// System.out.println("--2--");
				B6 tableRank = Utils.getBottomCards(model.getTable().getValue()).iterator().next().getB22(); // .getBottoms().iterator().next().getRank();
				getScreen().getCards().setEnabledForRank(tableRank);
			} else {
				// System.out.println("--3--");
				getScreen().getCards().setEnabledForSelected();
			}
			}
			if (sendOK && selected > 0) {
				buttonsToEnable.add(A5.A_7);
			}
			break;
		case 1:
			B20 selectedBottom = selectedBottoms.get(0);
			ArrayList<B20> aAbleToKill = new ArrayList<B20>();
			Iterator<B20> it = model.getCards().getValue().iterator();
			while (it.hasNext()) {
				B20 smoke = it.next();
				if (Utils.kills(model.getTrump().getValue(), smoke, selectedBottom)) {
					aAbleToKill.add(smoke);
				}
			}
			getScreen().getCards().enableCards(aAbleToKill);
			getScreen().getTable().enableCards(selectedBottoms);
			break;
		default:
			throw new IllegalStateException("Number is " + size);
		}
		if (Utils.isTableDefended(model.getTable().getValue())) {
			buttonsToEnable.add(A5.A_8);
		}
		getScreen().getButtons().enableTypes(buttonsToEnable);
	}

	/**
	 * Updates laying first state
	 */
	private void updateFirst() {
		int bottoms = Utils.getBottomCards(model.getTable().getValue()).size(); // model.getTable().getBottoms().size();
		Integer max = model.getMax().getValue();
		if (max == null) {
			max = new Integer(-1);
		}
		if (bottoms == 0) {
			getScreen().getCards().setEnabled(true);
			getScreen().getButtons().setEnabled(false);
		} else if (bottoms == max) {
			getScreen().getCards().setEnabled(false);
			getScreen().getTable().setEnabled(true);
			getScreen().getButtons().setEnabled(true);
		} else if (max != -1 && bottoms > max) {
			throw new IllegalStateException("Number is " + bottoms + ", while only " + max + " is allowed");
		} else {
			B6 rankSelected = Utils.getBottomCards(model.getTable().getValue()).iterator().next().getB22(); // .getBottoms().iterator().next().getRank();
			getScreen().getCards().setEnabledForRank(rankSelected);
			getScreen().getButtons().setEnabled(true);
			getScreen().getTable().setEnabled(true);
		}
	}

	/**
	 * Composes and returns game command that is to be sent to game server after
	 * player has pushed parameter action button
	 * 
	 * @param clickedButton
	 *          Pushed button
	 * @return Game command
	 */
	private D4 getPlayCommand(A5 clickedButton) {
		D4 ret = new D4();
		ret.setC13(C1.C_11);
		D19 content = new D19();
		content.setD20(D1.D_3);
		D24 actionContent = new D24();
		A18 action = new A18();
		action.setA19(clickedButton);
		actionContent.setD25(action);
		content.setD21(actionContent);
		ret.setD5(content);
		switch (clickedButton) {
		case A_10:
		case A_7:
		case A_6:
			B26 sof = new B26();
			sof.getB27().addAll(getCardsAdded());
			action.setA21(sof);
			break;
		case A_8:
			B28 table = new B28();
			table.getB29().addAll(model.getTable().getValue());
			action.setA20(table);
			break;
		case A_11:
		case A_9:
			break;
		default:
			throw new IllegalArgumentException("" + clickedButton);
		}
		getCardsAdded().clear();
		return ret;
	}

	@Override
	public void reset() {
		// XXX

		// In waiting state, so player can leave
		model.getState().setValue(G1.G_2);

		// Clear buttons and on turn state
		model.getSelf().getValue().setId(null);
		// model.getSelf().getValue().setOnTurnState(PodStateCode.)
		model.getSelf().getValue().getButtons().clear();
		model.getSelf().getValue().setOnTurnState(null);
		model.getSelf().notifyListeners();

		// No player or table cards
		model.getCards().setValue(new ArrayList<B20>());
		model.getTable().setValue(new ArrayList<B23>());

		// Seats are empty
		for (F1 idSeat : F1.values()) {
			ObservableValue<F14> cur = model.getSeat(idSeat);
			cur.setValue(new F19());
		}

		// Make start invisible
		// screen.getStartAndLeave().getStart().setVisible(false);
		getStart().setVisible(false);
	}

	@Override
	public void finishLayout() {
		getScreen().finishLayout();
	}

	/* START OF TEST METHODS */

	/**
	 * Programmatically clicks parameter Podkidnoy button
	 * 
	 * @param _type
	 *          Podkidnoy button code
	 * @return Command to be sent to the game server
	 */
	public D4 testClickButton(final A5 _type) {
		getScreen().getButtons().clickButton(_type);
		switch (_type) {
		case A_9:
			model.getCards().setValue(m.getCards());
			model.getTable().setValue(m.getTable());
			break;

		default:
			break;
		}
		D4 playCmd = this.getPlayCommand(_type);
		model.reset();
		getScreen().disableAll();
		return playCmd;
	}

	public void handleClose() {
		// System.out.println("Close clicked");
		/*
		 * try { Thread.sleep(1000); } catch (InterruptedException e) {
		 * e.printStackTrace(); } try { Thread.sleep(1000); } catch
		 * (InterruptedException e) { e.printStackTrace(); }
		 */
		mController.setScreenController(ScreenControllerType.LOBBY);
		
		D4 leaveCmd = new D4();
		leaveCmd.setC13(C1.C_8);
		D8 emptyContent = new D8();
		leaveCmd.setD5(emptyContent);
		mController.sendCS(leaveCmd);
		
		mController.enableLobby();
	}

	public void handleStart() {
		// System.out.println("Start clicked");
		getScreen().getStartButton().setEnabled(false);
		D4 startCmd = new D4();
		startCmd.setC13(C1.C_11);
		D19 gameContent = new D19();
		gameContent.setD20(D1.D_2);
		D23 emptyGame = new D23();
		gameContent.setD21(emptyGame);
		startCmd.setD5(gameContent);
		mController.sendCS(startCmd);
	}

	public void handleSit(final F1 _seatID) {
		// System.out.println("Sit clicked");
		getScreen().setEnabledToSitButtons(false);
		D4 startCmd = new D4();
		startCmd.setC13(C1.C_9);
		D17 gameContent = new D17();
		gameContent.setD18(_seatID);
		startCmd.setD5(gameContent);
		mController.sendCS(startCmd);
	}

	/* END OF TEST METHODS */

	@Override
	public void enable() {
	}
}
