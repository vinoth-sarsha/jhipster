import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPp_user } from 'app/shared/model/microservice1/pp-user.model';

@Component({
  selector: 'jhi-pp-user-detail',
  templateUrl: './pp-user-detail.component.html'
})
export class Pp_userDetailComponent implements OnInit {
  pp_user: IPp_user;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pp_user }) => {
      this.pp_user = pp_user;
    });
  }

  previousState() {
    window.history.back();
  }
}
