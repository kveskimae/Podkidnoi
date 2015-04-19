/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.podkidnoy;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.gui.lobby.constants.*;

/**
 * 
 * Renderer for Pokdidnoy table cell
 * 
 * @author Kristjan Veskim�e
 *
 */
public class PodkidnoyTableCellRenderer extends DefaultTableCellRenderer {
	
	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;
	
	/**
	 * 
	 */
	// private ISelectionInfo mSelectionInfo;
	
	private PodkidnoyTableCellRenderer () {
	}

	public PodkidnoyTableCellRenderer(final IImageLoader mImageLoader) { //, final ISelectionInfo lobbyTable) {
		this.mImageLoader = mImageLoader;
		// this.mSelectionInfo = lobbyTable;
	}

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
		JLabel ret = (JLabel)super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
		// ret.setBackground(Color.red);
		ret.setHorizontalAlignment(SwingConstants.CENTER);
		ret.setBorder(BorderFactory.createEmptyBorder());
		ret.setFont(LobbyFonts.LOBBY_CELL);
		// ret.setForeground(Colors.LOBBY_CELL_TEXT);
		if (arg5 == 1) {
			Boolean b = (Boolean)arg1;
			if (b) {
				if (arg2) {
					setIcon(mImageLoader.fetchImage(LobbyImages.SEND_YES_SELECTED));
					} else {
						setIcon(mImageLoader.fetchImage(LobbyImages.SEND_YES));
					}
			} else {
				if (arg2) {
				setIcon(mImageLoader.fetchImage(LobbyImages.SEND_NO_SELECTED));
				} else {
					setIcon(mImageLoader.fetchImage(LobbyImages.SEND_NO));
					
				}
			}
			setText(null);
		} else if (arg5 == 3) {
			List list = (List)arg1;
			if (list == null) {
				throw new NullPointerException(); // "List is null");
			}
			StringBuilder sb = new StringBuilder();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				String s = (String)it.next();
				sb.append(s);
				if (it.hasNext()) {
					sb.append(" - ");
				}
			}
			setText(sb.toString());
			
		}
		return ret;
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
