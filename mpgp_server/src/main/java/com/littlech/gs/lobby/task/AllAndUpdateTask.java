/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.lobby.task;

import static com.littlech.gs.gate.Gateway.logLobby;

import java.util.*;
import java.util.Map.Entry;

import com.littlech.cl.Utils;
import com.littlech.gen.c.*;
import com.littlech.gen.e.*;
import com.littlech.gen.g.*;
import com.littlech.gs.GameServer;
import com.littlech.gs.communication.ServerMarshalerImpl;
import com.littlech.gs.game.pod.task.ServerLobbyTable;
import com.littlech.gs.lobby.Lobby;

public class AllAndUpdateTask implements Runnable {

	private Lobby lobby;

	/** The m last lobby. */
	private Map<String, G30> mLastLobby = null;

	/** The upd. */
	private String all, upd;
	
	public AllAndUpdateTask(Lobby lobby) {
		this.lobby = lobby;
	}

	@Override
	public void run() {

		String threadName = Thread.currentThread().getName();
		try {
			Thread.currentThread().setName("LobbyAllAndUpdate");
			execute();
			if (logLobby.isDebugEnabled()) {
			logLobby.debug("New all and update");
			}
		} catch (Exception e) {
			logLobby.error("Exception while updating lobby", e);
		} finally {
			Thread.currentThread().setName(threadName);
		}
	}
	
	private void execute() {
		ServerMarshalerImpl marshaler = null;
		try {
			marshaler = (ServerMarshalerImpl) GameServer.getInstance()
					.getMarshalersPool().borrowObject();
			Map<String, G30> currentLobby = new HashMap<String, G30>();
			List<G30> currentTablesList = new ArrayList<G30>();
			synchronized (lobby.getTables()) {
				Set<Entry<String, ServerLobbyTable>> entries = lobby
						.getTables().entrySet();
				for (Entry<String, ServerLobbyTable> entry : entries) {
					String id = entry.getKey();
					ServerLobbyTable table = entry.getValue().getCopy();
					currentLobby.put(id, table.getTable());
					currentTablesList.add(table.getTable());
				}
			}
			E19 cmdAll = new E19();
			cmdAll.setC13(C1.C_4);
			cmdAll.setE21(E11.E_12);
			E48 allContent = new E48();
			cmdAll.setE20(allContent);
			allContent.getE49().addAll(currentTablesList);
			List<String> deletedTables = new ArrayList<String>();
			List<G30> addedTables = new ArrayList<G30>();
			List<G36> diffTableStates = new ArrayList<G36>();
			if (mLastLobby != null) {
				for (Entry<String, G30> entry : mLastLobby.entrySet()) {
					String oldKey = entry.getKey();
					if (!currentLobby.containsKey(oldKey)) {
						/* DELETED TABLES */
						deletedTables.add(oldKey);
					} else {
						/* TABLE STATE AND SEAT CHANGES */
						G27 oldState = entry.getValue().getG34();
						G27 currentState = currentLobby.get(oldKey).getG34();
						G36 diff = Utils.compareTables(oldKey, oldState,
								currentState);
						if (diff != null) {
							if (logLobby.isDebugEnabled()) {
								logLobby.debug("diff=" + diff);
							}
							diffTableStates.add(diff);
						}

					}
				}

				/* ADDED TABLES */
				for (Entry<String, G30> entry : currentLobby.entrySet()) {
					String newKey = entry.getKey();
					if (!mLastLobby.containsKey(newKey)) {
						addedTables.add(entry.getValue());
					}
				}
				E19 cmdUpd = new E19();
				cmdUpd.setC13(C1.C_5);
				cmdUpd.setE21(E11.E_12);
				E50 updContent = new E50();
				cmdUpd.setE20(updContent);
				updContent.getE51().addAll(addedTables);
				updContent.getE52().addAll(deletedTables);
				updContent.getE53().addAll(diffTableStates);
				upd = marshaler.marshalSC(cmdUpd);
				SendUpdatesTask sendUpdatesTask = new SendUpdatesTask(upd);
				GameServer.getInstance().getService().submit(sendUpdatesTask);
			}
			all = marshaler.marshalSC(cmdAll);
			lobby.setNewAll(all);
			mLastLobby = currentLobby;
		} catch (Exception e) {
			logLobby.error("Error while creating all and uptade commands", e);
		} finally {
			if (marshaler != null) {
				try {
					GameServer.getInstance().getMarshalersPool()
							.returnObject(marshaler);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
