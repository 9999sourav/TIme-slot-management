import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'node_modules/chart.js'
import { GraphService } from '../services/graph.service';
Chart.register(...registerables);
@Component({
  selector: 'app-candidates-graph',
  templateUrl: './candidates-graph.component.html',
  styleUrls: ['./candidates-graph.component.css']
})
export class CandidatesGraphComponent implements OnInit {

  constructor(private graphService:GraphService){}
 
  selected = 'option2';
  result:number[]=[]
  dates = new Array();
  arr:any[]=[];
  num:number=0;
  selection=true;
  
  
  barValue:boolean=false;
  lineValue:boolean=true;
  pieValue:boolean=true;
  polarValue:boolean=true;
  doughValue:boolean=true;
  
  ngOnInit() {
    let id='bar'
    let idName='barchart'
    this.setDates()
    for(let i=0; i<this.dates.length;i++){
      let slot_date=this.dates[i];
      console.log(slot_date)
      this.graphService.getAllCandidates(slot_date).subscribe(response=>{
        this.num=Number(response)
        // this.result.push(this.num);
        this.result.splice(i,0,this.num)
        this.chartGeneration2(this.result,id,idName)
      })
    }
  
  }
  
  chartGeneration2(maindata:any,id:any,idName:any) {
    console.log(maindata);
    this.barValue=true;
    this.lineValue=true;
    this.pieValue=true;
    this.polarValue=true;
    this.doughValue=true;

    var chartExist = Chart.getChart("barchart"); // <canvas> id
    if (chartExist != undefined)
      chartExist.destroy();
    var chartExist = Chart.getChart("linechart"); // <canvas> id
    if (chartExist != undefined)
      chartExist.destroy();
    var chartExist = Chart.getChart("piechart"); // <canvas> id
    if (chartExist != undefined)
      chartExist.destroy();
    var chartExist = Chart.getChart("polarAreachart"); // <canvas> id
    if (chartExist != undefined)
      chartExist.destroy();
      var chartExist = Chart.getChart("doughnutchart"); // <canvas> id
      if (chartExist != undefined)
        chartExist.destroy();
    
    if(idName=='barchart'){
      console.log("inside barchart");
      this.barValue=false;
    }
    else if(idName=='linechart'){
      this.lineValue=false;
    }
    else if(idName=='piechart'){
      this.pieValue=false;
    }
    else if(idName=='polarAreachart'){
      this.polarValue=false;
    }
    else if(idName=='doughnutchart'){
      this.doughValue=false;
    }
    const myChart = new Chart(idName, {
      type: id,
      data: {
        labels:this.dates,
        datasets: [{
          label: 'Number of Candidates Present',
          data: maindata,
          backgroundColor:  ["#9b5fe0","#16a4d8","#60dbe8","#8bd346","#efdf48","#f9a52c","#d64e12"],
        }]
      },
      
      options: {
        scales: {
          y: {
          
            ticks:{
              stepSize:1
        },
          beginAtZero: true
          }
        }
      }
    });
    this.selection=false;
  }
  
  getCandidateDetails(id:any,idName:any){
    this.result=[];
    for (let i = 0; i < this.dates.length; i++) {
      let slot_date = this.dates[i];
      this.graphService.getAllCandidates(slot_date).subscribe(response => {
        this.num = Number(response)
        this.result[i]=this.num;
        this.chartGeneration2(this.result, id, idName);
      })
    }
    }


    i=0;

  setDates(){
    const today = new Date();
    let date = new Date();

    date.setDate(date.getDate()-3+this.i)
    this.dates.push(this.getDate(date))

    date.setDate(date.getDate()+1)
    this.dates.push(this.getDate(date))

    date.setDate(date.getDate()+1)
    this.dates.push(this.getDate(date))
  
    date.setDate(date.getDate()+1)
    this.dates.push(this.getDate(date))

    date.setDate(date.getDate()+1)
    this.dates.push(this.getDate(date))

    date.setDate(date.getDate()+1)
    this.dates.push(this.getDate(date))

    date.setDate(date.getDate()+1)
    this.dates.push(this.getDate(date))

  }

  increase(){
    this.i++   
    this.dates=[] 
    this.setDates()
    this.result=[];
    for (let i = 0; i < this.dates.length; i++) {
      let slot_date = this.dates[i];
      this.graphService.getAllCandidates(slot_date).subscribe(response => {
        this.num = Number(response)
        this.result[i]=this.num;
        this.chartGeneration2(this.result, 'bar', 'barchart');
      })
    }
    
  }
  decrease(){
    this.i--
    this.dates=[] 
    this.setDates()
    this.result=[];
    for (let i = 0; i < this.dates.length; i++) {
      let slot_date = this.dates[i];
      this.graphService.getAllCandidates(slot_date).subscribe(response => {
        this.num = Number(response)
        this.result[i]=this.num;
        this.chartGeneration2(this.result, 'bar', 'barchart');
      })
    }
  }

  getDate(dateInDateFormat:Date){
    let today = new Date();
    console.log(dateInDateFormat)
    let dateInJson = today!.getFullYear().toString();
    dateInJson = dateInJson.concat("-");
    if (dateInDateFormat!.getMonth() < 10) {
      dateInJson = dateInJson.concat("0" + (dateInDateFormat!.getMonth() + 1));
    }
    else {
      dateInJson = dateInJson.concat((dateInDateFormat!.getMonth() + 1).toString());
    }
    dateInJson = dateInJson.concat("-");
    if (dateInDateFormat!.getDate() < 10) {
      dateInJson = dateInJson.concat("0" + dateInDateFormat!.getDate());
    }
    else
      dateInJson = dateInJson.concat((dateInDateFormat!.getDate()).toString())
    return dateInJson;
  }



}








