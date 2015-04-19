/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import java.awt.Color;

import com.littlech.cl.constants.Names;
import com.littlech.cl.gui.podkidnoy.constants.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Observable;

import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.Utils;
import com.littlech.cl.gui.podkidnoy.button.CardButton;
import com.littlech.cl.gui.podkidnoy.handler.TablePanelButtonHandler;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.gen.b.B1;
import com.littlech.gen.b.B20;
import com.littlech.gen.b.B23;
import com.littlech.gen.b.B6;
import com.littlech.gen.g.G1;

/**
 * 
 * Container for Podkidnoy table cards
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class TablePanel implements IScreenPanel {

	/**
	 * Maximum number of table card pairs on screen
	 */
	public static final int TABLE_CARDS = 6;

	/**
	 * Table cards as pairs of bottom card component and its defender
	 */
	private LinkedHashMap<CardButton, CardButton> dummies = new LinkedHashMap<CardButton, CardButton>();

	/**
	 * Base container's constraint
	 */
	private final AbsoluteConstraints constraint = new AbsoluteConstraints(PodkidnoyLocations.TABLE_CARDS, PodkidnoySizes.TABLE_CARDS);

	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;

	/**
	 * Panel where buttons and your turn label are added
	 */
	private JPanel panel = new JPanel();

	/**
	 * Proxy for button action events
	 */
	private TablePanelButtonHandler buttonHandler = new TablePanelButtonHandler();

	/**
	 * 
	 * Constructor
	 * 
	 * @param imageLoader
	 *          Image loader
	 */
	public TablePanel(final IImageLoader imageLoader) {
		this.mImageLoader = imageLoader;

		panel.setLayout(new AbsoluteLayout());
		panel.setOpaque(false);

		for (int i = 0; i < TABLE_CARDS; i++) {
			CardButton bottomCard = new CardButton(mImageLoader), defendingCard = new CardButton(mImageLoader);
			bottomCard.setName(Names.PODKIDNOY_TABLE_BOTTOM_ + i);
			defendingCard.setName(Names.PODKIDNOY_TABLE_DEFENDING_ + i);
			bottomCard.setBackground(Color.ORANGE);
			defendingCard.setBackground(Color.orange);
			initCard(bottomCard);
			initCard(defendingCard);
			dummies.put(bottomCard, defendingCard);

			Point location = new Point(0 + i * PodkidnoySizes.TABLE_CARDS_SHIFT, PodkidnoySizes.TABLE_CARDS_TOP_SHIFT_V);
			panel.add(defendingCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(new Point(location.x + PodkidnoySizes.TABLE_CARDS_TOP_SHIFT_H, location.y
					- PodkidnoySizes.TABLE_CARDS_TOP_SHIFT_V), PodkidnoySizes.CARD));
			panel.add(bottomCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(location, PodkidnoySizes.CARD));

			bottomCard.repaint();
			defendingCard.repaint();
		}
	}

	/**
	 * Retrieves panel where table card buttons are drawn
	 * 
	 * @return JPanel Panel
	 */
	@Override
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public void setEnabled(boolean b) {
		for (CardButton key : dummies.keySet()) {
			CardButton value = dummies.get(key);
			key.setEnabled(b);
			value.setEnabled(b);
		}
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
	 * Retrieves buttons handler for this table cards container
	 * 
	 * @return Button handler
	 */
	public TablePanelButtonHandler getButtonHandler() {
		return buttonHandler;
	}

	/**
	 * Applies common operations to parameter card button
	 * @param cb Card button
	 */
	private void initCard(CardButton cb) {
		cb.addActionListener(getButtonHandler());
		cb.setBorderPainted(false);
		cb.setContentAreaFilled(false);
		cb.setVisible(true);
	}

	/**
	 * Retrieves selected table cards
	 * @return Cards
	 */
	public List<B20> getSelectedBottoms() {
		ArrayList<B20> ret = new ArrayList<B20>();
		for (CardButton cb : dummies.keySet()) {
			if (cb.isSelected() && cb.isVisible()) {
				ret.add(cb.getCard());
			}
		}
		return ret;
	}

	/**
	 * Enables parameter cards
	 * @param cards Cards
	 */
	public void enableCards(List<B20> cards) {
		for (CardButton cb : dummies.keySet()) {
			CardButton value = dummies.get(cb);
			if (cards.contains(value.getCard())) {
				value.setEnabled(true);
			} else {
				value.setEnabled(false);

			}
			if (cards.contains(cb.getCard())) {
				cb.setEnabled(true);
			} else {
				cb.setEnabled(false);
			}
		}
	}

	/**
	 * Enables all table cards of parameter rank
	 * @param rankSelected Rank
	 */
	public void setEnabledForRank(B6 rankSelected) {
		for (CardButton key : dummies.keySet()) {
			if (key.getCard() != null && rankSelected.equals(key.getCard().getB22())) {
				key.setEnabled(true);
			}
			CardButton value = dummies.get(key);
			if (value != null && value.getCard() != null && rankSelected.equals(value.getCard().getB22())) {
				value.setEnabled(true);
			}
		}
	}

	public void update(Observable arg0, Object noMatter) {
		if (arg0 instanceof ObservableValue<?>) {
			ObservableValue ov = (ObservableValue) arg0;
			Object o1 = ov.getValue();
			if (o1 instanceof List<?>) {
				List cards1 = (List) o1;
				List<B23> updateCards = new ArrayList<B23>();
				List<B20> modelBottoms = new ArrayList<B20>();
				for (Object o2 : cards1) {
					if (o2 == null) {
						throw new NullPointerException(); // "Table pairs contained a null value");
					} else if (!(o2 instanceof B23)) {
						throw new IllegalStateException("Contained " + o2.getClass().getCanonicalName());
					} else {
						B23 c = (B23) o2;
						B20 bottom = c.getB25();
						if (bottom == null) {
							throw new NullPointerException(); // "Bottom card was null");
						}
						updateCards.add(c);
						modelBottoms.add(bottom);
					}
				}
				panel.setVisible(true);
				ArrayList<CardButton> keys = new ArrayList<CardButton>(dummies.keySet());
				for (B20 bottom : modelBottoms) {
					int idx = modelBottoms.indexOf(bottom);
					drawBottomCard(idx, bottom);
					B20 topCard = getTopCard(updateCards, bottom); // cards.get(bottom);
					CardButton buttonG = dummies.get(keys.get(idx));
					if (topCard != null) {
						buttonG.setCard(topCard);
						buttonG.setVisible(true);
					} else {
						buttonG.setVisible(false);
					}
					buttonG.repaint();
				}
				int modelBottomsSize = modelBottoms.size();
				int bottomButtonsSize = dummies.keySet().size();
				if (bottomButtonsSize > modelBottomsSize) {
					for (int i = modelBottomsSize; i < bottomButtonsSize; i++) {
						CardButton key = keys.get(i);
						key.setVisible(false);
						dummies.get(key).setVisible(false);
					}
				}
				for (CardButton key : dummies.keySet()) {
					key.setSelected(false);
					dummies.get(key).setSelected(false);
					key.setEnabled(false);
					dummies.get(key).setEnabled(false);
				}
			} else if (o1 instanceof G1) {
				G1 gameStateCode = (G1) o1;
				switch (gameStateCode) {
				case G_3:
					// ? what to do
					break;
				case G_2:

					panel.setVisible(true);
					for (CardButton key : dummies.keySet()) {
						key.setVisible(false);
						dummies.get(key).setVisible(false);
						key.setSelected(false);
						dummies.get(key).setSelected(false);
						key.setEnabled(false);
						dummies.get(key).setEnabled(false);
					}
					break;
				default:
					break;
				}
			} else {
				throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + o1.getClass().getCanonicalName());
			}
		} else {
			throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + arg0.getClass().getCanonicalName());
		}
	}

	/**
	 * Searches parameter table card pairs and retrieves defending card corresponding to parameter bottom card
	 * @param updateCards Table card pairs
	 * @param bottom Bottom card
	 * @return Defending card corresponding to bottom card
	 */
	private B20 getTopCard(List<B23> updateCards, B20 bottom) {
		B6 bottomRank = bottom.getB22();
		B1 bottomSuit = bottom.getB21();
		for (B23 p : updateCards) {
			B20 b = p.getB25();
			B6 bRank = b.getB22();
			B1 bSuit = b.getB21();
			if (bottomRank.equals(bRank)) {
				if (bottomSuit.equals(bSuit)) {
					B20 ret = p.getB24();
					return ret;
				}
			}
		}
		throw new IllegalArgumentException("" + Utils.getPresentableFormat(bottom) + " not in "
				+ Utils.getPresentableFormat(updateCards));
	}

	/**
	 * Draws parameter card as a bottom card on parameter position
	 * @param idx Position in visible table card pairs
	 * @param bottom Card to draw
	 */
	private void drawBottomCard(int idx, B20 bottom) {
		ArrayList<CardButton> keys = new ArrayList<CardButton>(dummies.keySet());
		CardButton buttonG = keys.get(idx);
		buttonG.setCard(bottom);
		buttonG.setVisible(true);
		buttonG.repaint();
	}

	/* START OF TEST METHODS */

	/**
	 * Returns table card pairs that are currently visible on screen
	 * @return Table card pairs
	 */
	public List<B23> testGetPairs() {
		List<B23> ret = new ArrayList<B23>();
		for (CardButton bottom : dummies.keySet()) {
			if (bottom.isVisible()) {
				CardButton defender = dummies.get(bottom);
				B23 p = new B23();
				p.setB25(bottom.getCard());
				if (defender.isVisible()) {
					p.setB24(defender.getCard());
				}
				ret.add(p);
			}
		}
		return ret;
	}

	/**
	 * Returns bottom cards that are currently in enabled state on screen
	 * @return Cards
	 */
	public List<B20> testGetEnabledBottoms() {
		return testGetBottoms(true);
	}


	/**
	 * Returns bottom cards that are currently visible on screen, both in enabled and disabled state
	 * @return Cards
	 */
	public List<B20> testGetBottoms() {
		return testGetBottoms(false);
	}

	/**
	 * Returns bottom cards that are visible on screen
	 * @param _enabled If true, only bottom cards that are in enabled state are returned 
	 * @return Cards
	 */
	private List<B20> testGetBottoms(final boolean _enabled) {
		List<B20> ret = new ArrayList<B20>();
		for (CardButton bottom : dummies.keySet()) {
			if (bottom.isVisible() && (!_enabled || bottom.isEnabled())) {
				ret.add(bottom.getCard());
			}
		}
		return ret;
	}

	/**
	 * Programmatically clicks parameter bottom card 
	 * @param _c Card
	 * @throws IllegalArgumentException Bottom card was not found among visible and enabled
	 */
	public void testClickBottomCard(final B20 _c) {
		for (CardButton bottom : dummies.keySet()) {
			if (bottom.isVisible() && bottom.isEnabled() && Utils.cardsAreEqual(bottom.getCard(), _c)) {
				bottom.doClick();
				return;
			}
		}
		throw new IllegalArgumentException(
				
				// "Bottom card" +
				"Not found: " + Utils.getPresentableFormat(_c));
	}

	/**
	 * Tries to remove all defending cards by programmatically clicking them
	 */
	public void testRemoveAllDefenders() {
		Iterator<CardButton> itBottoms = dummies.keySet().iterator();
		while (itBottoms.hasNext()) {
			CardButton bottom = itBottoms.next();
			CardButton defender = dummies.get(bottom);
			if (defender.isVisible() && defender.isEnabled()) {
				defender.doClick();
			}
		}

	}

	/**
	 * Tries to remove defending card by programmatically clicking it
	 * @param _c Card
	 */
	public void testClickDefendingCard(final B20 _c) {
		for (CardButton defender : dummies.values()) {
			if (defender.isVisible() && defender.isEnabled() && Utils.cardsAreEqual(defender.getCard(), _c)) {
				defender.doClick();
				return;
			}
		}
		throw new IllegalArgumentException(// "Bottom card n" +
				"Not found: " + Utils.getPresentableFormat(_c));
	}

	/* END OF TEST METHODS */

}
