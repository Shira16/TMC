import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ControlPoint } from './control-point.model';
import { ControlPointPopupService } from './control-point-popup.service';
import { ControlPointService } from './control-point.service';

@Component({
    selector: 'jhi-control-point-delete-dialog',
    templateUrl: './control-point-delete-dialog.component.html'
})
export class ControlPointDeleteDialogComponent {

    controlPoint: ControlPoint;

    constructor(
        private controlPointService: ControlPointService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.controlPointService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'controlPointListModification',
                content: 'Deleted an controlPoint'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-control-point-delete-popup',
    template: ''
})
export class ControlPointDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private controlPointPopupService: ControlPointPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.controlPointPopupService
                .open(ControlPointDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
