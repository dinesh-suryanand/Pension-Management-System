import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { PensionInput } from '../model/pension-input';
import { Observable } from 'rxjs';
import { PensionDetail } from '../model/pension-detail';
import { PensionerDetail } from '../model/pensioner-detail';
import { ProcessPensionInput } from '../model/process-pension-input';
import { PensionStatus } from '../model/pension-status';

@Injectable({
  providedIn: 'root'
})
export class PensionService {

  public processPensionUrl = environment.processPensionUrl;
  public pensionerDetailUrl = environment.pensionerDetailUrl;

  constructor(private httpClient: HttpClient) { }

  public getPensionDetail(pensionInput: PensionInput): Observable<PensionDetail> {
    return this.httpClient.post<PensionDetail>(`${this.processPensionUrl}/processPension/pensionerInput`, pensionInput);
  }

  public getPensionerDetailByAadhaar(aadhaarNumber: string): Observable<PensionerDetail> {
    return this.httpClient.get<PensionerDetail>(`${this.pensionerDetailUrl}/pensionerDetail/${aadhaarNumber}`);
  }

  public processAndDisbursePension(processPensionInput: ProcessPensionInput): Observable<PensionStatus> {
    return this.httpClient.post<PensionStatus>(`${this.processPensionUrl}/processPension`, processPensionInput);
  }
}
