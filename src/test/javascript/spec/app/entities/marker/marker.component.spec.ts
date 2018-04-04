/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TmcTestModule } from '../../../test.module';
import { MarkerComponent } from '../../../../../../main/webapp/app/entities/marker/marker.component';
import { MarkerService } from '../../../../../../main/webapp/app/entities/marker/marker.service';
import { Marker } from '../../../../../../main/webapp/app/entities/marker/marker.model';

describe('Component Tests', () => {

    describe('Marker Management Component', () => {
        let comp: MarkerComponent;
        let fixture: ComponentFixture<MarkerComponent>;
        let service: MarkerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmcTestModule],
                declarations: [MarkerComponent],
                providers: [
                    MarkerService
                ]
            })
            .overrideTemplate(MarkerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarkerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarkerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Marker(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.markers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
