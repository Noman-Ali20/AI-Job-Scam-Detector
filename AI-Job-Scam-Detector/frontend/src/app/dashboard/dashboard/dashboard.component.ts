import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{

  stats:any;
  recentScans:any;
  chart:any;
  selectedMessage:any='';
showModal:boolean=false;

  constructor(private http:HttpClient){}

  ngOnInit(){

    const token = localStorage.getItem("token");

    this.http.get("http://localhost:8080/dashboard/stats",{

      headers:{
        Authorization:'Bearer '+token
      }

    }).subscribe((res:any)=>{

  this.stats=res;

  this.createChart();

})

    

    this.http.get("http://localhost:8080/dashboard/recent-scans",{

  headers:{
    Authorization:'Bearer '+token
  }

}).subscribe(res=>{

  this.recentScans=res;

})

  }

  createChart()
  {

  this.chart = new Chart("scanChart",{

    type:'pie',

    data:{
      labels:["SAFE","SUSPICIOUS","FAKE"],

      datasets:[{

        data:[
          this.stats.SAFE,
          this.stats.SUSPICIOUS,
          this.stats.FAKE
        ],

        backgroundColor:[
          "#28a745",
          "#ffc107",
          "#dc3545"
        ]

      }]

    }

  })

}

viewMessage(message:any){
  this.selectedMessage=message;
  this.showModal=true;
}

closeModal(){
  this.showModal=false;
}

  

}