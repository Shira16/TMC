/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TmcTestModule } from '../../../test.module';
import { AssociatedPointComponent } from '../../../../../../main/webapp/app/entities/associated-point/associated-point.component';
import { AssociatedPointService } from '../../../../../../main/webapp/app/entities/associated-point/associated-point.service';
import { AssociatedPoint } from '../../../../../../main/webapp/app/entities/associated-point/associated-point.model';

describe('Component Tests', () => {

    describe('AssociatedPoint Management Component', () => {
        let comp: AssociatedPointComponent;
        let fixture: ComponentFixture<AssociatedPointComponent>;
        let service: AssociatedPointService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmcTestModule],
                declarations: [AssociatedPointComponent],
                providers: [
                    AssociatedPointService
                ]
            })
            .overrideTemplate(AssociatedPointComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssociatedPointComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssociatedPointService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AssociatedPoint(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.associatedPoints[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
