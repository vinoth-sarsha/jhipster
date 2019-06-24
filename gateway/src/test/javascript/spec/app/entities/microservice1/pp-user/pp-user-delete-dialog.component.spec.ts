/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { Pp_userDeleteDialogComponent } from 'app/entities/microservice1/pp-user/pp-user-delete-dialog.component';
import { Pp_userService } from 'app/entities/microservice1/pp-user/pp-user.service';

describe('Component Tests', () => {
  describe('Pp_user Management Delete Component', () => {
    let comp: Pp_userDeleteDialogComponent;
    let fixture: ComponentFixture<Pp_userDeleteDialogComponent>;
    let service: Pp_userService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [Pp_userDeleteDialogComponent]
      })
        .overrideTemplate(Pp_userDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Pp_userDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Pp_userService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
