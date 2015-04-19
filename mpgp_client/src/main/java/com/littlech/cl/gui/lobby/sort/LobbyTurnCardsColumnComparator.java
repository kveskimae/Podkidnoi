/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.sort;

import java.util.Comparator;

import com.littlech.gen.g.G21;
import com.littlech.gen.g.G30;

public class LobbyTurnCardsColumnComparator implements Comparator<G30> {
	
	private static LobbyTurnCardsColumnComparator instance = new LobbyTurnCardsColumnComparator();

	public static LobbyTurnCardsColumnComparator getInstance() {
		return instance;
	}
	
	@Override
	public int compare(G30 o1, G30 o2) {
		G21 conf1 = (G21)o1.getG33();
		int turn1 = conf1.getG24();
		G21 conf2 = (G21)o2.getG33();
		int turn2 = conf2.getG24();
		return turn1 - turn2;
	}

}
