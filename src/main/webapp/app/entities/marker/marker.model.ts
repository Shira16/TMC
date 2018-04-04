import { BaseEntity } from './../../shared';

export class Marker implements BaseEntity {
    constructor(
        public id?: number,
        public pointX?: number,
        public pointY?: number,
        public markerCode?: string,
        public beaconCode?: string,
    ) {
    }
}
