import {TaskRewardModel} from '../task/task-reward-model';
import {TaskUserModel} from '../task/task-user-model';

export class DealViewModel {
  id: number;
  title: string;
  description: string;
  createOn: string;
  completionOn: string;
  done: boolean;
  paid: boolean;
  trader: boolean;
  sale: boolean;
  reward: TaskRewardModel;
  executors: TaskUserModel[];
  customer: TaskUserModel;
  executor: TaskUserModel;
  executorsBookmarked: TaskUserModel[];
}
