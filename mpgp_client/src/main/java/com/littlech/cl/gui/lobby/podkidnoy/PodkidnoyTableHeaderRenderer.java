/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.podkidnoy;

import com.littlech.cl.gui.lobby.constants.*;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import com.littlech.cl.IImageLoader;

/**
 * 
 * Table header renderer for Podkidnoy lobby table
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class PodkidnoyTableHeaderRenderer extends JLabel implements TableCellRenderer {

	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;

	/**
	 * Constructor
	 * 
	 * @param _imageLoader
	 *          Image loader
	 */
	public PodkidnoyTableHeaderRenderer(final IImageLoader _imageLoader) {
		mImageLoader = _imageLoader;
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(LobbyFonts.LOBBY_HEADER);
		setForeground(LobbyColors.LOBBY_HEADER);
		setOpaque(true);
		setBackground(LobbyColors.LOBBY_BLUE);
	}

	/**
	 * This method is called each time a column header using this renderer needs
	 * to be rendered.
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		switch (vColIndex) {
		case 0:
			ImageIcon icon2 = mImageLoader.fetchImage(LobbyImages.LOBBY_TABLE_HEADER_TURN_CARDS);
			setIcon(icon2);
			break;
		case 1:
			ImageIcon icon = mImageLoader.fetchImage(LobbyImages.LOBBY_TABLE_HEADER_SENDING);
			setIcon(icon);
			break;
		default:
			setText(value.toString());
			break;
		}
		return this;
	}

	/* Overridden for performance */

	@Override
	public void validate() {
	}

	@Override
	public void revalidate() {
	}

	@Override
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
	}

	@Override
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
	}

}