import { Area } from "./Area";
import { AreaName } from "./AreaName";
import { EventDescription } from "./EventDescription";
import { WarningLevel } from "./WarningLevel";

export class WarningAreas {
    id: Number;
    area?: Area
    areaName: AreaName;
    approximateStart: string;
    approximateEnd: string;
    normalProbability: boolean;
    warningLevel: WarningLevel;
    eventDescription: EventDescription;
    published: string;
    descriptions: any[];
    affectedAreas: any[];
    constructor(
        id:number = 0,
        area: Area = {} as Area,
        areaName?: AreaName,
        approximateStart: string = '',
        approximateEnd: string = '',
        normalProbability: boolean = false,
        warningLevel?: WarningLevel,
        eventDescription?: EventDescription,
        published: string = '',
        descriptions: any[] = [],
        affectedAreas: any[] = []
    ) {
        this.id = id;
        this.area = area;
        this.areaName = new AreaName;
        this.approximateStart = approximateStart;
        this.approximateEnd = approximateEnd;
        this.normalProbability = normalProbability;
        this.warningLevel = new WarningLevel;
        this.eventDescription = new EventDescription;
        this.published = published;
        this.descriptions = descriptions;
        this.affectedAreas = affectedAreas;
    }
    getId() {
        return this.id;
    }

    setId(value: number) {
        this.id = value;
    }

    getArea() {
        return this.area;
    }

    setArea(value: Area | undefined) {
        this.area = value;
    }

    getAreaName() {
        return this.areaName;
    }

    setAreaName(value: AreaName) {
        this.areaName = value;
    }

    getApproximateStart() {
        return this.approximateStart;
    }

    setApproximateStart(value: string) {
        this.approximateStart = value;
    }

    getApproximateEnd() {
        return this.approximateEnd;
    }

    setApproximateEnd(value: string) {
        this.approximateEnd = value;
    }

    getNormalProbability() {
        return this.normalProbability;
    }

    setNormalProbability(value: boolean) {
        this.normalProbability = value;
    }

    getWarningLevel() {
        return this.warningLevel;
    }

    setWarningLevel(value: WarningLevel) {
        this.warningLevel = value;
    }

    getEventDescription() {
        return this.eventDescription;
    }

    setEventDescription(value: EventDescription) {
        this.eventDescription = value;
    }

    getPublished() {
        return this.published;
    }

    setPublished(value: string) {
        this.published = value;
    }

    getDescriptions() {
        return this.descriptions;
    }

    setDescriptions(value: any[]) {
        this.descriptions = value;
    }

    getAffectedAreas() {
        return this.affectedAreas;
    }

    setAffectedAreas(value: any[]) {
        this.affectedAreas = value;
    }
}