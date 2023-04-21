import { Component, OnInit } from '@angular/core';
import { GraphService } from '../services/graph.service';
import { Chart, registerables } from 'node_modules/chart.js'
import { responseData } from '../model/ResponseData';
Chart.register(...registerables);

@Component({
  selector: 'app-total-slots-graph',
  templateUrl: './total-slots-graph.component.html',
  styleUrls: ['./total-slots-graph.component.css']
})
export class TotalSlotsGraphComponent implements OnInit {
  constructor(private graphService: GraphService) { this.setDates() }
  
  i=0;

  ngOnInit() {
    let id='bar';
    let idName='barchart'
    this.result=[];
    for (let i = 0; i < this.dates.length; i++) {
      let slot_date = this.dates[i];
      this.graphService.getTotalSlots(slot_date).subscribe(response => {
        console.log("date=>",slot_date)
        console.log("data:",response)
        this.num = Number(response)
        this.result[i] = this.num;
        this.chartGeneration(this.result, id, idName);
      })
    }
  }


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
      this.graphService.getTotalSlots(slot_date).subscribe(response => {
        this.num = Number(response)
        this.result[i] = this.num
        this.chartGeneration(this.result, 'bar', 'barchart');
      }
      )
    }
  }

  decrease(){
    this.i--
    this.dates=[] 
    this.setDates()
    this.result=[];
    for (let i = 0; i < this.dates.length; i++) {
      let slot_date = this.dates[i];
      this.graphService.getTotalSlots(slot_date).subscribe(response => {
        this.num = Number(response)
        this.result[i]=this.num;
        this.chartGeneration(this.result, 'bar', 'barchart');
      })
    }
  }


  result: number[] = []
  dates = new Array();
  num: number = 0;
  selection = true;

  barValue: boolean = false;
  lineValue: boolean = true;
  pieValue: boolean = true;
  polarValue: boolean = true;
  doughValue: boolean = true;

  chartGeneration(maindata: number[], id: any, idName: any) {
    console.log(this.dates)
    console.log(maindata)

    this.barValue = true;
    this.lineValue = true;
    this.pieValue = true;
    this.polarValue = true;
    this.doughValue = true;

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


    if (idName == 'barchart') {
      this.barValue = false;
    }
    else if (idName == 'linechart') {
      this.lineValue = false;
    }
    else if (idName == 'piechart') {
      this.pieValue = false;
    }
    else if (idName == 'polarAreachart') {
      this.polarValue = false;
    }
    else if (idName == 'doughnutchart') {
      this.doughValue = false;
    }

    

    const myChart = new Chart(idName, {
      type: id,
      data: {
        labels: this.dates,
        datasets: [{
          label: 'Total number of slots',
          data: maindata,
          backgroundColor: ["#F72585", "#7209B7", "#4CC9F0", "#ffb703"],
          borderColor: "#4CC9F0",
          borderWidth: 1,
        },
        ]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
            ticks:{
              stepSize:1
        },
          }
        }
      }
    });
    
    this.selection = false;
  }
  
  getTotalNumberOfSlots(id: any, idName: any) {
    this.result=[]
    for (let i = 0; i < this.dates.length; i++) {
      let slot_date = this.dates[i];
      this.graphService.getTotalSlots(slot_date).subscribe({next:data=>{
        this.num=Number(data)
        this.result[i]=this.num  
        console.log(this.result)  
        this.chartGeneration(this.result, id, idName);
      }})
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
