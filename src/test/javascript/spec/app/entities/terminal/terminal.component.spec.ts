import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PdashTestModule } from '../../../test.module';
import { TerminalComponent } from 'app/entities/terminal/terminal.component';
import { TerminalService } from 'app/entities/terminal/terminal.service';
import { Terminal } from 'app/shared/model/terminal.model';

describe('Component Tests', () => {
  describe('Terminal Management Component', () => {
    let comp: TerminalComponent;
    let fixture: ComponentFixture<TerminalComponent>;
    let service: TerminalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PdashTestModule],
        declarations: [TerminalComponent],
      })
        .overrideTemplate(TerminalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TerminalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TerminalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Terminal(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.terminals && comp.terminals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
