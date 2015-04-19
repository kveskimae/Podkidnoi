/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.lobby.task;

import static com.littlech.gs.gate.Gateway.logLobby;

import com.littlech.gen.c.*;
import com.littlech.gen.d.*;
import com.littlech.gen.e.*;
import com.littlech.gen.g.*;
import com.littlech.gs.game.GameFactory;
import com.littlech.gs.game.RulesCheckResult;
import com.littlech.gs.game.pod.ServerPodConfigType;
import com.littlech.gs.game.pod.task.ServerLobbyTable;
import com.littlech.gs.lobby.Lobby;
import com.littlech.gs.user.ServerUser;

/**
 * 
 * Tries to create a new table in lobby by user
 * 
 * @author veskikri
 * 
 */
public class NewTableTask implements Runnable {

	private ServerUser u;
	private D4 cmd;
	private Lobby lobby;

	/**
	 * 
	 * Construct new create table task
	 * 
	 * @param u
	 *            The initial creator
	 * @param cmd
	 *            Create command
	 * @param lobby
	 *            Lobby reference
	 */
	public NewTableTask(ServerUser u, D4 cmd, Lobby lobby) {
		this.u = u;
		this.cmd = cmd;
		this.lobby = lobby;
	}

	/**
	 * Executes the task
	 */
	@Override
	public void run() {
		try {
			D12 createContent = (D12) cmd.getD5();
			G17 conf = createContent.getD13();
			RulesCheckResult approval = getCreationApproval(conf);
			if (approval.isAllowed()) {
				String id = "" + lobby.getNewTableID();
				if (G13.G_14.equals(conf.getG18())) {
					/* Adding some extra information to Podkidnoy configuration */
					G21 podConf = (G21) conf.getG19();
					ServerPodConfigType serverConf = new ServerPodConfigType();
					serverConf.setG25(podConf.getG25());
					serverConf.setG22(podConf.getG22());
					serverConf.setG23(podConf.isG23());
					serverConf.setG24(podConf.getG24());
					conf.setG19(serverConf);
				}
				ServerLobbyTable table = GameFactory.createTable(id,
						createContent.getD14(), false, conf, false);

				/* Move to add creator? */
				E19 answer = new E19();
				answer.setC13(C1.C_6);
				answer.setE21(E11.E_12);
				E25 cont = new E25();
				cont.setE26(G4.G_5);
				answer.setE20(cont);
				u.write(answer);

				table.getGame().getPlayerStore().addCreator(u);
				lobby.executeAddTable(id, table);

				if (logLobby.isDebugEnabled()) {
					logLobby.debug("Lobby.executeCreate New table created "
							+ table);
				}
			} else {
				E19 answer = new E19();
				answer.setC13(C1.C_6);
				answer.setE21(E11.E_13);
				answer.setE22("Creating new table failed. "
						+ approval.getReason());
				u.write(answer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				E19 answer = new E19();
				answer.setC13(C1.C_6);
				answer.setE21(E11.E_13);
				answer.setE22("An error occured in server.");
				u.write(answer);
			} catch (Exception e1) {
				logLobby.error(
						"Lobby.executeCreate Cannot write create failure", e1);
			}
		}
	}

	private RulesCheckResult getCreationApproval(G17 conf) {
		RulesCheckResult ret = new RulesCheckResult(true);
		return ret;
	}

}
