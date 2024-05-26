class AreaName {
    en: string;
    sv: string;

    constructor(en: string = '', sv: string = ''){
        this.en = en;
        this.sv = sv;
    }

    getEn(): string | null{
        return this.en;
    }

    setEn(en: string): void{
        this.en = en;
    }

    getSv(): string | null{
        return this.sv;
    }
    
    setSv(sv: string):void{
        this.sv = sv;
    }
}
export {AreaName};