import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { ScanComponent } from './scan/scan/scan.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [

{ path:'', redirectTo:'/login', pathMatch:'full' },

{ path:'login', component:LoginComponent },

{ path:'scan', component:ScanComponent, canActivate:[AuthGuard] },

{ path:'dashboard', component:DashboardComponent, canActivate:[AuthGuard] }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }