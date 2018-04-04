import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { AssociatedPoint } from './associated-point.model';
import { AssociatedPointService } from './associated-point.service';

@Injectable()
export class AssociatedPointPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private associatedPointService: AssociatedPointService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.associatedPointService.find(id)
                    .subscribe((associatedPointResponse: HttpResponse<AssociatedPoint>) => {
                        const associatedPoint: AssociatedPoint = associatedPointResponse.body;
                        this.ngbModalRef = this.associatedPointModalRef(component, associatedPoint);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.associatedPointModalRef(component, new AssociatedPoint());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    associatedPointModalRef(component: Component, associatedPoint: AssociatedPoint): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.associatedPoint = associatedPoint;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
