import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RouteOne } from './route-one.model';
import { RouteOneService } from './route-one.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-route-one',
    templateUrl: './route-one.component.html'
})
export class RouteOneComponent implements OnInit, OnDestroy {
routeOnes: RouteOne[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private routeOneService: RouteOneService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.routeOneService.query().subscribe(
            (res: HttpResponse<RouteOne[]>) => {
                this.routeOnes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRouteOnes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RouteOne) {
        return item.id;
    }
    registerChangeInRouteOnes() {
        this.eventSubscriber = this.eventManager.subscribe('routeOneListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
