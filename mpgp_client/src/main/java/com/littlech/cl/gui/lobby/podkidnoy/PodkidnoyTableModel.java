/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.podkidnoy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.littlech.cl.gui.lobby.sort.*;
import com.littlech.gen.c.C1;
import com.littlech.gen.e.E19;
import com.littlech.gen.e.E48;
import com.littlech.gen.e.E50;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F23;
import com.littlech.gen.g.G1;
import com.littlech.gen.g.G21;
import com.littlech.gen.g.G30;
import com.littlech.gen.g.G36;

/**
 * 
 * Model for lobby Podkidnoy table
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class PodkidnoyTableModel extends AbstractTableModel {

	/**
	 * Flip-flops column sorting between ascending and descending
	 */
	private boolean flops = true;

	/**
	 * Podkidnoy lobby tables
	 */
	private List<G30> tables = new ArrayList<G30>();

	/**
	 * Column names
	 */
	private static final String[] COLUMNS = { "Turn", // Not used
			"Send", // Not used
			"TABLE NAME", "PLAYERS" };

	/**
	 * Returns Podkidnoy tables
	 * 
	 * @return Tables
	 */
	public List<G30> getTables() {
		return tables;
	}

	/**
	 * Updates this table model with the parameter lobby update command
	 * 
	 * @param _update
	 *          Lobby update command
	 */
	public void updateTables(final E19 _update) {
		C1 code = _update.getC13();
		if (!C1.C_5.equals(code)) {
			throw new IllegalArgumentException("Not in here: " + code);
		}
		E50 content = (E50) _update.getE20();
		/* Removing deleted tables */
		for (String deleteID : content.getE52()) {
			G30 toBeDeleted = null;
			forTables: for (G30 cur : tables) {
				String id = cur.getG31();
				if (deleteID.equals(id)) {
					toBeDeleted = cur;
					break forTables;
				}
			}
			if (toBeDeleted == null) {
				throw new IllegalArgumentException("Not found: " + deleteID);
			} else {
				tables.remove(toBeDeleted);
			}
		}
		/* Adding created tables */
		tables.addAll(content.getE51());
		/* Updating existing tables */
		for (G36 curDiff : content.getE53()) {
			String diffID = curDiff.getG37();
			G30 curTable = null;
			forTables: for (G30 cur : tables) {
				String id = cur.getG31();
				if (diffID.equals(id)) {
					curTable = cur;
					break forTables;
				}
			}
			if (curTable == null) {
				throw new IllegalArgumentException("Not found: " + diffID);
			} else {
				G1 newCode = curDiff.getG38();
				if (newCode != null) {
					curTable.getG34().setG28(newCode);
				}
				for (F23 s : curDiff.getG39()) {
					F1 sID = s.getF24();
					F23 curRealSeat = null;
					forX: for (F23 x : curTable.getG34().getG29()) {
						if (sID.equals(x.getF24())) {
							curRealSeat = x;
							break forX;
						}
					}
					if (curRealSeat == null) {
						throw new IllegalArgumentException("Not found in t " + diffID + ": " + sID);
					} else {
						curRealSeat.setF25(s.getF25());
					}
				}
			}
		}
		fireTableDataChanged();
	}

	/**
	 * Initializes this table model with the parameter command
	 * 
	 * @param _all
	 *          Lobby all command
	 */
	public void processAll(final E19 _all) {
		if (!C1.C_4.equals(_all.getC13())) {
			throw new IllegalArgumentException("Not here: " + _all.getC13());
		}
		E48 content = (E48) _all.getE20();
		tables.clear();
		tables.addAll(content.getE49());
		fireTableDataChanged();
		sortByColumn(2);
		// selected index to -1?
	}

	@Override
	public Class getColumnClass(int column) {
		Class<? extends Object> ret = getValueAt(0, column).getClass();
		return ret;
	}

	@Override
	public String getColumnName(int column) {
		return COLUMNS[column];
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public int getRowCount() {
		return tables.size();
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		// "Turn", "Send", "Table name", "Players"
		G30 table = tables.get(rowIndex);
		switch (columnIndex) {
		case 0:
			Integer ret = new Integer(((G21) table.getG33()).getG24());
			return ret;
		case 1:
			Boolean s = ((G21) table.getG33()).isG23(); // ? "+" : "-";
			return s;
		case 2:
			return table.getG32();
		case 3:
			return getNames(table);
		default:
			throw new IllegalArgumentException("Asking " + columnIndex);
		}
	}

	/**
	 * Composes list of player names sitting at the table for parameter lobby
	 * table
	 * 
	 * @param _table
	 *          Table
	 * @return List of players
	 */
	private List<String> getNames(final G30 _table) {
		List<String> ret = new ArrayList<String>();
		List<F1> ids = new ArrayList<F1>();
		ids.add(F1.F_2);
		ids.add(F1.F_3);
		ids.add(F1.F_4);
		ids.add(F1.F_5);
		Iterator<F1> it = ids.iterator();
		List<F23> seats = _table.getG34().getG29();
		F23 seat;
		while (it.hasNext() && (seat = getSeat(seats, it.next())) != null) {
			if (seat.getF25() != null) {
				ret.add(seat.getF25().getF7());
			} else {
				ret.add("Empty");
			}
		}
		return ret;
	}

	/**
	 * Returns lobby seat from list for parameter seat ID code
	 * 
	 * @param seats
	 *          List of lobby seats
	 * @param seatIDCode
	 *          Seat ID code
	 * @return Lobby seat
	 */
	private F23 getSeat(List<F23> seats, F1 seatIDCode) {
		if (seatIDCode == null) {
			throw new NullPointerException(); // "Seat id is null");
		}
		if (seats == null) {
			throw new NullPointerException(); // "Seats are null");
		}
		Iterator<F23> it = seats.iterator();
		while (it.hasNext()) {
			F23 cur = it.next();
			if (seatIDCode.equals(cur.getF24())) {
				return cur;
			}
		}
		return null;
	}

	/**
	 * Returns state code (EMPTY/STARTING/PLAYING/WAITING) for parameter table row
	 * 
	 * @param row
	 *          Table row in model view
	 * @return State code
	 */
	public G1 getTableState(final int row) {
		return tables.get(row).getG34().getG28();
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// XXX to use?
	}

	/**
	 * Returns table ID for parameter table row
	 * 
	 * @param _row
	 *          Table row in model view
	 * @return Table ID
	 */
	public String getTableID(final int _row) {
		if (_row < 0) {
			throw new IllegalArgumentException("Must be non-negative: " + _row);
		}
		int size = tables.size();
		if (_row > size) {
			throw new IllegalArgumentException("Must be smaller than tsize: " + _row + " (tsize is " + size + ")");
		}
		G30 cur = tables.get(_row);
		String ret = cur.getG31();
		return ret;
	}

	/**
	 * Returns table row for parameter table ID
	 * 
	 * @param _id
	 *          Table ID
	 * @return Table row
	 */
	public int getTableIndexByTableID(final String _id) {
		if (_id != null) {
			for (G30 t : tables) {
				String id = t.getG31();
				if (_id.equals(id)) {
					return tables.indexOf(t);
				}
			}
		}
		// not found or nothing was selected
		return -1;
	}

	/**
	 * Sorts table by parameter column
	 * @param _col Column
	 */
	public void sortByColumn(final int _col) {
		switch (_col) {
		case 0:
			Collections.sort(tables, LobbyTurnCardsColumnComparator.getInstance());
			break;
		case 1:
			Collections.sort(tables, LobbySendingColumnComparator.getInstance());
			break;
		case 2:
			Collections.sort(tables, LobbyTableNamesColumnComparator.getInstance());
			break;
		case 3:
			Collections.sort(tables, LobbyPlayersColumnComparator.getInstance());
			break;

		default:
			break;
		}
		flops = !flops;
		if (flops) {
			Collections.reverse(tables);
		}
		fireTableDataChanged();
	}

	public void reset() {
		getTables().clear();
		fireTableDataChanged();
	}

}
