/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.Names;
import com.littlech.cl.gui.podkidnoy.constants.*;
import com.littlech.cl.gui.podkidnoy.button.ActionButton;
import com.littlech.cl.gui.podkidnoy.handler.ButtonsPanelButtonHandler;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.cl.gui.podkidnoy.util.Self;
import com.littlech.gen.a.A1;
import com.littlech.gen.a.A5;
import com.littlech.gen.g.G1;

/**
 * 
 * Action buttons panel
 * 
 * @author Kristjan Veskim�e
 *
 */
public class ButtonsPanel implements IScreenPanel {

	/**
	 * Maximum number of buttons
	 */
	public static final int BUTTONS = 3;

	/**
	 * List of button codes that are currently presented; TODO not really necessary
	 */
	private List<A5> modelButtons = new ArrayList<A5>();

	/**
	 * Buttons on screen
	 */
	private List<ActionButton> aVisibleButton = new ArrayList<ActionButton>();
	
	/**
	 * Your turn label
	 */
	// private JLabel yourTurnLabel = new javax.swing.JLabel();
	
	/**
	 * Constraint for buttons panel
	 */
	private AbsoluteConstraints constraint = new AbsoluteConstraints(PodkidnoyLocations.ACTIONS_PANEL, PodkidnoySizes.ACTIONS);

	/**
	 * Image loader
	 */
	protected IImageLoader mImageLoader;
	
	/**
	 * Panel where buttons and your turn label are added
	 */
	protected JPanel panel = new JPanel();
	
	/**
	 * Handler for button action events
	 */
	private ButtonsPanelButtonHandler buttonHandler = new ButtonsPanelButtonHandler();
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param imageLoader Image loader
	 */
	public ButtonsPanel(final IImageLoader imageLoader) {
		this.mImageLoader = imageLoader;

		panel.setLayout(new AbsoluteLayout());
		panel.setOpaque(false);
		// yourTurnLabel.setIcon(mImageLoader.fetchImage(PodkidnoyImages.YOUR_TURN));
		for (int i = 0; i < BUTTONS; i++) {
			ActionButton cur = new ActionButton(mImageLoader);
			cur.setName(Names.PODKIDNOY_ACTION_ + i);
			aVisibleButton.add(cur);
			cur.addActionListener(buttonHandler);
			Point location = new Point(i * PodkidnoySizes.ACTION_BUTTONS_SHIFT, 0);
			panel.add(cur.getPanel(), new org.netbeans.lib.awtextra.AbsoluteConstraints(location, PodkidnoySizes.ACTION));
		}
		// panel.add(yourTurnLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(PodkidnoyLocations.YOUR_TURN, PodkidnoySizes.YOUR_TURN));
	}
	
	/**
	 * Retrieves action events proxy for this buttons panel
	 * @return Button handler
	 */
	public ButtonsPanelButtonHandler getButtonHandler() {
		return buttonHandler;
	}

	/**
	 * Retrieves panel where buttons and your turn label are drawn
	 * @return JPanel Panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * 
	 * Retrieves model for buttons set drawn on screen
	 * 
	 * @return Buttons model as button codes
	 */
	public List<A5> getModelButtons() {
		return modelButtons;
	}

	/**
	 * Retrieves buttons actually drawn on screen
	 * @return Buttons as Swing components
	 */
	public List<ActionButton> getButtons() {
		return aVisibleButton;
	}

	/**
	 * Enables parameter buttons and disables rest of the buttons 
	 * @param types Buttons to be enabled as button codes
	 */
	public void enableTypes(List<A5> types) {
		for (ActionButton cur : getButtons()) {
			if (types.contains(cur.getType())) {
				cur.setEnabled(true);
			} else {
				cur.setEnabled(false);
			}
		}
	}

	/**
	 * Enables or disables buttons panel
	 */
	@Override
	public void setEnabled(boolean b) {
		for (ActionButton cur : getButtons()) {
			cur.setEnabled(b);

		}
	}

	/**
	 * Shows or hides buttons panel
	 */
	@Override
	public void setVisible(boolean b) {
		panel.setVisible(b);
		for (ActionButton ab : getButtons()) {
			ab.setVisible(b);
		}
	}

