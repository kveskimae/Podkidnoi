/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.littlech.gen.a.A12;
import com.littlech.gen.a.A13;
import com.littlech.gen.a.A16;
import com.littlech.gen.a.A18;
import com.littlech.gen.a.A5;
import com.littlech.gen.b.B1;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B23;
import com.littlech.gen.b.B26;
import com.littlech.gen.b.B28;
import com.littlech.gen.b.B6;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D15;
import com.littlech.gen.d.D17;
import com.littlech.gen.d.D19;
import com.littlech.gen.d.D22;
import com.littlech.gen.d.D23;
import com.littlech.gen.d.D24;
import com.littlech.gen.d.D4;
import com.littlech.gen.d.D7;
import com.littlech.gen.e.E19;
import com.littlech.gen.e.E24;
import com.littlech.gen.e.E28;
import com.littlech.gen.e.E34;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F10;
import com.littlech.gen.f.F12;
import com.littlech.gen.f.F14;
import com.littlech.gen.f.F15;
import com.littlech.gen.f.F17;
import com.littlech.gen.f.F19;
import com.littlech.gen.f.F20;
import com.littlech.gen.f.F23;
import com.littlech.gen.f.F26;
import com.littlech.gen.f.F6;
import com.littlech.gen.f.F9;
import com.littlech.gen.g.G1;
import com.littlech.gen.g.G27;
import com.littlech.gen.g.G30;
import com.littlech.gen.g.G36;

