/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.Names;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoyColors;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoyImages;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoyLocations;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoySizes;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F10;
import com.littlech.gen.f.F12;
import com.littlech.gen.f.F6;
import com.littlech.gen.f.F9;

public class SeatUserBox implements Observer {
	
	/**
	 * Seat's ID
	 */
	private final F1 id;

	/**
	 * Player box avatar image
	 */
	private JLabel avatarPic = new JLabel();

	/**
	 * Text field for player name
	 */
	private JFormattedTextField name = new JFormattedTextField();

	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;

	/**
	 * Base panel where cards are added
	 */
	private JPanel panel = new JPanel();

	private AbsoluteConstraints nameConstraint = new AbsoluteConstraints(PodkidnoyLocations.PLAYERBOX_NAME, PodkidnoySizes.PLAYERBOX_NAME);

	/**
	 * Constructor
	 * 
	 * @param imageLoader
	 *          Image loader
	 * @param _id
	 *          Seat ID
	 */
	public SeatUserBox(final IImageLoader imageLoader, final F1 _id) {
		this.mImageLoader = imageLoader;
		id = _id;

		panel.setLayout(new AbsoluteLayout());
		panel.setOpaque(false);

		name.setBorder(null);
		name.setEditable(false);
		name.setFocusable(false);
		name.setOpaque(true);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setForeground(PodkidnoyColors.PLAYERBOX_NAME_FG);
		name.setBackground(PodkidnoyColors.PLAYERBOX_NAME_BG);
		panel.add(name, nameConstraint);

		int gapUserPictureH = 35, gapUserPictureV = 10;
		avatarPic.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_DEFAULT_SELF));
		panel.add(avatarPic, new org.netbeans.lib.awtextra.AbsoluteConstraints(new Point(gapUserPictureH, gapUserPictureV), new Dimension(48, 48)));
		
		name.setName(Names.SEATBOX_USER_NAME_ + getID());
		avatarPic.setName(Names.SEATBOX_USER_AVATAR_ + getID());
		panel.setName(Names.SEATBOX_USER_PANEL_ + getID());
	}

	public void setVisible(boolean b) {
		getPanel().setVisible(b);
	}

	/**
	 * Sets player avatar picture for displaying on this seat
	 * 
	 * @param _avatar
	 *          Avatar picture's file name
	 */
	public void setAvatar(final String _avatar) {
		if (_avatar != null) {
		try {
			avatarPic.setIcon(mImageLoader.fetchImage(_avatar));
		} catch (Exception e) {
			System.out.println("Cannot find " + _avatar);
			e.printStackTrace();
		}
		avatarPic.repaint();
		avatarPic.setVisible(true);
		} else {
			avatarPic.setVisible(false);
		}
	 }

	/**
	 * Sets player name for displaying on this seat
	 * 
	 * @param _name
	 *          Player name
	 */
	public void setName(final String _name) {
		name.setText(_name);
		name.repaint();
	 }

	public void performUserUpdate(final F6 _u) {
		if (_u == null) {
			throw new NullPointerException(); // "User is null");
		}
		String userName = _u.getF7();
		String avatar = _u.getF8();
		setAvatar(avatar);
		setName(userName);
	}

	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Retrieves this seat's ID
	 * @return Seat ID
	 */
	public F1 getID() {
		return id;
	}

	@Override
	public void update(Observable arg0, Object irrelevant) {
		if (arg0 instanceof ObservableValue<?>) {
			ObservableValue ov = (ObservableValue) arg0;
			Object o1 = ov.getValue();
			if (o1 == null) {
				// System.out.println("-1- setting invisible " + getID());
				setVisible(false); // XXX OK?
			} else if (o1 instanceof F9) {
				F9 su = (F9) o1;
				if (su instanceof F10) {
					// System.out.println("-2- human " + getID());
					F10 hsu = (F10) su;
					F6 u = hsu.getF11();
					performUserUpdate(u);
				} else if (su instanceof F12) {
					// System.out.println("-3- bot " + getID());
					F12 bsu = (F12) su;
					F6 u = bsu.getF13();
					if (u == null)  { 
						u = SeatBox.getDefaultUser(getID()); 
					}
					performUserUpdate(u);
				} else {
					throw new IllegalStateException("Unsupported:" +
							// " seat user type: " + 
							su.getClass().getCanonicalName());
				}
				// setVisible(true);
			} else {
				String o1Name = o1.getClass().getCanonicalName();
				throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + o1Name);
			}
		} else {
			throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + arg0.getClass().getCanonicalName());
		}

	}

}
