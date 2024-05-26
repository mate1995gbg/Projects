class WarningLevel {
    sv: string;
    en: string;
    code: string;

    constructor(sv: string = '', en: string = '', code: string = '') {
        this.sv = sv;
        this.en = en;
        this.code = code;
    }

    getSv(sv: string){
        return this.sv;
    }

    setSv(sv:string): void{
        this.sv = sv;
    }

    getEn(en: string){
        return this.en;
    }

    setEn(en:string): void{
        this.en = en;
    }

    getCode(code: string){
        return this.code;
    }

    setCode(code:string): void{
        this.code = code;
    }
}
export {WarningLevel};