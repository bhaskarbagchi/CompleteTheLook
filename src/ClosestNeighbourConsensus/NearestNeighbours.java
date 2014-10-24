package ClosestNeighbourConsensus;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

import Common.FeatureLook;

public class NearestNeighbours {

	private static int NO_OF_NEAREST_NEIGHBOURS = 5;

	public Vector<FeatureLook> findNearestNeighbours(Vector<FeatureLook> dataset,
			FeatureLook f, int missingPart, DistanceMeasure d) {
		Vector<FeatureLook> nearestNeighbours = new Vector<FeatureLook>(NO_OF_NEAREST_NEIGHBOURS);
		Comparator<Pair> comparator = new FeatureDistanceComparator();
		PriorityQueue<Pair> neighbours = new PriorityQueue<Pair>(
				NO_OF_NEAREST_NEIGHBOURS, comparator);
		int nearestFound = 0;
		for (FeatureLook featureLook : dataset) {
			double distance = 0.0;
			switch (d) {
			case LINEAR_DISTANCE: {
				distance = LinearDistance(f, featureLook, missingPart);
			}
			case EUCLIDIAN_DISTANCE: {
				distance = EuclideanDistance(f, featureLook, missingPart);
			}
			case KL_DIVERGENCE: {
				distance = dist(f, featureLook, missingPart);
			}
			default: {
				distance = dist(f, featureLook, missingPart);
			}
			}
			if (nearestFound < NO_OF_NEAREST_NEIGHBOURS) {
				neighbours.add(new Pair(featureLook, distance));
				nearestFound++;
			} else {
				/*
				 * TODO : Check if the comparator makes a queue in ascending
				 * order or descending order
				 */
				if (neighbours.element().getDistance() < distance) {
					neighbours.remove();
					neighbours.add(new Pair(featureLook, distance));
				}
			}
		}
		for (Pair pair : neighbours) {
			nearestNeighbours.add(pair.getFeatureLook());
		}
		return nearestNeighbours;
	}

	private double LinearDistance(FeatureLook test, FeatureLook data,
			int missingPart) {
		double distance = 0.0;
		for (int i = 0; i < data.getFeaturesSize(); i++) {
			if (i != missingPart) {
				double distFeature = 0.0;
				for (int j = 0; j < data.getNthFeature(i).getFeatureSize(); j++) {
					distFeature += Math.abs(data.getNthFeature(i).getFeature()
							.get(j)
							- test.getNthFeature(i).getFeature().get(j));
				}
				distance += distFeature;
			}
		}
		return distance;
	}

	private double EuclideanDistance(FeatureLook test, FeatureLook data,
			int missingPart) {
		double distance = 0.0;
		for (int i = 0; i < data.getFeaturesSize(); i++) {
			if (i != missingPart) {
				double distFeature = 0.0;
				for (int j = 0; j < data.getNthFeature(i).getFeatureSize(); j++) {
					distFeature += (data.getNthFeature(i).getFeature().get(j) - test
							.getNthFeature(i).getFeature().get(j))
							* (data.getNthFeature(i).getFeature().get(j) - test
									.getNthFeature(i).getFeature().get(j));
				}
				distance += distFeature;
			}
		}
		return distance;
	}

	/*
	 * TODO : Implement other distance metrics
	 */

	private double dist(FeatureLook test, FeatureLook data, int missingPart) {
		return EuclideanDistance(test, data, missingPart);
	}

	private class Pair {
		private FeatureLook featureLook;
		private Double distance;

		public Pair(FeatureLook featureLook, Double distance) {
			this.featureLook = featureLook;
			this.distance = distance;
		}

		public FeatureLook getFeatureLook() {
			return featureLook;
		}

		public Double getDistance() {
			return distance;
		}

		public void setFeatureLook(FeatureLook featureLook) {
			this.featureLook = featureLook;
		}

		public void setDistance(Double distance) {
			this.distance = distance;
		}
	}

	private class FeatureDistanceComparator implements Comparator<Pair> {
		@Override
		public int compare(Pair x, Pair y) {
			if (x.getDistance() < y.getDistance()) {
				return -1;
			}
			if (x.getDistance() > y.getDistance()) {
				return 1;
			}
			return 0;
		}
	}
}
