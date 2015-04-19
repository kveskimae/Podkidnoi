/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.lobby;

import java.util.Map;

import com.littlech.gen.g.G13;
import com.littlech.gen.g.G17;
import com.littlech.gen.g.G9;
import com.littlech.gs.game.GameFactory;
import com.littlech.gs.game.pod.ServerPodConfigType;
import com.littlech.gs.game.pod.task.ServerLobbyTable;

public class DefaultTablesCreator {
	
	public static void addDefaultTables(final Lobby lobby) {
		Map<String, ServerLobbyTable> tables = lobby.getTables();
		synchronized (tables) {
			int counter = 0;
			for (int cycles = 0; cycles < 10; cycles++) {
				for (int i = 2; i <= 4; i++) {
					for (int j = 0; j < 4; j++) {
						/* Podkidnoy-specific conf */
						ServerPodConfigType pConf = new ServerPodConfigType();
						pConf.setG25(G9.G_11);
						pConf.setG22(i);
						boolean sending = j % 2 == 0;
						pConf.setG23(sending);
						int turnC = j < 2 ? 1 : 6;
						pConf.setG24(turnC);

						/* General game conf */
						G17 bigConf = new G17();
						bigConf.setG19(pConf);
						bigConf.setG18(G13.G_14);
						
						/* Game instance */
						int curId = lobby.getNewTableID();
						ServerLobbyTable table = GameFactory.createTable(""
								+ curId, "Podkidnoy " + curId, true,
								bigConf, false);
						String id = table.getTable().getG31();
						tables.put(id, table);
						counter++;
					}
				}
			}
			System.out.println("Created " + counter + " default tables");
		}
	}

}
