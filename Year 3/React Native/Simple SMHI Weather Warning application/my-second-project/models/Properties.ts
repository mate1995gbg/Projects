class Properties {
    private id: number | null;
    private dvoid: number | null;
    private baroid: number | null;
    private baroNameEn: string | null;
    private baroNameSv: string | null;
    private en: string | null;
    private sv: string | null;
    private code: string | null;
    private tovepolygon: boolean;
    private additionalProperties: { [key: string]: any };

    constructor(id: number | null = 0, 
                dvoid: number | null = 0, 
                baroid: number | null = 0, 
                baroNameEn: string | null = null, 
                baroNameSv: string | null = null, 
                en: string | null = null, 
                sv: string | null = null, 
                code: string | null = null, 
                tovepolygon: boolean = false, 
                additionalProperties: { [key: string]: any } = {}) {
        this.id = id;
        this.dvoid = dvoid;
        this.baroid = baroid;
        this.baroNameEn = baroNameEn;
        this.baroNameSv = baroNameSv;
        this.en = en;
        this.sv = sv;
        this.code = code;
        this.tovepolygon = tovepolygon;
        this.additionalProperties = additionalProperties;
    }

    getId(): number | null {
        return this.id;
    }

    setId(id: number | null): void {
        this.id = id;
    }

    getDvoid(): number | null {
        return this.dvoid;
    }

    setDvoid(dvoid: number | null): void {
        this.dvoid = dvoid;
    }

    getBaroid(): number | null {
        return this.baroid;
    }

    setBaroid(baroid: number | null): void {
        this.baroid = baroid;
    }

    getBaroNameEn(): string | null {
        return this.baroNameEn;
    }

    setBaroNameEn(baroNameEn: string | null): void {
        this.baroNameEn = baroNameEn;
    }

    getBaroNameSv(): string | null {
        return this.baroNameSv;
    }

    setBaroNameSv(baroNameSv: string | null): void {
        this.baroNameSv = baroNameSv;
    }

    getEn(): string | null {
        return this.en;
    }

    setEn(en: string | null): void {
        this.en = en;
    }

    getSv(): string | null {
        return this.sv;
    }

    setSv(sv: string | null): void {
        this.sv = sv;
    }

    getCode(): string | null {
        return this.code;
    }

    setCode(code: string | null): void {
        this.code = code;
    }

    getTovepolygon(): boolean {
        return this.tovepolygon;
    }

    setTovepolygon(tovepolygon: boolean): void {
        this.tovepolygon = tovepolygon;
    }

    getAdditionalProperties(): { [key: string]: any } {
        return this.additionalProperties;
    }

    setAdditionalProperties(additionalProperties: { [key: string]: any }): void {
        this.additionalProperties = additionalProperties;
    } 
}