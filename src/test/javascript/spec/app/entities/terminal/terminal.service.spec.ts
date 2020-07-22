import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TerminalService } from 'app/entities/terminal/terminal.service';
import { ITerminal, Terminal } from 'app/shared/model/terminal.model';

describe('Service Tests', () => {
  describe('Terminal Service', () => {
    let injector: TestBed;
    let service: TerminalService;
    let httpMock: HttpTestingController;
    let elemDefault: ITerminal;
    let expectedResult: ITerminal | ITerminal[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TerminalService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Terminal(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Terminal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Terminal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Terminal', () => {
        const returnedFromService = Object.assign(
          {
            terminalId: 'BBBBBB',
            merchantId: 'BBBBBB',
            merchantName: 'BBBBBB',
            marketName: 'BBBBBB',
            phoneNo: 'BBBBBB',
            sIMSerialNo: 'BBBBBB',
            pOSSerialNo: 'BBBBBB',
            location: 'BBBBBB',
            user: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Terminal', () => {
        const returnedFromService = Object.assign(
          {
            terminalId: 'BBBBBB',
            merchantId: 'BBBBBB',
            merchantName: 'BBBBBB',
            marketName: 'BBBBBB',
            phoneNo: 'BBBBBB',
            sIMSerialNo: 'BBBBBB',
            pOSSerialNo: 'BBBBBB',
            location: 'BBBBBB',
            user: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Terminal', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
