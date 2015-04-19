/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.cl.gui.lobby.sort;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.TableRowSorter;

import com.littlech.cl.gui.lobby.podkidnoy.PodkidnoyTableModel;


public class LobbySorter extends TableRowSorter<PodkidnoyTableModel> {
	
	public static class BooleanComparator implements Comparator<Boolean> {

		@Override
		public int compare(Boolean arg0, Boolean arg1) {
			if (arg0 == null && arg1 == null) {
				return 0;
			}
			if (arg0 != null && arg1 == null) {
				return 1;
			}
			if (arg0 == null && arg1 != null) {
				return -1;
			}
			if (arg0 && !arg1){
				return 1;
			}
			if (!arg0 && arg1){
				return -1;
			}
			return 0;
		}
		
	}
	
	public static class IntegerComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer arg0, Integer arg1) {

			if (arg0 == null && arg1 == null) {
				return 0;
			}
			if (arg0 != null && arg1 == null) {
				return 1;
			}
			if (arg0 == null && arg1 != null) {
				return -1;
			}
			return arg0 - arg1;
		}
		
	}
	
	public static class StringComparator implements Comparator<String> {

		public static final String regex = "Default table [0-9]+";
		private static Pattern numPattern = Pattern.compile("[0-9]+");
		
		@Override
		public int compare(String name1, String name2) {
			if (name1 == null && name2 == null) {
				return 0;
			}
			if (name1 == null) {
				return -1;
			}
			if (name2 == null) {
				return 1;
			}
			boolean matches1 = name1.matches(regex);
			boolean matches2 = name2.matches(regex);
			if (matches1 && !matches2) {
				return 1;
			}
			if (!matches1 && matches2) {
				return -1;
			}
			if (matches1 && matches2) {
				try {
					Matcher numMatcher1 = StringComparator.numPattern.matcher(name1);
					numMatcher1.find();
					String num1 = numMatcher1.group();
					Integer numInt1 = Integer.valueOf(num1);
					

					Matcher numMatcher2 = StringComparator.numPattern.matcher(name2);
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
	
	public static class ListComparator implements Comparator<List> {

		@Override
		public int compare(List arg0, List arg1) {
			if (arg0 == null && arg1 == null) {
				return 0;
			}
			if (arg0 != null && arg1 == null) {
				return 1;
			}
			if (arg0 == null && arg1 != null) {
				return -1;
			}
			int size1 = arg0.size();
			int size2 = arg1.size();
			return size1 - size2;
		}
		
	}
	/*
	static Comparator PlayersComparator = new Comparator() {

		
		@Override
		public int compare(Object o1, Object o2) {
*/
			// System.out.println("o1 was " + o1.getClass().getCanonicalName());	
		// System.out.println("o2 was " + o2.getClass().getCanonicalName());
		
			
			/*
			if (o1 instanceof List) {
				if (o2 instanceof List) {
					List l1 = (List)o1;
					List l2 = (List)o2;
					if (l1.size() > l2.size() ) {
						return 1;
					} else if (l2.size() > l1.size()){
						return -1;
					} else {
						return 0;
					}
				}
				throw new IllegalArgumentException("o2 was " + o2.getClass().getCanonicalName());
			}
			throw new IllegalArgumentException("o1 was " + o1.getClass().getCanonicalName());
		}
		*/
	/*
			if (o1 instanceof String && o2 instanceof String) {
				String s1 = (String)o1;
				String s2 = (String)o2;
				String del = ",";
				int players1 = s1.split("\\" + del, -1).length;
				int players2 = s2.split("\\" + del, -1).length;
				return players1 - players2;
			}
			return 0;
		}
			
	};
	*/
	public LobbySorter(PodkidnoyTableModel model) {
		super(model)
		;
	}
	
	private static ListComparator list = new ListComparator();
	private static BooleanComparator booleanComp = new BooleanComparator();
	private static IntegerComparator intComp = new IntegerComparator();
	private static StringComparator stringComp = new StringComparator();

	@Override
	public Comparator<?> getComparator(int modelCol) {
		if (modelCol == 0) {
			return LobbySorter.intComp;
		}
		else if (modelCol == 1) {
			return LobbySorter.booleanComp;
		}
		/*else if (modelCol == 2) {
			return LobbySorter.stringComp;
		} if (modelCol == 3) {
			return LobbySorter.list;
		}
		*/
		return null;
		// throw new IllegalArgumentException("Illegal column: " + modelCol);
		
		// }
	}
	
/*
	@Override
	public Comparator<?> getComparator(int column) {
		// System.out.println("col=" + column);
		if (column==3) {
			return PlayersComparator;
		}
		return super.getComparator(column);
	}
*/
}
