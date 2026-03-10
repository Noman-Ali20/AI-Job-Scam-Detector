import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  email: string = '';
  password: string = '';

  loginData = {
  email:'',
  password:''
};

  constructor(private http: HttpClient,
              private router: Router) {}

  login(){

this.http.post(
'http://localhost:8080/auth/login',
this.loginData,
{ responseType:'text' }   // IMPORTANT
).subscribe({

next:(res:any)=>{

localStorage.setItem("token",res);

this.router.navigate(['/scan']);

},

error:(err)=>{

alert("Invalid Email or Password");

}

});





}

}