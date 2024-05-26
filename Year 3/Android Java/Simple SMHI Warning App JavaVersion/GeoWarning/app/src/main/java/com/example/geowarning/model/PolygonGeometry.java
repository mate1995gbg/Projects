package com.example.geowarning.model;

import java.util.List;

public class PolygonGeometry {
    private String type = "Polygon";
    private List<List<List<Double>>> coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }
}
