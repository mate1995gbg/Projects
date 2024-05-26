package com.example.geowarning.model;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeometryDeserializer extends JsonDeserializer<Geometry> {

    @Override
    public Geometry deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String typeString = node.get("type").asText();
        GeometryTypeEnum type = GeometryTypeEnum.fromString(typeString);

        Geometry geometry = new Geometry();
        geometry.setType(type);

        JsonNode coordinatesNode = node.get("coordinates");

        // Depending on type, parse coordinates
        switch (type) {
            case POINT:
                 List<Double> point = new ArrayList<>(); //create new list to store point coordinates
                 if (coordinatesNode.isArray()) { //if the recieved coordinatesNode is an array (point)
                     for (JsonNode coordinateNode : coordinatesNode) {
                         point.add((coordinateNode.asDouble()));
                     }
                 }
                 else {
                     Log.d("TAG: ", "GEOMERTYDESERIALIZER - COORDINATESNODE IS NOT AN ARRAY OR OTHER ERROR");
                 }
                geometry.setPointCoordinates(point); //use pointCoordinates (in Geometry class) List Setter to set points to list
                break;
            case LINE_STRING:
                List<Double> lineString = new ArrayList<>();
                if (coordinatesNode.isArray()) {
                    for (JsonNode coordinateNode : coordinatesNode) {
                        lineString.add(coordinateNode.asDouble());
                    }
                }
                geometry.setLineStringCoordinates(lineString);
                break;
            case POLYGON:
                List<List<Double>> polygon = new ArrayList<>(); //create a list of lists.
                if (coordinatesNode.isArray()) { //if incoming object is array (list, whatever)
                    for (JsonNode linearRingNode : coordinatesNode) { //for each jsonNode object in array
                        if (linearRingNode.isArray()) { //check if linearRingNode is an array
                            List<Double> linearRing = new ArrayList<>();//create a new list for each iteration
                            for (JsonNode coordinateNode : linearRingNode) { //this will run 2 times, adding coordinates to the linearRing list.
                                linearRing.add(coordinateNode.asDouble());
                            }
                            polygon.add(linearRing);
                        }
                        else {
                            Log.d("TAG: ", "GEOMERTYDESERIALIZER - LINEARRINGNODE IN POLYGON IS NOT AN ARRAY OR OTHER ERROR");
                        }
                    }
                }
                else {
                    Log.d("TAG: ", "GEOMERTYDESERIALIZER - COORDINATESNODE IN POLYGON IS NOT AN ARRAY OR OTHER ERROR");
                }
                geometry.setPolygonCoordinates(polygon);
                break;
            case MULTI_POLYGON:
                List<List<List<Double>>> multiPolygon = new ArrayList<>();
                if (coordinatesNode.isArray()) {
                    for (JsonNode outerRingNode : coordinatesNode) {
                        if (outerRingNode.isArray()) {
                            List<List<Double>> outerRing = new ArrayList<>();
                            for (JsonNode linearRingNode : outerRingNode) {
                                if (linearRingNode.isArray()) {
                                    List<Double> linearRing = new ArrayList<>();
                                    for (JsonNode coordinateNode : linearRingNode) {
                                        linearRing.add(coordinateNode.asDouble());
                                    }
                                    outerRing.add(linearRing);
                                }
                                else {
                                    Log.d("TAG: ", "GEOMERTYDESERIALIZER - LINEARRINGNODE IN MULTIPOLYGON IS NOT AN ARRAY OR OTHER ERROR");
                                }
                            }
                            multiPolygon.add(outerRing);
                        }
                        else {
                            Log.d("TAG: ", "GEOMERTYDESERIALIZER - OUTERRINGNODE IN MULTIPOLYGON IS NOT AN ARRAY OR OTHER ERROR");
                        }
                    }
                }
                else {
                    Log.d("TAG: ", "GEOMERTYDESERIALIZER - COORDINATESNODE IN MULTIPOLYGON IS NOT AN ARRAY OR OTHER ERROR");
                }
                geometry.setMultiPolygonCoordinates(multiPolygon);
                break;
        }

        return geometry;
    }
}



