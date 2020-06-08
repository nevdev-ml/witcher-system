import { Component, OnInit } from '@angular/core';
import {TaskService} from '../../services/task.service';
import {Router} from '@angular/router';
import {BeastsEnum} from '../../enums/beasts.enum';
import {CurrencyEnum} from '../../enums/currency.enum';
import {RegionsEnum} from '../../enums/regions.enum';
import {TaskCreateModel} from '../../models/task/task.create.model';
import {Constants} from '../../utils/constants';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-task.create',
  templateUrl: './task.create.component.html',
  styleUrls: ['./task.create.component.css']
})
export class TaskCreateComponent implements OnInit {
  task: TaskCreateModel = new TaskCreateModel();
  errorMessage: string;
  form: FormGroup;
  beasts: {name: string}[] = [];
  currency: {name: string}[] = [];
  locations: {name: string}[] = [];
  titleBeast = 'Бестия';
  titleCurrency = 'Валюта';
  titleLocation = 'Локация';

  constructor(public taskService: TaskService, private router: Router) { }

  ngOnInit(): void {
    this.mapAnswers();
    this.form = new FormGroup({
      title: new FormControl('', Validators.required),
      checkedRewardValue: new FormControl('', [Validators.required, Validators.min(0),
        Validators.pattern('^[0-9]*$')]),
    });
  }

  mapAnswers(){
    for (const item of Object.keys(BeastsEnum).filter(key => !isNaN(Number(BeastsEnum[key])))){
      this.beasts = this.beasts.concat({name: Constants.BeastMap.get(Number(BeastsEnum[item]))});
    }
    for (const item of Object.keys(CurrencyEnum).filter(key => !isNaN(Number(CurrencyEnum[key])))){
      this.currency = this.currency.concat({name: Constants.CurrencyMap.get(Number(CurrencyEnum[item]))});
    }
    for (const item of Object.keys(RegionsEnum).filter(key => !isNaN(Number(RegionsEnum[key])))){
      this.locations = this.locations.concat({name: Constants.RegionMap.get(Number(RegionsEnum[item]))});
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
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.taskService.add(this.task, {Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          console.log(data);
          this.router.navigate(['tasks', data.id]).then(() => console.log(Constants.NAVIGATED));
        },
        error => {
          console.log(error);
        });
    }
  }

  back(): void {
    this.router.navigate(['tasks']).then(() => console.log(Constants.NAVIGATED));
  }
}
