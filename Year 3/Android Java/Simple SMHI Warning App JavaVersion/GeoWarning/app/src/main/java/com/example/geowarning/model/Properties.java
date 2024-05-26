package com.example.geowarning.model;

import java.util.Map;

public class Properties {
    private int id;
    private int dvoid;
    private int baroid;
    private String baroNameEn;
    private String baroNameSv;
    private String en;
    private String sv;
    private String code;
    private boolean tovepolygon;
    private Map<String, Object> additionalProperties; // For extra, potentially variable fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }



    // Getters and setters for common fields
    public int getDvoid() {
        return dvoid;
    }

    public void setDvoid(int dvoid) {
        this.dvoid = dvoid;
    }

    public int getBaroid() {
        return baroid;
    }

    public void setBaroid(int baroid) {
        this.baroid = baroid;
    }

    public String getBaroNameEn() {
        return baroNameEn;
    }

    public void setBaroNameEn(String baroNameEn) {
        this.baroNameEn = baroNameEn;
    }

    public String getBaroNameSv() {
        return baroNameSv;
    }

    public void setBaroNameSv(String baroNameSv) {
        this.baroNameSv = baroNameSv;
    }

    // Getter and setter for additional properties
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public boolean isTovepolygon() {
        return tovepolygon;
    }

    public void setTovepolygon(boolean tovepolygon) {
        this.tovepolygon = tovepolygon;
    }
}
