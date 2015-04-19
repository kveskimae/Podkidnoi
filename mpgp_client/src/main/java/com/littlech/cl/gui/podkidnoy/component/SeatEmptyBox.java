/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.component;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.Locations;
import com.littlech.cl.constants.Names;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoyColors;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoyLocations;
import com.littlech.cl.gui.podkidnoy.constants.PodkidnoySizes;
import com.littlech.gen.f.F1;

public class SeatEmptyBox {
	
	/**
	 * Seat's ID
	 */
	private final F1 id;

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

	private AbsoluteConstraints nameConstraint = new AbsoluteConstraints(PodkidnoyLocations.PLAYERBOX_NAME, PodkidnoySizes.PLAYERBOX_NAME),
	constraint = new AbsoluteConstraints(Locations.UPPER_LEFT, PodkidnoySizes.PLAYERBOX);

	/**
	 * Constructor
	 * 
	 * @param imageLoader
	 *          Image loader
	 * @param _id
	 *          Seat ID
	 */
	public SeatEmptyBox(final IImageLoader imageLoader, final F1 _id) {
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
	
		name.setText("Empty");
		
		panel.add(name, nameConstraint);
		
		name.setName(Names.SEATBOX_EMPTY_ + getID());
		panel.setName(Names.SEATBOX_EMPTY_PANEL_ + getID());
	}

	public void setVisible(boolean b) {
		/*
		if (SeatIDCode.THREE.equals(getID())) {
			System.out.println("three empty, b = " + b);
		}
		*/
		getPanel().setVisible(b);
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
	
	public AbsoluteConstraints getConstraint() {
		return constraint;
	}

}
