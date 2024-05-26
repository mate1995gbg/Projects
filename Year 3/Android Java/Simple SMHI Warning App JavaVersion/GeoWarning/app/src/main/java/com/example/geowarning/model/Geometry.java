package com.example.geowarning.model;

import java.util.List;

public class Geometry {
    private GeometryTypeEnum type;
    private List<Double> pointCoordinates;
    private List<Double> lineStringCoordinates;
    private List<List<Double>> polygonCoordinates;
    private List<List<List<Double>>> multiPolygonCoordinates;

    public GeometryTypeEnum getType() {
        return type;
    }

    public void setType(GeometryTypeEnum type) {
        this.type = type;
    }

    public List<Double> getPointCoordinates() {
        return pointCoordinates;
    }

    public void setPointCoordinates(List<Double> pointCoordinates) {
        this.pointCoordinates = pointCoordinates;
    }
    public List<Double> getLineStringCoordinates() {
        return lineStringCoordinates;
    }

    public void setLineStringCoordinates(List<Double> lineStringCoordinates) {
        this.lineStringCoordinates = lineStringCoordinates;
    }

    public List<List<Double>> getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setPolygonCoordinates(List<List<Double>> polygonCoordinates) {
        this.polygonCoordinates = polygonCoordinates;
    }

    public List<List<List<Double>>> getMultiPolygonCoordinates() {
        return multiPolygonCoordinates;
    }

    public void setMultiPolygonCoordinates(List<List<List<Double>>> multiPolygonCoordinates) {
        this.multiPolygonCoordinates = multiPolygonCoordinates;
    }
}
