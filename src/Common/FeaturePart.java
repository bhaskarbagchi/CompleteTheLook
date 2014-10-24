package Common;

import java.util.ListIterator;
import java.util.Vector;

public class FeaturePart {
	private Vector<Double> feature = new Vector<Double>();
	
	public Vector<Double> getFeature() {
		return this.feature;
	}
	
	public void setFeature(Vector<Double> v) {
		this.feature = v;
	}
	
	public int getFeatureSize() {
		return this.feature.size();
	}
	
	public FeaturePart addFeaturePart(FeaturePart p) {
		Vector<Double> pFeature = new Vector<Double>(p.getFeature());
		ListIterator<Double> iter = feature.listIterator();
		int i = 0;
		while(iter.hasNext()) {
			Double element = (Double) iter.next();
			iter.set(element + pFeature.get(i));
			i++;
		}
		return this;
	}
	
	public FeaturePart divideFeaturePart(double d) {
		if(d != 0.0) {
			for(Double element : feature) {
				element = element / d ;
			}
		}
		else {
			/*
			 * TODO
			 */
		}
		return this;
	}
}
