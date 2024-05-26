export class Text{
    sv:string;
    en:string;

    constructor(sv: string = '', en:string = '') {
        this.sv = sv;
        this.en = en;
    }

    getEn(en: string){
        return this.en;
    }

    setEn(en: string): void{
        this.en = en;
    }

    getSv(sv: string){
        return this.sv;
    }

    setSv(sv: string): void{
        this.sv = sv;
    }
}