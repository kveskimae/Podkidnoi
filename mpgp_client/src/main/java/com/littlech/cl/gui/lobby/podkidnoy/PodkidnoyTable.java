/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.podkidnoy;

import java.awt.Color;
import java.awt.Component;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.netbeans.lib.awtextra.AbsoluteConstraints;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.gui.lobby.constants.*;
import com.littlech.gen.g.G1;

/**
 * 
 * Table for Podkidnoy lobby tables
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class PodkidnoyTable extends JTable { // implements ISelectionInfo {

	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;

	/**
	 * Renderers for each cell
	 */
	private Map<Integer, Map<Integer, PodkidnoyTableCellRenderer>> renderers = new HashMap<Integer, Map<Integer, PodkidnoyTableCellRenderer>>();

	/**
	 * Constraint
	 */
	private AbsoluteConstraints constraint = new AbsoluteConstraints(LobbyLocations.LOBBY_TABLE_SCROLLPANE, LobbySizes.LOBBY_TABLE_SCROLLPANE);

	/**
	 * Constructor
	 * 
	 * @param imageLoader
	 *          Image loader
	 */
	public PodkidnoyTable(final IImageLoader imageLoader) {
		mImageLoader = imageLoader;
		setForeground(LobbyColors.LOBBY_TABLE_CELL_TEXT);
		setSelectionBackground(LobbyColors.LOBBY_TABLE_SELECTED_BG);
		setSelectionForeground(LobbyColors.LOBBY_TABLE_SELECTED_FG);
		setFillsViewportHeight(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setRowHeight(LobbySizes.LOBBY_ROW_HEIGHT);
		setAlignmentY(Component.CENTER_ALIGNMENT);
		getTableHeader().setReorderingAllowed(false);
		/* adjustColumns(); is not necessary here as it is done in setModel anyway */
	}

	@Override
	public void setModel(TableModel dataModel) {
		super.setModel(dataModel);
		adjustColumns();
	}

	/*
	 * @Override public int getSelected() { return getSelectedRow(); }
	 */

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		/* Getting the cell renderer from map or creating one if map does not contain */
		Integer keyRow = new Integer(row);
		Map<Integer, PodkidnoyTableCellRenderer> columnRenderers = renderers.get(keyRow);
		if (columnRenderers == null) {
			columnRenderers = new HashMap<Integer, PodkidnoyTableCellRenderer>();
			renderers.put(keyRow, columnRenderers);
		}
		Integer keyCol = new Integer(column);
		PodkidnoyTableCellRenderer rowRenderer = columnRenderers.get(keyCol);
		if (rowRenderer == null) {
			rowRenderer = new PodkidnoyTableCellRenderer(mImageLoader);
			columnRenderers.put(keyCol, rowRenderer);
		}
		// Foreground is set to default
		rowRenderer.setForeground(LobbyColors.LOBBY_TABLE_CELL_TEXT);
		Color cellBG; // New background color for the cell
		if (column != 3) {
			if (row % 2 == 0) {
				cellBG = LobbyColors.LOBBY_TABLE_ROW_YELLOW;
			} else {
				cellBG = LobbyColors.LOBBY_TABLE_ROW_WHITE;
			}
		} else {
			/* Players column has special backgrounds and foregrounds */
			PodkidnoyTableModel podModel = (PodkidnoyTableModel) getModel();
			G1 state = podModel.getTableState(row);
			switch (state) {
			case G_3:
				if (row % 2 == 0) {
					cellBG = LobbyColors.LOBBY_TABLE_ROW_YELLOW;
				} else {
					cellBG = LobbyColors.LOBBY_TABLE_ROW_WHITE;
				}
				break;
				/*
			case EMPTY:
				cellBG = LobbyColors.LOBBY_TABLE_PLAYERS_BG_EMPTY;
				break;
			case STARTING:
			*/
			case G_2:
				cellBG = LobbyColors.LOBBY_TABLE_PLAYERS_BG_WAITING;
				break;
			default:
				throw new IllegalStateException("Illegal state " + state);
			}

			switch (state) {
			/*
			case EMPTY:
			case STARTING:
			*/
			case G_2:
				rowRenderer.setForeground(LobbyColors.LOBBY_TABLE_PLAYERS_FG);
				break;
			default:
			}
		}
		rowRenderer.setBackground(cellBG);
		// If row is selected, foreground will be changed afterwards
		return rowRenderer;
	}

	/**
	 * Constraint for this table
	 * 
	 * @return Constraint
	 */
	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

	/**
	 * Adjusts table header; specifically column sizes and pictures
	 */
	private void adjustColumns() {
		TableColumnModel columnModel = getColumnModel();
		Enumeration<TableColumn> columns = columnModel.getColumns();
		while (columns.hasMoreElements()) {
			columns.nextElement().setResizable(false);
		}
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			int columnWidth = getColumnWidth(i);
			column.setPreferredWidth(columnWidth);
			column.setWidth(columnWidth);
			column.setMinWidth(columnWidth);
			column.setMaxWidth(columnWidth);
		}
		for (int i = 0; i < getColumnCount(); i++) {
			PodkidnoyTableHeaderRenderer headerRenderer = new PodkidnoyTableHeaderRenderer(mImageLoader);
			getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
		}
	}

	/**
	 * Returns width for parameter column index
	 * 
	 * @param i
	 *          Column index
	 * @return Width in pixels
	 */
	private int getColumnWidth(int i) {
		int columnWidth;
		switch (i) {
		case 0:
			columnWidth = 60;
			break;
		case 1:
			columnWidth = 40;
			break;
		case 2:
			columnWidth = 135;
			break;
		case 3:
			columnWidth = 500;
			break;
		default:
			throw new IllegalArgumentException("Not found: " + i);
		}
		return columnWidth;
	}

}
