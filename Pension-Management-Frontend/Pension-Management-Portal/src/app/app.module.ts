import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { PensionerInputComponent } from './component/pensioner-input/pensioner-input.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NotificationModule } from './notification.module';
import { AuthenticationGuard } from './guard/authentication.guard';
import { AuthenticationService } from './service/authentication.service';
import { UserService } from './service/user.service';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { NotificationService } from './service/notification.service';
import { NavbarComponent } from './component/navbar/navbar.component';
import { PensionService } from './service/pension.service';
import { DatePipe } from '@angular/common';
import { PensionerDetailComponent } from './component/pensioner-detail/pensioner-detail.component';
import { PensionDisburseComponent } from './component/pension-disburse/pension-disburse.component';
import { FooterComponent } from './component/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    PensionerInputComponent,
    NavbarComponent,
    PensionerDetailComponent,
    PensionDisburseComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NotificationModule
  ],
  providers: [AuthenticationGuard, AuthenticationService, UserService, { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }, 
    NotificationService, PensionService, DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
