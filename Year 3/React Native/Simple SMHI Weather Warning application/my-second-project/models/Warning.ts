import { Event } from "./Event";
import { AreaName } from "./AreaName";
import { Description } from "./Description";
import { WarningAreas } from "./WarningAreas";

event: Event;  // Placeholder. Please replace with the correct type.
areaName: AreaName;  // Placeholder. Please replace with the correct type.
descriptions: Description;  // Placeholder. Please replace with the correct type.
warningAreas: WarningAreas;  // Placeholder. Please replace with the correct type.

export class Warning {
    id: number;
    event: Event;
    normalProbability: boolean;
    areaName: AreaName;
    descriptions: Description[];
    warningAreas: WarningAreas[];

    constructor() {
        this.id = 0;
        this.event = {} as Event;
        this.normalProbability = false;
        this.areaName = {} as AreaName;
        this.descriptions = [];
        this.warningAreas = [];
    }

    getId() {
        return this.id;
    }
    setId(id:number):void {
        this.id = id;
    }

    getEvent() {
        return this.event;
    }
    setEvent(event: Event): void{
        this.event = event;
    }

    getNormalProbability(){
        return this.normalProbability;
    }
    setNormalProbability(normalProbability: boolean){
        this.normalProbability = normalProbability;
    }

    getAreaName(){
        return this.areaName;
    }
    setAreaName(areaName: AreaName):void{
        this.areaName = areaName;
    }

    getDescriptions(){
        return this.descriptions;
    }
    setDescriptions(descriptions: Description[]):void{
        this.descriptions = descriptions;
    }

    getWarningAreas(){
        return this.warningAreas;
    }
    setWarningAreas(warningAreas: WarningAreas[]):void{
        this.warningAreas = warningAreas;
    }
}