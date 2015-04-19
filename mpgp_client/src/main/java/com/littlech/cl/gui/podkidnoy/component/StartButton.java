/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import com.littlech.cl.gui.podkidnoy.constants.*;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.*;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.gen.g.G1;

/**
 * 
 * Container where Podkidnoy start and leave buttons are added
 * 
 * @author Kristjan Veskim�e
 *
 */
public class StartButton implements IScreenPanel {

	/**
	 * Base panel constraint
	 */
	private AbsoluteConstraints constraint = new AbsoluteConstraints(PodkidnoyLocations.STARTANDLEAVE_START, PodkidnoySizes.STARTANDLEAVE_START);

	
	/**
	 * Start button
	 */
	private JButton startButton = new JButton();

	/**
	 * Image loader
	 */
	protected IImageLoader mImageLoader;
	
	/**
	 * Panel where buttons and your turn label are added
	 */
	protected JPanel panel = new JPanel();


	private G1 state;
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param imageLoader Image loader
	 */
	public StartButton(final IImageLoader imageLoader) {
		this.mImageLoader = imageLoader;
		
		// leaveButton.setName(Names.PODKIDNOY_LEAVE);
		startButton.setName(Names.PODKIDNOY_START);
		// startButton.addActionListener(new TimeCounter(2000, startButton));

		panel.setLayout(new AbsoluteLayout());
		panel.setOpaque(false);
		// panel.setBackground(Color.magenta);
		
		initStartButton();

		// panel.add(leaveButton, new AbsoluteConstraints(PodkidnoyLocations.STARTANDLEAVE_LEAVE, PodkidnoySizes.STARTANDLEAVE_LEAVE));
		panel.add(startButton,
				// getConstraint()
				new AbsoluteConstraints(Locations.UPPER_LEFT, PodkidnoySizes.STARTANDLEAVE_START)
		);
	}

	/**
	 * Retrieves panel where buttons and your turn label are drawn
	 * @return JPanel Panel
	 */
	@Override
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

	@Override
	public void setEnabled(boolean b) {
		// System.out.println("b=" + b);
		// leaveButton.setEnabled(b);
		startButton.setEnabled(b);
	}

	@Override
	public void setVisible(boolean b) {
		// System.out.println("b=" + b);
		panel.setVisible(b);
		/*
		leaveButton.setVisible(b);
		startButton.setVisible(b);
		*/
		// throw new IllegalArgumentException();
	}
	
	public boolean isVisible() {
		boolean ret = panel.isVisible();
		return ret;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof ObservableValue<?>) {
			ObservableValue ov = (ObservableValue) arg0;
			Object o1 = ov.getValue();
			if (o1 instanceof G1) {
				G1 newState = (G1)o1;
				if (!newState.equals(getState())) {
					switch (newState) {
					case G_3:
						setVisible(false);
						setEnabled(false);
						break;

					default:
						setVisible(true);
						setEnabled(true);
						break;
					}
					setState(newState);
				}
			} else {
				throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + o1.getClass().getCanonicalName());
			}
		} else {
			throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + arg0.getClass().getCanonicalName());
		}
	}

	/**
	 * 
	 * Retrieves the last game state used to update this panel
	 * 
	 * @return
	 */
	public G1 getState() {
		return state;
	}
	
	/**
	 * 
	 * Sets parameter state to this component
	 * 
	 * @param state Game state
	 */
	public void setState(G1 state) {
		this.state = state;
	}

	/**
	 * Initializes the start button
	 */
	private void initStartButton() {
		
		startButton.setIcon(mImageLoader.fetchImage(PodkidnoyImages.START));
		startButton.setRolloverIcon(mImageLoader.fetchImage(PodkidnoyImages.START));
		
		startButton.setPressedIcon(mImageLoader.fetchImage(PodkidnoyImages.START_PRESSED));
		startButton.setRolloverSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.START_PRESSED));
		startButton.setSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.START_PRESSED));
		
		startButton.setDisabledIcon(mImageLoader.fetchImage(PodkidnoyImages.START_PRESSED));
		startButton.setDisabledSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.START_PRESSED));
	/*
		startButton.setDisabledIcon(mImageLoader.fetchImage(PodkidnoyImages.START_DIS));
		startButton.setDisabledSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.START_DIS_PRESSED));
		*/
		initButton(startButton);
	}

	/**
	 * Initializes the leave button
	 */
	/*
	private void initLeaveButton() {
		leaveButton.setIcon(mImageLoader.fetchImage(PodkidnoyImages.LEAVE));
		leaveButton.setRolloverIcon(mImageLoader.fetchImage(PodkidnoyImages.LEAVE)); // LEAVE_RO));
		leaveButton.setPressedIcon(mImageLoader.fetchImage(PodkidnoyImages.LEAVE_PRESSED));
		leaveButton.setDisabledIcon(mImageLoader.fetchImage(PodkidnoyImages.LEAVE_DIS));
		leaveButton.setDisabledSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.LEAVE_DIS));
		
		initButton(leaveButton);
	}
	*/

	/**
	 * Common operations applied to start and leave buttons
	 * @param but Button
	 */
	private void initButton(AbstractButton but) {
		but.setBorderPainted(false);
		but.setContentAreaFilled(false);
	}

	/**
	 * Retrieves the start button
	 * @return Start button
	 */
	public JButton getStart() {
		return startButton;
	}
	
	public void addActionListener(final ActionListener _al) {
		startButton.addActionListener(_al);
	}

	/**
	 * Retrieves the leave button
	 * @return Leave button
	 */
	/*
	public JButton getLeave() {
		return leaveButton;
	}
	*/

}
