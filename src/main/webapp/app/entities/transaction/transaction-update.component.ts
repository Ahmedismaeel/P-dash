import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html',
})
export class TransactionUpdateComponent implements OnInit {
  isSaving = false;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    clientId: [],
    terminalId: [],
    tranDateTime: [],
    date: [],
    time: [],
    tranAmount: [],
    tranFee: [],
    referenceNumber: [],
    responseStatus: [],
    serviceName: [],
    user: [],
  });

  constructor(protected transactionService: TransactionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);
    });
  }

  updateForm(transaction: ITransaction): void {
    this.editForm.patchValue({
      id: transaction.id,
      clientId: transaction.clientId,
      terminalId: transaction.terminalId,
      tranDateTime: transaction.tranDateTime,
      date: transaction.date,
      time: transaction.time,
      tranAmount: transaction.tranAmount,
      tranFee: transaction.tranFee,
      referenceNumber: transaction.referenceNumber,
      responseStatus: transaction.responseStatus,
      serviceName: transaction.serviceName,
      user: transaction.user,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id'])!.value,
      clientId: this.editForm.get(['clientId'])!.value,
      terminalId: this.editForm.get(['terminalId'])!.value,
      tranDateTime: this.editForm.get(['tranDateTime'])!.value,
      date: this.editForm.get(['date'])!.value,
      time: this.editForm.get(['time'])!.value,
      tranAmount: this.editForm.get(['tranAmount'])!.value,
      tranFee: this.editForm.get(['tranFee'])!.value,
      referenceNumber: this.editForm.get(['referenceNumber'])!.value,
      responseStatus: this.editForm.get(['responseStatus'])!.value,
      serviceName: this.editForm.get(['serviceName'])!.value,
      user: this.editForm.get(['user'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
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
