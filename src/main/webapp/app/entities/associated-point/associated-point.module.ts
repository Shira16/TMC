import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmcSharedModule } from '../../shared';
import {
    AssociatedPointService,
    AssociatedPointPopupService,
    AssociatedPointComponent,
    AssociatedPointDetailComponent,
    AssociatedPointDialogComponent,
    AssociatedPointPopupComponent,
    AssociatedPointDeletePopupComponent,
    AssociatedPointDeleteDialogComponent,
    associatedPointRoute,
    associatedPointPopupRoute,
} from './';

const ENTITY_STATES = [
    ...associatedPointRoute,
    ...associatedPointPopupRoute,
];

@NgModule({
    imports: [
        TmcSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AssociatedPointComponent,
        AssociatedPointDetailComponent,
        AssociatedPointDialogComponent,
        AssociatedPointDeleteDialogComponent,
        AssociatedPointPopupComponent,
        AssociatedPointDeletePopupComponent,
    ],
    entryComponents: [
        AssociatedPointComponent,
        AssociatedPointDialogComponent,
        AssociatedPointPopupComponent,
        AssociatedPointDeleteDialogComponent,
        AssociatedPointDeletePopupComponent,
    ],
    providers: [
        AssociatedPointService,
        AssociatedPointPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TmcAssociatedPointModule {}
