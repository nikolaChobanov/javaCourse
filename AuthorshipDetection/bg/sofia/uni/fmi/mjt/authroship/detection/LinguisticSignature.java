package bg.sofia.uni.fmi.mjt.authroship.detection;

import java.util.EnumMap;
import java.util.Map;

public class LinguisticSignature {


    private EnumMap<FeatureType, Double> allFeatures;

    public LinguisticSignature(EnumMap<FeatureType, Double> features) {
        this.allFeatures = features;

    }

    public Map<FeatureType, Double> getFeatures() {
        return allFeatures;
    }
}
