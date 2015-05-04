package RankAggregation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Broda {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<Integer> rankAggregation(List<List<Integer>> individualRankings) {
		int m = individualRankings.size();
		int k = individualRankings.get(0).size();
		int max = m * k;
		//debug
		max = m * 5;
		//end debug
		Comparator<Pair<Integer, Integer>> comparator = new PairComparator();
		ArrayList<Integer> aggregated = new ArrayList<Integer>();
		ArrayList<Pair<Integer, Integer>> members = new ArrayList<Pair<Integer, Integer>>();
		Map elements = new HashMap<>();
		for(List<Integer> list : individualRankings) {
			for(int i = 0; i < list.size(); i++) {
				Integer aux = list.get(i);
				List query = (List) elements.get(aux);
				if(query == null) {
					List<Integer> scores = new ArrayList<Integer>();
					scores.add(max - i);
					elements.put(aux, scores);
				} else {
					query.add(max - i);
					elements.put(aux, query);
				}
			}
		}
		Iterator<Integer> it = elements.keySet().iterator();
		while(it.hasNext()) {
			Integer temp = it.next();
			List<Integer> scores = (List) elements.get(temp);
			int total = 0;
			for(Integer score : scores) {
				total += score;
			}
			members.add(new Pair<Integer, Integer>(temp, total));
			//debug
			System.out.println(" Broda score = " + temp + " " + total);
			//end debug
		}
		Collections.sort(members, comparator);
		Collections.reverse(members);
		for(Pair<Integer, Integer> pair : members) {
			aggregated.add(pair.getL());
		}
		return aggregated;
	}
	
	public class Pair<L, R>{
		private L l;
		private R r;

		/**
		 * Constructor
		 * 
		 * @param l
		 * @param r
		 */
		public Pair(L l, R r) {
			this.l = l;
			this.r = r;
		}

		public void setL(L l) {
			this.l = l;
		}

		public void setR(R r) {
			this.r = r;
		}

		public L getL() {
			return this.l;
		}

		public R getR() {
			return this.r;
		}
	}
	
	private class PairComparator implements Comparator<Pair<Integer, Integer>> {
		@Override
		public int compare(Pair<Integer, Integer> x, Pair<Integer, Integer> y) {
			if(y.getR() - x.getR() > 0)
				return -1;
			else if(x.getR() - y.getR() > 0)
				return 1;
			else
				return 0;
		}
	}
}
