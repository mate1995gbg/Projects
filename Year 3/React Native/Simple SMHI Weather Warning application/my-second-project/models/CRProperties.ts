export class CRProperties{
    name: string;
    
    constructor(name: string =''){
        this.name = name;
    }

    getName(): string | null{
        return this.name;
    }

    setName(name: string): void{
        this.name = name;
    }
}