export interface ITerminal {
  id?: number;
  terminalId?: string;
  merchantId?: string;
  merchantName?: string;
  marketName?: string;
  phoneNo?: string;
  sIMSerialNo?: string;
  pOSSerialNo?: string;
  location?: string;
  user?: string;
}

export class Terminal implements ITerminal {
  constructor(
    public id?: number,
    public terminalId?: string,
    public merchantId?: string,
    public merchantName?: string,
    public marketName?: string,
    public phoneNo?: string,
    public sIMSerialNo?: string,
    public pOSSerialNo?: string,
    public location?: string,
    public user?: string
  ) {}
}
