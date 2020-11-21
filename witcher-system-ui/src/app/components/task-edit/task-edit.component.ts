import { Component, OnInit } from '@angular/core';
import {Constants} from '../../utils/constants';
import {TaskService} from '../../services/task.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TaskViewModel} from '../../models/task/task-view-model';
import {TaskModifyModel} from '../../models/task/task-modify-model';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-task-edit',
  templateUrl: './task-edit.component.html',
  styleUrls: ['./task-edit.component.css']
})
export class TaskEditComponent implements OnInit {
  errorMessage: string;
  form: FormGroup;
  beasts: {name: string}[] = [];
  currency: {name: string}[] = [];
  locations: {name: string}[] = [];
  titleBeast = 'Бестия';
  titleCurrency = 'Валюта';
  titleLocation = 'Локация';

  task: TaskViewModel;
  modifyTask: TaskModifyModel = new TaskModifyModel();
  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  isDataAvailable = false;


  constructor(private taskService: TaskService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.getTask();
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

  getTask() {
    this.route.paramMap.subscribe(params => {
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.taskService.task({Authorization : localStorage.getItem(Constants.TOKEN)}, params.get('task')).subscribe(
          data => {
            this.task = this.taskService.mapTask(data);
            this.isDataAvailable = true;
            this.mapTaskToModify();
          },
          error => {
            console.log(error);
          });
      }
    });
  }

  mapTaskToModify(){
    this.modifyTask.title = this.task.title;
    this.modifyTask.checkedRewardValue = this.task.reward.reward.toString();
    this.modifyTask.locationComment = this.task.locationComment;
    this.form.setValue({
      title: this.modifyTask.title,
      checkedRewardValue: this.modifyTask.checkedRewardValue,
      locationComment: this.modifyTask.locationComment
    });
  }



  isValidForm(){
    return !(this.modifyTask.checkedBeast === undefined || this.modifyTask.checkedLocation === undefined ||
      this.modifyTask.checkedCurrency === undefined);
  }

  edit(): void {
    if (this.form.invalid || !this.isValidForm()) {
      return;
    }
    this.modifyTask.title = this.form.get('title').value;
    this.modifyTask.locationComment = this.form.get('locationComment').value;
    this.modifyTask.checkedRewardValue = this.form.get('checkedRewardValue').value;

    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.taskService.edit(this.modifyTask, this.task.id, {Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.router.navigate(['tasks', data.id]).then(() => console.log(Constants.NAVIGATED));
        },
        error => {
          console.log(error);
        });
    }
  }

  onChangedLocation(checkedValue: string) {
    this.modifyTask.checkedLocation = checkedValue;
  }

  onChangedCurrency(checkedValue: string) {
    this.modifyTask.checkedCurrency = checkedValue;
  }

  onChangedBeast(checkedValue: string) {
    this.modifyTask.checkedBeast = checkedValue;
  }

  back(): void {
    this.router.navigate(['tasks']).then(() => console.log(Constants.NAVIGATED));
  }
}
