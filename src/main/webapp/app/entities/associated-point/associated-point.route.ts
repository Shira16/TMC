import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AssociatedPointComponent } from './associated-point.component';
import { AssociatedPointDetailComponent } from './associated-point-detail.component';
import { AssociatedPointPopupComponent } from './associated-point-dialog.component';
import { AssociatedPointDeletePopupComponent } from './associated-point-delete-dialog.component';

export const associatedPointRoute: Routes = [
    {
        path: 'associated-point',
        component: AssociatedPointComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AssociatedPoints'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'associated-point/:id',
        component: AssociatedPointDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AssociatedPoints'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const associatedPointPopupRoute: Routes = [
    {
        path: 'associated-point-new',
        component: AssociatedPointPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AssociatedPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'associated-point/:id/edit',
        component: AssociatedPointPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AssociatedPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'associated-point/:id/delete',
        component: AssociatedPointDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AssociatedPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