/**
 * 
 * Common operations
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class Utils {
	
public static G30 getCopy(G30 in) {
    	G30 ret = new G30();
    	ret.setG33(in.getG33());
    	ret.setG35(in.isG35());
    	ret.setG31(in.getG31());
    	ret.setG32(in.getG32());
    	ret.setG34(getCopy(in.getG34()));
    	return ret;
}

    public static G27 getCopy(G27 in) {
    	G27 ret = new G27();
    	ret.getG29().addAll(in.getG29());
    	ret.setG28(in.getG28());
    	return ret;
    }
	
	public static String print(Object o) {
		if (o == null) {
			return "null";
		}
		if (o instanceof A18){
			A18 pat = (A18)o;
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("PodActionType{");
	    	sb.append("" + pat.getA19());
	    	sb.append(", " + Utils.getPresentableFormatForCards(pat.getA21()));
	    	sb.append(", " + Utils.getPresentableFormatForTable(pat.getA20()));
	    	sb.append("}");
	    	return sb.toString();
	    } else if (o instanceof A12) {
A12 part = (A12)o;
	        	StringBuilder sb = new StringBuilder();
	        	sb.append("PodARType{");
	        	sb.append("" + part.getType());
	        	if (part instanceof A13) {
	        		A13 onTurn = (A13)part;
	        		sb.append(", onturn, ");
	        		sb.append("" + onTurn.getA14());
	        		sb.append(", max=" + onTurn.getA15());
	        	} else if (part instanceof A16) {
	        		A16 others = (A16)part;
	        		sb.append(", others, ");
	        		sb.append(others.getA17());
	        	} else {
	        		throw new IllegalStateException("Unknown Podkidnoy action request type: " + part.getClass().getCanonicalName());
	        	}
	        	sb.append("}");
	        	return sb.toString();
	    }else if (o instanceof B20) {
	        	String ret = Utils.getPresentableFormat((B20)o);
	        	return ret;
	    } else if (o instanceof B26) {
	    	B26 sot = (B26)o;
	        	StringBuilder sb = new StringBuilder();
	        	sb.append("SetOfCardsType{");
	        	// TODO cards separately
	        	sb.append(sot.getB27());
	        	sb.append("}");
	        	return sb.toString();
	}	
	    else if (o instanceof B28) {

	    	String ret = Utils.getPresentableFormatForTable((B28)o);
	    	return ret;
	    } else if (o instanceof D19) {
	    	D19 ccgt = (D19)o;
	        	StringBuilder sb = new StringBuilder();
	        	sb.append("CSContentGameType{");
	        	sb.append("" +  
	        			ccgt.getD20());
	        	sb.append(", " +
	        			ccgt.getD21()		
	        	);
	        	sb.append("}");
	        	return sb.toString();
	    }
	    else if (o instanceof D15) {
D15 ccjtt = (D15)o;
			StringBuilder sb = new StringBuilder();
			sb.append("CSContentJoinTableType{");
			sb.append("table=" + ccjtt.getD16());
			sb.append("}");
			return sb.toString();
	    } else if (o instanceof D17) {
D17 ccsdt = (D17)o;
			StringBuilder sb = new StringBuilder();
			sb.append("CSContentSitDownType{");
			sb.append("" + ccsdt.getD18());
			sb.append("}");
			return sb.toString();
	    } else if (o instanceof D22) {
D22 cgct = (D22)o;
			StringBuilder sb = new StringBuilder();
			if (cgct instanceof D23) {
				sb.append("CSGameEmptyType{");
			} else if (cgct instanceof D24) {
				D24 curPodAction = (D24)cgct;
				sb.append("CSGamePodActionType{" + curPodAction.getD25());
			} else if (cgct instanceof D22) {
				sb.append("CSGameContentType{");
			} else {
				throw new IllegalStateException("Unknown game content type: " + cgct.getClass().getCanonicalName());
			}
			sb.append("}");
			return sb.toString();
	    }
	    else if (o instanceof D4) {
D4 ct = (D4)o;
	        	StringBuilder sb = new StringBuilder();
	        	sb.append("CSType{");
	        	sb.append(
	        			// "code=" + 
	        			ct.getC13());
	        	sb.append(", " +
	        			// "content=" + 
	        			ct.getD5());
	        	sb.append("}");
	        	return sb.toString();
	    } else if (o instanceof E34) {
	    	E34 saut = (E34)o;
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("SCContentActionUpdateType{");
	    	sb.append("" + saut.getE29());
	    	sb.append(", actor=" + saut.getE35());
	    	sb.append(", action=" + saut.getE36());
	    	sb.append(", ar=" + saut.getE30());
	    	sb.append("}");
	    	return sb.toString();
	    } else if (o instanceof E28) {
E28 scgt = (E28)o;
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("SCContentGameType{");
	    	sb.append("" + scgt.getE29());
	    	sb.append(", " + scgt.getE30());
	    	sb.append("}");
	    	return sb.toString();
	    } else if (o instanceof F23) {
	    	F23 lst = (F23)o;
	        	StringBuilder sb = new StringBuilder();
	        	sb.append("LobbySeatType{");
	        	sb.append("" + lst.getF24() + ", " + lst.getF25());
	        	sb.append("}");
	        	return sb.toString();
	    }
	    else if (o instanceof F6) {
F6 u = (F6)o;
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("User{");
	    	sb.append("name=" + u.getF7());
	    	sb.append(", avatar=" + u.getF8());
	    	sb.append("}");
	    	return sb.toString();
	    } else if (o instanceof G27) {
	    	G27 tst = (G27)o;
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("TableStateType{");
	    	sb.append("" + tst.getG28());
	    	sb.append(", " + tst.getG29());
	    	sb.append("}");
	    	return sb.toString();
	    }
	    else {
	    	throw new IllegalArgumentException("Type not supported: " + o.getClass());
	    }
		
	}

	public static boolean seatUpdatesAreEqual(List<F20> list1,
			List<F20> list2) {
		for1: for (F20 cur1 : list1) {
			F1 id1 = cur1.getF21();
			for (F20 cur2 : list2) {
				if (cur2.getF21().equals(id1)) {
					if (cur1.getF22() instanceof F17) {
						if (cur2.getF22() instanceof F17) {
							F17 pss1 = (F17) cur1
									.getF22();
							F17 pss2 = (F17) cur2
									.getF22();
							if (pss1.getF18() != pss2.getF18()) {
								return false;
							}
						} else {
							return false;
						}
					} else if (cur1.getF22() instanceof F15) {
						if (cur2.getF22() instanceof F15) {
							F15 iss1 = (F15) cur1
									.getF22();
							F15 iss2 = (F15) cur2
									.getF22();
							if (iss1.isF16() && !iss2.isF16()) {
								return false;
							}
							if (!iss1.isF16() && iss2.isF16()) {
								return false;
							}
						} else {
							return false;
						}
					} else if (cur1.getF22() instanceof F19) {
						if (!(cur2.getF22() instanceof F19)) {
							return false;
						}
					}
					continue for1;
				}
			}
			return false;
		}
		return true;
	}

	public static boolean actionRequestsEqual(A12 ar1, A12 ar2) {
		if (ar1 == null) {
			if (ar2 == null) {
				return true;
			} else {
				return false;
			}
		}
		if (ar2 == null) {
			if (ar1 == null) {
				return true;
			} else {
				return false;
			}
		}
		if (ar1 instanceof A13) {
			if (ar2 instanceof A13) {
				A13 onTurn1 = (A13) ar1;
				A13 onTurn2 = (A13) ar2;
				if (!onTurn1.getType().equals(onTurn2.getType())) {
					return false;
				}
				if (!buttonsEqual(onTurn1.getA14(), onTurn2.getA14())) {
					return false;
				}
				if (onTurn1.getA15() == null) {
					if (onTurn2.getA15() != null) {
						return false;
					}
				}
				if (onTurn2.getA15() == null) {
					if (onTurn1.getA15() != null) {
						return false;
					}
				}
				if (onTurn1 != null && onTurn2 != null
						&& onTurn1.equals(onTurn2)) {
					return false;
				}
				return true;
			} else {
				return false;
			}
		}
		if (ar1 instanceof A16) {
			if (ar2 instanceof A16) {
				A16 others1 = (A16) ar1;
				A16 others2 = (A16) ar2;
				if (!others1.getA17().equals(others2.getA17())) {
					return false;
				}
				if (!others1.getType().equals(others2.getType())) {
					return false;
				}
				return true;
			} else {
				return false;
			}
		}
		throw new IllegalArgumentException(
				"Unknown action request class type: "
						+ ar1.getClass().getCanonicalName());
	}

	public static boolean tableTypesEqual(B28 table1,
			B28 table2) {
		if (table1 == null) {
			if (table2 != null) {
				return false;
			}
		}
		if (table2 == null) {
			if (table1 != null) {
				return false;
			}
		}
		if (table1 == null && table2 == null) {
			return true;
		}
		return tablesEqual(table1.getB29(), table2.getB29());
	}

	public static boolean actionsEqual(A18 a1, A18 a2) {
		if (a1 == null) {
			if (a2 == null) {
				return true;
			} else {
				return false;
			}
		}
		if (a2 == null) {
			if (a1 == null) {
				return true;
			} else {
				return false;
			}
		}
		if (!a1.getA19().equals(a2.getA19())) {
			return false;
		}
		if (!listsEqual(a1.getA21(), a2.getA21())) {
			return false;
		}
		if (!tableTypesEqual(a1.getA20(), a2.getA20())) {
			return false;
		}
		return true;
	}

	public static String getPresentableFormat(final A12 ar) {
		StringBuilder sb = new StringBuilder();
		if (ar instanceof A13) {
			A13 onTurn = (A13) ar;
			sb.append("OnTurn{");
			sb.append("type=" + onTurn.getType());
			sb.append(", buttons=" + onTurn.getA14());
			sb.append(", max=" + onTurn.getA15());
			sb.append("}");
		} else if (ar instanceof A16) {
			A16 others = (A16) ar;
			sb.append("Others{");
			sb.append("type=" + others.getType());
			sb.append(", on turn=" + others.getA17());
			sb.append("}");
		} else if (ar == null) {
			sb.append("null");
		} else {
			throw new IllegalArgumentException(
					"Unknown Podkidnoy action request class: "
							+ ar.getClass().getCanonicalName());
		}
		return sb.toString();
	}

	public static String getPresentableFormat(A18 action) {
		if (action == null) {
			return "null";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("PodAction{");
		sb.append("type=" + action.getA19());
		sb.append(", cards=");
		if (action.getA21() == null) {
			sb.append("null");
		} else {
			sb
					.append(getPresentableFormatForCards(action.getA21()
							.getB27()));
		}
		sb.append(", table=");
		if (action.getA20() == null) {
			sb.append("null");
		} else {
			sb.append(getPresentableFormat(action.getA20().getB29()));
		}
		sb.append("}");
		return sb.toString();
	}

	public static boolean isFourSameRanks(List<B23> pairs) {
		for (B6 rank : getRanksList(pairs)) {
			// List<CardType> allTableCards = getAllTableCards(pairs);
			Iterator<B20> it = getAllTableCards(pairs).iterator();
			int counter = 0;
			while (it.hasNext()) {
				if (it.next().getB22().equals(rank)) {
					counter++;
				}
			}
			if (counter < 4) {
				return false;
			}
		}
		return true;
	}

	public static List<B20> getAllTableCards(final B28 _table) {
		return getAllTableCards(_table.getB29());
	}

	public static List<B20> getAllTableCards(List<B23> pairs) {
		List<B20> ret = new ArrayList<B20>();
		Iterator<B23> it = pairs.iterator();
		while (it.hasNext()) {
			B23 p = it.next();
			ret.add(p.getB25());
			B20 defender = p.getB24();
			if (defender != null) {
				ret.add(defender);
			}
		}
		return ret;
	}

	/* START OF MARSHALING AND UNMARSHALING OPERATIONS */

	/**
	 * Marshals parameter card to compact string presentation
	 * 
	 * @param ct
	 *            Card
	 * @return Parameter card as a string
	 */
	public static String marshallCard(B20 ct) {
		char cRank = Utils.rankToChar(ct.getB22());
		char cSuit = Utils.suitToChar(ct.getB21());
		String ret = "" + cRank + cSuit;
		return ret;
	}

	/**
	 * Unmarshals parameter card from compact string presentation to card
	 * 
	 * @param cmd
	 *            String representation of card
	 * @return Card
	 */
	public static B20 unmarshallCard(String cmd) {
		char cRank = cmd.charAt(0);
		char cSuit = cmd.charAt(1);
		B20 ret = new B20();
		ret.setB22(Utils.charToRank(cRank));
		ret.setB21(Utils.charToSuit(cSuit));
		return ret;
	}

	/**
	 * Maps rank to character
	 * 
	 * @param r
	 *            Rank
	 * @return Character corresponding to parameter rank
	 */
	public static char rankToChar(B6 r) {
		switch (r) {
		case B_7:
			return '2';
		case B_8:
			return '3';
		case B_9:
			return '4';
		case B_10:
			return '5';
		case B_11:
			return '6';
		case B_12:
			return '7';
		case B_13:
			return '8';
		case B_14:
			return '9';
		case B_15:
			return 'T';
		case B_16:
			return 'J';
		case B_17:
			return 'Q';
		case B_18:
			return 'K';
		case B_19:
			return 'A';
		default:
			throw new IllegalArgumentException("Unsupported rank " + r);
		}
	}

	/**
	 * Maps character to rank
	 * 
	 * @param c
	 *            Character
	 * @return Rank corresponding to character
	 */
	public static B6 charToRank(char c) {
		switch (c) {
		case '2':
			return B6.B_7;
		case '3':
			return B6.B_8;
		case '4':
			return B6.B_9;
		case '5':
			return B6.B_10;
		case '6':
			return B6.B_11;
		case '7':
			return B6.B_12;
		case '8':
			return B6.B_13;
		case '9':
			return B6.B_14;
		case 'T':
			return B6.B_15;
		case 'J':
			return B6.B_16;
		case 'Q':
			return B6.B_17;
		case 'K':
			return B6.B_18;
		case 'A':
			return B6.B_19;
		default:
			throw new IllegalArgumentException("Unsupported rank: " + c);
		}
	}

	/**
	 * Maps suit to character
	 * 
	 * @param suit
	 *            Suit
	 * @return Character corresponding to parameter suit
	 */
	public static char suitToChar(B1 suit) {
		switch (suit) {
		case B_2:
			return 'C';
		case B_3:
			return 'D';
		case B_4:
			return 'H';
		case B_5:
			return 'S';
		default:
			throw new IllegalArgumentException("Unsupported suit: " + suit);
		}
	}

	/**
	 * Maps character to suit
	 * 
	 * @param c
	 *            Character
	 * @return Suit Suit corresponding to parameter character
	 */
	public static B1 charToSuit(char c) {
		switch (c) {
		case 'C':
			return B1.B_2;
		case 'D':
			return B1.B_3;
		case 'H':
			return B1.B_4;
		case 'S':
			return B1.B_5;
		default:
			throw new IllegalArgumentException("Unsupported suit: " + c);
		}
	}

	/**
	 * Marshals button into command representation
	 * 
	 * @param type
	 *            Button as button type code
	 * @return String presentation of parameter button
	 */
	public static String marshallButton(A5 type) {
		String ret = type.name();
		return ret;
	}

	/**
	 * Unmarshals button from command representation form into corresponding
	 * button type code
	 * 
	 * @param cmd
	 *            Command representation form
	 * @return Button type code
	 */
	public static A5 unmarshallButton(String cmd) {
		A5 ret = A5.valueOf(cmd);
		return ret;
	}

	/**
	 * Maps parameter seat ID to its position on client screen
	 * 
	 * @param selfID
	 *            Seat as seat ID code
	 * @return Position of seat on screen
	 */
	public static int mapIDToRank(F1 selfID) {
		return selfID.ordinal();
	}

	/**
	 * Maps parameter seat position to corresponding seat ID
	 * 
	 * @param retRank
	 *            Seat position on screen
	 * @return Seat as seat ID code
	 */
	public static F1 mapRankToSeatID(int retRank) {
		F1 ret = F1.values()[retRank];
		return ret;
	}

	/* END OF MARSHALING AND UNMARSHALING OPERATIONS */

	/* START OF STRING REPRESENTATION OPERATIONS */

	/**
	 * Returns human-readable representation of parameter cards
	 * 
	 * @param _cards
	 *            Cards
	 * @return Readable string representation of parameter cards
	 */
	public static String getPresentableFormatForCards(
			final B26 _cards) {
		if (_cards == null) {
			return "null";
		}
		return getPresentableFormatForCards(_cards.getB27());
	}

	/**
	 * Returns human-readable representation of parameter cards
	 * 
	 * @param value
	 *            Cards
	 * @return Readable string representation of parameter cards
	 */
	public static String getPresentableFormatForCards(List<B20> value) {
		StringBuilder sb = new StringBuilder();
		Iterator<B20> it = value.iterator();
		while (it.hasNext()) {
			String cur = getPresentableFormat(it.next());
			sb.append(cur);
			if (it.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	/**
	 * Returns human-readable representation of parameter table cards
	 * 
	 * @param B29
	 *            Table cards
	 * @return String representation of table cards
	 */
	public static String getPresentableFormatForTable(
			final B28 _table) {
		if (_table == null) {
			return "null";
		}
		return "TableCardsType{pairs=" + printTables(_table.getB29()) + "}";
	}

	/**
	 * Returns human-readable representation of parameter table cards
	 * 
	 * @param pairs
	 *            Table cards
	 * @return String representation of table cards
	 */
	public static String printTables(List<B23> pairs) {
		StringBuilder sb = new StringBuilder();
		Iterator<B23> it = pairs.iterator();
		while (it.hasNext()) {
			B23 p = it.next();
			sb.append(printPair(p));
			if (it.hasNext()) {
				sb.append(", ");
			}

		}
		return sb.toString();
	}

	/**
	 * Returns human-readable representation of one table cards pair
	 * 
	 * @param p
	 *            Table cards pair
	 * @return String representation of pair
	 */
	public static String printPair(B23 p) {
		StringBuilder sb = new StringBuilder();
		sb.append("" +
		// getPresentableFormat(p.getBottom().getRank()) + // "-" +
				// / suitToChar(p.getBottom().getSuit())
				getPresentableFormat(p.getB25()));
		if (p.getB24() == null) {
			sb.append("=null");
		} else {
			sb.append("=" +
			// p.getDefender().getRank() + "-" + p.getDefender().getSuit()
					getPresentableFormat(p.getB24()));
		}
		return sb.toString();
	}

	/**
	 * Returns human-readable presentation of rank
	 * 
	 * @param label
	 *            Rank
	 * @return Rank as string
	 */
	public static String getPresentableFormat(B6 label) {
		switch (label) {
		case B_15:
			return "10";
		default:
			return "" + rankToChar(label);
		}
	}

	/**
	 * Returns human-readable presentation of parameter card
	 * 
	 * @param bottom
	 *            Card
	 * @return String representation of parameter card
	 */
	public static String getPresentableFormat(B20 bottom) {
		if (bottom == null) {
			return "null";
		}
		B1 suit = bottom.getB21();
		B6 rank = bottom.getB22();
		char cSuit = suitToChar(suit);
		String cRank = getPresentableFormat(rank);
		String ret = "" + cRank + cSuit;
		return ret;
	}

	/**
	 * Returns human-readable presentation of parameter table cards
	 * 
	 * @param updateCards
	 *            Table cards
	 * @return String representation of parameter table cards
	 */
	public static String getPresentableFormat(
			List<B23> updateCards) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Iterator<B23> it = updateCards.iterator();
		while (it.hasNext()) {
			B23 cur = it.next();
			B20 bottom = cur.getB25();
			sb.append(getPresentableFormat(bottom));
			sb.append("-");
			B20 def = cur.getB24();
			if (def != null) {
				sb.append(getPresentableFormat(def));
			} else {
				sb.append("null");
			}
			if (it.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/* END OF STRING REPRESENTATION OPERATIONS */

	/* START OF TABLE CARDS OPERATIONS */

	/**
	 * Returns all cards from parameter cards that have rank equal to one of
	 * parameter table cards
	 * 
	 * @param _table
	 *            Table cards
	 * @param _cards
	 *            Cards
	 * @return Cards from parameter cards that have rank among table cards
	 */
	public static List<B20> addCards(List<B23> _table,
			List<B20> _cards) {
		List<B20> ret = new ArrayList<B20>();
		if (_cards.size() > 1) {
			List<B6> tableValues = Utils.getRanksList(_table);
			Iterator<B20> it = _cards.iterator();
			while (it.hasNext()) {
				B20 option = it.next();
				if (tableValues.contains(option.getB22())) {
					ret.add(option);
				}
			}
		}
		return ret;
	}

	/**
	 * Adds parameter cards to parameter table cards as bottom cards
	 * 
	 * @param _table
	 *            Table cards
	 * @param _cards
	 *            Cards to be added
	 */
	public static void addBottomsToTable(List<B23> _table,
			List<B20> _cards) {
		for (B20 c : _cards) {
			B23 p = new B23();
			p.setB25(c);
			_table.add(p);
		}
	}

	/**
	 * Checks if parameter table cards have parameter card as one of its
	 * defending cards
	 * 
	 * @param value
	 *            Table cards
	 * @param cardDef
	 *            Defending card
	 * @return True if defending card was found among table cards' defending
	 *         cards, false otherwise
	 */
	public static boolean tableContainsDefender(List<B23> value,
			B20 cardDef) {
		for (B23 p : value) {
			B20 curDef = p.getB24();
			if (cardsAreEqual(curDef, cardDef)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns list of all ranks represented in parameter table cards
	 * 
	 * @param _tablePairs
	 *            Table cards
	 * @return Ranks
	 */
	public static List<B6> getRanksList(final B28 _table) {
		return getRanksList(_table.getB29());
	}

	/**
	 * Returns list of all ranks represented in parameter table cards
	 * 
	 * @param _tablePairs
	 *            Table cards
	 * @return Ranks
	 */
	public static List<B6> getRanksList(List<B23> _tablePairs) {
		List<B6> ret = new ArrayList<B6>();
		Iterator<B23> it = _tablePairs.iterator();
		while (it.hasNext()) {
			B23 cur = it.next();
			B20 key = cur.getB25();
			B6 keyRank = key.getB22();
			if (!ret.contains(keyRank)) {
				ret.add(keyRank);
			}
			B20 value = cur.getB24();
			if (value != null) {
				B6 valueRank = value.getB22();
				if (!ret.contains(valueRank)) {
					ret.add(valueRank);
				}
			}
		}
		return ret;
	}

	/**
	 * Returns undefended bottom cards from parameter table cards
	 * 
	 * @param _value
	 *            Table cards
	 * @return Undefended bottom cards
	 */
	public static List<B20> getUndefendedBottoms(
			List<B23> _value) {
		List<B20> ret = new ArrayList<B20>();
		Iterator<B23> it = _value.iterator();
		while (it.hasNext()) {
			B23 cur = it.next();
			B20 key = cur.getB25();
			B20 value = cur.getB24();
			if (value == null) {
				ret.add(key);
			}
		}
		return ret;
	}

	/**
	 * Returns defending cards from parameter table cards
	 * 
	 * @param _value
	 *            Table cards
	 * @return Defending cards
	 */
	public static List<B20> getDefendingCards(B28 _table) {
		return getDefendingCards(_table.getB29());
	}

	/**
	 * Returns defending cards from parameter table cards
	 * 
	 * @param _value
	 *            Table cards
	 * @return Defending cards
	 */
	public static List<B20> getDefendingCards(
			List<B23> value) {
		List<B20> ret = new ArrayList<B20>();
		for (B23 p : value) {
			B20 c = p.getB24();
			if (c != null) {
				ret.add(c);
			}
		}
		return ret;
	}

	/**
	 * Returns bottom cards from parameter table cards
	 * 
	 * @param _value
	 *            Table cards
	 * @return Bottom cards
	 */
	public static List<B20> getBottomCards(final B28 _table) {
		return getBottomCards(_table.getB29());
	}

	/**
	 * Returns bottom cards from parameter table cards
	 * 
	 * @param _value
	 *            Table cards
	 * @return Bottom cards
	 */
	public static List<B20> getBottomCards(List<B23> value) {
		List<B20> ret = new ArrayList<B20>();
		for (B23 p : value) {
			B20 b = p.getB25();
			if (b != null) {
				ret.add(b);
			}
		}
		return ret;
	}

	/**
	 * Checks if parameter table cards can be defended with parameter cards
	 * 
	 * @param killers
	 *            Cards used for defending table; must contain also table cards'
	 *            defending cards if those are used
	 * @param table
	 *            Table cards; defending cards have to be added to parameter
	 *            cards if used
	 * @param trump
	 *            Trump card, used to determine trump suit
	 * @return One possible combination of table cards if table can be defended
	 *         by parameter cards, null if table cannot be defended by parameter
	 *         cards
	 */
	public static List<B23> calcDefendedTable(
			List<B20> killers, List<B23> table,
			B20 trump) {
		List<B20> bottomCards = Utils.getBottomCards(table);
		List<B23> defendedTable = new ArrayList<B23>();
		List<B23> ret = calcDefendedTableRecursively(killers,
				bottomCards, defendedTable, trump);
		return ret;
	}

	/**
	 * Tries recursively all combinations of table bottom cards and killing
	 * cards and returns defended table if found
	 * 
	 * @param killers
	 *            Killing cards
	 * @param bottomCards
	 *            Bottom cards to be defended
	 * @param defendedTable
	 *            Provide empty table with call from outside
	 * @param trump
	 *            Trump card
	 * @return One possible defended table if table can be defended, null
	 *         otherwise
	 */
	private static List<B23> calcDefendedTableRecursively(
			List<B20> killers, List<B20> bottomCards,
			List<B23> defendedTable, B20 trump) {
		Iterator<B20> itBottom = bottomCards.iterator();
		while (itBottom.hasNext()) {
			B20 cardBottom = itBottom.next();
			Iterator<B20> itKillers = killers.iterator();
			while (itKillers.hasNext()) {
				B20 cardKiller = itKillers.next();
				if (kills(trump, cardKiller, cardBottom)) {
					List<B20> tmpKillers = new ArrayList<B20>();
					tmpKillers.addAll(killers);
					ArrayList<B20> tmpBottomCards = new ArrayList<B20>();
					tmpBottomCards.addAll(bottomCards);
					List<B23> tmpDefendedTable = new ArrayList<B23>(
							defendedTable);
					tmpKillers.remove(cardKiller);
					tmpBottomCards.remove(cardBottom);
					B23 p = new B23();
					p.setB25(cardBottom);
					p.setB24(cardKiller);
					tmpDefendedTable.add(p);
					if (tmpBottomCards.isEmpty()) {
						return tmpDefendedTable;
					} else {
						List<B23> recursiveResult = calcDefendedTableRecursively(
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
	 * Checks if parameter table cards are defended
	 * 
	 * @param value
	 *            Table cards
	 * @return True if table is defended, false otherwise
	 */
	public static boolean isTableDefended(List<B23> value) {
		for (B23 p : value) {
			if (p.getB24() == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks that all bottom cards in parameter table cards are defended
	 * 
	 * @param value
	 *            Table cards
	 * @return True if all bottom cards are defended, false otherwise
	 */
	public static boolean isAllUndefended(List<B23> value) {
		for (B23 p : value) {
			if (p.getB24() != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Removes pair from parameter table cards that has parameter card as bottom
	 * card
	 * 
	 * @param value
	 *            Table cards
	 * @param cardFirst
	 *            Bottom card of pair to be removed
	 */
	public static void removePairByBottom(List<B23> value,
			B20 cardFirst) {
		for (B23 p : value) {
			B20 bottom = p.getB25();
			if (cardsAreEqual(bottom, cardFirst)) {
				value.remove(p);
				return;
			}
		}
		throw new IllegalArgumentException("Card "
				+ Utils.getPresentableFormat(cardFirst) + " not found among "
				+ getPresentableFormat(value));
	}

	/**
	 * Removes pair from parameter table cards that has parameter card as
	 * defending card
	 * 
	 * @param value
	 *            Table cards
	 * @param cardFirst
	 *            Defending card of pair to be removed
	 */
	public static void removeDefender(List<B23> value,
			B20 cardFirst) {
		for (B23 p : value) {
			B20 curDef = p.getB24();
			if (cardsAreEqual(curDef, cardFirst)) {
				p.setB24(null);
				return;
			}
		}
		throw new IllegalArgumentException("Card "
				+ Utils.getPresentableFormat(cardFirst) + " not found among "
				+ getPresentableFormat(value));
	}

	/**
	 * Defends bottom card in table cards with defending card
	 * 
	 * @param _pairs
	 *            Table cards
	 * @param _bottom
	 *            Bottom card
	 * @param _defender
	 *            Defending card
	 * @throws IllegalArgumentException
	 *             Bottom card was not found among table cards
	 */
	public static void defendBottomCard(List<B23> _pairs,
			B20 _bottom, B20 _defender) {
		for (B23 p : _pairs) {
			B20 pBotttom = p.getB25();
			if (Utils.cardsAreEqual(_bottom, pBotttom)) {
				p.setB24(_defender);
				return;
			}
		}
		throw new IllegalArgumentException("Table "
				+ Utils.getPresentableFormat(_pairs)
				+ " does not contain bottom "
				+ Utils.getPresentableFormat(_bottom));
	}

	/**
	 * Checks if parameter table cards are equal
	 * 
	 * @param list1
	 *            Table cards 1
	 * @param list2
	 *            Table cards 2
	 * @return True if table cards are equal, false otherwise
	 */
	public static boolean tablesEqual(List<B23> list1,
			List<B23> list2) {
		for1: for (B23 p1 : list1) {
			for (B23 p2 : list2) {
				if (cardsEqual(p2.getB25(), p1.getB25())) {
					if (p2.getB24() == null && p1.getB24() == null) {
						continue for1;
					} else if (p2.getB24() != null
							&& p1.getB24() != null) {
						if (cardsEqual(p2.getB24(), p1.getB24())) {
							continue for1;
						}
					}
				}
			}
			return false;
		} // for1
		return true;
	}

	/* END OF TABLE CARDS OPERATIONS */

	/* START OF CARD OPERATIONS */

	/**
	 * Returns card with given rank and suit
	 * 
	 * @param _rank
	 *            Rank
	 * @param _suit
	 *            Suit
	 * @return Card
	 */
	public static B20 getCard(final B6 _rank, final B1 _suit) {
		B20 ret = new B20();
		ret.setB22(_rank);
		ret.setB21(_suit);
		return ret;
	}

	/**
	 * Checks if parameter cards are equal
	 * 
	 * @param c1
	 *            Card 1
	 * @param c2
	 *            Card 2
	 * @return True if cards are equal, false otherwise
	 */
	public static boolean cardsAreEqual(B20 c1, B20 c2) {
		if (c1 != null && c2 != null && c1.getB22().equals(c2.getB22())
				&& (c1.getB21().equals(c2.getB21()))) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if parameter defending card can defend parameter bottom card
	 * 
	 * @param trump
	 *            Trump card
	 * @param cardKiller
	 *            Defending card
	 * @param cardBottom
	 *            Bottom card
	 * @return True if defending card can defend bottom card, false otherwise
	 */
	public static boolean kills(B20 trump, B20 cardKiller,
			B20 cardBottom) {
		boolean ret = false;
		int rankKiller = cardKiller.getB22().ordinal();
		int rankBottom = cardBottom.getB22().ordinal();
		B1 suitKiller = cardKiller.getB21();
		B1 suitBottom = cardBottom.getB21();
		B1 suitTrump = trump.getB21();
		if (suitKiller.equals(suitBottom)) {
			if (rankKiller >= rankBottom) {
				ret = true;
			}
		} else if (suitKiller.equals(suitTrump)
				&& !suitBottom.equals(suitTrump)) {
			ret = true;
		}
		return ret;
	}

	/**
	 * Checks if parameter cards are equal
	 * 
	 * @param card1
	 *            Card 1
	 * @param card2
	 *            Card 2
	 * @return True if cards are equal, false otherwise
	 */
	public static boolean cardsEqual(B20 card1, B20 card2) {
		return card2.getB22().equals(card1.getB22())
				&& card2.getB21().equals(card1.getB21());
	}

	/* END OF CARD OPERATIONS */

	/* START OF CARDS OPERATIONS */

	/**
	 * Checks if parameter cards contain parameter subset cards
	 * 
	 * @param _cards
	 *            Cards
	 * @param _mustBeContained
	 *            Subset cards
	 * @return True if parameter cards contain parameter subset cards, false
	 *         otherwise
	 */
	public static boolean listContainsOther(final B26 _cards,
			final B26 _mustBeContained) {
		return listContainsOther(_cards.getB27(), _mustBeContained.getB27());
	}

	/**
	 * Checks if parameter cards contain parameter subset cards
	 * 
	 * @param _cards
	 *            Cards
	 * @param _mustBeContained
	 *            Subset cards
	 * @return True if parameter cards contain parameter subset cards, false
	 *         otherwise
	 */
	public static boolean listContainsOther(final List<B20> _cards,
			final List<B20> _mustBeContained) {
		for1: for (B20 card1 : _mustBeContained) {
			for (B20 card2 : _cards) {
				if (cardsEqual(card1, card2)) {
					continue for1;
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * Removes parameter subset of cards from parameter cards
	 * 
	 * @param _cardsContainingRemoved
	 *            Cards
	 * @param _removed
	 *            Subset of cards to be removed
	 */
	public static void removeCardsFromList(
			List<B20> _cardsContainingRemoved, List<B20> _removed) {
		for (B20 ct : _removed) {
			removeCardFromList(_cardsContainingRemoved, ct);
		}
	}

	/**
	 * Returns all cards of one rank from parameter cards; it is guaranteed that
	 * no other rank has more cards among parameter cards
	 * 
	 * @param modelCards
	 *            Cards
	 * @return All equally ranked cards from parameter cards
	 */
	public static List<B20> maxRank(List<B20> modelCards) {
		if (modelCards == null) {
			throw new NullPointerException("Cards list is null");
		}
		if (modelCards.size() < 1) {
			throw new IllegalArgumentException("Cards list is empty");
		}
		List<B20> tmpMax = new ArrayList<B20>();
		for (B20 c : modelCards) {
			B6 rank = c.getB22();
			List<B20> tmp = new ArrayList<B20>();
			tmp.add(c);
			for (B20 d : modelCards) {
				if (d.getB22().equals(rank) && !c.equals(d)) {
					tmp.add(d);
				}
			}
			if (tmp.size() > tmpMax.size()) {
				tmpMax = tmp;
			}
		}
		if (tmpMax.size() < 1) {
			throw new IllegalStateException(
					"Equally ranked cards list is empty from " + modelCards);
		}
		return tmpMax;
	}

	/**
	 * Returns all cards from parameter cards with rank equal to parameter rank
	 * 
	 * @param _list
	 *            Cards
	 * @param _rank
	 *            Rank
	 * @return All cards from parameter cards with parameter rank
	 */
	public static List<B20> sameRanked(List<B20> _list, B6 _rank) {
		List<B20> ret = new ArrayList<B20>();
		for (B20 c : _list) {
			if (_rank.equals(c.getB22())) {
				ret.add(c);
			}
		}
		return ret;
	}

	/**
	 * Removes parameter card from parameter cards
	 * 
	 * @param value
	 *            Cards
	 * @param cardAdd
	 *            Card to be removed
	 * @throws IllegalArgumentException
	 *             Card to be removed was not found among parameter cards
	 */
	public static void removeCardFromList(List<B20> value, B20 cardAdd) {
		B20 toRemove = null;
		forCards: for (B20 c : value) {
			if (cardsAreEqual(c, cardAdd)) {
				toRemove = c;
				break forCards;
			}
		}
		if (toRemove != null) {
			value.remove(toRemove);
		} else {
			throw new IllegalArgumentException("Card "
					+ getPresentableFormat(cardAdd) + " not found among "
					+ getPresentableFormatForCards(value));
		}
	}

	/**
	 * Checks if parameter cards contain parameter card
	 * 
	 * @param _cards
	 *            Cards
	 * @param _c
	 *            Card
	 * @return True if cards contain card, false otherwise
	 */
	public static boolean cardsContain(List<B20> _cards, B20 _c) {
		for (B20 c : _cards) {
			if (cardsAreEqual(c, _c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Checks if parameter cards are equal
	 * 
	 * @param playerCards
	 *            Cards 1
	 * @param playerCards2
	 *            Cards 2
	 * @return True if cards are equal, false otherwise
	 */
	public static boolean listsEqual(final List<B20> playerCards,
			final List<B20> playerCards2) {
		for1: for (B20 card1 : playerCards) {
			for (B20 card2 : playerCards2) {
				if (cardsEqual(card1, card2)) {
					continue for1;
				}
			}
			return false;
		}
		for1: for (B20 card1 : playerCards2) {
			for (B20 card2 : playerCards) {
				if (cardsEqual(card1, card2)) {
					continue for1;
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * Checks if parameter cards are equal
	 * 
	 * @param playerCards
	 *            Cards 1
	 * @param playerCards2
	 *            Cards 2
	 * @return True if cards are equal, false otherwise
	 */
	public static boolean listsEqual(B26 playerCards,
			B26 playerCards2) {
		for1: for (B20 card1 : playerCards.getB27()) {
			for (B20 card2 : playerCards2.getB27()) {
				if (cardsEqual(card1, card2)) {
					continue for1;
				}
			}
			return false;
		}
		for1: for (B20 card1 : playerCards2.getB27()) {
			for (B20 card2 : playerCards.getB27()) {
				if (cardsEqual(card1, card2)) {
					continue for1;
				}
			}
			return false;
		}
		return true;
	}

	/* END OF CARDS OPERATIONS */

	/* START OF COPY OPERATIONS */

	/**
	 * Returns a copy of parameter cards; no copies are made of single cards
	 * instances
	 * 
	 * @param _cards
	 *            Cards
	 * @return Copy of cards
	 */
	public static List<B20> getCopy(final List<B20> _cards) {
		List<B20> ret = new ArrayList<B20>();
		for (B20 c : _cards) {
			ret.add(getCopy(c));
		}
		return ret;
	}

	/**
	 * Returns a copy of parameter table cards; no copies are made of single
	 * cards instances
	 * 
	 * @param _table
	 *            Table cards
	 * @return Copy of table cards
	 */
	public static List<B23> getCopyOfTable(
			final List<B23> _table) {
		List<B23> ret = new ArrayList<B23>();
		for (B23 p : _table) {
			B23 copy = new B23();
			copy.setB25(getCopy(p.getB25()));
			B20 defender = p.getB24();
			if (defender != null) {
				copy.setB24(getCopy(defender));
			}
			ret.add(copy);
		}
		return ret;
	}

	/**
	 * Returns a copy of parameter card
	 * 
	 * @param c
	 *            Card
	 * @return Copy of card
	 */
	public static B20 getCopy(final B20 c) {
		B20 copy = new B20();
		copy.setB22(c.getB22());
		copy.setB21(c.getB21());
		return copy;
	}

	/* END OF COPY OPERATIONS */

	/* START OF BUTTONS OPERATIONS */

	/**
	 * Checks if parameter buttons are equal (i.e. contain each other exactly)
	 * 
	 * @param _buttons1
	 *            Buttons 1
	 * @param _buttons2
	 *            Buttons 2
	 * @return True if buttons are equal, false otherwise
	 */
	public static boolean isButtonsEqual(final List<A5> _buttons1,
			final List<A5> _buttons2) {
		for1: for (A5 button1 : _buttons1) {
			for (A5 button2 : _buttons2) {
				if (button1.equals(button2)) {
					continue for1;
				}
			}
			return false;
		}
		return true;
	}

	/* END OF BUTTONS OPERATIONS */

	/* START OF USER OPERATIONS */

	/**
	 * Checks that users are equal
	 * 
	 * @param user
	 *            User 1
	 * @param user2
	 *            User 2
	 * @return True if users are equal, false otherwise
	 */
	public static boolean usersEqual(F6 user, F6 user2) {
		if (user == null && user2 == null) {
			return true;
		}
		if (user != null && user2 != null) {
			if (user.getF7().equals(user2.getF7())
					&& user.getF8().equals(user2.getF8())) {
				return true;
			}
		}
		return false;
	}

	/* END OF USER OPERATIONS */

	/* START OF TABLE OPERATIONS */

	/**
	 * Returns table state difference from parameter differences by its table ID
	 * 
	 * @param diffs
	 *            Table state differences
	 * @param id
	 *            Table ID
	 * @return Difference corresponding to parameter table ID
	 * @throws IllegalArgumentException
	 *             Difference with parameter ID was not found
	 */
	public static G36 getDiffByID(
			List<G36> diffs, String id) {
		for (G36 d : diffs) {
			if (id.equals(d.getG37())) {
				return d;
			}
		}
		throw new IllegalArgumentException("Diff not found by id: " + id);
	}

	/**
	 * Returns table from parameter tables by table ID
	 * 
	 * @param tables
	 *            Tables
	 * @param id1
	 *            Table ID
	 * @return Table with parameter table ID
	 * @throws IllegalArgumentException
	 *             Table with parameter ID was not found
	 */
	public static G30 getTableByID(List<G30> tables, String id1) {
		for (G30 t : tables) {
			if (id1.equals(t.getG31())) {
				return t;
			}
		}
		throw new IllegalArgumentException("Table id not found: " + id1);
	}

	/* END OF TABLE OPERATIONS */

	/* START OF SEAT OPERATIONS */

	/**
	 * Returns lobby seat from parameter lobby seats by its seat ID code
	 * 
	 * @param seats
	 *            Lobby seats
	 * @param id
	 *            Seat ID code
	 * @return Lobby seat that has parameter seat ID code
	 * @throws IllegalArgumentException
	 *             Seat with parameter ID was not found
	 */
	public static F23 getSeatByIDF23(List<F23> seats,
			F1 id) {
		for (F23 s : seats) {
			if (id.equals(s.getF24())) {
				return s;
			}
		}
		throw new IllegalArgumentException("Seat id not found: " + id);
	}

	/**
	 * Returns seat update from parameter updates by seat ID code
	 * 
	 * @param seatUpdates
	 *            Seat updates
	 * @param id
	 *            Seat ID code
	 * @return Seat update with parameter seat ID code
	 * @throws IllegalArgumentException
	 *             Seat with parameter ID was not found
	 */
	public static F20 getSeatByIDF20(
			List<F20> seatUpdates, F1 id) {
		for (F20 ssut : seatUpdates) {
			if (id.equals(ssut.getF21())) {
				return ssut;
			}
		}
		throw new IllegalArgumentException(
				"Seat state update by id not found: " + id);
	}

	/**
	 * Checks if parameter seats are equal
	 * 
	 * @param seat1
	 *            Seat 1
	 * @param seat2
	 *            Seat 2
	 * @return True if parameter seats are equal, false otherwise
	 */
	public static boolean seatsAreEqual(F26 seat1, F26 seat2) {
		if (!seat1.getF27().equals(seat2.getF27())) {
			return false;
		}
		F14 seat1State = seat1.getF29();
		F14 seat2State = seat2.getF29();

		if (seat1State instanceof F19) {
			if (!(seat2State instanceof F19)) {
				return false;
			}
		} else if (seat1State instanceof F15) {
			if (seat2State instanceof F15) {
				F15 idle1 = (F15) seat1State;
				F15 idle2 = (F15) seat2State;
				if ((idle1.isF16() && !idle2.isF16())
						|| (idle2.isF16() && !idle1.isF16())) {
					return false;
				}
			} else {
				return false;
			}
		} else if (seat1State instanceof F17) {
			if (seat2State instanceof F17) {
				F17 playing1 = (F17) seat1State;
				F17 playing2 = (F17) seat2State;
				if (playing1.getF18() != playing2.getF18()) {
					return false;
				}
			} else {
				return false;
			}
		}
		F9 user1 = seat1.getF28();
		F9 user2 = seat2.getF28();
		if (user1 == null) {
			if (user2 != null) {
				return false;
			}
		} else if (user1 instanceof F12) {
			if (user2 instanceof F12) {
				F12 bot1 = (F12) user1;
				F12 bot2 = (F12) user2;
				F6 bot1User = bot1.getF13();
				F6 bot2User = bot2.getF13();
				if (bot1User == null) {
					if (bot2User != null) {
						return false;
					}
				} else {

					if (!usersEqual(bot1User, bot2User)) {
						return false;
					}
				}
			} else {
				return false;
			}
		} else if (user1 instanceof F10) {
			if (user2 instanceof F10) {
				F10 human1 = (F10) user1;
				F10 human2 = (F10) user1;
				if (!usersEqual(human1.getF11(), human2.getF11())) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns seat from seat list by its ID
	 * 
	 * @param seats
	 *            Seat list
	 * @param id
	 *            Seat ID
	 * @return Seat
	 */
	public static F26 getAllSeatByID(List<F26> seats, F1 id) {
		for (F26 s : seats) {
			if (id.equals(s.getF27())) {
				return s;
			}
		}
		throw new IllegalArgumentException("Seat id not found: " + id);
	}

	/* END OF SEAT OPERATIONS */

	/* START OF IMAGE OPERATIONS */

	/* END OF IMAGE OPERATIONS */

	/* START OF COLOR OPERATIONS */

	public static List<A5> unmarshallButtons(String buttonsString,
			String DEL) {
		String[] buttonsSplit = buttonsString.split(DEL, -1);
		List<A5> ret = new ArrayList<A5>();
		if (buttonsSplit[0].length() > 0) {
			for (String s : buttonsSplit) {
				A5 cur = unmarshallButton(s);
				ret.add(cur);
			}
		}
		return ret;
	}

	public static List<B20> unmarshallCards(final String _s,
			final String _del) {
		String[] split = _s.split(_del, -1);
		List<B20> ret = new ArrayList<B20>();
		if (split[0].length() > 0) {
			for (String s : split) {
				B20 cur = Utils.unmarshallCard(s);
				ret.add(cur);
			}
		}
		return ret;
	}

	public static boolean buttonsEqual(List<A5> _list1,
			List<A5> _list2) {
		for (A5 code1 : _list1) {
			if (!_list2.contains(code1)) {
				return false;
			}
		}
		for (A5 code2 : _list2) {
			if (!_list1.contains(code2)) {
				return false;
			}
		}
		return true;
	}

	public static List<B23> unmarshallTableCards(String _s,
			String _del, String _tableDel) {
		String[] split = _s.split(_del, -1);
		List<B23> ret = new ArrayList<B23>();
		if (split[0].length() > 0) {
			// System.out.println("-1-");
			for (String s : split) {
				// System.out.println("-2- s=" + s);
				String[] pairString = s.split(_tableDel, -1);
				String bottomString = pairString[0];
				String topString = pairString[1];
				// System.out.println("-3- bottom=" + bottomString + ", top=" +
				// topString);
				B20 bottom = Utils.unmarshallCard(bottomString);
				B20 top = null;
				if (!"null".equals(topString)) {
					top = Utils.unmarshallCard(topString);
				}
				B23 p = new B23();
				p.setB25(bottom);
				p.setB24(top);
				// System.out.println("p=" + Utils.printPair(p));
				ret.add(p);
			}
		}
		return ret;
	}

	public static String getPresentableFormat(final F9 value) {
		StringBuilder sb = new StringBuilder();
		sb.append("SeatUserType{");
		if (value instanceof F12) {
			F12 bsu = (F12) value;
			sb.append("type=BotSeatUser, user="
					+ getPresentableFormat(bsu.getF13()));
		} else if (value instanceof F10) {
			F10 bsu = (F10) value;
			sb.append("type=HumanSeatUser, user="
					+ getPresentableFormat(bsu.getF11()));
		} else if (value == null) {
			sb.append("null");
		}
		sb.append("");
		sb.append("}");
		return sb.toString();
	}

	public static String getPresentableFormat(final F6 user) {
		StringBuilder sb = new StringBuilder();
		sb.append("User{");
		if (user == null) {
			sb.append("null");
		} else {
			sb
					.append("name=" + user.getF7() + ", avatar="
							+ user.getF8());
		}
		sb.append("}");
		return sb.toString();
	}

	public static String getPresentableFormat(final F14 value) {
		StringBuilder sb = new StringBuilder();
		sb.append("AbstractSeatState{");
		if (value instanceof F17) {
			F17 bsu = (F17) value;
			sb.append("type=PlayingSeatState, cards=" + bsu.getF18());
		} else if (value instanceof F19) {
			F19 bsu = (F19) value;
			sb.append("type=UnusedSeatState");
		} else if (value instanceof F15) {
			F15 bsu = (F15) value;
			sb.append("type=IdleSeatState, start=" + bsu.isF16());
		} else if (value == null) {
			sb.append("null");
		}
		sb.append("");
		sb.append("}");
		return sb.toString();
	}

	public static B20 findDefendingCard(final B28 _table,
			final B20 _bottom) {
		if (_table == null) {
			throw new NullPointerException("Table cards are null");
		}
		if (_bottom == null) {
			throw new NullPointerException("Bottom card is null");
		}
		if (_bottom.getB22() == null) {
			throw new NullPointerException("Bottom card rank is not set");
		}
		if (_bottom.getB21() == null) {
			throw new NullPointerException("Bottom card suit is not set");
		}
		/*
		 * List<CardType> bottoms = getBottomCards(_table); if
		 * (!cardsContain(bottoms, _bottom)) { throw new
		 * IllegalArgumentException("Table cards " + printTables(_table) +
		 * " do not contain bottom card " + getPresentableFormat(_bottom)); }
		 */
		Iterator<B23> it = _table.getB29().iterator();
		while (it.hasNext()) {
			B23 cur = it.next();
			if (cardsAreEqual(_bottom, cur.getB25())) {
				B20 ret = cur.getB24();
				// Can add not null check in here
				return ret;
			}
		}
		throw new IllegalArgumentException("Table cards "
				+ getPresentableFormatForTable(_table)
				+ " do not contain bottom card "
				+ getPresentableFormat(_bottom));
	}
	
	public static G36 compareTables(String _key,
			G27 _old, G27 _current) {
		boolean change = false;
		G36 ret = new G36();
		ret.setG37(_key);
		 G1 curCode = _current.getG28();
		 G1 oldCode = _old.getG28();
		// System.out.println(_key + " curCode=" + curCode + ", oldCode=" + oldCode);
		if (!curCode.equals(oldCode)) {
			ret.setG38(curCode);
			change = true;
			// System.out.println("HHHHHHHHHHHHHHH\nHHHHHHHHHHHHHH\nstate code change " + _key);
		}
		List<F23> oldSeats = _old.getG29();
		// Iterator<LobbySeatType> itOld = _old.getSeats().iterator();
		// System.out.println("seats size = " + _current.getSeats().size());
		Iterator<F23> itCurrent = _current.getG29().iterator();
		while (itCurrent.hasNext()) {
			F23 cur = itCurrent.next();
			F1 curID = cur.getF24();
			F23 curOld = Utils.getSeatByIDF23(oldSeats, curID);
			// System.out.println(_key + ", curID=" +curID + ", cur (seat)=" + cur + ", curOld=" + curOld);
			boolean equal = Utils.isSeatsEqual(cur, curOld);
			if (!equal) {
				ret.getG39().add(cur);
				change = true;
				// System.out.println("seat change " + _key);
			}
		}
		if (change) {
			return ret;
		} else {
			return null;
		}
	}

	private static boolean isSeatsEqual(F23 _lobbySeat1,
			F23 _lobbySeat2) {
		if (_lobbySeat1.getF24().equals(_lobbySeat2.getF24())) {
			if (Utils.usersEqual(_lobbySeat1.getF25(), _lobbySeat2.getF25())) {
				return true;
			}
		}
		return false;
	}

	/*
	public static GameConfType getGameConfForPod(final CardPackType _pack,
			final int _numOfPlayers, final boolean _sending,
			final int _turnCards) {
		GameConfType ret = new GameConfType();
		ret.setGameType(GameTypeCode.POD);
		ServerPodConfigType podConf = new ServerPodConfigType();
		podConf.setPackType(_pack);
		podConf.setPlayers(_numOfPlayers);
		podConf.setSending(_sending);
		podConf.setTurnCards(_turnCards);
		ret.setConf(podConf);
		return ret;
	}
*/

	public static void checkCommand(D4 _cmd, C1 _code) {
		if (_cmd == null) {
			throw new NullPointerException("Parameter command is null");
		}
		C1 code = _cmd.getC13();
		if (code == null) {
			throw new NullPointerException("Command code is not set");
		}
		if (_code == null) {
			throw new NullPointerException("Parameter command code is null");
		}
		if (!_code.equals(code)) {
			throw new IllegalArgumentException("Command code " + code
					+ " does not match that of expected " + _code);
		}
		D7 content = _cmd.getD5();
		if (content == null) {
			throw new NullPointerException("Content is null");
		}
	}
	public static void checkCommand(E19 _cmd, C1 _code) {
		checkCommand( _cmd,  _code, false
				);
	}

	public static void checkCommand(E19 _cmd, C1 _code, boolean _mustHaveNonNullContent) {
		if (_cmd == null) {
			throw new NullPointerException("Parameter command is null");
		}
		C1 code = _cmd.getC13();
		if (code == null) {
			throw new NullPointerException("Command code is not set");
		}
		if (_code == null) {
			throw new NullPointerException("Parameter command code is null");
		}
		if (!_code.equals(code)) {
			throw new IllegalArgumentException("Command code " + code
					+ " does not match that of expected " + _code);
		}
		if (_mustHaveNonNullContent) {
		E24 content = _cmd.getE20();
		if (content == null) {
			throw new NullPointerException("Content is null");
		}
		}
	}

	/* END OF COLOR OPERATIONS */

}
