/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby;

import com.littlech.cl.gui.lobby.util.CreateState;
import com.littlech.cl.gui.podkidnoy.util.ObservableValue;

/**
 * 
 * Model for Lobby GUI
 * 
 * @author Kristjan Veskim�e
 * 
 */
public class LobbyModel {

	
	private ObservableValue<CreateState> state = new ObservableValue<CreateState>();
	
	public ObservableValue<CreateState> getState() {
		return state;
	}
	
}
