import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TmcSharedModule } from '../../shared';
import {
    RouteOneService,
    RouteOnePopupService,
    RouteOneComponent,
    RouteOneDetailComponent,
    RouteOneDialogComponent,
    RouteOnePopupComponent,
    RouteOneDeletePopupComponent,
    RouteOneDeleteDialogComponent,
    routeOneRoute,
    routeOnePopupRoute,
} from './';

const ENTITY_STATES = [
    ...routeOneRoute,
    ...routeOnePopupRoute,
];

@NgModule({
    imports: [
        TmcSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RouteOneComponent,
        RouteOneDetailComponent,
        RouteOneDialogComponent,
        RouteOneDeleteDialogComponent,
        RouteOnePopupComponent,
        RouteOneDeletePopupComponent,
    ],
    entryComponents: [
        RouteOneComponent,
        RouteOneDialogComponent,
        RouteOnePopupComponent,
        RouteOneDeleteDialogComponent,
        RouteOneDeletePopupComponent,
    ],
    providers: [
        RouteOneService,
        RouteOnePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TmcRouteOneModule {}
