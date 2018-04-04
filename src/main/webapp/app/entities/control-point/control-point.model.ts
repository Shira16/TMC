import { BaseEntity } from './../../shared';

export class ControlPoint implements BaseEntity {
    constructor(
        public id?: number,
        public ordinal?: string,
        public markerId?: number,
        public routeId?: number,
    ) {
    }
}
