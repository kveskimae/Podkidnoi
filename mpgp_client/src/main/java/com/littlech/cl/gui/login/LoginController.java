/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.login;

import java.awt.Container;

import com.littlech.cl.Controller;
import com.littlech.cl.gui.IInfoPresenter;
import com.littlech.cl.gui.IScreenController;
import com.littlech.cl.gui.ScreenControllerType;
import com.littlech.cl.gui.login.constants.LoginColors;
import com.littlech.gen.e.E19;

/**
 * 
 * Controller for login screen
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class LoginController implements IScreenController, IInfoPresenter {

	/**
	 * Client application controller
	 */
	private final Controller mController;

	/**
	 * Login screen
	 */
	private LoginScreen screen;

	/**
	 * Constructor
	 * 
	 * @param _controller
	 *          Client application controller
	 */
	public LoginController(final Controller _controller) {
		mController = _controller;
		screen = new LoginScreen(mController.getImageLoader());
	}

	@Override
	public ScreenControllerType getType() {
		return ScreenControllerType.LOGIN;
	}
	
	@Override
	public void enable() {	
	}

	@Override
	public void startShow() {
		screen.getLoading().start();
	}

	@Override
	public void stopShow() {
		screen.getLoading().stop();
	}

	@Override
	public Container getPanel() {
		return screen.getPanel();
	}

	@Override
	public boolean handleSC(E19 cmd) {
		return false;
	}

	@Override
	public void info(String msg) {
		screen.setText(msg);
	}

	@Override
	public void fatal(String msg) {
		screen.getLoading().stop();
		screen.getLoading().setVisible(false);
		screen.getCancel().setEnabled(false);
		screen.setText(msg, LoginColors.ERROR);
	}

	@Override
	public void reset() {
		screen.setText("");
	}

	@Override
	public void finishLayout() {
	 screen.finishLayout();
	}

}
