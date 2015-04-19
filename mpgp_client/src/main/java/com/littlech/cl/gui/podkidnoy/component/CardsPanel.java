/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.Utils;
import com.littlech.cl.constants.Names;
import com.littlech.cl.gui.podkidnoy.constants.*;
import com.littlech.cl.gui.podkidnoy.button.CardButton;
import com.littlech.cl.gui.podkidnoy.handler.CardsPanelArrowHandler;
import com.littlech.cl.gui.podkidnoy.handler.CardsPanelArrowHandlerType;
import com.littlech.cl.gui.podkidnoy.handler.CardsPanelButtonHandler;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B6;
import com.littlech.gen.g.G1;

/**
 * 
 * Podkidnoy player cards panel
 * 
 * @author Kristjan Veskim�e
 *
 */
public class CardsPanel implements IScreenPanel {

	/**
	 * Maximum number of player cards that can be drawn on screen
	 */
	public static final int PLAYER_CARDS = 10;

	/**
	 * Visible card components
	 */
	private List<CardButton> visibleCardButtons = new ArrayList<CardButton>();
	
	/**
	 * Enabled cards
	 */
	private List<B20> enabledCards = new ArrayList<B20>();
	
	/**
	 * Position of visible components' leftmost card in model cards 
	 */
	private int position = 0;
	
	/**
	 * Left and right arrow; drawn only if player cards contain more cards than the maximum number
	 * of cards that can be drawn on screen 
	 */
	private JToggleButton leftArrow = new JToggleButton(), rightArrow = new JToggleButton();
	
	/**
	 * Player cards; may contain cards that are not drawn on screen
	 */
	private List<B20> allPlayerCards = new ArrayList<B20>();
	
	/**
	 * Constraint for base panel
	 */
	private AbsoluteConstraints constraint = new AbsoluteConstraints(PodkidnoyLocations.PLAYER_CARDS, PodkidnoySizes.PLAYERCARDS);

	/**
	 * Image loader
	 */
	protected IImageLoader mImageLoader;
	
	/**
	 * Base panel where cards are added
	 */
	protected JPanel panel = new JPanel();
	
	/**
	 * Proxy for button action events
	 */
	private CardsPanelButtonHandler buttonHandler = new CardsPanelButtonHandler();
	
	/**
	 * Constructor
	 * @param imageLoader Image loader
	 */
	public CardsPanel(final IImageLoader imageLoader) {
		this.mImageLoader = imageLoader;
		
		panel.setLayout(new AbsoluteLayout());
		panel.setOpaque(false);

		leftArrow.setIcon(mImageLoader.fetchImage(PodkidnoyImages.LEFT_DEFAULT));
		leftArrow.setDisabledIcon(mImageLoader.fetchImage(PodkidnoyImages.LEFT_DISABLED));
		leftArrow.setPressedIcon(mImageLoader.fetchImage(PodkidnoyImages.LEFT_DISABLED));
		leftArrow.setBorderPainted(false);
		leftArrow.setContentAreaFilled(false);

		rightArrow.setIcon(mImageLoader.fetchImage(PodkidnoyImages.RIGHT_DEFAULT));
		rightArrow.setDisabledIcon(mImageLoader.fetchImage(PodkidnoyImages.RIGHT_DISABLED));
		rightArrow.setPressedIcon(mImageLoader.fetchImage(PodkidnoyImages.RIGHT_DISABLED));
		rightArrow.setBorderPainted(false);
		rightArrow.setContentAreaFilled(false);

		leftArrow.addActionListener(new CardsPanelArrowHandler(this, CardsPanelArrowHandlerType.LEFT));
		rightArrow.addActionListener(new CardsPanelArrowHandler(this, CardsPanelArrowHandlerType.RIGHT));

		int sum = 0 + PodkidnoySizes.ARROW.width + PodkidnoySizes.PLAYER_CARDS_ARROW_GAP + (PLAYER_CARDS - 1) * PodkidnoySizes.PLAYER_CARDS_SHIFT + PodkidnoySizes.CARD.width;

		Point locationLeftArrow = new Point(0, PodkidnoySizes.PLAYER_CARDS_ARROW_GAP_V), locationRightArrow = new Point(sum + PodkidnoySizes.PLAYER_CARDS_ARROW_GAP, PodkidnoySizes.PLAYER_CARDS_ARROW_GAP_V);

		panel.add(rightArrow, new AbsoluteConstraints(locationRightArrow, PodkidnoySizes.ARROW));
		panel.add(leftArrow, new AbsoluteConstraints(locationLeftArrow, PodkidnoySizes.ARROW));

		for (int i = 0; i < PLAYER_CARDS; i++) {
			CardButton cur = new CardButton(mImageLoader);
			cur.setName(Names.PODKIDNOY_CARD_ + i);
			visibleCardButtons.add(cur);
			cur.addActionListener(buttonHandler);
		}
		for (int i = PLAYER_CARDS - 1; i >= 0; i--) {
			CardButton cur = visibleCardButtons.get(i);
			Point location = new Point(PodkidnoySizes.ARROW.width + PodkidnoySizes.PLAYER_CARDS_ARROW_GAP + i * PodkidnoySizes.PLAYER_CARDS_SHIFT, 0);
			panel.add(cur, new AbsoluteConstraints(location, PodkidnoySizes.CARD));
		}
	}

