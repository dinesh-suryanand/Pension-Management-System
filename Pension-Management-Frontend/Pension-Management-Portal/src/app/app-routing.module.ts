import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { PensionDisburseComponent } from './component/pension-disburse/pension-disburse.component';
import { PensionerDetailComponent } from './component/pensioner-detail/pensioner-detail.component';
import { PensionerInputComponent } from './component/pensioner-input/pensioner-input.component';
import { RegisterComponent } from './component/register/register.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'pensionerDetail',
    component: PensionerInputComponent
  },
  {
    path: 'searchPensioner',
    component: PensionerDetailComponent
  },
  {
    path: 'processPension',
    component: PensionDisburseComponent
  },
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
