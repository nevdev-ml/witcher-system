import {Component, OnInit} from '@angular/core';
import {TaskService} from '../../services/task.service';
import {Router} from '@angular/router';
import {TaskViewModel} from '../../models/task/task-view-model';
import {Constants} from '../../utils/constants';
import {TasksViewModel} from '../../models/task/tasks-view-model';
import {faPlusCircle} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  constructor(private taskService: TaskService, private router: Router) {}
  tasks: TasksViewModel;
  activeTasks: TaskViewModel[];
  winTasks: TaskViewModel[];
  loseTasks: TaskViewModel[];

  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  isWitcherProfile = false;
  isCustomerProfile = false;
  isDataAvailable = false;

  faAdd = faPlusCircle;


  ngOnInit(): void {
    if (this.router.url === '/tasks'){
      this.getTasks();
    }
    else{
      if (this.role === 'USER'){
        this.getCustomerQuests();
      }
      else{
        this.getQuests();
      }
    }
  }

  getTasks(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.taskService.tasks({Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.init(data, false);
        },
        error => {
          console.log(error);
        });
    }
  }

  getQuests(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.taskService.quests({Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.isWitcherProfile = true;
          this.init(data, true);
        },
        error => {
          console.log(error);
        });
    }
  }

  getCustomerQuests(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.taskService.customerQuests({Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.isCustomerProfile = true;
          this.init(data, true);
        },
        error => {
          console.log(error);
        });
    }
  }

  init(data, isProfile: boolean) {
    if (isProfile){
      this.tasks = data;
      this.tasks.active.forEach((item) => {
        this.taskService.mapTask(item);
      });
      this.activeTasks = this.tasks.active;

      this.tasks.win.forEach((item) => {
        this.taskService.mapTask(item);
      });
      this.winTasks = this.tasks.win;
      this.tasks.lose.forEach((item) => {
        this.taskService.mapTask(item);
      });
      this.loseTasks = this.tasks.lose;
      this.isDataAvailable = true;
    }
    else{
      data.forEach((item) => {
        this.taskService.mapTask(item);
      });
      this.activeTasks = data;
      this.isDataAvailable = true;
    }
  }

  add(): void {
    this.router.navigate(['add_task']).then(() => console.log(Constants.NAVIGATED));
  }
}
