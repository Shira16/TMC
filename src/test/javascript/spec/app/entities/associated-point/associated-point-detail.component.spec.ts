/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TmcTestModule } from '../../../test.module';
import { AssociatedPointDetailComponent } from '../../../../../../main/webapp/app/entities/associated-point/associated-point-detail.component';
import { AssociatedPointService } from '../../../../../../main/webapp/app/entities/associated-point/associated-point.service';
import { AssociatedPoint } from '../../../../../../main/webapp/app/entities/associated-point/associated-point.model';

describe('Component Tests', () => {

    describe('AssociatedPoint Management Detail Component', () => {
        let comp: AssociatedPointDetailComponent;
        let fixture: ComponentFixture<AssociatedPointDetailComponent>;
        let service: AssociatedPointService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmcTestModule],
                declarations: [AssociatedPointDetailComponent],
                providers: [
                    AssociatedPointService
                ]
            })
            .overrideTemplate(AssociatedPointDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssociatedPointDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssociatedPointService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AssociatedPoint(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.associatedPoint).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
