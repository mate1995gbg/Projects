class LocalizedText {
    sv: string;
    en: string;
    constructor(sv: string = '', en: string = ''){
        this.sv = sv;
        this.en = en;
    }

    getSv(): string | null{
        return this.sv;
    }
    
    setSv(sv: string):void{
        this.sv = sv;
    }
    
    getEn(): string | null{
        return this.en;
    }

    setEn(en: string): void{
        this.en = en;
    }
}
export {LocalizedText};