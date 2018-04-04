/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { TmcTestModule } from '../../../test.module';
import { MarkerDetailComponent } from '../../../../../../main/webapp/app/entities/marker/marker-detail.component';
import { MarkerService } from '../../../../../../main/webapp/app/entities/marker/marker.service';
import { Marker } from '../../../../../../main/webapp/app/entities/marker/marker.model';

describe('Component Tests', () => {

    describe('Marker Management Detail Component', () => {
        let comp: MarkerDetailComponent;
        let fixture: ComponentFixture<MarkerDetailComponent>;
        let service: MarkerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TmcTestModule],
                declarations: [MarkerDetailComponent],
                providers: [
                    MarkerService
                ]
            })
            .overrideTemplate(MarkerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarkerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarkerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Marker(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.marker).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
