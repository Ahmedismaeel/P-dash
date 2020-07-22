import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITerminal } from 'app/shared/model/terminal.model';
import { TerminalService } from './terminal.service';

@Component({
  templateUrl: './terminal-delete-dialog.component.html',
})
export class TerminalDeleteDialogComponent {
  terminal?: ITerminal;

  constructor(protected terminalService: TerminalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.terminalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('terminalListModification');
      this.activeModal.close();
    });
  }
}
