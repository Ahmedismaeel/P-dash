import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'transaction',
        loadChildren: () => import('./transaction/transaction.module').then(m => m.PdashTransactionModule),
      },
      {
        path: 'terminal',
        loadChildren: () => import('./terminal/terminal.module').then(m => m.PdashTerminalModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PdashEntityModule {}
