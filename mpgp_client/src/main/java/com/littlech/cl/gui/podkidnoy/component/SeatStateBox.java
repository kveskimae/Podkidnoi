/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import com.littlech.cl.gui.podkidnoy.constants.*;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.*;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.gen.f.F1;
import com.littlech.gen.f.F14;
import com.littlech.gen.f.F15;
import com.littlech.gen.f.F17;
import com.littlech.gen.f.F19;

/**
 * 
 * Component for displaying one Podkidnoy seat
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class SeatStateBox implements Observer {

	/**
	 * Player box background image and card square background image
	 */
	private JLabel carpet = new JLabel(), cardsCarpet = new JLabel();

	/**
	 * Text fields for player name and number of cards
	 */
	private JFormattedTextField cardsText = new JFormattedTextField();

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

	/**
	 * Constructor
	 * 
	 * @param imageLoader
	 *          Image loader
	 * @param _id
	 *          Seat ID
	 */
	public SeatStateBox(final IImageLoader imageLoader, final F1 _id) {
		this.mImageLoader = imageLoader;
		id = _id;

		panel.setLayout(new AbsoluteLayout());
		panel.setOpaque(false);

		cardsText.setBorder(null);
		cardsText.setEditable(false);
		cardsText.setFocusable(false);
		cardsText.setOpaque(false);
		cardsText.setHorizontalAlignment(SwingConstants.CENTER);
		cardsText.setForeground(PodkidnoyColors.PLAYERBOX_CARDS_NUMBER);
		cardsText.setFont(PodkidnoyFonts.PLAYERBOX_CARDS_NUMBER);
		panel.add(cardsText, new org.netbeans.lib.awtextra.AbsoluteConstraints(PodkidnoyLocations.PLAYERBOX_CARDS_NUMBER_TEXT,
				PodkidnoySizes.PLAYERBOX_CARDS_NUMBER));

		cardsCarpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_CARDS_NUMBER_BG));
		panel.add(cardsCarpet, new org.netbeans.lib.awtextra.AbsoluteConstraints(PodkidnoyLocations.PLAYERBOX_CARDS_NUMBER, new Dimension(35, 25)));

		carpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SEAT_NOT_ON_TURN));
		panel.add(carpet, new org.netbeans.lib.awtextra.AbsoluteConstraints(new Point(0, 0), PodkidnoySizes.PLAYERBOX));
		
		cardsText.setName(Names.SEATBOX_NUMOFCARDSTEXT_ + getID());
		cardsCarpet.setName(Names.SEATBOX_CARDSCARPET_ + getID());
		carpet.setName(Names.SEATBOX_ONTURNCARPET_ + getID());
		panel.setName(Names.SEATBOX_STATEPANEL_ + getID());
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

	/**
	 * Sets number of cards for displaying on this seat
	 * 
	 * @param numOfCards
	 */
	public void setNumOfCards(int numOfCards) {
		this.cardsText.setText("" + numOfCards);
		if (numOfCards < 1) {
			cardsText.setVisible(false);
			cardsCarpet.setVisible(false);
		} else {
			cardsCarpet.setVisible(true);
			cardsText.setVisible(true);
		}
	}

	/**
	 * Sets this seats state
	 * 
	 * @param b
	 *          True if this seat is to be displayed in on move state, false if in
	 *          not on move state
	 */
	public void setOnMove(boolean b) {
		if (b) {
			carpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SEAT_ON_TURN));
		} else {
			carpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SEAT_NOT_ON_TURN));
		}
	}

	public Container getPanel() {
		return panel;
	}

	public void setVisible(boolean b) {
/*
		if (SeatIDCode.THREE.equals(getID())) {
			System.out.println("three state, b = " + b + Arrays.toString(Thread.currentThread().getStackTrace()));
		}
		*/
		getPanel().setVisible(b);
	}

	@Override
	public void update(Observable arg0, Object irrelevant) {
		if (arg0 instanceof ObservableValue<?>) {
			ObservableValue ov = (ObservableValue) arg0;
			Object o1 = ov.getValue();
			String className = (o1 == null) ? "null" : o1.getClass().getCanonicalName();
			if (o1 instanceof F1) {
				F1 onTurn = (F1) o1;
				if (getID().equals(onTurn)) {
					carpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SEAT_ON_TURN));
				} else {
					carpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SEAT_NOT_ON_TURN));
				}
			} else if (o1 instanceof F14) {
				F14 abss = (F14) o1;
				if (abss instanceof F15) {
					F15 iss = (F15) abss;
					if (iss.isF16()) {
						carpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SEAT_START_PRESSED));
					} else {
						carpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SEAT_IDLE));
					}

					cardsText.setVisible(false);
					cardsCarpet.setVisible(false);
					// setVisible(true);
				} else if (abss instanceof F17) {
					F17 pss = (F17) abss;
					int numOfCards = pss.getF18();
					setNumOfCards(numOfCards);
					// setVisible(true);
					carpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SEAT_NOT_ON_TURN));
				} else if (abss instanceof F19) {
					// setVisible(false);
				}
			} 
			else if (o1 == null) {
					carpet.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PLAYERBOX_SEAT_NOT_ON_TURN));
			} 
			
			else {
				throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + className);
			}
		} else {
			throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + arg0.getClass().getCanonicalName());
		}
	}

}
