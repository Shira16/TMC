/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TmcTestModule } from '../../../test.module';
import { RouteOneComponent } from '../../../../../../main/webapp/app/entities/route-one/route-one.component';
import { RouteOneService } from '../../../../../../main/webapp/app/entities/route-one/route-one.service';
import { RouteOne } from '../../../../../../main/webapp/app/entities/route-one/route-one.model';

describe('Component Tests', () => {

    describe('RouteOne Management Component', () => {
        let comp: RouteOneComponent;
        let fixture: ComponentFixture<RouteOneComponent>;
        let service: RouteOneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmcTestModule],
                declarations: [RouteOneComponent],
                providers: [
                    RouteOneService
                ]
            })
            .overrideTemplate(RouteOneComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RouteOneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RouteOneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RouteOne(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.routeOnes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
