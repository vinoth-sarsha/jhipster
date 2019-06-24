import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPp_user } from 'app/shared/model/microservice1/pp-user.model';
import { Pp_userService } from './pp-user.service';

@Component({
  selector: 'jhi-pp-user-delete-dialog',
  templateUrl: './pp-user-delete-dialog.component.html'
})
export class Pp_userDeleteDialogComponent {
  pp_user: IPp_user;

  constructor(protected pp_userService: Pp_userService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pp_userService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pp_userListModification',
        content: 'Deleted an pp_user'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pp-user-delete-popup',
  template: ''
})
export class Pp_userDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pp_user }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(Pp_userDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pp_user = pp_user;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pp-user', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pp-user', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
