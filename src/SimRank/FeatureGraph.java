package SimRank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the memory data structure to store the graph.
 * 
 * This class is strongly inspired by the WebGraph data structure that stores
 * the link structure of the World Wide Web and makes the applications of
 * different algorithms like SimRank and PageRank possible.
 * 
 * This classes uses main memory for storage of graph.
 * 
 * @author Bhaskar Bagchi
 */

public class FeatureGraph {
	/**
	 * A mapping from feature space to integer(identifier)
	 */
	@SuppressWarnings("rawtypes")
	private Map identityToFeature;
	/**
	 * A mapping from identifier to corresponding feature
	 */
	@SuppressWarnings("rawtypes")
	private Map featureToIdentity;
	/**
	 * A map storing inLinks. For each identifier, another Map is stored,
	 * containing for each inlink an associated weight.
	 */
	@SuppressWarnings("rawtypes")
	private Map inLinks;
	/**
	 * A map storing outLinks. For each identifier, another Map is stored,
	 * containing for each outlink an associated weight.
	 */
	@SuppressWarnings("rawtypes")
	private Map outLinks;
	/**
	 * Number of nodes in the graph.
	 */
	private int nodeCounts;

	/**
	 * Default constructor for the class
	 */
	@SuppressWarnings("rawtypes")
	public FeatureGraph() {
		identityToFeature = new HashMap();
		featureToIdentity = new HashMap();
		inLinks = new HashMap();
		outLinks = new HashMap();
		nodeCounts = 0;
	}

	/**
	 * 
	 * @param file
	 *            The name of the file from where graph is to be constructed
	 * @throws IOException
	 *             Error occurred while reading the file.
	 * @throws FileNotFoundException
	 *             File passed in the parameter doesn't exist
	 */
	public FeatureGraph(File file) throws IOException, FileNotFoundException {
		this();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		int imageNo;
		List<Pair<String, String>> featuresList = new ArrayList<Pair<String, String>>();
		line = reader.readLine();
		imageNo = Integer.parseInt(line.split(",")[0]);
		featuresList.add(new Pair<String, String>(line.split(",")[1], line
				.split(",")[2]));
		while ((line = reader.readLine()) != null) {
			if (Integer.parseInt(line.split(",")[0]) == imageNo) {
				featuresList.add(new Pair<String, String>(line.split(",")[1]
						.trim(), line.split(",")[2].trim()));
			} else {
				addClique(featuresList);
//				System.out.println(featuresList);
//				for (Pair p : featuresList) {
//					System.out.println(p.getL() + " " + p.getR());
//				}
				featuresList.clear();
				imageNo = Integer.parseInt(line.split(",")[0]);
				featuresList.add(new Pair<String, String>(line.split(",")[1]
						.trim(), line.split(",")[2].trim()));
			}
		}
		addClique(featuresList);
//		System.out.println(featuresList);
//		for (Pair p : featuresList) {
//			System.out.println(p.getL() + " " + p.getR());
//		}
		reader.close();
	}

	private void addClique(List<Pair<String, String>> featuresList) {
		for (int i = 0; i < featuresList.size(); i++) {
			for (int j = i + 1; j < featuresList.size(); j++) {
				String feature1 = featuresList.get(i).getL().trim() + " -> "
						+ featuresList.get(i).getR().trim();
				String feature2 = featuresList.get(j).getL().trim() + " -> "
						+ featuresList.get(j).getR().trim();
				Double strength = new Double(1.0);
				addLink(feature1, feature2, strength);
				addLink(feature2, feature1, strength);
			}
		}
	}

	public Integer featureToIdentity(String feature) {
		Object object = featureToIdentity.get(feature);
		if (object == null) {
			return null;
		}
		return (Integer) object;
	}

	public String identityToFeature(Integer id) {
		return (String) (identityToFeature.get(id));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addLink(String feature) {
		Integer id = featureToIdentity(feature);
		if (id == null) {
			id = new Integer(++nodeCounts);
			featureToIdentity.put(feature, id);
			identityToFeature.put(id, feature);
			inLinks.put(id, new HashMap());
			outLinks.put(id, new HashMap());
		}
	}

	private Double addLink(String from, String to, Double strength) {
		addLink(from);
		addLink(to);
		Integer idFrom = featureToIdentity(from);
		Integer idTo = featureToIdentity(to);
		return addLink(idFrom, idTo, strength);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Double addLink(Integer from, Integer to, Double strength) {
		Double aux;
		Map map1 = (Map) (inLinks.get(to));
		Map map2 = (Map) (outLinks.get(from));
		aux = (Double) (map1.get(from));
		if (aux == null) {
			map1.put(from, strength);
		} else {
			map1.put(from, (aux + strength));
		}
		aux = (Double) (map2.get(to));
		if (aux == null) {
			map2.put(to, strength);
		} else {
			map2.put(to, (aux + strength));
		}
		inLinks.put(to, map1);
		outLinks.put(from, map2);
		return aux==null?strength:(aux + strength);
	}

	@SuppressWarnings("rawtypes")
	public Map inLinks(String node) {
		Integer id = featureToIdentity(node);
		return inLinks(id);
	}

	@SuppressWarnings("rawtypes")
	public Map inLinks(Integer id) {
		if (id == null) {
			return new HashMap();
		}
		Map aux = (Map) (inLinks.get(id));
		return aux == null ? new HashMap() : aux;
	}

	@SuppressWarnings("rawtypes")
	public Map outLinks(String node) {
		Integer id = featureToIdentity(node);
		return outLinks(id);
	}

	@SuppressWarnings("rawtypes")
	public Map outLinks(Integer id) {
		if (id == null) {
			return new HashMap();
		}
		Map aux = (Map) (outLinks.get(id));
		return aux == null ? new HashMap() : aux;
	}

	public Double inLink(String from, String to) {
		Integer id1 = featureToIdentity(from);
		Integer id2 = featureToIdentity(to);
		return inLink(id1, id2);
	}

	@SuppressWarnings("rawtypes")
	public Double inLink(Integer from, Integer to) {
		Map aux = inLinks(to);
		if (aux == null) {
			return new Double(0.0);
		}
		Double weight = (Double) (aux.get(from));
		return weight == null ? new Double(0.0) : weight;
	}

	public Double outLink(String from, String to) {
		Integer id1 = featureToIdentity(from);
		Integer id2 = featureToIdentity(to);
		return outLink(id1, id2);
	}

	@SuppressWarnings("rawtypes")
	public Double outLink(Integer from, Integer to) {
		Map aux = outLinks(from);
		if (aux == null) {
			return new Double(0);
		}
		Double weight = (Double) aux.get(to);
		return weight == null ? new Double(0) : weight;
	}

	public int numNodes() {
		return nodeCounts;
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
