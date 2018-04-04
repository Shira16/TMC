/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TmcTestModule } from '../../../test.module';
import { ControlPointDialogComponent } from '../../../../../../main/webapp/app/entities/control-point/control-point-dialog.component';
import { ControlPointService } from '../../../../../../main/webapp/app/entities/control-point/control-point.service';
import { ControlPoint } from '../../../../../../main/webapp/app/entities/control-point/control-point.model';
import { MarkerService } from '../../../../../../main/webapp/app/entities/marker';
import { RouteOneService } from '../../../../../../main/webapp/app/entities/route-one';

describe('Component Tests', () => {

    describe('ControlPoint Management Dialog Component', () => {
        let comp: ControlPointDialogComponent;
        let fixture: ComponentFixture<ControlPointDialogComponent>;
        let service: ControlPointService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmcTestModule],
                declarations: [ControlPointDialogComponent],
                providers: [
                    MarkerService,
                    RouteOneService,
                    ControlPointService
                ]
            })
            .overrideTemplate(ControlPointDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ControlPointDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ControlPointService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ControlPoint(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.controlPoint = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'controlPointListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ControlPoint();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.controlPoint = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'controlPointListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
