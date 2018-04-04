/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TmcTestModule } from '../../../test.module';
import { ControlPointDetailComponent } from '../../../../../../main/webapp/app/entities/control-point/control-point-detail.component';
import { ControlPointService } from '../../../../../../main/webapp/app/entities/control-point/control-point.service';
import { ControlPoint } from '../../../../../../main/webapp/app/entities/control-point/control-point.model';

describe('Component Tests', () => {

    describe('ControlPoint Management Detail Component', () => {
        let comp: ControlPointDetailComponent;
        let fixture: ComponentFixture<ControlPointDetailComponent>;
        let service: ControlPointService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmcTestModule],
                declarations: [ControlPointDetailComponent],
                providers: [
                    ControlPointService
                ]
            })
            .overrideTemplate(ControlPointDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ControlPointDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ControlPointService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ControlPoint(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.controlPoint).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
