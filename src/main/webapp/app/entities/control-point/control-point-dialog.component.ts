import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ControlPoint } from './control-point.model';
import { ControlPointPopupService } from './control-point-popup.service';
import { ControlPointService } from './control-point.service';
import { Marker, MarkerService } from '../marker';
import { RouteOne, RouteOneService } from '../route-one';

@Component({
    selector: 'jhi-control-point-dialog',
    templateUrl: './control-point-dialog.component.html'
})
export class ControlPointDialogComponent implements OnInit {

    controlPoint: ControlPoint;
    isSaving: boolean;

    markers: Marker[];

    routeones: RouteOne[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private controlPointService: ControlPointService,
        private markerService: MarkerService,
        private routeOneService: RouteOneService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.markerService.query()
            .subscribe((res: HttpResponse<Marker[]>) => { this.markers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.routeOneService.query()
            .subscribe((res: HttpResponse<RouteOne[]>) => { this.routeones = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.controlPoint.id !== undefined) {
            this.subscribeToSaveResponse(
                this.controlPointService.update(this.controlPoint));
        } else {
            this.subscribeToSaveResponse(
                this.controlPointService.create(this.controlPoint));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ControlPoint>>) {
        result.subscribe((res: HttpResponse<ControlPoint>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ControlPoint) {
        this.eventManager.broadcast({ name: 'controlPointListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMarkerById(index: number, item: Marker) {
        return item.id;
    }

    trackRouteOneById(index: number, item: RouteOne) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-control-point-popup',
    template: ''
})
export class ControlPointPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private controlPointPopupService: ControlPointPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.controlPointPopupService
                    .open(ControlPointDialogComponent as Component, params['id']);
            } else {
                this.controlPointPopupService
                    .open(ControlPointDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
