/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl;

import java.awt.CardLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import com.littlech.cl.connect.ICSHandler;
import com.littlech.cl.connect.ISCHandler;
import com.littlech.cl.gui.IInfoPresenter;
import com.littlech.cl.gui.IScreenController;
import com.littlech.cl.gui.ScreenControllerType;
import com.littlech.cl.gui.lobby.LobbyController;
import com.littlech.cl.gui.login.LoginController;
import com.littlech.cl.gui.podkidnoy.PodkidnoyController;
import com.littlech.gen.c.C1;
import com.littlech.gen.d.D4;
import com.littlech.gen.d.D8;
import com.littlech.gen.e.E11;
import com.littlech.gen.e.E19;

/**
 * 
 * Client application controller
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class Controller implements ICSHandler, ISCHandler {

	/**
	 * Currently represented screen's controller
	 */
	private IScreenController current;

	/**
	 * Login screen controller
	 */
	private IScreenController login;

	/**
	 * Lobby screen controller
	 */
	private IScreenController lobby;

	/**
	 * Podkidnoy screen controller
	 */
	private PodkidnoyController podkidnoy;

	/**
	 * Image loader
	 */
	private IImageLoader mIL;

	/**
	 * Applet content pane
	 */
	private Container mPanel;

	/**
	 * Card layout for switching between screens
	 */
	private CardLayout cl;

	/**
	 * Client to server commands handling decorator
	 */
	private List<ICSHandler> mCSHandler = new ArrayList<ICSHandler>();
	
	/**
	 * Info presenter
	 */
	private IInfoPresenter infoPresenter;

	/**
	 * Test input field
	 */
	// private ITestInput testInput;
