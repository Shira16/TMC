import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ControlPointComponent } from './control-point.component';
import { ControlPointDetailComponent } from './control-point-detail.component';
import { ControlPointPopupComponent } from './control-point-dialog.component';
import { ControlPointDeletePopupComponent } from './control-point-delete-dialog.component';

export const controlPointRoute: Routes = [
    {
        path: 'control-point',
        component: ControlPointComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ControlPoints'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'control-point/:id',
        component: ControlPointDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ControlPoints'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const controlPointPopupRoute: Routes = [
    {
        path: 'control-point-new',
        component: ControlPointPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ControlPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'control-point/:id/edit',
        component: ControlPointPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ControlPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'control-point/:id/delete',
        component: ControlPointDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ControlPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
