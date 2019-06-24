import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
  Pp_userComponent,
  Pp_userDetailComponent,
  Pp_userUpdateComponent,
  Pp_userDeletePopupComponent,
  Pp_userDeleteDialogComponent,
  pp_userRoute,
  pp_userPopupRoute
} from './';

const ENTITY_STATES = [...pp_userRoute, ...pp_userPopupRoute];

@NgModule({
  imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    Pp_userComponent,
    Pp_userDetailComponent,
    Pp_userUpdateComponent,
    Pp_userDeleteDialogComponent,
    Pp_userDeletePopupComponent
  ],
  entryComponents: [Pp_userComponent, Pp_userUpdateComponent, Pp_userDeleteDialogComponent, Pp_userDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Microservice1Pp_userModule {}
