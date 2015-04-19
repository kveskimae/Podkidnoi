/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import com.littlech.cl.gui.podkidnoy.constants.*;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.*;

import java.awt.Container;
import java.awt.Point;
import java.util.Observable;

import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.cl.gui.podkidnoy.util.Self;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F12;
import com.littlech.gen.f.F14;
import com.littlech.gen.f.F19;
import com.littlech.gen.f.F6;
import com.littlech.gen.f.F9;
import com.littlech.gen.g.G1;

/**
 * 
 * Component for displaying one Podkidnoy seat
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class SeatBox implements IScreenPanel {

	// private JToggleButton selfName = new JToggleButton();

	/**
	 * Seat's state
	 */
	// private AbstractSeatState state = new UnusedSeatState();

	/**
	 * Seat's user
	 */
	// private SeatUserType seatUser;

	/**
	 * Seat's ID
	 */
	private final F1 id;

	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;

	/**
	 * Base panel where cards are added
	 */
	private JPanel panel = new JPanel();

	private SitButton sittingButton;
	
	private SeatEmptyBox emptyBox;

	// private boolean bot;

	// private SitButton sitButton;

	/*
	 * private AbsoluteConstraints nameConstraint = new AbsoluteConstraints(
	 * PodkidnoyLocations.PLAYERBOX_NAME , PodkidnoySizes.PLAYERBOX_NAME );
	 */

	private SeatUserBox userPart;

	private SeatStateBox statePart;

	private ObservableValue<F9> mUser;

	private ObservableValue<F14> mState;

	private ObservableValue<G1> mGameState;

	private ObservableValue<Self> mSelf;

	private ObservableValue<Boolean> mIsSitting;

	/**
	 * Retrieves the default bot player name and avatar picture for parameter seat
	 * 
	 * @param _id
	 *          Seat ID
	 * @return User Bot user
	 */
	public static F6 getDefaultUser(final F1 _id) {
		// System.out.println("id=" + _id);
		switch (_id) {
		case F_2:
			F6 u1 = new F6();
			u1.setF8(PodkidnoyImages.PLAYERBOX_DEFAULT_BOT1);
			u1.setF7("JollyBot");
			return u1;
		case F_3:
			F6 u2 = new F6();
			u2.setF8(PodkidnoyImages.PLAYERBOX_DEFAULT_BOT2);
			u2.setF7("BotPC");
			return u2;
		case F_4:
			F6 u3 = new F6();
			u3.setF8(PodkidnoyImages.PLAYERBOX_DEFAULT_BOT3);
			u3.setF7("BotLaptop");
			return u3;
		case F_5:
			F6 u4 = new F6();
			u4.setF8(PodkidnoyImages.PLAYERBOX_DEFAULT_BOT4);
			u4.setF7("ThinkTankBot");
			return u4;
		default:
			throw new IllegalArgumentException("Not supported: " + _id);
		}
	}

	/**
	 * Constructor
	 * 
	 * @param imageLoader
	 *          Image loader
	 * @param _id
	 *          Seat ID
	 * @param _gameState
	 * @param _state
	 * @param _user
	 * @param _self
	 */
	public SeatBox(
	// Image loader and Seat ID code
			final IImageLoader imageLoader, final F1 _id,
			// Observable values
			// Seat user
			ObservableValue<F9> _user,
			// Seat state
			ObservableValue<F14> _state,
			// Game state
			ObservableValue<G1> _gameState,
			// Self
			ObservableValue<Self> _self, ObservableValue<Boolean> _isSitting) {
		this.mImageLoader = imageLoader;
		id = _id;
		mUser = _user;
		mState = _state;
		mGameState = _gameState;
		mSelf = _self;
		mIsSitting = _isSitting;

		panel.setLayout(new AbsoluteLayout());
		panel.setOpaque(false);

		userPart = new SeatUserBox(mImageLoader, getID());
		statePart = new SeatStateBox(mImageLoader, getID());
		sittingButton = new SitButton(mImageLoader, getID());
		emptyBox = new SeatEmptyBox(mImageLoader, getID());

		// panel.add(sittingButton.getPanel(), sittingButton.getConstraint());
		// sittingButton.setEnabled(false); ?
panel.add(sittingButton.getPanel(), sittingButton.getConstraint());
		panel.add(getEmptyBox().getPanel(), getEmptyBox().getConstraint());
		panel.add(userPart.getPanel(), new AbsoluteConstraints(Locations.UPPER_LEFT, PodkidnoySizes.PLAYERBOX));
		panel.add(statePart.getPanel(), new AbsoluteConstraints(Locations.UPPER_LEFT, PodkidnoySizes.PLAYERBOX));
		

		panel.setName(Names.SEAT_BASEPANEL_ + getID());
	}

	public ObservableValue<Boolean> getIsSitting() {
		return mIsSitting;
	}

	public ObservableValue<Self> getSelf() {
		return mSelf;
	}

	public ObservableValue<F9> getUser() {
		return mUser;
	}

	public ObservableValue<F14> getState() {
		return mState;
	}

	public ObservableValue<G1> getGameState() {
		return mGameState;
	}

	public SitButton getSitButton() {
		return sittingButton;
	}

	public SeatStateBox getStatePart() {
		return statePart;
	}

	public SeatUserBox getUserPart() {
		return userPart;
	}
	
	public SeatEmptyBox getEmptyBox() {
		return emptyBox;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// sb.append("PlayerBox{" +
		// "name=" + Si.getText() + ", " +
		// "id="+getID()+ "}");
		return sb.toString();
	}

	/**
	 * Retrieves this seat's ID
	 * 
	 * @return Seat ID
	 */
	public F1 getID() {
		return id;
	}

	@Override
	public Container getPanel() {
		return panel;
	}

	@Override
	public void setVisible(boolean b) {
		/*
		 * avatarPic.setVisible(b); carpet.setVisible(b); joinButton.setVisible(b);
		 * name.setVisible(b);
		 */
		getPanel().setVisible(b);
	}

	@Override
	public AbsoluteConstraints getConstraint() {
		Point p;
		switch (getID()) {
		case F_2:
			p = PodkidnoyLocations.PLAYERBOX_SELF;
			break;
		case F_3:
			p = PodkidnoyLocations.PLAYERBOX_OTHER1;
			break;
		case F_4:
			p = PodkidnoyLocations.PLAYERBOX_OTHER2;
			break;
		case F_5:
			p = PodkidnoyLocations.PLAYERBOX_OTHER3;
			break;
		default:
			throw new IllegalArgumentException("Unknown: " + getID());
		}
		AbsoluteConstraints ret = new AbsoluteConstraints(p, PodkidnoySizes.PLAYERBOX);
		return ret;
	}

	@Override
	public void setEnabled(boolean b) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Observable arg0, Object irrelevant) {
		
		/*
		if (F1.F_4.equals(getID())) {
			// getUser()getState()getGameState()
			System.out.println("user=" + Utils.getPresentableFormat(getUser().getValue()) + ", state=" + Utils.getPresentableFormat(getState().getValue()) + ", game state=" + getGameState().getValue());
		}
		*/
		
		if (getState().getValue() != null && !(getState().getValue() instanceof F19)) {
			switch (getGameState().getValue()) {
			case G_3:
				// System.out.println("playing " + getID());
				getUserPart().setVisible(true);
				getStatePart().setVisible(true);
				getSitButton().setVisible(false);
				getEmptyBox().setVisible(false);
				setVisible(true);
				break;
			case G_2:
				// System.out.println("waiting " + getID());
				if (getUser().getValue() != null && getUser().getValue() instanceof F12) {
					if (
					// Self not sitting
					getSelf().getValue() != null && getSelf().getValue().getId() == null &&
					// Eligible to sit
							getIsSitting().getValue() != null && getIsSitting().getValue()) {
						// System.out.println("-a- " + getID());
						getSitButton().setVisible(true);
						getSitButton().setEnabled(true);
						
						getEmptyBox().setVisible(false);
					} else {
						// System.out.println("-b- " + getID());
						
						/*
						getUserPart().setName("Empty");
						getUserPart().setAvatar(null);
						getUserPart().setVisible(true);
						*/
						// getUserPart().setVisible(false);
						// getStatePart().setVisible(false); // !!
						getSitButton().setVisible(false);
						getEmptyBox().setVisible(true);
					}
						getUserPart().setVisible(false);
						getStatePart().setVisible(false);

				} else {
					// System.out.println("-I-");
					getUserPart().setVisible(true);
					getStatePart().setVisible(true);
					getSitButton().setVisible(false);
					getEmptyBox().setVisible(false);
				}
				setVisible(true);
				break;
			default:
				// throw new IllegalStateException("");
				setVisible(false);
				// System.out.println("-II-");
				break;
			}
		} else {
			setVisible(false);
			// System.out.println("-III-");
		}
getPanel().repaint();
	}

}
