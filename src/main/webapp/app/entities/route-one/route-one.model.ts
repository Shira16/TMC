import { BaseEntity } from './../../shared';

export class RouteOne implements BaseEntity {
    constructor(
        public id?: number,
        public abbr?: string,
        public name?: string,
    ) {
    }
}