	/**
	 * Retrieves the base panel where card components are added
	 */
	@Override
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void setEnabled(boolean b) {
		for (CardButton cur : visibleCardButtons) {
			cur.setEnabled(b);
		}
		enabledCards.clear();
		if (b) {
			enabledCards.addAll(this.allPlayerCards);
		}
		/*
		leftArrow.setEnabled(b);
		rightArrow.setEnabled(b);
		*/
	}

	@Override
	public void setVisible(boolean b) {
		panel.setVisible(b);
	}

	@Override
	public AbsoluteConstraints getConstraint() {
		return constraint;
	}
	
	/**
	 * Retrieves the handler for the card buttons' action events
	 * @return Handler
	 */
	public CardsPanelButtonHandler getButtonHandler() {
		return buttonHandler;
	}

	/**
	 * Shifts all visible cards one position left
	 */
	public void rightButtonClicked() {
		int numOfPlayerCards = allPlayerCards.size();
		int numOnScreen = visibleCardButtons.size();
		if (numOnScreen + this.position < numOfPlayerCards) {
			position++;
			for (int i = position; i < position + PLAYER_CARDS; i++) {
				CardButton g2 = visibleCardButtons.get(i - position);
				B20 xd = allPlayerCards.get(i);
				g2.setCard(xd);
				g2.repaint();
			}
		}
		if (position > 0) {
			this.leftArrow.setEnabled(true);
		}
		if (numOnScreen + this.position > numOfPlayerCards - 1) {
			this.rightArrow.setEnabled(false);
		}

		for (CardButton cb : visibleCardButtons) {
			if (enabledCards.contains(cb.getCard())) {
				cb.setEnabled(true);
			} else {
				cb.setEnabled(false);

			}
		}
	}

	/**
	 * Shift all visible cards one position right
	 */
	public void leftButtonClicked() {
		if (position > 0) {
			position--;
			for (int i = position; i < position + PLAYER_CARDS; i++) {
				CardButton g2 = visibleCardButtons.get(i - position);
				B20 xd = allPlayerCards.get(i);
				g2.setCard(xd);
				g2.repaint();
			}
		}
		if (position < 1) {
			this.leftArrow.setEnabled(false);
		}
		int numOfPlayerCards = allPlayerCards.size();
		int numOnScreen = visibleCardButtons.size();
		if (numOnScreen + this.position < numOfPlayerCards) {
			this.rightArrow.setEnabled(true);
		}

		for (CardButton cb : visibleCardButtons) {
			if (enabledCards.contains(cb.getCard())) {
				cb.setEnabled(true);
			} else {
				cb.setEnabled(false);

			}
		}
	}

