package Main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import SimRank.FeatureGraph;
import SimRank.KNearestNeighbors;
import SimRank.SimRank;

public class CompleteTheLook {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String fileName = "inputs/features.csv";
		File file = new File(fileName);
		FeatureGraph featureGraph;
		SimRank simRank;
		KNearestNeighbors neighbors;
		try {
			featureGraph = new FeatureGraph(file);
			System.out.println("Graph created successfully");
			simRank = new SimRank(featureGraph);
			System.out.println("SimRank initialized.");
			System.out.println("SimRank calculated for the whole graph.");
			System.out.println("Similarity between blue short and off white sweater = " + simRank.simRank("Shorts -> Blue", "Sweater -> Off White"));
			neighbors = new KNearestNeighbors(featureGraph);
			//neighbors.setK(10);
			System.out.println("k nearest neighbors initialized");
			System.out.println("The neighbors of \'Sandles -> Black\'(" + featureGraph.featureToIdentity("Sandles -> Black") + ") are as follows: ");
			List<Integer> list = neighbors.k_nearest_neighbors("Sandles -> Black");
			for(Integer aux : list) {
				System.out.println(aux + " = " + featureGraph.identityToFeature(aux));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error in main");
			e.printStackTrace();
		}
		System.out.println("Finished!!!");
	}

}
