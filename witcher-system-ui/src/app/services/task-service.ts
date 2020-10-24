import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TaskViewModel} from '../models/task/task-view-model';
import {BeastsEnum} from '../enums/beasts-enum';
import {RegionsEnum} from '../enums/regions-enum';
import {CurrencyEnum} from '../enums/currency-enum';
import {TaskModifyModel} from '../models/task/task-modify-model';
import {Constants} from '../utils/constants';
import {TasksViewModel} from '../models/task/tasks-view-model';
import {TaskModifyUtil} from '../models/task/task-modify-util';

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
    return this.http.post<TasksViewModel>(Constants.PATH_QUESTS, null, {headers: token});
  }

  customerQuests(token: { Authorization: string }){
    return this.http.post<TasksViewModel>(Constants.PATH_CUSTOMER_QUESTS, null, {headers: token});
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

  complete(token: { Authorization: string }, task: string) {
    return this.http.post<TaskViewModel>(Constants.PATH_COMPLETE_TASK + task, null, {headers: token});
  }

  reward(token: { Authorization: string }, witcherId: number, taskId: number){
    return this.http.post<TaskViewModel>(Constants.PATH_REWARD_TASK + taskId, witcherId, {headers: token});
  }

  refuse(token: { Authorization: string }, witcherId: number, taskId: number){
    return this.http.post<TaskViewModel>(Constants.PATH_REFUSE_TASK + taskId, witcherId, {headers: token});
  }

  edit(task: TaskModifyModel, taskId: number, token: { Authorization: string }) {
    return this.http.post<TaskViewModel>(Constants.PATH_EDIT_TASK + taskId, task, {headers: token});
  }

  delete(taskId: number, token: { Authorization: string }) {
    return this.http.post<TaskViewModel>(Constants.PATH_DELETE_TASK + taskId, null, {headers: token});
  }

  add(task: TaskModifyModel, token: { Authorization: string }) {
    return this.http.post<TaskViewModel>(Constants.PATH_ADD_TASK, task, {headers: token});
  }

  mapAnswers(beasts: {name: string}[], currency: {name: string}[], locations: {name: string}[]): TaskModifyUtil{
    for (const item of Object.keys(BeastsEnum).filter(key => !isNaN(Number(BeastsEnum[key])))){
      beasts = beasts.concat({name: Constants.BeastMap.get(Number(BeastsEnum[item]))});
    }
    for (const item of Object.keys(CurrencyEnum).filter(key => !isNaN(Number(CurrencyEnum[key])))){
      currency = currency.concat({name: Constants.CurrencyMap.get(Number(CurrencyEnum[item]))});
    }
    for (const item of Object.keys(RegionsEnum).filter(key => !isNaN(Number(RegionsEnum[key])))){
      locations = locations.concat({name: Constants.RegionMap.get(Number(RegionsEnum[item]))});
    }
    return new TaskModifyUtil(beasts, currency, locations);
  }
}
