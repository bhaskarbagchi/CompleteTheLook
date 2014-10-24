package SimRank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This class computes the K nearest neighbors of a node using SimRank as
 * distance score given a graph and SimRanks between pairs of nodes.
 * 
 * @author bhaskar
 * 
 */
public class KNearestNeighbors {
	
	private FeatureGraph graph;
	
	private SimRank simRank;
	
	public KNearestNeighbors(FeatureGraph graph) {
		this.graph = graph;
		this.simRank = new SimRank(graph);
	}
	
	private int K = 5;
	
	/**
	 * Setter for K
	 * @param k
	 */
	public void setK(int k) {
		this.K = k;
	}
	
	/**
	 * Getter for K
	 * @return the current value of K
	 */
	public int getK() {
		return this.K;
	}
	
	/**
	 * Function to find the k nearest neighbors of a String feature
	 * @param feature String feature description of node
	 * @return ArrayList of id's of its k nearest neighbors
	 */
	public List<Integer> k_nearest_neighbors(String feature) {
		Integer id = new Integer(graph.featureToIdentity(feature));
		return k_nearest_neighbors(id);
	}
	
	/**
	 * Function to find the k nearest neighbors given id of a feature
	 * @param id feature id of node
	 * @return ArrayList of id's of its k nearest neighbors
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Integer> k_nearest_neighbors(Integer id) {
		List<Integer> neighbors = new ArrayList<Integer>();
		Comparator<Pair<Integer, Double>> comparator = new PairComparator();
		PriorityQueue<Pair<Integer, Double>> pQueue = new PriorityQueue<Pair<Integer, Double>>(K, comparator);
		Set<Integer> set = new HashSet<Integer>();
		set = BFS(id);
		int count = 0;
		double aux = 0.0;
		for(Integer node : set) {
			if(id != node) {
				aux = simRank.simRank(id, node);
				if(count < K){
					pQueue.add(new Pair<Integer, Double>(node, aux));
				}
				else {
					Pair<Integer, Double> tAux = pQueue.peek();
					if(tAux.getR() < aux) {
						pQueue.poll();
						pQueue.add(new Pair<Integer, Double>(node, aux));
					}
				}
				Iterator it = pQueue.iterator();
				while(it.hasNext()) {
					Pair<Integer, Double> tt = (Pair<Integer, Double>) it.next();
					System.out.print(tt.getL() + " " + tt.getR() + " , ");
				}
				System.out.println();
				count++;
			}
		}
		ArrayList<Pair<Integer, Double>> list = new ArrayList(pQueue);
		Collections.sort(list, comparator);
		Collections.reverse(list);
		for(Pair<Integer, Double> pair : list) {
			neighbors.add(pair.getL());
		}
		pQueue.clear();
		return neighbors;
	}
	
	@SuppressWarnings("rawtypes")
	private Set<Integer> BFS(Integer node) {
		int count = 0;
		Set<Integer> set = new HashSet<Integer>();
		set.add(node);
		Iterator it = set.iterator();
		while(it.hasNext() && count < K) {
			int id = (Integer)(it.next());
			Map aux = new HashMap();
			aux = graph.inLinks(id);
			if(aux != null) {
				Iterator it2 = aux.keySet().iterator();
				while(it2.hasNext()) {
					set.add((Integer)(it2.next()));
				}
			}
			count++;
		}
		return set;
	}
	
	private class PairComparator implements Comparator<Pair<Integer, Double>> {
		@Override
		public int compare(Pair<Integer, Double> x, Pair<Integer, Double> y) {
			if(y.getR() - x.getR() > 0)
				return -1;
			else if(x.getR() - y.getR() > 0)
				return 1;
			else
				return 0;
		}
	}
	
	/**
	 * Generic class to store pairs
	 * 
	 * @author Bhaskar Bagchi
	 * 
	 * @param <L>
	 * @param <R>
	 */
	private class Pair<L, R> {
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
	};
}
