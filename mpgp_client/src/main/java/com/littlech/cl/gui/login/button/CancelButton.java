/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.login.button;

import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.gui.login.constants.LoginColors;
import com.littlech.cl.gui.login.constants.LoginFonts;
import com.littlech.cl.gui.login.constants.LoginImages;
import com.littlech.cl.gui.login.constants.LoginLocations;
import com.littlech.cl.gui.login.constants.LoginSizes;
import com.littlech.cl.gui.podkidnoy.constants.*;


/**
 * 
 * Button for canceling loading the client application
 * 
 * @author Kristjan Veskim�e
 *
 */
public class CancelButton {

	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;
	
	/**
	 * Text label for drawing text upon the button
	 */
	private JLabel label = new JLabel();
	
	/**
	 * Core panel holding the button itself and its text label
	 */
	private JPanel panel = new JPanel();
	
	private JButton button;

	/**
	 * 
	 * Constructor
	 * 
	 * @param _imageLoader Image loader
	 */
	public CancelButton(final IImageLoader _imageLoader) {
		this.mImageLoader = _imageLoader;
		button = new JButton();

		panel.setOpaque(false);
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		Point locationText = LoginLocations.CANCEL_TEXT;

		label.setBorder(null);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("NI");
		label.setFocusable(false);
		label.setFont(LoginFonts.CANCEL_TEXT);
		label.setForeground(LoginColors.CANCEL_FG);
		label.setOpaque(false);

		panel.add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(locationText, LoginSizes.CANCEL_TEXT));

		button.setIcon(mImageLoader.fetchImage(PodkidnoyImages.ACTION_DEFAULT));
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setBorderPainted(false);
		
		ImageIcon cancelDef = mImageLoader.fetchImage(LoginImages.LOGIN_CANCEL);
		ImageIcon cancelPressed = mImageLoader.fetchImage(LoginImages.LOGIN_CANCEL_PRESSED);
		
		button.setIcon(cancelDef);
		button.setRolloverIcon(cancelDef);
		button.setPressedIcon(cancelPressed);
		button.setDisabledIcon(cancelPressed);
		button.setDisabledSelectedIcon(cancelPressed);

		Point location = new Point(0, 0);
		panel.add(button, new org.netbeans.lib.awtextra.AbsoluteConstraints(location, LoginSizes.CANCEL));
	}
	
	/**
	 * Sets parameter text on this button
	 * @param _text Text
	 */
	public void setText(final String _text) {
		label.setText(_text);
	}

	/**
	 * 
	 * Retrieves the core panel where the button and its text label are drawn
	 * 
	 * @return
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * 
	 * Sets button's state
	 * 
	 * @param b Set true if enable, false otherwise
	 */
	public void setEnabled(boolean b) {
		button.setEnabled(b);
	}

	/**
	 * 
	 * Sets button's visibility
	 * 
	 * @param b Set true to make visible and false to hide
	 */
	public void setVisible(boolean b) {
		panel.setVisible(b);
		label.setVisible(b);
		button.setVisible(b);
	}

	/**
	 * 
	 * Adds action listener to this button
	 * 
	 * @param _al Action listener
	 */
	public void addActionListener(ActionListener _al) {
		button.addActionListener(_al);
	}
	
	@Override
	public String toString() {
		return label.getText();
	}

	/**
	 * 
	 * Visibility of this action button; returns true if at least one of the following is visible:
	 * 1) core panel; 2) text label; 3) button
	 * 
	 * @return True if button is visible, false otherwise
	 */
	public boolean isVisible() {
		boolean ret = panel.isVisible() || label.isVisible() || button.isVisible();
		return ret;
	}

	/**
	 * 
	 * Checks if this action button is in enabled state
	 * 
	 * @return True if enabled, false otherwise
	 */
	public boolean isEnabled() {
		boolean ret = button.isEnabled();
		return ret;
	}

	/**
	 * Programmatically clicks this action button
	 */
	public void doClick() {
		button.doClick();
	}

}