	public void update(Observable arg0, Object noMatter) {
		if (arg0 instanceof ObservableValue<?>) {
			ObservableValue ov = (ObservableValue) arg0;
			Object o1 = ov.getValue();
			if (o1 instanceof List<?>) {
				List cards1 = (List) o1;
				List<B20> updateCards = new ArrayList<B20>();
				for (Object o2 : cards1) {
					if (o2 == null) {
						throw new NullPointerException(); // "Cards list contained a null value");
					} else if (!(o2 instanceof B20)) {
						throw new IllegalStateException("Contained " + o2.getClass().getCanonicalName());
					} else {
						B20 c = (B20) o2;
						updateCards.add(c);
					}
				}
				// System.out.println("VKVKVK: updating with " + updateCards.size());
				// XXX
				position = 0;
				panel.setVisible(true);
				enabledCards.clear();
				allPlayerCards.clear();
				allPlayerCards.addAll(updateCards);
				int min = Math.min(visibleCardButtons.size(), allPlayerCards.size());
				for (int i = 0; i < min; i++) {
					CardButton g2 = this.visibleCardButtons.get(i);
					B20 xd = allPlayerCards.get(i);
					g2.setCard(xd);
				}
				for (int i = 0; i < min; i++) {
						visibleCardButtons.get(i).setVisible(true);
				}
				if (visibleCardButtons.size() > allPlayerCards.size()) {
					for (int i = allPlayerCards.size(); i < visibleCardButtons.size(); i++) {
						visibleCardButtons.get(i).setVisible(false);
					}
				}
				int visibleSize = visibleCardButtons.size();
				int allSize = allPlayerCards.size();
				// System.out.println("visibleSize=" + visibleSize + " < allSize=" + allSize);
				if (visibleSize < allSize) {
					leftArrow.setVisible(true);
					rightArrow.setVisible(true);
					leftArrow.setEnabled(false);
					rightArrow.setEnabled(true);
				} else {
					leftArrow.setVisible(false);
					rightArrow.setVisible(false);
				}
				for (CardButton childCB : visibleCardButtons) {
					childCB.setEnabled(false);
					childCB.setSelected(false);
					childCB.repaint();
				}
			} 
			 else if (o1 instanceof G1) { // == null) {
					G1 gameStateCode = (G1)o1;
					switch (gameStateCode) {
					case G_3:
						 // ? what to do
						break;
					case G_2:
						panel.setVisible(false);
						enabledCards.clear();
						allPlayerCards.clear();
						for (int i = 0; i < visibleCardButtons.size(); i++) {
							CardButton childCB = visibleCardButtons.get(i);
							childCB.setVisible(false);
							childCB.setEnabled(false);
							childCB.setSelected(false);
							childCB.repaint();
						}
						leftArrow.setEnabled(false);
						rightArrow.setEnabled(false);
						leftArrow.setVisible(false);
						rightArrow.setVisible(false);						
						break;
					default:
						break;
					} 
			} else {
				throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + (o1 == null ? "null" : o1.getClass().getCanonicalName()));
			}
		} else {
			throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + arg0.getClass().getCanonicalName());
		}
	}

	/**
	 * Retrieves cards that are currently selected on the screen
	 * @return Selected cards
	 */
	public List<B20> getSelectedCards() {
		ArrayList<B20> selectedCards = new ArrayList<B20>();
		for (CardButton child : visibleCardButtons) {
			if (child.isSelected() && child.isVisible()) {
				B20 childCard = child.getCard();
				selectedCards.add(childCard);
			}
		}
		return selectedCards;
	}

	/**
	 * Enables all cards in selected state and disables rest of the cards
	 */
	public void setEnabledForSelected() {
		enabledCards.clear();
		for (CardButton childCB : visibleCardButtons) {
			if (childCB.isSelected() && childCB.isVisible()) {
				childCB.setEnabled(true);
				enabledCards.add(childCB.getCard());
			} else {
				childCB.setEnabled(false);
			}
		}
	}

	/**
	 * Enables all cards that are of one of the parameter ranks and disables rest of the cards
	 * @param ranks Ranks
	 */
	public void setEnabledForRanks(List<B6> ranks) {
		enabledCards.clear();
		for (B20 c : this.allPlayerCards) {
			if (ranks.contains(c.getB22())) {
				enabledCards.add(c);
			}
		}
		for (CardButton child : visibleCardButtons) {
			B20 childCard = child.getCard();
			if (childCard != null && ranks.contains(childCard.getB22())) {
				child.setEnabled(true);
			} else {
				child.setEnabled(false);
			}
		}
	}

	/**
	 * Enables all cards of parameter rank and disables rest of the cards
	 * @param rank Rank
	 */
	public void setEnabledForRank(B6 rank) {
		enabledCards.clear();
		for (B20 c : this.allPlayerCards) {
			if (rank.equals(c.getB22())) {
				enabledCards.add(c);
			}
		}
		for (CardButton child : visibleCardButtons) {
			B20 childCard = child.getCard();
			if (childCard != null && childCard.getB22().equals(rank)) {
				child.setEnabled(true);
			} else {
				child.setEnabled(false);
			}
		}
	}

	/**
	 * Enables all parameter cards and disables rest of the cards
	 * @param parCards Cards
	 */
	public void enableCards(List<B20> parCards) {
		enabledCards.clear();
		for (B20 c : this.allPlayerCards) {
			if (Utils.cardsContain(parCards, c)) {
				enabledCards.add(c);
			}
		}

		for (CardButton child : visibleCardButtons) {
			B20 childCard = child.getCard();
			if (Utils.cardsContain(parCards, childCard)) {
				child.setEnabled(true);
			} else {
				child.setEnabled(false);
			}
		}
	}

	/* START OF TEST METHODS */

	/**
	 * Retrieves all the visible cards
	 * @return Cards
	 */
	public List<B20> getVisible() {
		return getCards(false);
	}

	/**
	 * Retrieves all the enabled cards
	 * @return Cards
	 */
	public List<B20> getEnabled() {
		return getCards(true);
	}

	/**
	 * Retrieves either only visible and enabled or all the visible cards
	 * @param _enabled If true only visible and enabled cards are returned, otherwise all visible cards
	 * @return Cards
	 */
	private List<B20> getCards(final boolean enabled) {
		List<B20> ret = new ArrayList<B20>();
		for (CardButton cb : visibleCardButtons) {
			if (cb.isVisible() && (!enabled || cb.isEnabled())) {
				B20 c = cb.getCard();
				ret.add(c);
			}
		}
		return ret;
	}

	/**
	 * Programmatically clicks parameter card
	 * @param _type Card
	 * @throws IllegalArgumentException Card was not found among visible and enabled
	 */
	public void clickCard(final B20 _c) {
		for (CardButton cb : visibleCardButtons) {
			if (cb.isVisible() && (Utils.cardsAreEqual(cb.getCard(), _c))) {
				cb.doClick();
				return;
			}
		}
		throw new IllegalArgumentException("Not found: " + Utils.getPresentableFormat(_c));
	}
	
	/* END OF TEST METHODS */

}
