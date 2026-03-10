import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-scan',
  templateUrl: './scan.component.html',
  styleUrls: ['./scan.component.css']
})
export class ScanComponent {

  text:any;
  email:any;
  company:any;

  result:any;
  selectedFile:any;
  loading:boolean=false;
  isLoading:boolean=false;

  onFileSelected(event:any){

  this.selectedFile = event.target.files[0];

}
scanFile(){

this.isLoading = true;

const token = localStorage.getItem("token");

const headers = new HttpHeaders({
  Authorization: 'Bearer ' + token
});

const formData = new FormData();
formData.append("file", this.selectedFile);

this.http.post(
'http://localhost:8080/scan/image',
formData,
{ headers: headers }

).subscribe({

next:(res:any)=>{

this.result = res;
this.isLoading = false;

},

error:(err)=>{

this.isLoading = false;
alert("Scan failed");

}

});

}

  constructor(private http:HttpClient){}

  scan(){

    

if(!this.text || !this.email || !this.company){
alert("Please fill all fields");
return;
}
  this.loading=true;

  const token = localStorage.getItem("token");

  const headers = new HttpHeaders({
  Authorization: 'Bearer ' + token
});
  const formData = new FormData();

  formData.append("text",this.text);
  formData.append("email",this.email);
  formData.append("company",this.company);

  this.http.post("http://localhost:8080/scan",
  formData,{ headers: headers }
  ).subscribe({

next:(res:any)=>{

this.result = res;
this.loading = false;

},

error:(err)=>{

this.loading = false;
alert("Scan failed");

}

});

}

}