import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RouteOneComponent } from './route-one.component';
import { RouteOneDetailComponent } from './route-one-detail.component';
import { RouteOnePopupComponent } from './route-one-dialog.component';
import { RouteOneDeletePopupComponent } from './route-one-delete-dialog.component';

export const routeOneRoute: Routes = [
    {
        path: 'route-one',
        component: RouteOneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RouteOnes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'route-one/:id',
        component: RouteOneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RouteOnes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const routeOnePopupRoute: Routes = [
    {
        path: 'route-one-new',
        component: RouteOnePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RouteOnes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'route-one/:id/edit',
        component: RouteOnePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RouteOnes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'route-one/:id/delete',
        component: RouteOneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RouteOnes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
