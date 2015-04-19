/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.sort;

import java.util.Comparator;
import java.util.List;

import com.littlech.gen.f.F23;
import com.littlech.gen.g.G27;
import com.littlech.gen.g.G30;

public class LobbyPlayersColumnComparator implements Comparator<G30> {
	
	private static LobbyPlayersColumnComparator instance = new LobbyPlayersColumnComparator();
	
	public static LobbyPlayersColumnComparator getInstance() {
		return instance;
	}

	@Override
	public int compare(G30 arg0, G30 arg1) {
		G27 state1 = arg0.getG34();
		List<F23> seats1 = state1.getG29();
		int size1 = seats1.size();

		G27 state2 = arg1.getG34();
		List<F23> seats2 = state2.getG29();
		int size2 = seats2.size();
		return size1 - size2;
	}

	/*
	@Override
	public int compare(List<User> arg0, List<User> arg1) {
		int size1 = arg0.size();
		int size2 = arg1.size();
		return size1 - size2;
	}
*/
}
