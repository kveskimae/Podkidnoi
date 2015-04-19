/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.lobby.task;

import static com.littlech.gs.gate.Gateway.logCommunication;
import static com.littlech.gs.gate.Gateway.logLobby;

import com.littlech.gs.lobby.Lobby;
import com.littlech.gs.user.ServerUser;

public class RegisterAndSendAllTask implements Runnable {
	
private Lobby lobby;
private ServerUser u;
private String all;

public RegisterAndSendAllTask(final Lobby lobby, final ServerUser u, final String all) {
	this.lobby = lobby;
	this.u = u;
	this.all = all;
}

@Override
public void run() {
	try {
		u.writeString(all);
		u.setSendLobbyUpdates(true);
	} catch (Exception e) {
		if (logLobby.isDebugEnabled()) {
			logCommunication.debug("Could not send all command to user '"
					+ u.getUsername() + "'", e);
		}
	}
}

}
