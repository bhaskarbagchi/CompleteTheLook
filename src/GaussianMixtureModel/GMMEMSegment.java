package GaussianMixtureModel;

import java.util.Vector;

import Common.FeatureLook;

public class GMMEMSegment {
	private Vector<FeatureLook> dataset = new Vector<FeatureLook>();
	private int n = dataset.size();
	static private int MAXIMUM_ITERATIONS = 500;
	static private double threshold = 0.0001;
	private boolean converged = false;
	
	//Hold log likelihood for each iteration to test for convergence
	Vector<Pair<Double, Double>> llhs = new Vector<>(MAXIMUM_ITERATIONS);
	/*
	GMMInitialize.initialize(dataset, k);	// k = initial number of classes for k means clustering
	Vector<Double> oldMeans = GMMInitialize.getMeans();
	Vector<Double> oldVariances = GMMInitilize.getVariances();
	Vector<Double> oldCoefficients = GMMInitialize.getCoefficients();
	
	int iter = 1;
	while(!converged && iter < MAXIMUM_ITERATIONS) {
		Vector<Double> scores = GMMExpectations(dataset, k, oldMeans, oldVariances, oldCoefficients);
		GMMMaximization.init();
		Vector<Double> newMeans = GMMMaximization.getMeans();
		Vector<Double> newVariances = GMMMaximization.getVariances();
		Vector<Double> newCoefficients = GMMMaximization.getCoefficients();
		llhs.add(new Pair<Double, Double>(iter, GMMLogLikelyhood(dataset, k, newMeans, newVariances, newCoefficients)));
		if(iter!=1){
			
		}
	}
	*/
	
	/*
	 * TODO : implement the pair class
	 */
	private class Pair<L, R> {
		public L l;
		public R r;
	}
}
