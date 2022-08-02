import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enum/notification-type.enum';
import { PensionerDetail } from 'src/app/model/pensioner-detail';
import { NotificationService } from 'src/app/service/notification.service';
import { PensionService } from 'src/app/service/pension.service';

@Component({
  selector: 'app-pensioner-detail',
  templateUrl: './pensioner-detail.component.html',
  styleUrls: ['./pensioner-detail.component.css']
})
export class PensionerDetailComponent implements OnInit, OnDestroy {

  public showLoading: boolean = false;
  private subscriptions: Subscription[] = [];
  public pensionerDetail!: PensionerDetail;
  
  constructor(private pensionService: PensionService, private notificationService: NotificationService) { }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  ngOnInit(): void {
  }

  onSubmitDetails(aadhaarNumber: string): void {
    this.showLoading = true;
    this.subscriptions.push(
      this.pensionService.getPensionerDetailByAadhaar(aadhaarNumber).subscribe(
        (response: PensionerDetail) => {
          this.pensionerDetail = response;
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
