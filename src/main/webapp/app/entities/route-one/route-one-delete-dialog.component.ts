import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RouteOne } from './route-one.model';
import { RouteOnePopupService } from './route-one-popup.service';
import { RouteOneService } from './route-one.service';

@Component({
    selector: 'jhi-route-one-delete-dialog',
    templateUrl: './route-one-delete-dialog.component.html'
})
export class RouteOneDeleteDialogComponent {

    routeOne: RouteOne;

    constructor(
        private routeOneService: RouteOneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.routeOneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'routeOneListModification',
                content: 'Deleted an routeOne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-route-one-delete-popup',
    template: ''
})
export class RouteOneDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private routeOnePopupService: RouteOnePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.routeOnePopupService
                .open(RouteOneDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
