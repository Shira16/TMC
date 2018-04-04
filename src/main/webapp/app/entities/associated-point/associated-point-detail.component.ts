import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AssociatedPoint } from './associated-point.model';
import { AssociatedPointService } from './associated-point.service';

@Component({
    selector: 'jhi-associated-point-detail',
    templateUrl: './associated-point-detail.component.html'
})
export class AssociatedPointDetailComponent implements OnInit, OnDestroy {

    associatedPoint: AssociatedPoint;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private associatedPointService: AssociatedPointService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAssociatedPoints();
    }

    load(id) {
        this.associatedPointService.find(id)
            .subscribe((associatedPointResponse: HttpResponse<AssociatedPoint>) => {
                this.associatedPoint = associatedPointResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAssociatedPoints() {
        this.eventSubscriber = this.eventManager.subscribe(
            'associatedPointListModification',
            (response) => this.load(this.associatedPoint.id)
        );
    }
}
