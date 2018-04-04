import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Marker } from './marker.model';
import { MarkerPopupService } from './marker-popup.service';
import { MarkerService } from './marker.service';

@Component({
    selector: 'jhi-marker-dialog',
    templateUrl: './marker-dialog.component.html'
})
export class MarkerDialogComponent implements OnInit {

    marker: Marker;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private markerService: MarkerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marker.id !== undefined) {
            this.subscribeToSaveResponse(
                this.markerService.update(this.marker));
        } else {
            this.subscribeToSaveResponse(
                this.markerService.create(this.marker));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Marker>>) {
        result.subscribe((res: HttpResponse<Marker>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Marker) {
        this.eventManager.broadcast({ name: 'markerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-marker-popup',
    template: ''
})
export class MarkerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private markerPopupService: MarkerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.markerPopupService
                    .open(MarkerDialogComponent as Component, params['id']);
            } else {
                this.markerPopupService
                    .open(MarkerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
