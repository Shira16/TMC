/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TmcTestModule } from '../../../test.module';
import { RouteOneDetailComponent } from '../../../../../../main/webapp/app/entities/route-one/route-one-detail.component';
import { RouteOneService } from '../../../../../../main/webapp/app/entities/route-one/route-one.service';
import { RouteOne } from '../../../../../../main/webapp/app/entities/route-one/route-one.model';

describe('Component Tests', () => {

    describe('RouteOne Management Detail Component', () => {
        let comp: RouteOneDetailComponent;
        let fixture: ComponentFixture<RouteOneDetailComponent>;
        let service: RouteOneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmcTestModule],
                declarations: [RouteOneDetailComponent],
                providers: [
                    RouteOneService
                ]
            })
            .overrideTemplate(RouteOneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RouteOneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RouteOneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new RouteOne(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.routeOne).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
