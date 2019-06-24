import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPp_user } from 'app/shared/model/microservice1/pp-user.model';

type EntityResponseType = HttpResponse<IPp_user>;
type EntityArrayResponseType = HttpResponse<IPp_user[]>;

@Injectable({ providedIn: 'root' })
export class Pp_userService {
  public resourceUrl = SERVER_API_URL + 'services/microservice1/api/pp-users';

  constructor(protected http: HttpClient) {}

  create(pp_user: IPp_user): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pp_user);
    return this.http
      .post<IPp_user>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pp_user: IPp_user): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pp_user);
    return this.http
      .put<IPp_user>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPp_user>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPp_user[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pp_user: IPp_user): IPp_user {
    const copy: IPp_user = Object.assign({}, pp_user, {
      dob: pp_user.dob != null && pp_user.dob.isValid() ? pp_user.dob.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dob = res.body.dob != null ? moment(res.body.dob) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pp_user: IPp_user) => {
        pp_user.dob = pp_user.dob != null ? moment(pp_user.dob) : null;
      });
    }
    return res;
  }
}
