import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Marker } from './marker.model';
import { MarkerService } from './marker.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-marker',
    templateUrl: './marker.component.html'
})
export class MarkerComponent implements OnInit, OnDestroy {
markers: Marker[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private markerService: MarkerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.markerService.query().subscribe(
            (res: HttpResponse<Marker[]>) => {
                this.markers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMarkers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Marker) {
        return item.id;
    }
    registerChangeInMarkers() {
        this.eventSubscriber = this.eventManager.subscribe('markerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
