package Common;

import java.util.Vector;

public class FeatureLook {
	private Vector<FeaturePart> features = new Vector<FeaturePart>();
	
	public Vector<FeaturePart> getFeatures() {
		return this.features;
	}
	
	public FeaturePart getNthFeature(int n) {
		return this.features.get(n);
	}
	
	public void setFeatures(Vector<FeaturePart> v) {
		this.features = v;
	}
	
	public int getFeaturesSize() {
		return this.features.size();
	}
}
