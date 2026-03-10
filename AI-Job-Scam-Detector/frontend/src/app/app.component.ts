import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'job-scam-ai-frontend';

  constructor(private router: Router) {}

  logout(){

localStorage.removeItem("token");

this.router.navigate(['/login']);

}

}