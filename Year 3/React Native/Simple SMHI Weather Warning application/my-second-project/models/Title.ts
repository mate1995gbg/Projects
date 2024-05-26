class Title{
    sv: string;
    en: string;
    code: string;

    constructor(sv: string = '', en: string = '', code: string = ''){
        this.sv = sv;
        this.en = en;
        this.code = code;
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

    getCode() {
        return this.code;
    }

    setCode(code: string): void {
        this.code = code;
    }
    
}