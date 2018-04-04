/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TmcTestModule } from '../../../test.module';
import { ControlPointComponent } from '../../../../../../main/webapp/app/entities/control-point/control-point.component';
import { ControlPointService } from '../../../../../../main/webapp/app/entities/control-point/control-point.service';
import { ControlPoint } from '../../../../../../main/webapp/app/entities/control-point/control-point.model';

describe('Component Tests', () => {

    describe('ControlPoint Management Component', () => {
        let comp: ControlPointComponent;
        let fixture: ComponentFixture<ControlPointComponent>;
        let service: ControlPointService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmcTestModule],
                declarations: [ControlPointComponent],
                providers: [
                    ControlPointService
                ]
            })
            .overrideTemplate(ControlPointComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ControlPointComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ControlPointService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ControlPoint(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.controlPoints[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
