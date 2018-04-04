import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Marker } from './marker.model';
import { MarkerPopupService } from './marker-popup.service';
import { MarkerService } from './marker.service';

@Component({
    selector: 'jhi-marker-delete-dialog',
    templateUrl: './marker-delete-dialog.component.html'
})
export class MarkerDeleteDialogComponent {

    marker: Marker;

    constructor(
        private markerService: MarkerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.markerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'markerListModification',
                content: 'Deleted an marker'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-marker-delete-popup',
    template: ''
})
export class MarkerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private markerPopupService: MarkerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.markerPopupService
                .open(MarkerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
