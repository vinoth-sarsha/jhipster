/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { Pp_userDetailComponent } from 'app/entities/microservice1/pp-user/pp-user-detail.component';
import { Pp_user } from 'app/shared/model/microservice1/pp-user.model';

describe('Component Tests', () => {
  describe('Pp_user Management Detail Component', () => {
    let comp: Pp_userDetailComponent;
    let fixture: ComponentFixture<Pp_userDetailComponent>;
    const route = ({ data: of({ pp_user: new Pp_user(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GatewayTestModule],
        declarations: [Pp_userDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(Pp_userDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Pp_userDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pp_user).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
