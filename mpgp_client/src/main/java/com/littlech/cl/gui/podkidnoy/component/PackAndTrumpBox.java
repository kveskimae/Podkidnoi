/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.gui.podkidnoy.constants.*;
import com.littlech.cl.gui.podkidnoy.button.CardButton;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.gen.g.G1;

/**
 * 
 * Podkidnoy screen's card pack and trump box
 * 
 * <p style="margin-top: 0">
 * Trump card button listens to Podkidnoy model's trump and PackAndTrumpBox
 * listens to cards left in model
 * 
 * Cards left in model also determines the visibility of box's components
 * </p>
 */
public class PackAndTrumpBox implements IScreenPanel {

	/**
	 * Trump card component
	 */
	private Trump trump;

	/**
	 * Card back component where cards left number is drawn
	 */
	private JLabel cardBackPicture = new JLabel();

	/**
	 * Base picture
	 */
	private JLabel carpetPicture = new JLabel();

	/**
	 * Number of cards label
	 */
	private CardPackLabel numberOfCardsLabel;

	/**
	 * Constraint for base panel where components are added
	 */
	private AbsoluteConstraints constraint = new AbsoluteConstraints(PodkidnoyLocations.PACK_AND_TRUMP, PodkidnoySizes.PACK_AND_TRUMP);

	/**
	 * Image loader
	 */
	protected IImageLoader mImageLoader;

	/**
	 * Base panel where cards are added
	 */
	protected JPanel panel = new JPanel();

	/**
	 * Constructor
	 * 
	 * @param imageLoader
	 *          Image loader
	 */
	public PackAndTrumpBox(final IImageLoader imageLoader) {
		this.mImageLoader = imageLoader;

		panel.setLayout(new AbsoluteLayout());
		panel.setOpaque(false);

		numberOfCardsLabel = new CardPackLabel(mImageLoader);
		trump = new Trump(mImageLoader);

		int shift = (int) Math.round((PodkidnoySizes.PACK_AND_TRUMP.height - PodkidnoySizes.CARD.height) / 2.0);
		int shiftCardBack = (int) Math.round((PodkidnoySizes.PACK_AND_TRUMP.height - PodkidnoySizes.CARD.width) / 2.0);
		Point cardBackLocation = new Point(25, shiftCardBack);
		Point remainingLocation = new Point(cardBackLocation.x + 20, cardBackLocation.y + 15), trumpCardLocation = new Point(10, shift), trumpCarpetLocation = new Point(
				0, 0);
		panel.add(numberOfCardsLabel.getPack(), new AbsoluteConstraints(remainingLocation, new Dimension(50, 30)));

		cardBackPicture.setIcon(mImageLoader.fetchImage(PodkidnoyImages.CARD_BACK));
		panel.add(cardBackPicture, new AbsoluteConstraints(cardBackLocation, new Dimension(PodkidnoySizes.CARD.height, PodkidnoySizes.CARD.width)));

		CardButton trumpCard = trump.getCardButton();
		trumpCard.setBorderPainted(false);
		trumpCard.setContentAreaFilled(false);
		trumpCard.setFocusable(false);
		trumpCard.setEnabled(false);
		panel.add(trumpCard, new AbsoluteConstraints(trumpCardLocation, PodkidnoySizes.CARD));

		carpetPicture.setIcon(mImageLoader.fetchImage(PodkidnoyImages.PACK_AND_TRUMP_CARPET));
		panel.add(carpetPicture, new AbsoluteConstraints(trumpCarpetLocation, PodkidnoySizes.PACK_AND_TRUMP));
	}

	/**
	 * Retrieves the component where trump card is drawn
	 * @return Trump card component
	 */
	public Trump getTrump() {
		return trump;
	}

	public void update(Observable arg0, Object o) {
		if (arg0 instanceof ObservableValue<?>) {
			ObservableValue ov = (ObservableValue) arg0;
			Object o1 = ov.getValue();
			if (o1 instanceof Integer) {
				panel.setVisible(true);
				int value = (Integer) o1;
				if (value > 1) {
					numberOfCardsLabel.setValue(value - 1);
					numberOfCardsLabel.setVisible(true);
					cardBackPicture.setVisible(true);
					trump.getCardButton().setVisible(true);
				} else {
					hidePackLabel();
					if (value == 1) {
						trump.getCardButton().setVisible(true);
					} else {
						hideTrumpCard();
					}
				}
			} else if (o1 instanceof G1) {
				G1 gameStateCode = (G1) o1;
				switch (gameStateCode) {
				case G_3:
					// XXX
					// ? what to do
					break;
				case G_2:

					hideTrumpCard();
					hidePackLabel();

					break;
				default:
					break;
				}
			} else {
				throw new IllegalArgumentException("To " + o.getClass().getCanonicalName());
			}
		} else {
			throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + arg0.getClass().getCanonicalName());
		}
	}

	/**
	 * Hides the trump card
	 */
	private void hideTrumpCard() {
		trump.getCardButton().setVisible(false);
		trump.getCardButton().repaint();
	}

	/**
	 * Hides card pack and number of cards label 
	 */
	private void hidePackLabel() {
		numberOfCardsLabel.setValue(-1);
		numberOfCardsLabel.setVisible(false);
		cardBackPicture.setVisible(false);
	}

	/**
	 * Retrieves the base panel where pack and trump components are added
	 */
	@Override
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void setEnabled(boolean b) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setVisible(boolean b) {
		panel.setVisible(b);
	}

	@Override
	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

}
