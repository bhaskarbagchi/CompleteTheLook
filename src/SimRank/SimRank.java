/**
 * 
 */
package SimRank;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Bhaskar Bagchi This class consists of the basic functions to compute
 *         SimRank between nodes of the graph.
 */
public class SimRank {
	/**
	 * Dampening is the amount to which the similarity of parents pass to child.
	 * Default value is set to be 0.85 which is mostly used.
	 */
	private double damping = 0.85;

	/**
	 * The graph structure for nodes of which SimRank is to be calculated.
	 */
	private FeatureGraph graph;

	/**
	 * A hashMap containing the similarity or SimRank score between all node
	 * pairs consisting an edge between them.
	 */
	@SuppressWarnings("rawtypes")
	private Map scores;

	/**
	 * Constructor for the SimRank class. Initializes the score Map.
	 * 
	 * @param graph
	 *            The graph for nodes of which SimRank is to be calculated.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SimRank(FeatureGraph graph) {
		this.graph = graph;
		this.scores = new HashMap();
		int numLinks = graph.numNodes();
		for (int i = 0; i < numLinks; i++) {
			HashMap aux = new HashMap();
			for (int j = 0; j < i; j++) {
				aux.put(new Integer(j), new Double(0/*-1*/));
			}
			scores.put(new Integer(i), aux);
		}

		computeSimRank();
	}

	/**
	 * Constructor for the SimRank class. Initializes the score Map.
	 * 
	 * @param graph
	 *            The graph for nodes of which SimRank is to be calculated.
	 * @param noIter
	 *            Number of iterations for computing simRakns
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SimRank(FeatureGraph graph, int noIter) {
		this.graph = graph;
		this.scores = new HashMap();
		int numLinks = graph.numNodes();
		for (int i = 0; i < numLinks; i++) {
			HashMap aux = new HashMap();
			for (int j = 0; j < i; j++) {
				aux.put(new Integer(j), new Double(0/*-1*/));
			}
			scores.put(new Integer(i), aux);
		}

		computeSimRank(noIter);
	}

	/**
	 * Function to set the dampening to something other than the default value.
	 * 
	 * @param damping
	 *            (dampening value supplied by user)
	 */
	public void setDamping(double damping) {
		this.damping = damping;
	}

	/**
	 * 
	 * @return the current dampening value
	 */
	public double getDamping() {
		return this.damping;
	}

	public void computeSimRank() {
		int n = graph.numNodes();
		int iter = (((int) Math.abs(Math.log((double) n))) / (int) Math
				.log((double) 10)) + 1;
		computeSimRank(iter);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void computeSimRank(int iter) {
		Double score = null;
		int numLinks = graph.numNodes();
		// for (int i = 0; i < numLinks; i++) {
		// HashMap aux = new HashMap();
		// for (int j = 0; j < i; j++) {
		// aux.put(new Integer(j), new Double(0));
		// }
		// scores.put(new Integer(i), aux);
		// }
		while ((iter--) > 0) {
			Map newScore = new HashMap();
			for (int id1 = 0; id1 < numLinks; id1++) {
				Map map3 = (Map) (scores.get(new Integer(id1)));
				Map map1 = graph.inLinks(new Integer(id1));
				for (int id2 = 0; id2 < /* id1 */numLinks; id2++) {
					Map map2 = graph.inLinks(new Integer(id2));
					int numInLinks1 = 0;
					int numInLinks2 = 0;
					score = new Double(0);
					boolean first = false;
					Iterator it1 = map1.keySet().iterator();
					while (it1.hasNext()) {
						Iterator it2 = map2.keySet().iterator();
						Integer feature1 = (Integer) (it1.next());
						Double weight1 = (Double) (map1.get(feature1));
						if (weight1 != null && weight1.doubleValue() > 0) {
							numInLinks1++;
						}
						while (it2.hasNext()) {
							Integer feature2 = (Integer) (it2.next());
							Double weight2 = (Double) (map2.get(feature2));
							if (weight2 != null && weight2.doubleValue() > 0) {
								if (weight1 != null
										&& weight1.doubleValue() > 0) {
									score = new Double(simRank(feature1,
											feature2).doubleValue()
											+ score.doubleValue());
								}
								if (first) {
									numInLinks2++;
								}
							}
						}
						first = true;
						score = new Double(
								(damping / (numInLinks1 + numInLinks2))
										* score.doubleValue());
						map3.put(new Integer(id2), score);
					}
				}
				newScore.put(new Integer(id1), map3);
			}
			for (int j = 0; j < numLinks; j++) {
				scores.put(new Integer(j),
				/* (Double) */(newScore.get(new Integer(j))));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Map simRank(String feature) {
		return simRank(graph.featureToIdentity(feature));
	}

	public Double simRank(String from, String to) {
		return simRank(graph.featureToIdentity(from),
				graph.featureToIdentity(to));
	}

	@SuppressWarnings("rawtypes")
	private Map simRank(Integer id) {
		if (id.intValue() != 0) {
			if (((Integer) (((Map) (scores.get(id)))).get(new Integer(0)))
					.doubleValue() < 0) {
				computeSimRank();
				return simRank(id);
			}
		} else {
			if (((Integer) (((Map) (scores.get(new Integer(1))))
					.get(new Integer(0)))).doubleValue() < 0) {
				computeSimRank();
				return simRank(id);
			}
		}
		return (Map) (scores.get(id));
	}

	@SuppressWarnings("rawtypes")
	public Double simRank(Integer id1, Integer id2) {
		if (id1.equals(id2))
			return new Double(1);
		if (id2.intValue() > id1.intValue()) {
			Integer id3 = id1;
			id1 = id2;
			id2 = id1;
		}
		Map temp = (Map) (scores.get(id1));
		Double aux = new Double(0);
		if (temp != null)
			aux = (Double) (temp.get(id2));
		if (aux == null)
			return new Double(0);
		if (aux.intValue() < 0) {
			computeSimRank();
			return simRank(id1, id2);
		}
		return aux;
	}
}
