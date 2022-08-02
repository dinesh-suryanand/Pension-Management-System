import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enum/notification-type.enum';
import { PensionStatus } from 'src/app/model/pension-status';
import { ProcessPensionInput } from 'src/app/model/process-pension-input';
import { NotificationService } from 'src/app/service/notification.service';
import { PensionService } from 'src/app/service/pension.service';

@Component({
  selector: 'app-pension-disburse',
  templateUrl: './pension-disburse.component.html',
  styleUrls: ['./pension-disburse.component.css']
})
export class PensionDisburseComponent implements OnInit, OnDestroy {

  public showLoading: boolean = false;
  private subscriptions: Subscription[] = [];
  public pensionStatus!: PensionStatus;
  
  constructor(private pensionService: PensionService, private notificationService: NotificationService) { }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  ngOnInit(): void {
  }

  onProcessPension(processPensionInput: ProcessPensionInput): void {
    this.showLoading = true;
    console.log(processPensionInput);
    this.subscriptions.push(
      this.pensionService.processAndDisbursePension(processPensionInput).subscribe(
        (response: PensionStatus) => {
          this.pensionStatus = response;
          this.showLoading = false;
          if(this.pensionStatus.processPensionStatusCode === 10) {
            this.sendNotification(NotificationType.SUCCESS, "Your pension has been disbursed successfully");
          }
          else {
            this.sendNotification(NotificationType.ERROR, "Incorrect pension amount or bank service charge. Please try again.")
          }
        },
        (errorResponse: HttpErrorResponse) => {
          console.error(errorResponse);
          this.sendNotification(NotificationType.ERROR, errorResponse.error.message);
          this.showLoading = false;
        }
      )
    );
  }

  private sendNotification(notificationType: NotificationType, message: string): void {
    if (message) {
      this.notificationService.notify(notificationType, message);
    }
    else {
      this.notificationService.notify(notificationType, 'An Error Occured, Please Try Again');
    }
  }
}