	@Override
	public void update(Observable arg0, Object arg) {
		if (arg0 instanceof ObservableValue<?>) {
			ObservableValue ov = (ObservableValue) arg0;
			Object o1 = ov.getValue();
			if (o1 instanceof Self) {
				Self self = (Self) o1;
				A1 stateCode = self.getOnTurnState();
				getModelButtons().clear();
				if (stateCode != null) {
					switch (stateCode) {
					case A_4:
						getModelButtons().add(A5.A_11);
						break;
					case A_3:
						getModelButtons().add(A5.A_9);
						break;
					case A_2:
						getModelButtons().add(A5.A_6);
						break;
					default:
						throw new IllegalArgumentException("Unsupported: " + stateCode);
					}
					getModelButtons().addAll(self.getButtons());
					setButtonsVisible(getModelButtons());
				} else {
					setVisible(false);
				}
			} else if (o1 instanceof List<?>) {
				List cards1 = (List) o1;
				List<A5> buttonsAL = new ArrayList<A5>();
				for (Object o2 : cards1) {
					if (o2 == null) {
						throw new NullPointerException();
					} else if (!(o2 instanceof A5)) {
						throw new IllegalStateException("" + o2.getClass().getCanonicalName());
					} else {
						A5 c = (A5) o2;
						buttonsAL.add(c);
					}
				}
				getModelButtons().addAll(buttonsAL);
				setButtonsVisible(getModelButtons());
			}

			else if (o1 instanceof G1) {
				G1 gameStateCode = (G1) o1;
				switch (gameStateCode) {
				case G_3:
					// ? what to do
					break;
				case G_2:
					setVisible(false);
					break;
				default:
					break;
				}
			} else {
				throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + o1.getClass().getCanonicalName());
			}
			setEnabled(false);
		} else {
			throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + arg0.getClass().getCanonicalName());
		}
	}

	/**
	 * Shows parameter buttons and hides rest of the buttons
	 * @param buttonsAL Buttons to be shown as button codes
	 */
	private void setButtonsVisible(List<A5> buttonsAL) {
		panel.setVisible(true);
		// yourTurnLabel.setVisible(true);
		for (A5 cur : buttonsAL) {
			int idx = buttonsAL.indexOf(cur);
			ActionButton smoke = aVisibleButton.get(idx);
			smoke.setType(cur);
			smoke.setVisible(true);
		}
		if (buttonsAL.size() < aVisibleButton.size()) {
			for (int i = buttonsAL.size(); i < aVisibleButton.size(); i++) {
				aVisibleButton.get(i).setVisible(false);
			}
		}
	}

	@Override
	public AbsoluteConstraints getConstraint() {
		return this.constraint;
	}

	/* START OF TEST METHODS */
	
	/**
	 * Retrieves all the visible buttons
	 * @return Button codes
	 */
	public List<A5> testGetVisible() {
		return testGetVisibleAndOptionallyEnabled(false);
	}

	/**
	 * Retrieves all the enabled buttons
	 * @return Button codes
	 */
	public List<A5> testGetEnabled() {
		return testGetVisibleAndOptionallyEnabled(true);
	}

	/**
	 * Retrieves either only enabled or all the visible buttons
	 * @param _enabled If true only enabled buttons are returned, otherwise all visible buttons
	 * @return Button codes
	 */
	private List<A5> testGetVisibleAndOptionallyEnabled(final boolean _enabled) {
		List<A5> ret = new ArrayList<A5>();
		for (ActionButton cur : getButtons()) {
			if (panel.isVisible() && cur.isVisible() && (!_enabled || cur.isEnabled())) {
				ret.add(cur.getType());
			}
		}
		return ret;
	}

	/**
	 * Programmatically clicks parameter button
	 * @param _type Button code
	 * @throws IllegalArgumentException Button was not found among visible and enabled
	 */
	public void clickButton(final A5 _type) {
		for (ActionButton cur : getButtons()) {
			if (cur.isVisible() && cur.isEnabled() && _type.equals(cur.getType())) {
				cur.doClick();
				return;
			}
		}
		throw new IllegalArgumentException("Not found: " + _type);
	}
	
	/* END OF TEST METHODS */

}


