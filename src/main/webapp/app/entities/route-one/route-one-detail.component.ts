import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RouteOne } from './route-one.model';
import { RouteOneService } from './route-one.service';

@Component({
    selector: 'jhi-route-one-detail',
    templateUrl: './route-one-detail.component.html'
})
export class RouteOneDetailComponent implements OnInit, OnDestroy {

    routeOne: RouteOne;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private routeOneService: RouteOneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRouteOnes();
    }

    load(id) {
        this.routeOneService.find(id)
            .subscribe((routeOneResponse: HttpResponse<RouteOne>) => {
                this.routeOne = routeOneResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRouteOnes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'routeOneListModification',
            (response) => this.load(this.routeOne.id)
        );
    }
}
