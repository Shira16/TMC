import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmcSharedModule } from '../../shared';
import {
    ControlPointService,
    ControlPointPopupService,
    ControlPointComponent,
    ControlPointDetailComponent,
    ControlPointDialogComponent,
    ControlPointPopupComponent,
    ControlPointDeletePopupComponent,
    ControlPointDeleteDialogComponent,
    controlPointRoute,
    controlPointPopupRoute,
} from './';

const ENTITY_STATES = [
    ...controlPointRoute,
    ...controlPointPopupRoute,
];

@NgModule({
    imports: [
        TmcSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ControlPointComponent,
        ControlPointDetailComponent,
        ControlPointDialogComponent,
        ControlPointDeleteDialogComponent,
        ControlPointPopupComponent,
        ControlPointDeletePopupComponent,
    ],
    entryComponents: [
        ControlPointComponent,
        ControlPointDialogComponent,
        ControlPointPopupComponent,
        ControlPointDeleteDialogComponent,
        ControlPointDeletePopupComponent,
    ],
    providers: [
        ControlPointService,
        ControlPointPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TmcControlPointModule {}
