/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.podkidnoy;

import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import org.netbeans.lib.awtextra.AbsoluteConstraints;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.*;
import com.littlech.cl.gui.lobby.constants.*;



public class JoinButton {

	private IImageLoader mImageLoader;
	private JLabel label = new JLabel();
	private JPanel panel = new JPanel();
	private JToggleButton button;
	
	
	private AbsoluteConstraints constraint = new AbsoluteConstraints(LobbyLocations.LOBBY_JOIN, LobbySizes.LOBBY_JOIN),
	textConstraint = new AbsoluteConstraints(LobbyLocations.LOBBY_JOIN_TEXT, LobbySizes.LOBBY_JOIN_TEXT);

	public JoinButton(final IImageLoader imageLoader) {
		this.mImageLoader = imageLoader;
		button = new JToggleButton();

		panel.setOpaque(false);
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		label.setBorder(null);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFocusable(false);
		label.setFont(LobbyFonts.LOBBY_JOIN);
		label.setForeground(LobbyColors.LOBBY_JOIN_FG);
		label.setOpaque(false);

		panel.add(label, textConstraint);

		button.setName(Names.JOIN_BUTTON);
		button.setIcon(mImageLoader.fetchImage(LobbyImages.JOIN));
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setPressedIcon(mImageLoader.fetchImage(LobbyImages.JOIN_HOVER));
		button.setRolloverIcon(mImageLoader.fetchImage(LobbyImages.JOIN_HOVER));
		button.setDisabledIcon(mImageLoader.fetchImage(LobbyImages.JOIN_DIS));
		button.setDisabledSelectedIcon(mImageLoader.fetchImage(LobbyImages.JOIN_DIS));

		// Point location = new Point(0, 0);
		panel.add(button, new AbsoluteConstraints(new Point(0, 0), LobbySizes.LOBBY_JOIN));
		setButtonText("JOIN GAME");
	}

	public void setButtonText(final String _text) {
		label.setText(_text);
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setEnabled(boolean b) {
		button.setEnabled(b);
	}

	public void setVisible(boolean b) {
		panel.setVisible(b);
		label.setVisible(b);
		button.setVisible(b);
	}

	@Override
	public String toString() {
		return label.getText();
	}

	public boolean isVisible() {
		boolean ret =
		panel.isVisible() &&
		label.isVisible() &&
		button.isVisible();
		return ret;
	}
	
	public boolean isEnabled() {
		boolean ret = button.isEnabled();
		return ret;
	}

	public void doClick() {
		button.doClick();
	}

	public void addActionListener(ActionListener l) {
		button.addActionListener(l);
	}
	
	
	/**
	 * Returns constraint for this component
	 * @return Constraint
	 */
	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

}
