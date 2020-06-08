import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TaskViewModel} from '../models/task/task.view.model';
import {BeastsEnum} from '../enums/beasts.enum';
import {RegionsEnum} from '../enums/regions.enum';
import {CurrencyEnum} from '../enums/currency.enum';
import {TaskCreateModel} from '../models/task/task.create.model';
import {Constants} from '../utils/constants';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(public http: HttpClient) { }

  task(token: { Authorization: string }, task: string){
    return this.http.get<TaskViewModel>(Constants.PATH_TASK + task, {headers: token});
  }

  tasks(token: { Authorization: string }){
    return this.http.post<TaskViewModel[]>(Constants.PATH_TASKS, null, {headers: token});
  }

  quests(token: { Authorization: string }){
    return this.http.post<TaskViewModel[]>(Constants.PATH_QUESTS, null, {headers: token});
  }

  mapTask(data): TaskViewModel {
    data.beasts.forEach((item) => {
        item.beastName = Constants.BeastMap.get(Number(BeastsEnum[item.beastName]));
      }
    );
    data.location.region = Constants.RegionMap.get(Number(RegionsEnum[data.location.region]));
    data.reward.type = Constants.CurrencyMap.get(Number(CurrencyEnum[data.reward.type]));
    return data;
  }

  accept(token: { Authorization: string }, task: string) {
    return this.http.post<TaskViewModel>(Constants.PATH_ACCEPT_TASK + task, null, {headers: token});
  }

  cancel(token: { Authorization: string }, task: string) {
    return this.http.post<TaskViewModel>(Constants.PATH_CANCEL_TASK + task, null, {headers: token});
  }

  edit(task): void {
    // TODO
  }

  add(task: TaskCreateModel, token: { Authorization: string }) {
    return this.http.post<TaskViewModel>(Constants.PATH_ADD_TASK, task, {headers: token});
  }
}
