package com.example.geowarning.model;

import java.util.List;

public class WarningAreas {
    private int id; // Unique ID for each alert
    private Area area; // GeoJSON object (you may need to create a separate class for this)
    private AreaName areaName; // AreaName object (if applicable)
    private String approximateStart; // Date string for when the warning area takes effect
    private String approximateEnd; // Date string for when the warning area expires (optional)
    private boolean normalProbability; // Describes whether there is a normal probability
    private WarningLevel warningLevel; // Warning level object
    private EventDescription eventDescription; // EventDescription object
    private String published; // Date string for when the warning area was published
    private List<Description> descriptions; // List of descriptions (if applicable)
    private List<AffectedArea> affectedAreas; // List of affected areas

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public AreaName getAreaName() {
        return areaName;
    }

    public void setAreaName(AreaName areaName) {
        this.areaName = areaName;
    }

    public String getApproximateStart() {
        return approximateStart;
    }

    public void setApproximateStart(String approximateStart) {
        this.approximateStart = approximateStart;
    }

    public String getApproximateEnd() {
        return approximateEnd;
    }

    public void setApproximateEnd(String approximateEnd) {
        this.approximateEnd = approximateEnd;
    }

    public boolean isNormalProbability() {
        return normalProbability;
    }

    public void setNormalProbability(boolean normalProbability) {
        this.normalProbability = normalProbability;
    }

    public WarningLevel getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(WarningLevel warningLevel) {
        this.warningLevel = warningLevel;
    }

    public EventDescription getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(EventDescription eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public List<AffectedArea> getAffectedAreas() {
        return affectedAreas;
    }

    public void setAffectedAreas(List<AffectedArea> affectedAreas) {
        this.affectedAreas = affectedAreas;
    }
}
