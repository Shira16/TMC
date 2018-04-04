import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AssociatedPoint } from './associated-point.model';
import { AssociatedPointPopupService } from './associated-point-popup.service';
import { AssociatedPointService } from './associated-point.service';
import { ControlPoint, ControlPointService } from '../control-point';

@Component({
    selector: 'jhi-associated-point-dialog',
    templateUrl: './associated-point-dialog.component.html'
})
export class AssociatedPointDialogComponent implements OnInit {

    associatedPoint: AssociatedPoint;
    isSaving: boolean;

    controlpoints: ControlPoint[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private associatedPointService: AssociatedPointService,
        private controlPointService: ControlPointService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.controlPointService.query()
            .subscribe((res: HttpResponse<ControlPoint[]>) => { this.controlpoints = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.associatedPoint.id !== undefined) {
            this.subscribeToSaveResponse(
                this.associatedPointService.update(this.associatedPoint));
        } else {
            this.subscribeToSaveResponse(
                this.associatedPointService.create(this.associatedPoint));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AssociatedPoint>>) {
        result.subscribe((res: HttpResponse<AssociatedPoint>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AssociatedPoint) {
        this.eventManager.broadcast({ name: 'associatedPointListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackControlPointById(index: number, item: ControlPoint) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-associated-point-popup',
    template: ''
})
export class AssociatedPointPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private associatedPointPopupService: AssociatedPointPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.associatedPointPopupService
                    .open(AssociatedPointDialogComponent as Component, params['id']);
            } else {
                this.associatedPointPopupService
                    .open(AssociatedPointDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
