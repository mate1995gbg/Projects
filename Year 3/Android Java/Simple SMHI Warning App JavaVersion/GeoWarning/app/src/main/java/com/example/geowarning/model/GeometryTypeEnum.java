package com.example.geowarning.model;

public enum GeometryTypeEnum {
    POINT("Point"),
    POLYGON("Polygon"),
    MULTI_POLYGON("MultiPolygon"),
    LINE_STRING("LineString");

    private final String value;

    GeometryTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GeometryTypeEnum fromString(String text) {
        for (GeometryTypeEnum type : GeometryTypeEnum.values()) {
            if (type.value.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown geometry type: " + text);
    }
}
