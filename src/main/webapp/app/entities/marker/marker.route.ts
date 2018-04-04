import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MarkerComponent } from './marker.component';
import { MarkerDetailComponent } from './marker-detail.component';
import { MarkerPopupComponent } from './marker-dialog.component';
import { MarkerDeletePopupComponent } from './marker-delete-dialog.component';

export const markerRoute: Routes = [
    {
        path: 'marker',
        component: MarkerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Markers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'marker/:id',
        component: MarkerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Markers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const markerPopupRoute: Routes = [
    {
        path: 'marker-new',
        component: MarkerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Markers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'marker/:id/edit',
        component: MarkerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Markers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'marker/:id/delete',
        component: MarkerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Markers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
