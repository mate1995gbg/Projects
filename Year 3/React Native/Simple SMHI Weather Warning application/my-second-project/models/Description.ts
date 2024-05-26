export class Description{
title: Title;
text: Text;
    constructor(title: Title, text: Text){
        this.title = title;
        this.text = text;
    }

    getTitle(){
        return this.title;
    }
    setTitle(title: Title): void{
        this.title = title;
    }
    getText(){
        return this.text;
    }
    setText(text: Text): void{
        this.text = text;
    }
}