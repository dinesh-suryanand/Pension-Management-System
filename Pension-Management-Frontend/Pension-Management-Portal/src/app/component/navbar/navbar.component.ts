import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationType } from 'src/app/enum/notification-type.enum';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { NotificationService } from 'src/app/service/notification.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  public loggedInUsername!: string;
  public userProfileImageUrl!: string;

  constructor(private authenticationService: AuthenticationService, private notificationService: NotificationService, private router: Router) { }

  ngOnInit(): void {
    this.authenticationService.isLoggedIn();
    this.loggedInUsername = this.authenticationService.loggedInUsername;
    this.userProfileImageUrl = environment.authenticationUrl+"/user/image/profile/"+this.loggedInUsername;
  }

  onLogout(): void {
    this.authenticationService.logout();
    this.router.navigateByUrl('/login');
    this.notificationService.notify(NotificationType.SUCCESS, "Logout Successful");
  }

}
