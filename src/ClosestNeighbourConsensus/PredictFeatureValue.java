package ClosestNeighbourConsensus;

import java.util.Vector;

import Common.FeatureLook;
import Common.FeaturePart;

public class PredictFeatureValue {
	public FeaturePart predictFeatureValue(Vector<FeatureLook> dataset,
			FeatureLook test, int missingPart, DistanceMeasure distanceMeasure,
			ConsensusFunctionType consensusFunctionType) {
		FeaturePart feature = new FeaturePart();
		NearestNeighbours nearestNeighbours = new NearestNeighbours();
		Vector<FeatureLook> neighbours = nearestNeighbours
				.findNearestNeighbours(dataset, test, missingPart,
						distanceMeasure);
		switch (consensusFunctionType) {
			case AVERAGE: {
				feature = AverageConsensus(neighbours, missingPart);
			}
			default: {
				feature = consensus(neighbours, missingPart);
			}
		}
		return feature;
	}

	private FeaturePart AverageConsensus(Vector<FeatureLook> neighbours,
			int missingPart) {
		FeaturePart f = new FeaturePart();
		FeaturePart temp = new FeaturePart();
		/*
		 * TODO : implement average consensus
		 */
		for(FeatureLook featureLook : neighbours) {
			temp = featureLook.getNthFeature(missingPart);
			f.addFeaturePart(temp);
		}
		f.divideFeaturePart(neighbours.size());
		return f;
	}

	/*
	 * TODO : Implement other consensus functions
	 */
	private FeaturePart consensus(Vector<FeatureLook> neighbours,
			int missingPart) {
		return AverageConsensus(neighbours, missingPart);
	}
}
