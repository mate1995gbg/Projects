class LineStringGeometry {
    private type: string;
    private coordinates: any[];

    constructor(type: string = "LineString", coordinates: any[] = []) {
        this.type = type;
        this.coordinates = coordinates;
    }

    getType(): string {
        return this.type;
    }

    setType(type: string): void {
        this.type = type;
    }

    getCoordinates(): any[] {
        return this.coordinates;
    }

    setCoordinates(coordinates: any[]): void {
        this.coordinates = coordinates;
    }
}