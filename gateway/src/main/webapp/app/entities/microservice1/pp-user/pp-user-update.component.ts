import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IPp_user, Pp_user } from 'app/shared/model/microservice1/pp-user.model';
import { Pp_userService } from './pp-user.service';

@Component({
  selector: 'jhi-pp-user-update',
  templateUrl: './pp-user-update.component.html'
})
export class Pp_userUpdateComponent implements OnInit {
  pp_user: IPp_user;
  isSaving: boolean;
  dobDp: any;

  editForm = this.fb.group({
    id: [],
    user_name: [null, [Validators.required, Validators.minLength(2)]],
    dob: [],
    role: [],
    location: [],
    about_me: []
  });

  constructor(protected pp_userService: Pp_userService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pp_user }) => {
      this.updateForm(pp_user);
      this.pp_user = pp_user;
    });
  }

  updateForm(pp_user: IPp_user) {
    this.editForm.patchValue({
      id: pp_user.id,
      user_name: pp_user.user_name,
      dob: pp_user.dob,
      role: pp_user.role,
      location: pp_user.location,
      about_me: pp_user.about_me
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pp_user = this.createFromForm();
    if (pp_user.id !== undefined) {
      this.subscribeToSaveResponse(this.pp_userService.update(pp_user));
    } else {
      this.subscribeToSaveResponse(this.pp_userService.create(pp_user));
    }
  }

  private createFromForm(): IPp_user {
    const entity = {
      ...new Pp_user(),
      id: this.editForm.get(['id']).value,
      user_name: this.editForm.get(['user_name']).value,
      dob: this.editForm.get(['dob']).value,
      role: this.editForm.get(['role']).value,
      location: this.editForm.get(['location']).value,
      about_me: this.editForm.get(['about_me']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPp_user>>) {
    result.subscribe((res: HttpResponse<IPp_user>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
