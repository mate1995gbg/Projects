import { CRS } from "./CRS";

class GeoJSON {

    type: string;
    features: any[];
    crs: CRS;

    constructor(type: string ='', features: any[] = [], crs?: CRS ){
        this.type = type;
        this.features = features;
        this.crs = new CRS();
    }

    getType(type: string | null){
        return this.type;
    }

    setType(type:string): void{
        this.type = type;
    }

    getFeatures(features: any[] | null){
        return this.features;
    }

    setFeatures(features: any[]): void{
        this.features = features;
    }

    getCrs(crs: CRS){
        return this.crs;
    }

    setCrs(crs: CRS): void{
        this.crs = crs;
    }
}