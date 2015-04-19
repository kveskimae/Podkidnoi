/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.sort;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.littlech.gen.g.G30;

public class LobbyTableNamesColumnComparator implements Comparator<G30> {
	
	private static LobbyTableNamesColumnComparator instance = new LobbyTableNamesColumnComparator();
	
	public static final String regex = "Default[0-9]+";
	private static Pattern numPattern = Pattern.compile("[0-9]+");
	
	public static LobbyTableNamesColumnComparator getInstance() {
		return instance;
	}

	@Override
	public int compare(G30 o1, G30 o2) {
		String name1 = o1.getG32();
		String name2 = o2.getG32();
		if (name1 == null && name2 == null) {
			return 0;
		}
		if (name1 == null) {
			return -1;
		}
		if (name2 == null) {
			return 1;
		}
		boolean matches1 = o1.isG35(); // name1.matches(regex);
		boolean matches2 = o2.isG35(); // name2.matches(regex);
		if (matches1 && !matches2) {
			return 1;
		}
		if (!matches1 && matches2) {
			return -1;
		}
		if (matches1 && matches2) {
			try {
				Matcher numMatcher1 = LobbyTableNamesColumnComparator.numPattern.matcher(name1);
				numMatcher1.find();
				String num1 = numMatcher1.group();
				Integer numInt1 = Integer.valueOf(num1);
				

				Matcher numMatcher2 = LobbyTableNamesColumnComparator.numPattern.matcher(name2);
				numMatcher2.find();
				String num2 = numMatcher2.group();
				Integer numInt2 = Integer.valueOf(num2);
				
				return numInt1 - numInt2;
			} catch (Exception e) {
			}
		}
		return name1.compareTo(name2);
	}

}
