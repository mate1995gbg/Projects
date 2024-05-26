export type Area = Feature | FeatureCollection;

interface Feature {
    type: "Feature";
    // ... other properties specific to Feature
}

interface FeatureCollection {
    type: "FeatureCollection";
    // ... other properties specific to FeatureCollection
}