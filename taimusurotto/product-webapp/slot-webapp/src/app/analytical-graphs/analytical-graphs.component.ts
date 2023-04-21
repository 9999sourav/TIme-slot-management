import { Component, OnInit, ViewChild } from '@angular/core';
// import * as Chart from 'chart.js';
import { GraphService } from '../services/graph.service';
import { Chart, registerables } from 'node_modules/chart.js'
Chart.register(...registerables);

@Component({
  selector: 'app-analytical-graphs',
  templateUrl: './analytical-graphs.component.html',
  styleUrls: ['./analytical-graphs.component.css']
})
export class AnalyticalGraphsComponent {
  constructor(private graphService: GraphService) { }
  result: number[] = []
  dates: any[] = ['2023-02-16', '2023-02-17', '2023-02-18', '2023-02-19'];
  num: number = 0;
  selection = true;

  barValue: boolean = true;
  lineValue: boolean = true;
  pieValue: boolean = true;
  polarValue: boolean = true;
  doughValue: boolean = true;

  chartGeneration(maindata: number[], id: any, idName: any) {
    console.log(maindata)
    this.barValue = true;
    this.lineValue = true;
    this.pieValue = true;
    this.polarValue = true;
    this.doughValue = true;

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
          label: 'Number of cancelled slots',
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
            beginAtZero: true
          }
        }
      }
    });
    
    this.selection = false;
  }
  cancellationGraphs(id: any, idName: any) {
    this.result=[];
    for (let i = 0; i < this.dates.length; i++) {
      let slot_date = this.dates[i];
      this.graphService.getCancelledSlots(slot_date).subscribe(response => {
        this.num = Number(response)
        this.result.push(this.num);
      })
    }
    this.chartGeneration(this.result, id, idName);
  }
  someEvent() {
    
  }
}