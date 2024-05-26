export enum GeometryTypeEnum {
    POINT = "Point",
    POLYGON = "Polygon",
    MULTI_POLYGON = "MultiPolygon",
    LINE_STRING = "LineString"
}

export function fromString(text: string): GeometryTypeEnum {
    const value = Object.values(GeometryTypeEnum).find(v => v.toLowerCase() === text.toLowerCase());
    if (!value) {
        throw new Error(`Unknown geometry type: ${text}`);
    }
    return value as GeometryTypeEnum;
}