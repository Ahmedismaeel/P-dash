import { Moment } from 'moment';

export interface ITransaction {
  id?: number;
  clientId?: string;
  terminalId?: string;
  tranDateTime?: string;
  date?: Moment;
  time?: string;
  tranAmount?: number;
  tranFee?: number;
  referenceNumber?: string;
  responseStatus?: string;
  serviceName?: string;
  user?: string;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public clientId?: string,
    public terminalId?: string,
    public tranDateTime?: string,
    public date?: Moment,
    public time?: string,
    public tranAmount?: number,
    public tranFee?: number,
    public referenceNumber?: string,
    public responseStatus?: string,
    public serviceName?: string,
    public user?: string
  ) {}
}
