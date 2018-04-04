import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RouteOne } from './route-one.model';
import { RouteOnePopupService } from './route-one-popup.service';
import { RouteOneService } from './route-one.service';

@Component({
    selector: 'jhi-route-one-dialog',
    templateUrl: './route-one-dialog.component.html'
})
export class RouteOneDialogComponent implements OnInit {

    routeOne: RouteOne;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private routeOneService: RouteOneService,
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
        if (this.routeOne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.routeOneService.update(this.routeOne));
        } else {
            this.subscribeToSaveResponse(
                this.routeOneService.create(this.routeOne));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RouteOne>>) {
        result.subscribe((res: HttpResponse<RouteOne>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RouteOne) {
        this.eventManager.broadcast({ name: 'routeOneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-route-one-popup',
    template: ''
})
export class RouteOnePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private routeOnePopupService: RouteOnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.routeOnePopupService
                    .open(RouteOneDialogComponent as Component, params['id']);
            } else {
                this.routeOnePopupService
                    .open(RouteOneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
