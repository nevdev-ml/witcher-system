import { Component, OnInit } from '@angular/core';
import {TaskService} from '../../services/task.service';
import {AppComponent} from '../../app.component';
import {Router} from '@angular/router';
import {BeastsEnum} from '../../enums/beasts.enum';
import {CurrencyEnum} from '../../enums/currency.enum';
import {RegionsEnum} from '../../enums/regions.enum';
import {TaskCreateModel} from '../../models/task/task.create.model';

@Component({
  selector: 'app-task.create',
  templateUrl: './task.create.component.html',
  styleUrls: ['./task.create.component.css']
})
export class TaskCreateComponent implements OnInit {
  task: TaskCreateModel = new TaskCreateModel();
  errorMessage: string;
  beasts: {name: string}[] = [];
  currency: {name: string}[] = [];
  locations: {name: string}[] = [];
  titleBeast = 'Бестия';
  titleCurrency = 'Валюта';
  titleLocation = 'Локация';

  constructor(public taskService: TaskService, private router: Router) { }

  ngOnInit(): void {
    this.mapAnswers();
  }

  mapAnswers(){
    for (const item of Object.keys(BeastsEnum).filter(key => !isNaN(Number(BeastsEnum[key])))){
      this.beasts = this.beasts.concat({name: AppComponent.BeastMap.get(Number(BeastsEnum[item]))});
    }
    for (const item of Object.keys(CurrencyEnum).filter(key => !isNaN(Number(CurrencyEnum[key])))){
      this.currency = this.currency.concat({name: AppComponent.CurrencyMap.get(Number(CurrencyEnum[item]))});
    }
    for (const item of Object.keys(RegionsEnum).filter(key => !isNaN(Number(RegionsEnum[key])))){
      this.locations = this.locations.concat({name: AppComponent.RegionMap.get(Number(RegionsEnum[item]))});
    }
  }

  onChangedLocation(checkedValue: string) {
    console.log(checkedValue);
    this.task.checkedLocation = checkedValue;
  }

  onChangedCurrency(checkedValue: string) {
    console.log(checkedValue);
    this.task.checkedCurrency = checkedValue;
  }

  onChangedBeast(checkedValue: string) {
    console.log(checkedValue);
    this.task.checkedBeast = checkedValue;
  }

  add(): void {
    console.log(this.task);
    if (localStorage.getItem(AppComponent.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(AppComponent.NOT_AUTH));
    } else{
      this.taskService.add(this.task, {Authorization : localStorage.getItem(AppComponent.TOKEN)}).subscribe(
        data => {
          console.log(data);
          this.router.navigate(['tasks', data.id]).then(() => console.log(AppComponent.NAVIGATED));
        },
        error => {
          console.log(error);
        });
    }
  }

  back(): void {
    this.router.navigate(['tasks']).then(() => console.log(AppComponent.NAVIGATED));
  }
}
