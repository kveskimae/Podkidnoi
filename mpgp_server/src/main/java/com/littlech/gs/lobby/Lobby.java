/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.lobby;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.littlech.gen.d.D4;
import com.littlech.gs.GameServer;
import com.littlech.gs.game.pod.task.ServerLobbyTable;
import com.littlech.gs.lobby.task.*;
import com.littlech.gs.user.ServerUser;

/**
 * 
 * Game server lobby
 * <br />
 * Use as a table store
 * 
 * @author veskikri
 *
 */
public class Lobby {

	/** The table id-s sequence */
	private AtomicInteger tableID = new AtomicInteger(0);

	/** The lobby tables */
	private Map<String, ServerLobbyTable> tables = new HashMap<String, ServerLobbyTable>();

	/**
	 * All lobby state command sent to newly logged in players
	 */
	private String all;

	/**
	 * Instantiates a new lobby.
	 */
	public Lobby() {
		DefaultTablesCreator.addDefaultTables(this);
	}

	/**
	 * Updates the lobby all state command that is sent to newly logged in players
	 * @param newAll New and already marshaled value
	 */
	public void setNewAll(final String newAll) {
		this.all = newAll;
	}

	/**
	 * Retrieves the next table id from the id sequence
	 * @return New table id
	 */
	public int getNewTableID() {
		return tableID.incrementAndGet();
	}

	/**
	 * Retrieves the tables.
	 * 
	 * @return List of current lobby tables
	 */
	public Map<String, ServerLobbyTable> getTables() {
		return this.tables;
	}

	/**
	 * Starts periodically updating lobby all state command and sending lobby updates
	 * <br />
	 * Call after the lobby has been initialized
	 */
	public void start() {
		ScheduledExecutorService service = GameServer.getInstance()
				.getService();
		int cycle = 30;
		Runnable allAndUpdateTask = new AllAndUpdateTask(this);
		service.scheduleAtFixedRate(allAndUpdateTask, 1, cycle,
				TimeUnit.SECONDS);
	}

	/**
	 * Tries to create a new table or writes failure command to user
	 * @param u User requesting to create a new table
	 * @param cmd Create table command
	 */
	public void executeNewTable(ServerUser u, D4 cmd) {
		NewTableTask newTask = new NewTableTask(u, cmd, this);
		newTask.run();
	}

	/**
	 * Registers a new player in lobby and sends him current all lobby state 
	 * @param u User who has just connected
	 */
	public void executeRegisterAndSendAll(ServerUser u) {
		RegisterAndSendAllTask registerAndSendAll = new RegisterAndSendAllTask(this, u, all);
		registerAndSendAll.run();
	}

	/**
	 * Deletes parameter table from lobby
	 * @param id Table id to delete
	 */
	public void executeDelete(String id) {
		DeleteTask dt = new DeleteTask(this, id);
		dt.run();
	}

	/**
	 * Adds parameter table to lobby
	 * @param id Table id
	 * @param table Table
	 */
	public void executeAddTable(String id, ServerLobbyTable table) {
		AddTask addTask = new AddTask(this, id, table);
		addTask.run();
	}

}
