import {TaskLocationModel} from './task.location.model';
import {TaskUserModel} from './task.user.model';
import {TaskBeastModel} from './task.beast.model';
import {TaskRewardModel} from './task.reward.model';

export class TaskViewModel {
  id: number;
  title: string;
  locationComment: string;
  location: TaskLocationModel;
  createOn: string;
  completionOn: string;
  done: boolean;
  paid: boolean;
  reward: TaskRewardModel;
  beasts: TaskBeastModel[];
  witchers: TaskUserModel[];
  customer: TaskUserModel;
  witcher: TaskUserModel;

  checkedLocation: string;
  checkedCurrency: string;
  checkedRewardValue: string;
  checkedBeast: string;
}
