/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.netbeans.lib.awtextra.AbsoluteConstraints;

import com.littlech.cl.IImageLoader;
import com.littlech.cl.constants.Names;
import com.littlech.cl.constants.Sizes;
import com.littlech.cl.gui.lobby.create.CreateTab;
import com.littlech.cl.gui.lobby.podkidnoy.JoinButton;
import com.littlech.cl.gui.lobby.podkidnoy.PodkidnoyTab;
import com.littlech.cl.gui.lobby.podkidnoy.handler.SelectionListener;
import com.littlech.cl.gui.lobby.tabs.LobbyTabbedPane;
import com.littlech.cl.gui.lobby.tabs.handler.TabsHandler;
import com.littlech.cl.gui.lobby.util.CreateState;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;
import com.littlech.cl.gui.lobby.constants.*;

/**
 * 
 * Screen for Lobby GUI
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class LobbyScreen implements Observer {

	/**
	 * Image loader
	 */
	private IImageLoader mImageLoader;

	/**
	 * Base panel where components are added
	 */
	private JPanel panel = new JPanel();

	private LobbyTabbedPane tabs;

	private PodkidnoyTab podkidnoyTab;

	private CreateTab createTab;

	/**
	 * Join button
	 */
	private JoinButton joinButton;

	/**
	 * Join button enabler for table row selection
	 */
	private SelectionListener joinButtonEnabler;

	private JLabel copyRightLabel;
	
	private AbsoluteConstraints copyRightConstraint = new AbsoluteConstraints(LobbyLocations.COPYRIGHT, LobbySizes.COPYRIGHT);
	

	/**
	 * 
	 * Constructor
	 * 
	 * @param imageLoader
	 *          Image loader
	 */
	public LobbyScreen(final IImageLoader imageLoader) {
		mImageLoader = imageLoader;
		panel.setBackground(LobbyColors.LOBBY_BG);
		panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
		panel.setSize(Sizes.APPLET);

		/* Join button */
		joinButton = new JoinButton(mImageLoader);
		joinButton.setEnabled(false);

		initUI();
		tabs = new LobbyTabbedPane(); // changes to UI apply now
		tabs.setName(Names.LOBBY_TABS);
		
		podkidnoyTab = new PodkidnoyTab(mImageLoader);
		createTab = new CreateTab(mImageLoader);


		tabs.addTab(podkidnoyTab.getName(), podkidnoyTab.getPanel());
		// tabs.addTab(createTab.getName(), createTab.getPanel());

		joinButtonEnabler = new SelectionListener(podkidnoyTab.getTable(), joinButton);
		podkidnoyTab.getTable().getSelectionModel().addListSelectionListener(joinButtonEnabler);

		tabs.addChangeListener(new TabsHandler(this));
		

		ImageIcon copyRightIcon = mImageLoader.fetchImage(LobbyImages.COPYRIGHT);
		copyRightLabel = new JLabel(copyRightIcon);
		/*
		 * podkidnoyTab.getTable().getColumnModel().getSelectionModel().addListSelectionListener
		 * (joinButtonEnabler); don't know if this is necessary
		 */
	}
	
	public void finishLayout() {
		panel.add(joinButton.getPanel(), joinButton.getConstraint());
		panel.add(tabs, tabs.getConstraint());

		panel.add(copyRightLabel, copyRightConstraint);
	}

	public void setSelectedTab(final String _tabID) {
		if (_tabID == null) {
			throw new NullPointerException(); // "Parameter tab ID is null");
		}
		if (LobbyController.TAB_CREATE.equals(_tabID)) {
			tabs.setSelectedComponent(createTab.getPanel());
		} else if (LobbyController.TAB_PODKIDNOY.equals(_tabID)) {
			tabs.setSelectedComponent(podkidnoyTab.getPanel());
		} else {
			throw new IllegalArgumentException("Unsupported: " + _tabID);
		}
	}

	public String getSelectedTab() {
		if (createTab.getPanel() == tabs.getSelectedComponent()) {
			return LobbyController.TAB_CREATE;
		} else if (podkidnoyTab.getPanel() == tabs.getSelectedComponent()) {
			return LobbyController.TAB_PODKIDNOY;
		} else if (tabs.getSelectedIndex() == -1) {
			return null;
		}
		throw new IllegalStateException("Unknown: " + tabs.getSelectedComponent().getClass().getCanonicalName());
	}

	/**
	 * Returns join button
	 * 
	 * @return Button
	 */
	public JoinButton getJoinButton() {
		return joinButton;
	}

	public PodkidnoyTab getPodkidnoyTab() {
		return podkidnoyTab;
	}

	private void initUI() {
		/* Selected tab is white and unselected tab is bluish */
		UIManager.put("TabbedPane.selected", Color.white);
		UIManager.put("TabbedPane.background", LobbyColors.LOBBY_BLUE);
	}

	/**
	 * Returns base panel where components are placed
	 * 
	 * @return Base panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Enables or disables game components
	 * 
	 * @param b
	 *          Set true to enable screen or false to disable
	 */
	public void setEnabled(boolean b) {
	}

	/**
	 * Shows or hides screen components
	 * 
	 * @param b
	 *          Set true to make screen components visible or false to hide screen
	 *          components
	 */
	public void setVisible(boolean b) {
	}

	public CreateTab getCreateTab() {
		return createTab;
	}

	public LobbyTabbedPane getTabs() {
		return tabs;
	}

	public void removeTab(final String _type) {
		if (LobbyController.TAB_CREATE.equals(_type)) {
			tabs.remove(getCreateTab().getPanel());
		} else if (LobbyController.TAB_PODKIDNOY.equals(_type)) {
			tabs.remove(getPodkidnoyTab().getPanel());
		} else {
			throw new IllegalArgumentException("Unknown: " + _type);
		}
	}

	@Override
	public void update(Observable arg0, Object o) {
		if (arg0 instanceof ObservableValue<?>) {
			ObservableValue ov = (ObservableValue) arg0;
			Object o1 = ov.getValue();
			if (o1 instanceof CreateState) {
				CreateState createState = (CreateState) o1;
				boolean b;
				switch (createState) {
				case IDLE:
					b = true;
					break;
				case WAITING_RESPONSE:
					b = false;
					break;
				default:
					throw new IllegalArgumentException("Unsupported: " + createState);

				}
				tabs.setEnabled(b);
				if (getPodkidnoyTab().getTable().getSelectedRow() >= 0) {
					getJoinButton().setEnabled(b);
				}
				getCreateTab().setEnabled(b);
				getPodkidnoyTab().setEnabled(b);
				// XXX ? create caret event
				// getCreateTab().getCreateButton().setEnabled(b);
				// getCreateTab().getTableName().getCaret()
				getCreateTab().getTableName().fireCaretChange();
			} else {
				throw new IllegalArgumentException(o.getClass().getCanonicalName());
			}
		} else {
			// Listening to wrong class
			throw new IllegalStateException(this.getClass().getCanonicalName() + " to " + arg0.getClass().getCanonicalName());
		}

	}

}
