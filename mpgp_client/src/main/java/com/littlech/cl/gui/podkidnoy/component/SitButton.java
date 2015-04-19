/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.netbeans.lib.awtextra.AbsoluteConstraints;

import com.littlech.gen.f.F1;
import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.*;
import com.littlech.cl.gui.lobby.constants.*;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoyFonts;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoyImages;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoyLocations;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoySizes;



public class SitButton {

	private IImageLoader mImageLoader;
	private JLabel label = new JLabel();
	private JPanel panel = new JPanel();
	private 
	// JToggleButton 
	JButton
	button;

	/**
	 * Seat's ID
	 */
	private final F1 id;
	
	
	private AbsoluteConstraints constraint = new AbsoluteConstraints(
			PodkidnoyLocations.PLAYERBOX_SIT
			, PodkidnoySizes.SIT	
	),
	textConstraint = new AbsoluteConstraints(new Point(0, 0), PodkidnoySizes.SIT),
	buttonConstraint = new AbsoluteConstraints(new Point(0, 0), PodkidnoySizes.SIT)
	;

	public SitButton(final IImageLoader imageLoader, final F1 _id) {
		this.mImageLoader = imageLoader;
		id = _id;
		
		button = new JButton(); // JToggleButton();

		panel.setOpaque(false);
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		label.setBorder(null);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFocusable(false);
		label.setFont(PodkidnoyFonts.PLAYERBOX_NAME);
		label.setForeground(LobbyColors.LOBBY_JOIN_FG);
		label.setOpaque(false);

		panel.add(label, textConstraint);
		
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setBorderPainted(false);

		button.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SIT_DEFAULT_NEW));
		button.setRolloverIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SIT_HOVER_NEW));
		
		button.setPressedIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SIT_DISABLED_NEW));
		button.setSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SIT_DISABLED_NEW));
		button.setDisabledIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SIT_DISABLED_NEW));
		button.setDisabledSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SIT_DISABLED_NEW));

		panel.add(button, buttonConstraint);
		setButtonText(Texts.SIT);

		button.setName(Names.SEAT_SITBUTTON_ + id.name());

		label.setName(Names.SEAT_SITBUTTON_TEXT_ + id.name());
		panel.setName(Names.SEAT_SITBUTTON_PANEL_ + id.name());
	}
	
	/*
	public void setButtonName(final String _name) {
		button.setName(_name);
	}
	*/

	public void setButtonText(final String _text) {
		label.setText(_text);
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setEnabled(boolean b) {
		button.setEnabled(b);
		// button.set
		// getPanel().repaint();
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
