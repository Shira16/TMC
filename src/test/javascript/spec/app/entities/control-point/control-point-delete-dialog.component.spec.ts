/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TmcTestModule } from '../../../test.module';
import { ControlPointDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/control-point/control-point-delete-dialog.component';
import { ControlPointService } from '../../../../../../main/webapp/app/entities/control-point/control-point.service';

describe('Component Tests', () => {

    describe('ControlPoint Management Delete Component', () => {
        let comp: ControlPointDeleteDialogComponent;
        let fixture: ComponentFixture<ControlPointDeleteDialogComponent>;
        let service: ControlPointService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmcTestModule],
                declarations: [ControlPointDeleteDialogComponent],
                providers: [
                    ControlPointService
                ]
            })
            .overrideTemplate(ControlPointDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ControlPointDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ControlPointService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
