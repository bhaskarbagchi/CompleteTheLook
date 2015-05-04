package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import RankAggregation.Broda;
import SimRank.FeatureGraph;
import SimRank.KNearestNeighbors;
import SimRank.SimRank;

public class CTL_user_input {
	public static void main(String args[]) {
		int number_of_inputs;
		Scanner in = new Scanner(System.in);
		String fileName = "inputs/features.csv";
		File file = new File(fileName);
		FeatureGraph featureGraph;
		KNearestNeighbors neighbors;
		System.out.println("Welcome to the recommendation evaluation module.");
		System.out.println("Please wait till the initializations.");
		System.out.println("You are required to make 20 ratings to 20 sets of shopping baskets.");
		System.out.println("Ratings are to be made in a scale of 1 - 10, where 1 being very bad recommendation and 10 being very good recommendation.");
		try {
			featureGraph = new FeatureGraph(file);
			System.out.println("Feature Graph created successfully");
			neighbors = new KNearestNeighbors(featureGraph);
//			neighbors.setK(10);
			System.out.println("k nearest neighbors initialized");
			System.out.println();

			FileInputStream inf = new FileInputStream(new File("inputs/UserInput.txt"));
			BufferedReader br = new BufferedReader(new InputStreamReader(inf));
			int xx = Integer.parseInt(br.readLine());
			for(int iter = 0; iter < xx; iter++) {
				System.out.println("Evaluation no " + iter);
				ArrayList<List<Integer>> individualLists = new ArrayList<List<Integer>>();
				System.out.print("Enter the number of items in shopping cart");
				number_of_inputs = Integer.parseInt(br.readLine());
				String temp = new String();
				for(int i = 0; i < number_of_inputs; i++) {
					System.out.println("Enter item " + (i+1) + ":" );
					temp = br.readLine();
					System.out.println(temp);
//					System.out.println("The neighbouts of \'" + temp + "\'(" + featureGraph.featureToIdentity(temp) +") are as follows:" );
					List<Integer> list;
					try {
						list = neighbors.k_nearest_neighbors(temp);
						individualLists.add(list);
					} catch(NullPointerException ee) {
						list = null;
						System.out.println(temp + " is not found in database.");
					}
//					for(Integer aux : list) {
//						System.out.println(aux + " = " + featureGraph.identityToFeature(aux));
//					}
//					System.out.println();
					
				}
				System.out.println();
				System.out.println("The recommendations for your basket are as follows");
				System.out.println();
				Broda b = new Broda();
				if(individualLists.size() != 0) {
					ArrayList<Integer> finalList = b.rankAggregation(individualLists);
	//				for(Integer aux : finalList) {
	//					if(!(aux == featureGraph.featureToIdentity("Sandles -> Black") || aux == featureGraph.featureToIdentity("Bag -> White") || aux == featureGraph.featureToIdentity("Jeans -> Blue"))){
	//						System.out.println(aux + " = " + featureGraph.identityToFeature(aux));
	//					}
	//				}
					for(int i = 0; (i < 10 && i<finalList.size()); i++) {
						System.out.println(featureGraph.identityToFeature(finalList.get(i)));
					}
				}
				System.out.println();
				System.out.println();
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error in main");
			e.printStackTrace();
		}
		System.out.println("Evaluation Module Finished!!! Thank you for your kind help.");
		in.close();
	}
}
