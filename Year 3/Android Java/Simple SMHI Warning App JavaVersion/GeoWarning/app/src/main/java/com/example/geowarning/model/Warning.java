package com.example.geowarning.model;

import java.util.List;

public class Warning {
    private int id;
    private Event event;
    private boolean normalProbability;
    private AreaName areaName;
    private List<Description> descriptions;
    private List<WarningAreas> warningAreas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isNormalProbability() {
        return normalProbability;
    }

    public void setNormalProbability(boolean normalProbability) {
        this.normalProbability = normalProbability;
    }

    public AreaName getAreaName() {
        return areaName;
    }

    public void setAreaName(AreaName areaName) {
        this.areaName = areaName;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public List<WarningAreas> getWarningAreas() {
        return warningAreas;
    }

    public void setWarningAreas(List<WarningAreas> warningAreas) {
        this.warningAreas = warningAreas;
    }
}
