import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITerminal } from 'app/shared/model/terminal.model';
import { TerminalService } from './terminal.service';
import { TerminalDeleteDialogComponent } from './terminal-delete-dialog.component';

@Component({
  selector: 'jhi-terminal',
  templateUrl: './terminal.component.html',
})
export class TerminalComponent implements OnInit, OnDestroy {
  terminals?: ITerminal[];
  eventSubscriber?: Subscription;

  constructor(protected terminalService: TerminalService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.terminalService.query().subscribe((res: HttpResponse<ITerminal[]>) => (this.terminals = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTerminals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITerminal): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTerminals(): void {
    this.eventSubscriber = this.eventManager.subscribe('terminalListModification', () => this.loadAll());
  }

  delete(terminal: ITerminal): void {
    const modalRef = this.modalService.open(TerminalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.terminal = terminal;
  }
}
