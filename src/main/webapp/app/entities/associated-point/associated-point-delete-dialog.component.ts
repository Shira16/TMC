import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AssociatedPoint } from './associated-point.model';
import { AssociatedPointPopupService } from './associated-point-popup.service';
import { AssociatedPointService } from './associated-point.service';

@Component({
    selector: 'jhi-associated-point-delete-dialog',
    templateUrl: './associated-point-delete-dialog.component.html'
})
export class AssociatedPointDeleteDialogComponent {

    associatedPoint: AssociatedPoint;

    constructor(
        private associatedPointService: AssociatedPointService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.associatedPointService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'associatedPointListModification',
                content: 'Deleted an associatedPoint'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-associated-point-delete-popup',
    template: ''
})
export class AssociatedPointDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private associatedPointPopupService: AssociatedPointPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.associatedPointPopupService
                .open(AssociatedPointDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
