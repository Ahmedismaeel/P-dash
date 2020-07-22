import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITerminal } from 'app/shared/model/terminal.model';

type EntityResponseType = HttpResponse<ITerminal>;
type EntityArrayResponseType = HttpResponse<ITerminal[]>;

@Injectable({ providedIn: 'root' })
export class TerminalService {
  public resourceUrl = SERVER_API_URL + 'api/terminals';

  constructor(protected http: HttpClient) {}

  create(terminal: ITerminal): Observable<EntityResponseType> {
    return this.http.post<ITerminal>(this.resourceUrl, terminal, { observe: 'response' });
  }

  update(terminal: ITerminal): Observable<EntityResponseType> {
    return this.http.put<ITerminal>(this.resourceUrl, terminal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITerminal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITerminal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
