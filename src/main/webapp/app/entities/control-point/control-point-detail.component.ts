import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ControlPoint } from './control-point.model';
import { ControlPointService } from './control-point.service';

@Component({
    selector: 'jhi-control-point-detail',
    templateUrl: './control-point-detail.component.html'
})
export class ControlPointDetailComponent implements OnInit, OnDestroy {

    controlPoint: ControlPoint;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private controlPointService: ControlPointService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInControlPoints();
    }

    load(id) {
        this.controlPointService.find(id)
            .subscribe((controlPointResponse: HttpResponse<ControlPoint>) => {
                this.controlPoint = controlPointResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInControlPoints() {
        this.eventSubscriber = this.eventManager.subscribe(
            'controlPointListModification',
            (response) => this.load(this.controlPoint.id)
        );
    }
}
