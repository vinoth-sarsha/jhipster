import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pp_user } from 'app/shared/model/microservice1/pp-user.model';
import { Pp_userService } from './pp-user.service';
import { Pp_userComponent } from './pp-user.component';
import { Pp_userDetailComponent } from './pp-user-detail.component';
import { Pp_userUpdateComponent } from './pp-user-update.component';
import { Pp_userDeletePopupComponent } from './pp-user-delete-dialog.component';
import { IPp_user } from 'app/shared/model/microservice1/pp-user.model';

@Injectable({ providedIn: 'root' })
export class Pp_userResolve implements Resolve<IPp_user> {
  constructor(private service: Pp_userService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPp_user> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pp_user>) => response.ok),
        map((pp_user: HttpResponse<Pp_user>) => pp_user.body)
      );
    }
    return of(new Pp_user());
  }
}

export const pp_userRoute: Routes = [
  {
    path: '',
    component: Pp_userComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pp_users'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: Pp_userDetailComponent,
    resolve: {
      pp_user: Pp_userResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pp_users'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: Pp_userUpdateComponent,
    resolve: {
      pp_user: Pp_userResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pp_users'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: Pp_userUpdateComponent,
    resolve: {
      pp_user: Pp_userResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pp_users'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pp_userPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: Pp_userDeletePopupComponent,
    resolve: {
      pp_user: Pp_userResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pp_users'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
