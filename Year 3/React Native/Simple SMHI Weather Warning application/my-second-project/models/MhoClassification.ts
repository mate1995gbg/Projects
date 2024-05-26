export class MhoClassification {
    private en: string | null;
    private sv: string | null;
    private code: string | null;

    constructor(en: string | null = null, sv: string | null = null, code: string | null = null) {
        this.en = en;
        this.sv = sv;
        this.code = code;
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
}