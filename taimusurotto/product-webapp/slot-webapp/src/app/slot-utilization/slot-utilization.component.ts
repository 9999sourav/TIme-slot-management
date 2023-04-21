import { Component, OnInit } from '@angular/core';
import { Chart, registerables } from 'node_modules/chart.js'
import { DatePipe } from '@angular/common';
import { GraphService } from '../services/graph.service';
Chart.register(...registerables);

@Component({
  selector: 'app-slot-utilization',
  templateUrl: './slot-utilization.component.html',
  styleUrls: ['./slot-utilization.component.css']
})
export class SlotUtilizationComponent implements OnInit {

  constructor(private graphService: GraphService) { }

  ngOnInit(){
    this.date = this.getDate(new Date())
    let id='bar';
    let idName='barchart'
        this.graphService.getutilizationofslots(this.getTodaysDate()).subscribe(response => {
        this.chartGeneration2(response, id, idName);
      })
    
  }

  getTodaysDate(){
    let dateToday = new Date();
    let today:any;
    today = dateToday!.getFullYear().toString();
    today = today.concat("-");
    if (dateToday!.getMonth() < 10) {
      today = today.concat("0" + (dateToday!.getMonth() + 1));
    }
    else {
      today = today.concat(dateToday!.getMonth() + 1);
    }
    today = today.concat("-");
    if (dateToday!.getDate() < 10) {
      today = today.concat("0" + dateToday!.getDate());
    }
    else
      today = today.concat(dateToday!.getDate())
    return today;
  }

  result: any[] = []
  arr: any[] = [];
  num: number = 0;
  selection = true;
  selected: any;
  date: any

  barValue: boolean = false;
  lineValue: boolean = true;
  pieValue: boolean = true;
  polarValue: boolean = true;
  doughValue: boolean = true;

  chartGeneration2(maindata: any, id: any, idName: any) {
    
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

    console.log("data=>",maindata)
    console.log("id=>",id)
    const myChart = new Chart(idName, {
      type: id,
      data: {
        labels: ["Utilization", "cancelled", "Bookings", "Available"],
        datasets: [{
          label: 'Utilization of Slots By Date',
          data: maindata,
          backgroundColor: ["#9b5fe0","#16a4d8","#60dbe8","#8bd346"],
        }]
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

  getUtilizationofslots(id: any, idName: any) {
    if(this.selected!=null){
      this.date=this.getDate(this.selected)
    }
      this.graphService.getutilizationofslots(this.date).subscribe(response => {
      this.responseArray = response
      console.log(response)
      this.chartGeneration2(response, id, idName);
    })
    console.log(id,idName)
    this.chartGeneration2(this.responseArray,id,idName)
  }

  dateChanged(event: any) {
    this.result = [];
    this.date = this.selected!.getFullYear().toString();
    this.date = this.date.concat("-");
    if (this.selected!.getMonth() < 10) {
      this.date = this.date.concat("0" + (this.selected!.getMonth() + 1));
    }
    else {
      this.date = this.date.concat(this.selected!.getMonth() + 1);
    }
    this.date = this.date.concat("-");
    if (this.selected!.getDate() < 10) {
      this.date = this.date.concat("0" + this.selected!.getDate());
    }
    else
      this.date = this.date.concat(this.selected!.getDate())

    this.graphService.getutilizationofslots(this.date).subscribe(response => {
      this.responseArray = response;
        this.result = this.responseArray;
        this.chartGeneration2(this.result, 'bar', 'barchart')
      })
    }

    responseArray:any;

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
