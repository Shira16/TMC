import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ControlPoint } from './control-point.model';
import { ControlPointService } from './control-point.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-control-point',
    templateUrl: './control-point.component.html'
})
export class ControlPointComponent implements OnInit, OnDestroy {
controlPoints: ControlPoint[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private controlPointService: ControlPointService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.controlPointService.query().subscribe(
            (res: HttpResponse<ControlPoint[]>) => {
                this.controlPoints = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInControlPoints();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ControlPoint) {
        return item.id;
    }
    registerChangeInControlPoints() {
        this.eventSubscriber = this.eventManager.subscribe('controlPointListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
