/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.lobby.task;

import static com.littlech.gs.gate.Gateway.logLobby;

import java.util.Map;

import com.littlech.gs.game.pod.task.ServerLobbyTable;
import com.littlech.gs.lobby.Lobby;

public class DeleteTask implements Runnable {
	
	private Lobby lobby;
	private String id;

	public DeleteTask(final Lobby lobby, String id) {
		this.lobby = lobby;
		this.id = id;
	}

	@Override
	public void run() {
		Map<String, ServerLobbyTable> tables = lobby.getTables();
		synchronized (tables) {
			if (tables.containsKey(id)) {
				tables.remove(id);
				if (logLobby.isDebugEnabled()) {
					logLobby.debug("Table " + id + " deleted");
				}
			} else {
				if (logLobby.isDebugEnabled()) {
					logLobby.debug("Tables do not contain " + id);
				}
			}
		}
	}

}
