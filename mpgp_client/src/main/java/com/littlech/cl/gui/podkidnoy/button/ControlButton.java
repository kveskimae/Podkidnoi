/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.podkidnoy.button;

import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.gui.podkidnoy.constants.*;
import com.littlech.cl.gui.podkidnoy.util.ControlButtonType;


/**
 * 
 * Button for options and table controls
 * 
 * @author Kristjan Veskim�e
 *
 */
public class ControlButton {

	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;
	
	/**
	 * Type for this button; determines text for button and subsequent game behaviour
	 */
	// private PodButtonCode type;
	
	/**
	 * Text label for drawing text upon the button
	 */
	private JLabel label = new JLabel();
	
	/**
	 * Core panel holding the button itself and its text label
	 */
	private JPanel panel = new JPanel();
	
	/**
	 * The button itself
	 */
	private ControlButtonTypeImpl button;

	/**
	 * 
	 * Constructor
	 * 
	 * @param _imageLoader Image loader
	 */
	public ControlButton(final IImageLoader _imageLoader, final ControlButtonType _type) {
		this.mImageLoader = _imageLoader;
		button = new ControlButtonTypeImpl();

		panel.setOpaque(false); // true);
		// panel.setBackground(Color.magenta);
		
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		label.setBorder(null);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("NI");
		label.setFocusable(false);
		label.setFont(PodkidnoyFonts.CONTROL);
		label.setForeground(PodkidnoyColors.CONTROL_FG);
		label.setOpaque(false);

		panel.add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(PodkidnoyLocations.CONTROL_TEXT, PodkidnoySizes.CONTROL_TEXT));

		button.setIcon(mImageLoader.fetchImage(PodkidnoyImages.CONTROL_BUTTON));
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setBorderPainted(false);
		button.setPressedIcon(mImageLoader.fetchImage(PodkidnoyImages.CONTROL_BUTTON));
		button.setRolloverIcon(mImageLoader.fetchImage(PodkidnoyImages.CONTROL_BUTTON_HOVER));
		button.setDisabledIcon(mImageLoader.fetchImage(PodkidnoyImages.CONTROL_BUTTON_DIS));
		button.setDisabledSelectedIcon(mImageLoader.fetchImage(PodkidnoyImages.CONTROL_BUTTON_DIS));
		
		button.addChangeListener(new TextColorChanger(label, PodkidnoyColors.CONTROL_FG, PodkidnoyColors.CONTROL_FG_DISABLED));
		
		/*    final JButton ok = new JButton("ok");

    JCheckBox cb = new JCheckBox("Enabled", true);

    ok.setBounds(40, 30, 80, 25);
    ok.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        DefaultButtonModel model = (DefaultButtonModel) ok.getModel();
        if (model.isEnabled())
          System.out.println("Enabled: true");
        else
          System.out.println("Enabled: false");

        if (model.isArmed())
          System.out.println("Armed: true");
        else
          System.out.println("Armed: false");

        if (model.isPressed())
          System.out.println("Pressed: true");
        else
          System.out.println("Pressed: false");
      }

    });

    cb.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        if (ok.isEnabled())
          ok.setEnabled(false);
        else
          ok.setEnabled(true);
      }
    });*/

		Point location = new Point(0, 0);
		panel.add(button, new org.netbeans.lib.awtextra.AbsoluteConstraints(location, PodkidnoySizes.CONTROL_BUTTON));
	
		setType(_type);}
	
	public JToggleButton getButton() {
		return button;
	}
	
	public void setName(String name) {
		button.setName(name);
	}

	private void setButtonText(ControlButtonType type) {
		label.setText(type.name());
	}

	/**
	 * 
	 * Retrieves the core panel where the button and its text label are drawn
	 * 
	 * @return
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * 
	 * Sets button's state
	 * 
	 * @param b Set true if enable, false otherwise
	 */
	public void setEnabled(boolean b) {
		button.setEnabled(b);
	}

	/**
	 * 
	 * Sets button's visibility
	 * 
	 * @param b Set true to make visible and false to hide
	 */
	public void setVisible(boolean b) {
		panel.setVisible(b);
		label.setVisible(b);
		button.setVisible(b);
	}

	/**
	 * 
	 * Adds action listener to this button
	 * 
	 * @param _al Action listener
	 */
	public void addActionListener(ActionListener _al) {
		button.addActionListener(_al);
	}

	/**
	 * 
	 * Retrieves the button's type
	 * 
	 * @return Button type
	 */
	public ControlButtonType getType() {
		return button.getType(); // type;
	}

	/**
	 * 
	 * Sets the button's type; determines text for button and subsequent game behavior
	 * 
	 * @param _type Button type
	 */
	public void setType(final ControlButtonType _type) {
		if (_type == null) {
			throw new NullPointerException(); // "Type is null");
		}
		// this.type = _type;
		button.setType(_type);
		setButtonText(_type);
	}

	@Override
	public String toString() {
		return label.getText();
	}

	/**
	 * 
	 * Visibility of this action button; returns true if at least one of the following is visible:
	 * 1) core panel; 2) text label; 3) button
	 * 
	 * @return True if button is visible, false otherwise
	 */
	public boolean isVisible() {
		boolean ret = panel.isVisible() || label.isVisible() || button.isVisible();
		return ret;
	}

	/**
	 * 
	 * Checks if this action button is in enabled state
	 * 
	 * @return True if enabled, false otherwise
	 */
	public boolean isEnabled() {
		boolean ret = button.isEnabled();
		return ret;
	}

	/**
	 * Programmatically clicks this action button
	 */
	public void doClick() {
		button.doClick();
	}

}
