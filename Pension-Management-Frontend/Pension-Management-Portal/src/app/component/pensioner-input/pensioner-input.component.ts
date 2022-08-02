import { DatePipe } from '@angular/common';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enum/notification-type.enum';
import { PensionDetail } from 'src/app/model/pension-detail';
import { PensionInput } from 'src/app/model/pension-input';
import { NotificationService } from 'src/app/service/notification.service';
import { PensionService } from 'src/app/service/pension.service';

@Component({
  selector: 'app-pensioner-input',
  templateUrl: './pensioner-input.component.html',
  styleUrls: ['./pensioner-input.component.css']
})
export class PensionerInputComponent implements OnInit, OnDestroy {

  public showLoading: boolean = false;
  private subscriptions: Subscription[] = [];
  public pensionDetail!: PensionDetail;
  
  constructor(private pensionService: PensionService, private notificationService: NotificationService, private datepipe: DatePipe) { }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  ngOnInit(): void {
  }

  onSubmitPensionDetails(pensionInput: PensionInput): void {
    this.showLoading = true;
    pensionInput.dateOfBirth= new Date(JSON.stringify(this.datepipe.transform(pensionInput.dateOfBirth, 'dd-MM-yyyy')));
    this.subscriptions.push(
      this.pensionService.getPensionDetail(pensionInput).subscribe(
        (response: PensionDetail) => {
          this.pensionDetail = response;
          this.showLoading = false;
        },
        (errorResponse: HttpErrorResponse) => {
          console.log(errorResponse);
          this.sendErrorNotification(NotificationType.ERROR, errorResponse.error.message);
          this.showLoading = false;
        }
      )
    );
  }

  private sendErrorNotification(notificationType: NotificationType, message: string): void {
    if (message) {
      this.notificationService.notify(notificationType, message);
    }
    else {
      this.notificationService.notify(notificationType, 'An Error Occured, Please Try Again');
    }
  }
}
