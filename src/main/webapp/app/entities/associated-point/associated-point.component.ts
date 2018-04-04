import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AssociatedPoint } from './associated-point.model';
import { AssociatedPointService } from './associated-point.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-associated-point',
    templateUrl: './associated-point.component.html'
})
export class AssociatedPointComponent implements OnInit, OnDestroy {
associatedPoints: AssociatedPoint[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private associatedPointService: AssociatedPointService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.associatedPointService.query().subscribe(
            (res: HttpResponse<AssociatedPoint[]>) => {
                this.associatedPoints = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAssociatedPoints();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AssociatedPoint) {
        return item.id;
    }
    registerChangeInAssociatedPoints() {
        this.eventSubscriber = this.eventManager.subscribe('associatedPointListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
