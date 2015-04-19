/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.login;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.Locations;
import com.littlech.cl.constants.Names;
import com.littlech.cl.constants.Sizes;
import com.littlech.cl.constants.Texts;
import com.littlech.cl.gui.login.button.CancelButton;
import com.littlech.cl.gui.login.constants.*;
import com.littlech.cl.gui.login.loadingbar.LoadingBar;

/**
 * 
 * Login screen
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class LoginScreen {

	/**
	 * Image controller
	 */
	protected IImageLoader mImageLoader;

	/**
	 * Base container where components are added
	 */
	protected JPanel panel = new JPanel();

	/**
	 * Label for presenting informative messages
	 */
	private JLabel loginText = new JLabel();

	private AbsoluteConstraints
	// Login text
	loginTextConstraint = new AbsoluteConstraints(LoginLocations.LOGIN_TEXT,
			LoginSizes.LOGIN_TEXT),
			// Background image
			bgLabelConstraint = new AbsoluteConstraints(Locations.UPPER_LEFT,
					Sizes.APPLET),
			cancelButtonConstraint = new AbsoluteConstraints(
					LoginLocations.CANCEL, LoginSizes.CANCEL);

	private JLabel bgLabel;

	private CancelButton cancel;

	private LoadingBar loading;


	/**
	 * Constructor
	 * 
	 * @param imageLoader
	 *            Image loader
	 */
	public LoginScreen(IImageLoader imageLoader) {
		mImageLoader = imageLoader;
		panel.setBackground(Color.GRAY);
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
		panel.setSize(Sizes.APPLET);
		initComponents();
	}

	/**
	 * Returns panel where components are added
	 * 
	 * @return Base container
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Shows parameter text on message label
	 * 
	 * @param text
	 *            Text to show
	 */
	public void setText(String text) {
		setText(text, LoginColors.INFO);
	}

	/**
	 * Shows parameter text on message label
	 * 
	 * @param text
	 *            Text to show
	 */
	public void setText(String text, Color col) {
		loginText.setForeground(col);
		loginText.setText(text);
	}

	/**
	 * Initializes screen components
	 */
	private void initComponents() {
		loginText.setFont(LoginFonts.LOGIN_TEXT);
		loginText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		// loginText.setText(Texts.LOADING);
		setText(Texts.LOADING);
		loginText.setName(Names.LOGIN_TEXT);
		ImageIcon bg = mImageLoader.fetchImage(LoginImages.LOGIN_BG);
		bgLabel = new JLabel(bg);

		cancel = new CancelButton(mImageLoader);
		cancel.setText(Texts.CANCEL);
		
		loading = new LoadingBar(mImageLoader);
	}

	public void finishLayout() {
		panel.add(loginText, loginTextConstraint);
		panel.add(loading, loading.getConstraint());
		panel.add(cancel.getPanel(), cancelButtonConstraint);
		panel.add(bgLabel, bgLabelConstraint);
	}
	
	/**
	 * Returns loading bar
	 * @return Loading bar
	 */
	public LoadingBar getLoading() {
		return loading;
	}
	
	/**
	 * Returns cancel button
	 * @return Cancel button
	 */
	public CancelButton getCancel() {
		return cancel;
	}

}
