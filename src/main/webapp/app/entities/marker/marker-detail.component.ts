import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Marker } from './marker.model';
import { MarkerService } from './marker.service';

@Component({
    selector: 'jhi-marker-detail',
    templateUrl: './marker-detail.component.html'
})
export class MarkerDetailComponent implements OnInit, OnDestroy {

    marker: Marker;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private markerService: MarkerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarkers();
    }

    load(id) {
        this.markerService.find(id)
            .subscribe((markerResponse: HttpResponse<Marker>) => {
                this.marker = markerResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarkers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'markerListModification',
            (response) => this.load(this.marker.id)
        );
    }
}
