package com.example.geowarning.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//for instructing Jackson to use the type property in the JSON to determine-
// - which subclass (Feature or FeatureCollection) to deserialize the JSON data into.
@JsonTypeInfo( //tells Jackson that you want to use type information from the JSON to decide which class to instantiate.
        use = JsonTypeInfo.Id.NAME, //specifying that the decision should be made based on a named type.
        include = JsonTypeInfo.As.PROPERTY, //means that the type information is a property in the JSON itself.
        property = "type") //specifies that the type property in the JSON will contain the type information.

@JsonSubTypes({ //This is an array of mappings from the value of the type property in the JSON to the actual class it should map to.
        @JsonSubTypes.Type(value = Feature.class, name = "Feature"), //This tells Jackson that if the type property in the JSON is "Feature", then it should deserialize to the Feature class.
        @JsonSubTypes.Type(value = FeatureCollection.class, name = "FeatureCollection") //Similarly, this line maps the "FeatureCollection" value to the FeatureCollection class.
})
public interface Area {
}
