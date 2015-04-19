/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.sort;

import java.util.Comparator;

import com.littlech.gen.g.G21;
import com.littlech.gen.g.G30;

public class LobbySendingColumnComparator implements Comparator<G30> {

	private static LobbySendingColumnComparator instance = new LobbySendingColumnComparator();

	@Override
	public int compare(G30 o1, G30 o2) {
		G21 conf1 = (G21)o1.getG33();
		boolean send1 = conf1.isG23();
		G21 conf2 = (G21)o2.getG33();
		boolean send2 = conf2.isG23();
		if (send1 && send2) {
			return 0;
		}
		if (!send1 && !send2) {
			return 0;
		}
		if (send1) {
			return 1;
		}
		return -1;
	}

	public static LobbySendingColumnComparator getInstance() {
		return instance ;
	}

}
