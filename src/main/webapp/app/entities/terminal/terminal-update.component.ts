import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITerminal, Terminal } from 'app/shared/model/terminal.model';
import { TerminalService } from './terminal.service';

@Component({
  selector: 'jhi-terminal-update',
  templateUrl: './terminal-update.component.html',
})
export class TerminalUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    terminalId: [null, [Validators.required]],
    merchantId: [],
    merchantName: [],
    marketName: [],
    phoneNo: [],
    sIMSerialNo: [],
    pOSSerialNo: [],
    location: [],
    user: [],
  });

  constructor(protected terminalService: TerminalService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ terminal }) => {
      this.updateForm(terminal);
    });
  }

  updateForm(terminal: ITerminal): void {
    this.editForm.patchValue({
      id: terminal.id,
      terminalId: terminal.terminalId,
      merchantId: terminal.merchantId,
      merchantName: terminal.merchantName,
      marketName: terminal.marketName,
      phoneNo: terminal.phoneNo,
      sIMSerialNo: terminal.sIMSerialNo,
      pOSSerialNo: terminal.pOSSerialNo,
      location: terminal.location,
      user: terminal.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const terminal = this.createFromForm();
    if (terminal.id !== undefined) {
      this.subscribeToSaveResponse(this.terminalService.update(terminal));
    } else {
      this.subscribeToSaveResponse(this.terminalService.create(terminal));
    }
  }

  private createFromForm(): ITerminal {
    return {
      ...new Terminal(),
      id: this.editForm.get(['id'])!.value,
      terminalId: this.editForm.get(['terminalId'])!.value,
      merchantId: this.editForm.get(['merchantId'])!.value,
      merchantName: this.editForm.get(['merchantName'])!.value,
      marketName: this.editForm.get(['marketName'])!.value,
      phoneNo: this.editForm.get(['phoneNo'])!.value,
      sIMSerialNo: this.editForm.get(['sIMSerialNo'])!.value,
      pOSSerialNo: this.editForm.get(['pOSSerialNo'])!.value,
      location: this.editForm.get(['location'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITerminal>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
