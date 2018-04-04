import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmcSharedModule } from '../../shared';
import {
    MarkerService,
    MarkerPopupService,
    MarkerComponent,
    MarkerDetailComponent,
    MarkerDialogComponent,
    MarkerPopupComponent,
    MarkerDeletePopupComponent,
    MarkerDeleteDialogComponent,
    markerRoute,
    markerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...markerRoute,
    ...markerPopupRoute,
];

@NgModule({
    imports: [
        TmcSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MarkerComponent,
        MarkerDetailComponent,
        MarkerDialogComponent,
        MarkerDeleteDialogComponent,
        MarkerPopupComponent,
        MarkerDeletePopupComponent,
    ],
    entryComponents: [
        MarkerComponent,
        MarkerDialogComponent,
        MarkerPopupComponent,
        MarkerDeleteDialogComponent,
        MarkerDeletePopupComponent,
    ],
    providers: [
        MarkerService,
        MarkerPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TmcMarkerModule {}
