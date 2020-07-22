import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITerminal, Terminal } from 'app/shared/model/terminal.model';
import { TerminalService } from './terminal.service';
import { TerminalComponent } from './terminal.component';
import { TerminalDetailComponent } from './terminal-detail.component';
import { TerminalUpdateComponent } from './terminal-update.component';

@Injectable({ providedIn: 'root' })
export class TerminalResolve implements Resolve<ITerminal> {
  constructor(private service: TerminalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITerminal> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((terminal: HttpResponse<Terminal>) => {
          if (terminal.body) {
            return of(terminal.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Terminal());
  }
}

export const terminalRoute: Routes = [
  {
    path: '',
    component: TerminalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pdashApp.terminal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TerminalDetailComponent,
    resolve: {
      terminal: TerminalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pdashApp.terminal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TerminalUpdateComponent,
    resolve: {
      terminal: TerminalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pdashApp.terminal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TerminalUpdateComponent,
    resolve: {
      terminal: TerminalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pdashApp.terminal.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
