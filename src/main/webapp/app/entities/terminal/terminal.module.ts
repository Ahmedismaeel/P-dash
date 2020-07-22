import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PdashSharedModule } from 'app/shared/shared.module';
import { TerminalComponent } from './terminal.component';
import { TerminalDetailComponent } from './terminal-detail.component';
import { TerminalUpdateComponent } from './terminal-update.component';
import { TerminalDeleteDialogComponent } from './terminal-delete-dialog.component';
import { terminalRoute } from './terminal.route';

@NgModule({
  imports: [PdashSharedModule, RouterModule.forChild(terminalRoute)],
  declarations: [TerminalComponent, TerminalDetailComponent, TerminalUpdateComponent, TerminalDeleteDialogComponent],
  entryComponents: [TerminalDeleteDialogComponent],
})
export class PdashTerminalModule {}
