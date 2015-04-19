/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.lobby.task;

import java.util.Map;

import com.littlech.gs.game.pod.task.ServerLobbyTable;
import com.littlech.gs.lobby.Lobby;

public class AddTask implements Runnable {

private Lobby lobby;
private String id;
private ServerLobbyTable table;

public AddTask(final Lobby lobby, String id, ServerLobbyTable table) {
	this.lobby = lobby;
	this.id = id;
	this.table = table;
}

@Override
public void run() {
	Map<String, ServerLobbyTable> tables = lobby.getTables();
	synchronized (tables) {
		tables.put(id, table);
	}
}
}