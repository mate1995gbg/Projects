import { MhoClassification } from './MhoClassification';

class Event {
    en: string;
    sv: string;
    code: string;
    mhoClassification: MhoClassification;

    constructor(en: string = '', sv: string = '', code: string = '', mhoClassification?: MhoClassification) {
        this.en = en;
        this.sv = sv;
        this.code = code;
        this.mhoClassification = mhoClassification || new MhoClassification();
    }

    getEn(): string | null {
        return this.en;
    }

    setEn(en: string): void {
        this.en = en;
    }

    getSv(): string | null {
        return this.sv;
    }

    setSv(sv: string): void {
        this.sv = sv;
    }

    getCode(): string | null {
        return this.code;
    }

    setCode(code: string): void {
        this.code = code;
    }

    getMhoClassification():MhoClassification | null {
        return this.mhoClassification;
    }

    setMhoClassification(mhoClassification: MhoClassification): void {
        this.mhoClassification = mhoClassification;
    }
}
export {Event};