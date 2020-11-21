import { Component, OnInit } from '@angular/core';
import {TaskService} from '../../services/task.service';
import {Router} from '@angular/router';
import {TaskModifyModel} from '../../models/task/task-modify-model';
import {Constants} from '../../utils/constants';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-task.create',
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.css']
})
export class TaskCreateComponent implements OnInit {
  task: TaskModifyModel = new TaskModifyModel();
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
    const mappedTask = this.taskService.mapAnswers(this.beasts, this.currency, this.locations);
    this.beasts = mappedTask.beasts;
    this.currency = mappedTask.currency;
    this.locations = mappedTask.locations;
    this.form = new FormGroup({
      title: new FormControl('', Validators.required),
      checkedRewardValue: new FormControl('', [Validators.required, Validators.min(0),
        Validators.pattern('^[0-9]*$')]),
      locationComment: new FormControl()
    });
  }

  onChangedLocation(checkedValue: string) {
    this.task.checkedLocation = checkedValue;
  }

  onChangedCurrency(checkedValue: string) {
    this.task.checkedCurrency = checkedValue;
  }

  onChangedBeast(checkedValue: string) {
    this.task.checkedBeast = checkedValue;
  }

  add(): void {
    if (this.form.invalid || !this.isValidForm()) {
      return;
    }
    this.task.title = this.form.get('title').value;
    this.task.locationComment = this.form.get('locationComment').value;
    this.task.checkedRewardValue = this.form.get('checkedRewardValue').value;

    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.taskService.add(this.task, {Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.router.navigate(['tasks', data.id]).then(() => console.log(Constants.NAVIGATED));
        },
        error => {
          console.log(error);
        });
    }
  }

  isValidForm(){
    return !(this.task.checkedBeast === undefined || this.task.checkedLocation === undefined ||
      this.task.checkedCurrency === undefined);
  }

  back(): void {
    this.router.navigate(['tasks']).then(() => console.log(Constants.NAVIGATED));
  }
}