/*
	public Controller(final IImageLoader _imageLoader, final Container _applectContentPane) {
		this(_imageLoader, _applectContentPane); //, false);
	}
*/
	public Controller(final IImageLoader _imageLoader, final Container _applectContentPane) { // , final boolean test) { // {
		mIL = _imageLoader;
		mPanel = _applectContentPane;
		// mCSHandler = _csHandler;
		
		LoginController login1 = new LoginController(this);
		infoPresenter = login1;
		login = login1;
		
		// LobbyController lobby1 = new LobbyController(this);
		lobby = new LobbyController(this); // lobby1;
		
		podkidnoy = new PodkidnoyController(this);

		cl = new CardLayout();
		mPanel.setLayout(cl);
		mPanel.add(login.getPanel(), ScreenControllerType.LOGIN.name());
		mPanel.add(podkidnoy.getPanel(), ScreenControllerType.PODKIDNOY.name());
		mPanel.add(lobby.getPanel(), ScreenControllerType.LOBBY.name());
		
		/* Anything can be added to top here */
		
		podkidnoy.finishLayout();
		login.finishLayout();
		lobby.finishLayout();
		
		setScreenController(ScreenControllerType.LOGIN);
	}

	/**
	 * Sets client to server commands handler decorator for this controller
	 * 
	 * @param _csHandler
	 *          Client to server commands handler where commands are forwarded
	 */
	public void setCSHandler(final ICSHandler _csHandler) {
		// this.mCSHandler = _csHandler;
		mCSHandler.add(_csHandler);
	}

	/**
	 * Retrieves image loader
	 * 
	 * @return Image loader
	 */
	public IImageLoader getImageLoader() {
		return mIL;
	}
	
	/**
	 * Returns currently presented screen's type 
	 * @return Screen type or null if no screen is presented
	 */
	public ScreenControllerType getScreenController() {
		if (current != null) {
			return current.getType();
		}
		return null;
	}

	/**
	 * Starts presenting parameter screen
	 * 
	 * @param type
	 *          Screen type
	 */
	public void setScreenController(final ScreenControllerType type) {
		if (type == null) {
			throw new NullPointerException(); // "Type is null");
		}
		// System.out.println("--> " + type);
		if (current != null) {
			if (current.getType().equals(type)) {
				throw new IllegalArgumentException(); // "Already showing " + type);
			} else {
				current.stopShow();
			}
		}
		switch (type) {
		case PODKIDNOY:
			current = podkidnoy;
			enableSitButtons(true);
			break;
		case LOBBY:
			current = lobby;
			break;
		case LOGIN:
			current = login;
			break;
		default:
			throw new IllegalArgumentException(); // "Unknown screen controller type: " + type);
		}
		current.startShow();
		cl.show(mPanel, type.name());
	}

	public void addDialog(final String _msg) {
		JOptionPane.showMessageDialog(mPanel, _msg);
	}

	/**
	 * Handles command received from server
	 * 
	 * @param cmd
	 *          Command from server to client
	 */
	public void handleSC(final E19 cmd) {
		switch (cmd.getC13()) {
		case C_4:
		case C_5:
			lobby.handleSC(cmd);
			break;
		case C_8:
			E11 ec3 = cmd.getE21();
			switch (ec3) {
			case E_12:
				// setScreenController(ScreenControllerType.LOBBY); 
				// lobby is already set, if user has joined a game again, may cause an error
				break;
			case E_13:
				addDialog(cmd.getE22());
				break;
			default:
				throw new IllegalArgumentException(); // "Unknown error code: " + ec3);
			}
			break;
		case C_7:
			E11 ec = cmd.getE21();
			switch (ec) {
			case E_12:
				// Ignore, already disabled
				setScreenController(ScreenControllerType.PODKIDNOY);
				break;
			case E_13:
				if (!lobby.handleSC(cmd)) {
					System.out.println("L dnh. " + cmd.getC13());;
				}
				break;
			default:
				throw new IllegalArgumentException(); // "Unknown error code: " + ec);
			}
			break;
		case C_2:
			E11 ec2 = cmd.getE21();
			switch (ec2) {
			case E_12:
				// Ignore, login has nothing to disable
				podkidnoy.handleSC(cmd); // sets self
				setScreenController(ScreenControllerType.LOBBY);
				
				// Requesting all
				D4 allRequest = new D4();
				allRequest.setC13(C1.C_4);
				allRequest.setD5(new D8());
				sendCS(allRequest);
				break;
			case E_13:
				// Already in login screen
				String errMsg = cmd.getE22();
				if (errMsg != null) {
				presentFatalError(errMsg);
				} else {
					presentFatalError("Login failed");
				}
				break;
			default:
				throw new IllegalArgumentException(); // "Unknown error code: " + ec2);
			}
			break;
		default:
			boolean handled = false;
			try {
				handled = current.handleSC(cmd);
			if (!handled) {
				System.err.println("" + current.getType() + " dnh. " + cmd.getC13());
			}
			} catch (Exception e) {
				// System.err.println("" + current.getType() + " got exception while handling " + cmd.getC13());
				e.printStackTrace();
			}
			break;
		}
	}
	
	public void presentInfo(final String _msg) {
		if (getScreenController() == null || !getScreenController().equals(ScreenControllerType.LOGIN)) {
			setScreenController(ScreenControllerType.LOGIN);
		}
		infoPresenter.info(_msg);
	}
	
	public void presentFatalError(final String _msg) {
		if (getScreenController() == null || !getScreenController().equals(ScreenControllerType.LOGIN)) {
			setScreenController(ScreenControllerType.LOGIN);
		}
		infoPresenter.fatal(_msg);
	}

	/**
	 * Sends command from client to server
	 * 
	 * @param cs
	 *          Command from client to server
	 */
	@Override
	public void sendCS(final D4 cs) {
		for (ICSHandler handler: mCSHandler) {
			// System.out.println("-1-");
		handler.sendCS(cs);
		}
	}
	
	public void resetCurrentScreen() {
		current.reset();
	}

	public IScreenController getLogin() {
		return login;
	}

	public IScreenController getLobby() {
		return lobby;
	}

	public IScreenController getPodkidnoy() {
		return podkidnoy;
	}
	
private PodkidnoyController getPodkidnoyController() {
	return podkidnoy;
}	

	public void enableLobby() {
		getLobby().enable();
	}

	public void enableSitButtons(boolean b) {
		getPodkidnoyController().getScreen().setEnabledToSitButtons(true);
	}
	
	

	/**
	 * Returns test input for client application
	 * 
	 * @return Test input
	 */
	/*
	public ITestInput getTest() {
		return testInput;
	}
	*/

}
