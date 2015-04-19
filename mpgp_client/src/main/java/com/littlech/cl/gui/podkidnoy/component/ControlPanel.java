/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.Names;
import com.littlech.cl.gui.podkidnoy.constants.*;
import com.littlech.cl.gui.podkidnoy.button.ButtonDisabler;
import com.littlech.cl.gui.podkidnoy.button.ControlButton;
import com.littlech.cl.gui.podkidnoy.handler.CardsPanelButtonHandler;
import com.littlech.cl.gui.podkidnoy.util.ControlButtonType;

/**
 * 
 * Control buttons panel
 * 
 * @author Kristjan Veskim�e
 *
 */
public class ControlPanel implements IScreenPanel {

	/**
	 * Maximum number of control buttons that can be drawn on screen
	 */
	public static final int CONTROLBUTTONS = 2;

	/**
	 * Visible control buttons
	 */
	private List<ControlButton> cards = new ArrayList<ControlButton>();
	
	/**
	 * Constraint for base panel
	 */
	private AbsoluteConstraints constraint = new AbsoluteConstraints(PodkidnoyLocations.CONTROL_BG, PodkidnoySizes.CONTROL_BG);

	/**
	 * Image loader
	 */
	protected IImageLoader mImageLoader;
	
	/**
	 * Base panel where buttons are added
	 */
	protected JPanel panel = new JPanel();

	/**
	 * Control buttons area background image
	 */
	private JLabel controlBG = new JLabel();
	
	/**
	 * Proxy for button action events
	 */
	private CardsPanelButtonHandler buttonHandler = new CardsPanelButtonHandler();
	
	private ControlButton optionsButton, closeButton;
	
	/**
	 * Constructor
	 * @param imageLoader Image loader
	 */
	public ControlPanel(final IImageLoader imageLoader) { // , final PodkidnoyController _controller) {
		this.mImageLoader = imageLoader;
		
		panel.setLayout(new AbsoluteLayout());
		panel.setOpaque(false);
		
		optionsButton = new ControlButton(mImageLoader, ControlButtonType.OPTIONS);
		closeButton = new ControlButton(mImageLoader, ControlButtonType.CLOSE);
		
		closeButton.addActionListener(new ButtonDisabler(closeButton.getButton()));

		ControlButton cur;
		for (int i = 0; i < CONTROLBUTTONS; i++) {
			// ControlButtonType type;
			String name;
			switch (i) {
			case 0:
				// type = ControlButtonType.CLOSE;
				cur = closeButton;
				name = Names.CONTROL_CLOSE 
					// Names.CONTROL_CLOSE
					;
				break;
			case 1:
				// type = ControlButtonType.OPTIONS;
				cur = optionsButton;
				name = Names.CONTROL_OPTIONS;
				break;
			default:
				throw new IllegalArgumentException("Unsupported number of control buttons: " + i);
			}
			// ControlButton cur = new ControlButton(mImageLoader, type);
			cur.setName(name);
			cards.add(cur);
			// cur.addActionListener(buttonHandler);
			Point location = new Point(
					// x
					(i + 1) * PodkidnoySizes.CONTROL_BUTTONS_SHIFT + i * PodkidnoySizes.CONTROL_BUTTON.width,
					// y
					PodkidnoySizes.CONTROL_GAP_V);
			panel.add(cur.getPanel(), new AbsoluteConstraints(location, PodkidnoySizes.CONTROL_BUTTON));
		}
		
		controlBG.setIcon(mImageLoader.fetchImage(PodkidnoyImages.CONTROL_BG));		
		panel.add(controlBG, new AbsoluteConstraints(new Point(0, 0), PodkidnoySizes.CONTROL_BG));
		
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
		throw new UnsupportedOperationException("Control buttons must be enabled/disabled separately");
	}

	@Override
	public void setVisible(boolean b) {
		panel.setVisible(b);
	}

	@Override
	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

	@Override
	public void update(Observable o, Object arg) {
		throw new UnsupportedOperationException(); // "Control buttons don't listen to anyone");
	}
	
	public void addActionListenerToButtons(final ActionListener _al) {
		for (ControlButton cb : cards) {
			cb.addActionListener(_al);
		}
	}

	public ControlButton getOptionsButton() {
		return optionsButton;
	}

	public ControlButton getCloseButton() {
		return closeButton;
	}
	
	
	
	/**
	 * Retrieves the handler for the card buttons' action events
	 * @return Handler
	 */
	/*
	public CardsPanelButtonHandler getButtonHandler() {
		return buttonHandler;
	}
	*/

}
