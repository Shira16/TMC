import { BaseEntity } from './../../shared';

export class AssociatedPoint implements BaseEntity {
    constructor(
        public id?: number,
        public associatedId?: number,
    ) {
    }
}
