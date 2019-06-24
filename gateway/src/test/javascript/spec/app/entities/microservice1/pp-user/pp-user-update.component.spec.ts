/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { Pp_userUpdateComponent } from 'app/entities/microservice1/pp-user/pp-user-update.component';
import { Pp_userService } from 'app/entities/microservice1/pp-user/pp-user.service';
import { Pp_user } from 'app/shared/model/microservice1/pp-user.model';

describe('Component Tests', () => {
  describe('Pp_user Management Update Component', () => {
    let comp: Pp_userUpdateComponent;
    let fixture: ComponentFixture<Pp_userUpdateComponent>;
    let service: Pp_userService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [Pp_userUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(Pp_userUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Pp_userUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Pp_userService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pp_user(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pp_user();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
