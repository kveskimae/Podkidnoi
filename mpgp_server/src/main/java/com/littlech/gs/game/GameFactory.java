/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;


import com.littlech.gen.g.G13;
import com.littlech.gen.g.G17;
import com.littlech.gs.game.pod.Podkidnoy;
import com.littlech.gs.game.pod.task.ServerLobbyTable;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Game objects.
 */
public class GameFactory {
	
	/**
	 * Creates a new Game object.
	 *
	 * @param _id the _id
	 * @param _name the _name
	 * @param _def the _def
	 * @param _configuration the _configuration
	 * @param _test the _test
	 * @return the server lobby table
	 */
	public static ServerLobbyTable createTable(final String _id, final String _name,
			final boolean _def, final G17 _configuration, boolean _test) {
		IGame game = createGame(_configuration, _id, _test);
		ServerLobbyTable ret = new ServerLobbyTable(_id, _name, _def, _configuration, game);
		return ret;
	}

	/**
	 * Creates a new Game object.
	 *
	 * @param _configuration the _configuration
	 * @param _id the _id
	 * @param _test the _test
	 * @return the i game
	 */
	private static IGame createGame(G17 _configuration, String _id, boolean _test) {
		G13 type = _configuration.getG18();
		switch (type) {
		case  G_14:
			return new Podkidnoy(_configuration, _id, _test);

		default:
			throw new IllegalArgumentException("Unsupported game type: "  + type);
		}
	}

}
