/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.lobby.task;

import static com.littlech.gs.gate.Gateway.logCommunication;
import static com.littlech.gs.gate.Gateway.logLobby;

import java.util.Iterator;
import com.littlech.gs.GameServer;
import com.littlech.gs.user.ServerUser;

public class SendUpdatesTask implements Runnable {
	
	private String updString;

	public SendUpdatesTask(final String updString) {
		this.updString = updString;
	}

	@Override
	public void run() {
		logCommunication.debug("Start of sending lobby updates");
		Iterator<ServerUser> toSend = GameServer.getInstance().getOnlineUsers().iterator();
		while (toSend.hasNext()) {
			ServerUser cur = toSend.next();
			// getLogger().debug("Sending updates to " + updString + " (send upates=" +cur.isSendLobbyUpdates()+ ")");
			if (cur.isSendLobbyUpdates()) {
			try {
				cur.writeString(updString);
			} catch (Exception e) {
				if (logLobby.isDebugEnabled()) {
				logCommunication.debug("Exception while writing lobby update to user " + cur, e);
				}
				cur.setSendLobbyUpdates(false);
			}
			}
		}

		if (logLobby.isDebugEnabled()) {
		logCommunication.debug("End of sending lobby updates");
		}
	}

}
