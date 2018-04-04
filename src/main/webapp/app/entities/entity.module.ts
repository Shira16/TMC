import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TmcMarkerModule } from './marker/marker.module';
import { TmcRouteOneModule } from './route-one/route-one.module';
import { TmcControlPointModule } from './control-point/control-point.module';
import { TmcAssociatedPointModule } from './associated-point/associated-point.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TmcMarkerModule,
        TmcRouteOneModule,
        TmcControlPointModule,
        TmcAssociatedPointModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TmcEntityModule {}
