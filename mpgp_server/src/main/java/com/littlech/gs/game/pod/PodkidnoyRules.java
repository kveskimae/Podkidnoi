/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod;

import java.util.Iterator;
import java.util.List;

import com.littlech.cl.Utils;
import com.littlech.gen.a.A18;
import com.littlech.gen.a.A5;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B26;
import com.littlech.gen.b.B28;
import com.littlech.gen.b.B6;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D1;
import com.littlech.gen.d.D17;
import com.littlech.gen.d.D19;
import com.littlech.gen.d.D22;
import com.littlech.gen.d.D24;
import com.littlech.gen.d.D4;
import com.littlech.gen.f.F1;
import com.littlech.gen.g.G1;
import com.littlech.gs.game.IRules;
import com.littlech.gs.game.RulesCheckResult;
import com.littlech.gs.user.ServerUser;


// TODO: Auto-generated Javadoc
/**
 * The Class PodkidnoyRules.
 */
public class PodkidnoyRules implements IRules {

	/** The m podkidnoy. */
	private Podkidnoy mPodkidnoy;

	/**
	 * Instantiates a new podkidnoy rules.
	 *
	 * @param podkidnoy the podkidnoy
	 */
	public PodkidnoyRules(Podkidnoy podkidnoy) {
		mPodkidnoy = podkidnoy;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Podkidnoy getGame() {
		return mPodkidnoy;
	}

	/* (non-Javadoc)
	 * @see server.IRules#isAllowed(server.ServerUser, com.littlech.gen.d.D4)
	 */
	@Override
	public RulesCheckResult isAllowed(ServerUser user, D4 cmd) {
		C1 commandType = cmd.getC13();
		switch (commandType) {
		case C_9:
			return PodkidnoyRules.checkSitDownCommand(getGame(), user, cmd);
		case C_11:
			return checkGameCommand(getGame(), user, cmd);
		case C_7:
			return PodkidnoyRules.checkJoinTableCommand(getGame(), user, cmd);
		case C_8:
			return new RulesCheckResult(true);
		default:
			break;
		}

		throw new IllegalArgumentException("Unsupported command: " + cmd);
	}

	/**
	 * Check join table command.
	 *
	 * @param game the game
	 * @param user the user
	 * @param cmd the cmd
	 * @return the rules check result
	 */
	private static RulesCheckResult checkJoinTableCommand(Podkidnoy game,
			ServerUser user, D4 cmd) {
		return new RulesCheckResult(true);
	}

	/**
	 * Check sit down command.
	 *
	 * @param game the game
	 * @param user the user
	 * @param cmd the cmd
	 * @return the rules check result
	 */
	private static RulesCheckResult checkSitDownCommand(Podkidnoy game,
			ServerUser user, D4 cmd) {
		if (!(cmd.getD5() instanceof D17)) {
			RulesCheckResult fail0 = new RulesCheckResult(false,
					"Bad data from user", new IllegalArgumentException(
							"Command content is not sit down content: "
									+ cmd.getD5()));
			return fail0;
		}
		D17 sitDownContent = (D17) cmd
				.getD5();
		F1 seatID = sitDownContent.getD18();
		if (seatID == null) {
			RulesCheckResult fail0 = new RulesCheckResult(false,
					"Bad data from user", new NullPointerException("Requested seat ID is null"));
			return fail0;
		}
		PodkidnoySeat u = game.getPodkidnoyPlayerStore().getSeatByID(seatID);
		if (!u.isEmpty()) {
			RulesCheckResult fail0 = new RulesCheckResult(false,
					"Seat is already taken", new IllegalStateException());
			return fail0;
		}
		RulesCheckResult ret = new RulesCheckResult(true);
		return ret;
	}

	/**
	 * Check game command.
	 *
	 * @param _game the _game
	 * @param user the user
	 * @param cmd the cmd
	 * @return the rules check result
	 */
	private static RulesCheckResult checkGameCommand(Podkidnoy _game,
			ServerUser user, D4 cmd) {

		F1 userID = _game.getPlayerStore()
				.getSeatIDByUser(user);
		if (userID == null) {
			RulesCheckResult fail0 = new RulesCheckResult(false,
					"Illegal client state", new IllegalStateException(
							"User was not found among sitting users"));
			return fail0;
		}
		if (!(cmd.getD5() instanceof D19)) {
			RulesCheckResult fail0 = new RulesCheckResult(false,
					"Bad data from user", new IllegalArgumentException(
							"Command content is not game content"
									+ cmd.getD5()));
			return fail0;
		}
		G1 gameState = _game.getStateMachine().getTableState();
		D19 gameContent = (D19) cmd.getD5();
		D1 gameCommandCode = gameContent.getD20();
		PodkidnoyGameStateType durakGS = _game.getPodkidnoyStateMachine()
				.getState();
		if (gameCommandCode.equals(D1.D_3)) {
			if (!_game.getPlayerStore().isSeatsTurn(userID)) {
				RulesCheckResult fail1 = new RulesCheckResult(false,
						"It is not your turn", new IllegalStateException(
								"It is not user " + user + " turn"));
				return fail1;
			}
			if (gameState.equals(G1.G_3)) {
				D22 gameContentContent = gameContent.getD21();

				if (!(gameContentContent instanceof D24)) {
					RulesCheckResult fail2 = new RulesCheckResult(false,
							"Bad data from user", new IllegalArgumentException(
									"Game content is not Podkidny action: "
											+ gameContentContent));
					return fail2;
				}
				D24 podAction = (D24) gameContentContent;

				A18 podActionAction = podAction.getD25();
				A5 butCode = podActionAction.getA19();
				switch (durakGS) {
				case GS_DURAK_ROUND_STARTED:
					if (butCode.equals(A5.A_6)) {
						B26 firstCards = podActionAction.getA21();
						try {
							validateFirstCards(_game, firstCards);
							return new RulesCheckResult(true);
						} catch (Exception e) {
							RulesCheckResult failx = new RulesCheckResult(
									false, "Bad data from user", e);
							return failx;
						}
					}
					return throwIllegalArgumentException(durakGS, butCode);
				case GS_DURAK_DEFENDING_FIRST:
					if (butCode.equals(A5.A_7)) {
						if (!_game.getPodkidnoyConf().isG23()) {
							RulesCheckResult fail5 = new RulesCheckResult(
									false, "Bad data from user",
									new IllegalStateException(
											"Sending in a non-Podkidnoy game"));
							return fail5;
						}
						B26 sendingCards = podActionAction
								.getA21();
						try {
							validateSendCards(_game, sendingCards);
							return new RulesCheckResult(true);
						} catch (Exception e) {
							RulesCheckResult failx = new RulesCheckResult(
									false, "Bad data from user", e);
							return failx;
						}
					}
					// falls through
				case GS_DURAK_DEFENDING_AGAIN:
					if (butCode.equals(A5.A_8)) {
						B28 defendedTable = podActionAction
								.getA20();
						try {
							validateDefendedTable(_game, defendedTable);
							return new RulesCheckResult(true);
						} catch (Exception e) {
							RulesCheckResult failx = new RulesCheckResult(
									false, "Bad data from user", e);
							return failx;
						}
					}
					if (butCode.equals(A5.A_9)) {
						break;
					}
					return throwIllegalArgumentException(durakGS, butCode);
				case GS_DURAK_WAITING_MORE_CARDS:
					if (butCode.equals(A5.A_10)) {
						B26 cards = podActionAction.getA21();
						try {
							validateAdd(_game, cards);
							return new RulesCheckResult(true);
						} catch (Exception e) {
							RulesCheckResult failx = new RulesCheckResult(
									false, "Bad data from user", e);
							return failx;
						}
					}
				case GS_DURAK_PICKED_UP:
					if (butCode.equals(A5.A_10)) {
						B26 cards = podActionAction.getA21();
						try {
							validateAdd(_game, cards);
							return new RulesCheckResult(true);
						} catch (Exception e) {
							RulesCheckResult failx = new RulesCheckResult(
									false, "Bad data from user", e);
							return failx;
						}
					}
					if (butCode.equals(A5.A_11)) {
						break;
					}
					return throwIllegalArgumentException(durakGS, butCode);
				default:
					return throwIllegalArgumentException(durakGS, butCode);
				} // switch (durakGS)
			} // if (gameState.equals(TableStateCode.PLAYING))
			else {
				RulesCheckResult fail7 = new RulesCheckResult(false,
						"Bad data from user", new IllegalStateException(
								"Game in state " + gameState
										+ " got action command "
										+ gameCommandCode));
				return fail7;
			}
		} else if (gameCommandCode.equals(D1.D_2)) {
			if (!gameState.equals(G1.G_2)) {
				RulesCheckResult fail7 = new RulesCheckResult(false,
						"Bad data from user", new IllegalStateException(
								"Game in state " + gameState
										+ " got action command "
										+ gameCommandCode));
				return fail7;
			}
		}
		RulesCheckResult ret = new RulesCheckResult(true);
		return ret;

	}

	/**
	 * Validate add.
	 *
	 * @param _game the _game
	 * @param cards the cards
	 */
	private static void validateAdd(Podkidnoy _game, B26 cards) {
		validateAdd(cards, _game.getPodkidnoyPlayerStore().getDefender()
				.getSetOfCards(), _game.getPodkidnoyData().getTableCards(),
				_game.getPodkidnoyPlayerStore().getActor().getSetOfCards(),
				_game.getPodkidnoyConf().getG24());

	}

	/**
	 * Validate defended table.
	 *
	 * @param _game the _game
	 * @param defendedTable the defended table
	 */
	private static void validateDefendedTable(Podkidnoy _game,
			B28 defendedTable) {
		validateDefendedTable(defendedTable, _game.getPodkidnoyData()
				.getTrumpCard(), _game.getPodkidnoyData().getTableCards(),
				_game.getKillers());
	}

	/**
	 * Validate send cards.
	 *
	 * @param _game the _game
	 * @param sendingCards the sending cards
	 */
	private static void validateSendCards(Podkidnoy _game,
			B26 sendingCards) {
		validateSendCards(sendingCards, _game.getPodkidnoyPlayerStore()
				.getDefender().getSetOfCards(), _game.getPodkidnoyPlayerStore()
				.getNextToDefender().getSetOfCards(), _game.getPodkidnoyData()
				.getTableCards(), _game.getPodkidnoyConf().getG24());
	}

	/**
	 * Validate first cards.
	 *
	 * @param _game the _game
	 * @param firstCards the first cards
	 */
	private static void validateFirstCards(Podkidnoy _game,
			B26 firstCards) {
		validateFirstCards(firstCards, _game.getPodkidnoyConf()
				.getNumOfTableCards(), _game.getPodkidnoyPlayerStore()
				.getFirst().getSetOfCards(), _game.getPodkidnoyPlayerStore()
				.getDefender().getSetOfCards(), _game.getPodkidnoyConf()
				.getG24());
	}

	/**
	 * Validates cards added to the table.
	 *
	 * @param cards Add cards
	 * @param defenderCards Defender's cards
	 * @param tableCards Table cards
	 * @param attackerCards Current attacker's cards
	 * @param maxOnTurn the max on turn
	 */
	public static void validateAdd(B26 cards,
			B26 defenderCards, B28 tableCards,
			B26 attackerCards, final int maxOnTurn) {
		// Checking that cards contain at least one card
		if (cards == null) {
			throw new IllegalArgumentException(
					"Attacker requested adding null to the table cards");
		} else if (cards.getB27().size() < 1) {
			throw new IllegalArgumentException(
					"Attacker requested adding zero cards to the table");
		} else if (cards.getB27().size() > maxOnTurn) {

			throw new IllegalArgumentException("Attacker requested adding "
					+ cards.getB27().size()
					+ " cards to the table, while maximum number per turn is "
					+ maxOnTurn);
		}

		// Checking that attacker cards contain adding cards
		if (!Utils.listContainsOther(attackerCards, cards)) {
			throw new IllegalArgumentException("Attacker cards "
					+ Utils.getPresentableFormatForCards(attackerCards)
					+ " do not contain cards to be added "
					+ Utils.getPresentableFormatForCards(cards));
		}

		// Checking if the defender has enough cards
		int noOfDefenderCards = defenderCards.getB27().size()
				+ Utils.getDefendingCards(tableCards).size();
		int onTable = Utils.getBottomCards(tableCards).size();
		int afterOnTable = cards.getB27().size() + onTable;
		if (afterOnTable > noOfDefenderCards) {
			throw new IllegalArgumentException("Attacker added cards " + cards
					+ " (on table " + tableCards + "), after would be "
					+ afterOnTable + " bottoms, but the defender only has "
					+ noOfDefenderCards + " cards");
		}

		// Checking that ranks added match those on table
		List<B6> tableValues = Utils.getRanksList(tableCards);
		Iterator<B20> it = cards.getB27().iterator();
		while (it.hasNext()) {
			B20 option = it.next();
			if (!tableValues.contains(option.getB22())) {
				throw new IllegalArgumentException("Attacker requested adding "
						+ cards + ", while table was " + tableCards
						+ " (ranks do not match)");
			}
		}
	}

	/**
	 * Validates the parameter table cards for eligibility, i.e. if all the
	 * bottom cards are really currently on the table and if all the defending
	 * cards are contained among the defender's cards combined with the
	 * defending table cards
	 *
	 * @param defendedTable Table cards to be checked for validity
	 * @param trumpCard Trump card
	 * @param oldTable Previous table cards
	 * @param killers All the possible defending cards
	 */
	public static void validateDefendedTable(B28 defendedTable,
			B20 trumpCard, B28 oldTable, B26 killers) {

		// Checking that table cards are not null
		if (defendedTable == null) {
			throw new IllegalArgumentException(
					"Defender requested defended event with null table");
		}

		// Checking that old table defenders and defender cards contain all the
		// new defending cards

		List<B20> defenders = Utils.getDefendingCards(defendedTable
				.getB29());
		if (!Utils.listContainsOther(killers.getB27(), defenders)) {
			throw new IllegalArgumentException(
					"Defender requested defended event with table "
							+ defendedTable
							+ ", where killing cards are not within possible killing cards "
							+ killers);
		}

		// Checking that old and new bottom cards match
		List<B20> clientBottom = Utils.getBottomCards(defendedTable);
		List<B20> serverBottom = Utils.getBottomCards(oldTable);
		if (
				/*
				!clientBottom.containsAll(serverBottom)
				|| !
				serverBottom.containsAll(clientBottom)
				*/
				!Utils.listsEqual(serverBottom, clientBottom)
				
		) {
			throw new IllegalArgumentException(
					"Defender requested defended event with the table cards "
							+ Utils.getPresentableFormatForTable(defendedTable)
							+ ", where the bottom cards do not match with the server's table "
							+ Utils.getPresentableFormatForTable(oldTable));
		}

		// Defending cards must kill bottoms

		Iterator<B20> it = serverBottom.iterator();
		while (it.hasNext()) {
			B20 bottom = it.next();
			B20 defender = Utils.findDefendingCard(defendedTable, bottom);
			if (!Utils.kills(trumpCard, defender, bottom)) {
				throw new IllegalArgumentException(
						"Defender requested defended event with table cards "
								+ defendedTable + ", where " + defender
								+ " does not kill " + bottom
								+ " (trump card is " + trumpCard + ")");
			}
		}
	} // validateDefendedTable

	/**
	 * Validates round's first cards.
	 *
	 * @param firstCards First cards
	 * @param maxTableBottomCards Maximum number of table bottom cards
	 * @param firstAttackerCards First attacker's cards
	 * @param defenderCards Defender's cards
	 * @param maxTurnCards the max turn cards
	 */
	public static void validateFirstCards(B26 firstCards,
			int maxTableBottomCards, B26 firstAttackerCards,
			B26 defenderCards, int maxTurnCards) {

		if (firstCards == null) {
			throw new NullPointerException("Received first cards were null");
		}

		List<B20> cards = firstCards.getB27();
		int numOfFirstCards = cards.size();

		// Checking if there is at least one card
		if (numOfFirstCards < 1) {
			throw new IllegalArgumentException(
					"Received first cards were empty");
		} else if (numOfFirstCards > maxTableBottomCards) {
			throw new IllegalArgumentException("First cards contain "
					+ numOfFirstCards + " cards, but allowed is "
					+ maxTableBottomCards);
		} else if (numOfFirstCards > maxTurnCards) {

			throw new IllegalArgumentException("First cards contain "
					+ numOfFirstCards
					+ " cards, but maximum allowed number per turn is "
					+ maxTurnCards);
		}

		// Cards must be of the same rank
		B20 firstCard = cards.get(0);
		B6 requiredValue = firstCard.getB22();
		Iterator<B20> it = cards.iterator();
		while (it.hasNext()) {
			if (it.next().getB22() != requiredValue) {
				throw new IllegalArgumentException("First cards "
						+ cards.toString() + " are not of the same rank");
			}
		}

		// First attacker must have the cards
		if (!Utils.listContainsOther(firstAttackerCards, firstCards)) {
			throw new IllegalArgumentException("First attacker cards "
					+ Utils.getPresentableFormatForCards(firstAttackerCards)
					+ " do not contain cards "
					+ Utils.getPresentableFormatForCards(firstCards));
		}

		// Defender must have enough cards to defend
		int numOfDefenderCards = defenderCards.getB27().size();
		if (numOfFirstCards > numOfDefenderCards) {
			throw new IllegalArgumentException("Defender has "
					+ numOfDefenderCards + " cards (" + defenderCards
					+ ") and could not defend " + numOfFirstCards + " cards ("
					+ cards + ")");
		}
	} // validateFirstCards

	/**
	 * Validates send cards.
	 *
	 * @param sendCards Send cards
	 * @param defenderCards Defender's cards
	 * @param nextToDefenderCards Player next to defender's cards
	 * @param tableCards Table cards
	 * @param maxTurnCards the max turn cards
	 */
	public static void validateSendCards(B26 sendCards,

	B26 defenderCards, B26 nextToDefenderCards,
			B28 tableCards, int maxTurnCards) {
		// Checking if there is at least one card
		if (sendCards == null) {
			throw new IllegalArgumentException(
					"Defender requested send forward with a null as the cards");
		} else if (sendCards.getB27().size() < 1) {
			throw new IllegalArgumentException(
					"Defender requested send forward with zero cards");
		} else if (sendCards.getB27().size() > maxTurnCards) {

			throw new IllegalArgumentException("Send cards contain "
					+ sendCards.getB27().size()
					+ " cards, but maximum allowed number per turn is "
					+ maxTurnCards);
		}

		// Checking if defender has send cards
		if (!Utils.listContainsOther(defenderCards, sendCards)) {
			throw new IllegalArgumentException("Defender cards "
					+ defenderCards + " do not contain " + sendCards);
		}

		// Checking if the player that seats next to defender has enough cards
		int nextCards = nextToDefenderCards.getB27().size();
		int onTable = Utils.getBottomCards(tableCards.getB29()).size();
		int afterOnTable = sendCards.getB27().size() + onTable;
		if (afterOnTable > nextCards) {
			throw new IllegalArgumentException(
					"Defender requested send with "
							+ sendCards
							+ " (on table "
							+ tableCards
							+ "), after adding would be "
							+ afterOnTable
							+ " cards on the table, but player next to the defender only has "
							+ nextCards + " cards");
		}

		// All cards must be of the same rank
		List<B20> bottoms = Utils.getBottomCards(tableCards.getB29());
		B6 rank = bottoms.get(0).getB22();
		Iterator<B20> it = sendCards.getB27().iterator();
		while (it.hasNext()) {
			B20 option = it.next();
			if (option.getB22() != rank) {
				throw new IllegalArgumentException(
						"Defender requested send with " + sendCards
								+ ", while on table " + tableCards
								+ " (ranks do not match)");
			}
		}
	} // validateSendCards

	/**
	 * Throws an exception when current game state does not accept parameter
	 * command type.
	 *
	 * @param durakGS Podkidnoy game state
	 * @param butCode Command type
	 * @return the rules check result
	 */
	private static RulesCheckResult throwIllegalArgumentException(
			PodkidnoyGameStateType durakGS, A5 butCode) {
		RulesCheckResult failx = new RulesCheckResult(false,
				"Bad data from user", new IllegalArgumentException("State "
						+ durakGS + " got command " + butCode));
		return failx;
	}

}
