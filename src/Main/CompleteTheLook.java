package Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import RankAggregation.Broda;
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
			//featureGraph.writeEdgeList();
			simRank = new SimRank(featureGraph);
			System.out.println("SimRank initialized.");
			System.out.println("SimRank calculated for the whole graph.");
//			System.out.println("Similarity between blue short and off white sweater = " + simRank.simRank("Shorts -> Blue", "Sweater -> Off White"));
			neighbors = new KNearestNeighbors(featureGraph);
			//neighbors.setK(10);
			System.out.println("k nearest neighbors initialized");
			System.out.println();
			System.out.println("The neighbors of \'Sandles -> Black\'(" + featureGraph.featureToIdentity("Sandles -> Black") + ") are as follows: ");
			List<Integer> list1 = neighbors.k_nearest_neighbors("Sandles -> Black");
			for(Integer aux : list1) {
				System.out.println(aux + " = " + featureGraph.identityToFeature(aux));
			}
			System.out.println();
			System.out.println("The neighbors of \'Bag -> White\'(" + featureGraph.featureToIdentity("Bag -> White") + ") are as follows: ");
			List<Integer> list2 = neighbors.k_nearest_neighbors("Bag -> White");
			for(Integer aux : list2) {
				System.out.println(aux + " = " + featureGraph.identityToFeature(aux));
			}
			
			System.out.println();
			System.out.println("The neighbors of \'Jeans -> Blue\'(" + featureGraph.featureToIdentity("Jeans -> Blue") + ") are as follows: ");
			List<Integer> list3 = neighbors.k_nearest_neighbors("Jeans -> Blue");
			for(Integer aux : list3) {
				System.out.println(aux + " = " + featureGraph.identityToFeature(aux));
			}
			
			System.out.println();
			System.out.println("The neighbors of \'Necklace -> Gold\'(" + featureGraph.featureToIdentity("Necklace -> Gold") + ") are as follows: ");
			List<Integer> list4 = neighbors.k_nearest_neighbors("Necklace -> Gold");
			for(Integer aux : list4) {
				System.out.println(aux + " = " + featureGraph.identityToFeature(aux));
			}
			
			System.out.println();
			System.out.println("Final compiled list is as follows");
			System.out.println();
			ArrayList<List<Integer>> individualLists = new ArrayList<List<Integer>>();;
			individualLists.add(list1);
			individualLists.add(list2);
			individualLists.add(list3);
			Broda b = new Broda();
			ArrayList<Integer> finalList = b.rankAggregation(individualLists);
			for(Integer aux : finalList) {
				if(!(aux == featureGraph.featureToIdentity("Sandles -> Black") || aux == featureGraph.featureToIdentity("Bag -> White") || aux == featureGraph.featureToIdentity("Jeans -> Blue"))){
					System.out.println(aux + " = " + featureGraph.identityToFeature(aux));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error in main");
			e.printStackTrace();
		}
		System.out.println("Finished!!!");
	}

}
