export class Geometry {
    private type: string | null;
    private pointCoordinates: any[];
    private lineStringCoordinates: any[];
    private polygonCoordinates: any[];
    private multiPolygonCoordinates: any[];

    constructor(type: string | null = null, 
                pointCoordinates: any[] = [], 
                lineStringCoordinates: any[] = [], 
                polygonCoordinates: any[] = [], 
                multiPolygonCoordinates: any[] = []) {
        this.type = type;
        this.pointCoordinates = pointCoordinates;
        this.lineStringCoordinates = lineStringCoordinates;
        this.polygonCoordinates = polygonCoordinates;
        this.multiPolygonCoordinates = multiPolygonCoordinates;
    }

    getType(): string | null {
        return this.type;
    }

    setType(type: string | null): void {
        this.type = type;
    }

    getPointCoordinates(): any[] {
        return this.pointCoordinates;
    }

    setPointCoordinates(pointCoordinates: any[]): void {
        this.pointCoordinates = pointCoordinates;
    }

    getLineStringCoordinates(): any[] {
        return this.lineStringCoordinates;
    }

    setLineStringCoordinates(lineStringCoordinates: any[]): void {
        this.lineStringCoordinates = lineStringCoordinates;
    }

    getPolygonCoordinates(): any[] {
        return this.polygonCoordinates;
    }

    setPolygonCoordinates(polygonCoordinates: any[]): void {
        this.polygonCoordinates = polygonCoordinates;
    }

    getMultiPolygonCoordinates(): any[] {
        return this.multiPolygonCoordinates;
    }

    setMultiPolygonCoordinates(multiPolygonCoordinates: any[]): void {
        this.multiPolygonCoordinates = multiPolygonCoordinates;
    }
}
