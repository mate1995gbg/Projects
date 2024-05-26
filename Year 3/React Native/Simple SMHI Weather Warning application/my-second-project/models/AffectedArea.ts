class AffectedArea {
    id: number;
    sv: string;
    en: string;

    constructor(id: number = 0, sv: string = '', en: string = ''){
        this.id = id;
        this.sv = sv;
        this.en = en;
    }

    getId(id: number | null) {
        return this.id;
    }

    setId(id: number): void {
        this.id = id;
    }

    getSv(sv: string | null) {
        return this.sv;
    }

    setSv(sv: string): void {
        this.sv = sv;
    }

    getEn(en: string | null) {
        return this.en;
    }

    setEn(en: string): void {
        this.en = en;
    }
}
