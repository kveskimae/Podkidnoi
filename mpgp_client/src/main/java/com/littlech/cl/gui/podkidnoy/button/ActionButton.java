/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.button;

import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.gui.podkidnoy.constants.*;
import com.littlech.gen.a.A5;


/**
 * 
 * Button for confirming user game action
 * 
 * @author Kristjan Veskim�e
 *
 */
public class ActionButton {

	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;
	
	/**
	 * Type for this button; determines text for button and subsequent game behaviour
	 */
	// private PodButtonCode type;
	
	/**
	 * Text label for drawing text upon the button
	 */
	private JLabel label = new JLabel();
	
	/**
	 * Core panel holding the button itself and its text label
	 */
	private JPanel panel = new JPanel();
	
	/**
	 * The button itself
	 */
	private ActionButtonTypeImpl button;

	/**
	 * 
	 * Constructor
	 * 
	 * @param _imageLoader Image loader
	 */
	public ActionButton(final IImageLoader _imageLoader) {
		this.mImageLoader = _imageLoader;
		button = new ActionButtonTypeImpl();

		panel.setOpaque(false);
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		Point locationText = PodkidnoyLocations.ACTION_TEXT;

		label.setBorder(null);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("NI");
		label.setFocusable(false);
		label.setFont(PodkidnoyFonts.ACTION);
		label.setForeground(PodkidnoyColors.ACTION_FG);
		label.setOpaque(false);

		panel.add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(locationText, PodkidnoySizes.ACTION_TEXT));

		button.setIcon(mImageLoader.fetchImage(PodkidnoyImages.ACTION_DEFAULT));
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setPressedIcon(mImageLoader.fetchImage(PodkidnoyImages.ACTION_PRESSED));
		button.setRolloverIcon(mImageLoader.fetchImage(PodkidnoyImages.ACTION_HOVER));
		button.setDisabledIcon(mImageLoader.fetchImage(PodkidnoyImages.ACTION_DISABLED));
		button.setDisabledSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.ACTION_DISABLED_PRESSED));

		Point location = new Point(0, 0);
		panel.add(button, new org.netbeans.lib.awtextra.AbsoluteConstraints(location, PodkidnoySizes.ACTION));
	}
	
	public void setName(String name) {
		button.setName(name);
	}

	private void setButtonText(A5 type) {
		/*PodButtonCode=A5
FIRST=A_6
SEND=A_7
DEFEND=A_8
PICK=A_9
ADD=A_10
PASS=A_11*/
		// label.setText(type.name());
		String text = "?";
		switch (type) {
		case A_10:
			text = "Add";
			
			break;
		case A_11:
			text = "Pass";
			break;
		case A_6:
			text = "First";
			break;
		case A_7:
			text = "Send";
			break;
		case A_8:
			text = "Defend";
			break;
		case A_9:
			text = "Pick";
			break;

		default:
			break;
		}
		label.setText(text);
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

	/**
	 * 
	 * Retrieves the button's type
	 * 
	 * @return Button type
	 */
	public A5 getType() {
		return button.getType(); // type;
	}

	/**
	 * 
	 * Sets the button's type; determines text for button and subsequent game behavior
	 * 
	 * @param _type Button type
	 */
	public void setType(final A5 _type) {
		if (_type == null) {
			throw new NullPointerException(); // "Type is null");
		}
		// this.type = _type;
		button.setType(_type);
		setButtonText(_type);
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
