/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.podkidnoy;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JViewport;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.Names;
import com.littlech.cl.gui.lobby.constants.*;
import com.littlech.cl.gui.lobby.LobbyController;
import com.littlech.cl.gui.lobby.podkidnoy.handler.PodkidnoyTableHeaderHandler;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D15;
import com.littlech.gen.d.D4;

/**
 * 
 * Podkidnoy tab for lobby tabbed pane
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class PodkidnoyTab {

	/**
	 * Base panel
	 */
	private final JPanel panel = new JPanel();

	/**
	 * Image loader
	 */
	private final IImageLoader mImageLoader;

	/**
	 * Table scroll pane
	 */
	private PodkidnoyTableScrollPane scrollPane;

	/**
	 * Table
	 */
	private PodkidnoyTable table;

	/**
	 * Table model
	 */
	private PodkidnoyTableModel tableModel;

	/**
	 * Reference to view port
	 */
	private JViewport viewport;

	/**
	 * Constructor
	 * 
	 * @param _imageLoader
	 *          Image loader
	 */
	public PodkidnoyTab(final IImageLoader _imageLoader) {
		mImageLoader = _imageLoader;
		panel.setBackground(LobbyColors.LOBBY_TABS_GAME_CONTENT_BG);
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		table = new PodkidnoyTable(mImageLoader);
		table.setName(Names.LOBBY_POD_TABLE);
		tableModel = new PodkidnoyTableModel();
		scrollPane = new PodkidnoyTableScrollPane(table);
		viewport = (JViewport) table.getParent();

		table.setModel(tableModel);

		panel.add(scrollPane, scrollPane.getConstraint());
		getTable().getTableHeader().addMouseListener(new PodkidnoyTableHeaderHandler(this));
	}

	public void headerClicked() {
		String selectedTableID = getSelectedTableID();
		boolean corrigateSelection = false;
		int diff = 0;
		if (selectedTableID != null) {
			corrigateSelection = isSelectionVisible();
			if (corrigateSelection) {
				diff = getDiff();
			}
		}
		Point p = getTable().getTableHeader().getMousePosition();
		int col = getTable().getTableHeader().columnAtPoint(p);
		int modelCol = -1;
		if (col >= 0) {
			modelCol = getTable().convertColumnIndexToModel(col);
		}
		getTableModel().sortByColumn(modelCol);
		int modelRow = getTableModel().getTableIndexByTableID(selectedTableID);
		if (modelRow >= 0) {
			int converted = getTable().convertRowIndexToView(modelRow);
			getTable().setRowSelectionInterval(converted, converted);
			if (corrigateSelection) {
				scrollToVisible(getTable(), converted, 0, diff);
			}
		}
	}

	public String getSelectedTableID() {
		int selectedRow = getTable().getSelectedRow();
		String ret = null;
		if (selectedRow >= 0) {
			selectedRow = getTable().convertRowIndexToModel(selectedRow);
			ret = getTableModel().getTableID(selectedRow);
		}
		return ret;
	}

	/**
	 * Returns table model for Podkidnoy lobby tables
	 * 
	 * @return Table model
	 */
	public PodkidnoyTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * Returns base panel for Podkinoy tab
	 * 
	 * @return Panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Returns name to be displayed on tab
	 * 
	 * @return Name
	 */
	public String getName() {
		return LobbyController.TAB_PODKIDNOY;
	}

	/**
	 * Returns table with lobby Podkidnoy tables
	 * 
	 * @return Table
	 */
	public PodkidnoyTable getTable() {
		return table;
	}

	/**
	 * Returns the distance of selected row from the upper left corner of the view
	 * port visible area in Podkidnoy table
	 * 
	 * @return Distance in pixels
	 */
	public int getDiff() {
		Rectangle rect = table.getCellRect(table.getSelectedRow(), 0, true);
		Point pt = viewport.getViewPosition();
		int diff = rect.y - pt.y;
		return diff;
	}

	/**
	 * Checks if the selected row is currently visible on the view port
	 * 
	 * @return True if the selected row is visible on the view port, false if
	 *         otherwise
	 */
	public boolean isSelectionVisible() {
		// This rectangle is relative to the table where the
		// northwest corner of cell (0,0) is always (0,0).
		Rectangle rect = table.getCellRect(table.getSelectedRow(), 0, true);

		// The location of the viewport relative to the table
		Point pt = viewport.getViewPosition();

		if (rect.getMaxY() < pt.y) {
			return false;
		}
		Dimension vpSize = viewport.getSize();
		if (rect.getMinY() > pt.y + vpSize.getHeight()) {
			return false;
		}
		return true;
	}

	/**
	 * Scrolls the selected row (i.e. in here cell with parameter row and column
	 * index) to visible area within the view port and if it is possible, back to
	 * its initial distance in the view port. Initial distance of selected row is
	 * from the upper left corner of the visible area of the view port in
	 * Podkidnoy table.
	 * 
	 * @param _table
	 *          Table
	 * @param _row
	 *          Selected row index
	 * @param _col
	 *          Any valid column index
	 * @param _distance
	 *          Initial distance of selected row
	 */
	public void scrollToVisible(final PodkidnoyTable _table, final int _row, final int _col, final int _distance) {
		if (!(_table.getParent() instanceof JViewport)) {
			throw new IllegalStateException("Parent component is " + _table.getParent().getClass().getCanonicalName());
		}
		Point pt2 = viewport.getViewPosition();
		pt2.y = _table.getCellRect(_row, _col, true).y;
		if (pt2.y - _distance >= 0) {
			pt2.y -= _distance;// Shift will not cause whitespace to appear at the
			// beginning of viewport
			viewport.setViewPosition(pt2);
		}
	}

	public void setEnabled(boolean b) {
		table.setEnabled(b);
	}

	public D4 getJoinCommand() {
		D4 ret = new D4();
		ret.setC13(C1.C_7);
		D15 content = new D15();
		content.setD16(getSelectedTableID());
		ret.setD5(content);
		return ret;
	}

}
