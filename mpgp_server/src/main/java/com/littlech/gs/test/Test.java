/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


import com.littlech.gen.g.G13;
import com.littlech.gen.g.G17;
import com.littlech.gen.g.G9;
import com.littlech.gs.game.GameFactory;
import com.littlech.gs.game.pod.Podkidnoy;
import com.littlech.gs.game.pod.ServerPodConfigType;
import com.littlech.gs.game.pod.task.ServerLobbyTable;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 */
public class Test {

	private static final int THOUSAND = 1000, TEST_ROUNDS = 10 * THOUSAND;

	/** The SERVIC e_ threads. */
	public static int SERVICE_THREADS = 2;
	
	/** The service. */
	private static ScheduledExecutorService service = Executors.newScheduledThreadPool(SERVICE_THREADS);
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// System.out.println("-4-");
		G17 gameConf = getGameConfForPod(G9.G_11, 3, true, 6);
		ServerLobbyTable pGame = GameFactory.createTable("TesT", "Test table", false, gameConf, true);
		// ((Podkidnoy)pGame.getGame()).driveNewTestRound();
		((Podkidnoy)pGame.getGame()).driveTestGames(TEST_ROUNDS);
	}

	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	public static ScheduledExecutorService getService() {
		return service ;
	}


	/**
	 * Gets the game conf for pod.
	 *
	 * @param _pack the _pack
	 * @param _numOfPlayers the _num of players
	 * @param _sending the _sending
	 * @param _turnCards the _turn cards
	 * @return the game conf for pod
	 */
	public static G17 getGameConfForPod(final G9 _pack,
			final int _numOfPlayers, final boolean _sending,
			final int _turnCards) {
		G17 ret = new G17();
		ret.setG18(G13.G_14);
		ServerPodConfigType podConf = new ServerPodConfigType();
		podConf.setG25(_pack);
		podConf.setG22(_numOfPlayers);
		podConf.setG23(_sending);
		podConf.setG24(_turnCards);
		ret.setG19(podConf);
		return ret;
	}

}
