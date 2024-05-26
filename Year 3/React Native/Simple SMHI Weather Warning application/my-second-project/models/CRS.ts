import { CRProperties } from "./CRProperties";

class CRS{
    type: string;
    properties: CRProperties;

    constructor(type: string = '', properties?: CRProperties){
        this.type = type;
        this.properties = properties || new CRProperties();
    }

    getType(): string | null{
        return this.type;
    }

    setType(type: string):void{
        this.type = type;
    }

    getProperties(): CRProperties | null{
        return this.properties;
    }

    setProperties(properties: CRProperties): void{
        this.properties = properties;
    }
}
export { CRS };