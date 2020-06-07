import { Component, OnInit } from '@angular/core';
import {AppComponent} from '../../app.component';
import {Router, ActivatedRoute} from '@angular/router';
import {TaskService} from '../../services/task.service';
import {TaskViewModel} from '../../models/task/task.view.model';
import {TaskUserModel} from '../../models/task/task.user.model';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  task: TaskViewModel;
  role = localStorage.getItem(AppComponent.ROLES);
  id = localStorage.getItem(AppComponent.ID);
  isDataAvailable = false;

  constructor(private taskService: TaskService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.getTask();
  }

  getTask(): void {
    this.route.paramMap.subscribe(params => {
      if (localStorage.getItem(AppComponent.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(AppComponent.NOT_AUTH));
      } else{
        this.taskService.task({Authorization : localStorage.getItem(AppComponent.TOKEN)}, params.get('task')).subscribe(
          data => {
            console.log(data);
            this.task = this.taskService.mapTask(data);
            this.isDataAvailable = true;
          },
          error => {
            console.log(error);
          });
      }
    });
  }

  accept(): void {
    this.route.paramMap.subscribe(params => {
      if (localStorage.getItem(AppComponent.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(AppComponent.NOT_AUTH));
      } else{
        this.taskService.accept({Authorization : localStorage.getItem(AppComponent.TOKEN)}, params.get('task')).subscribe(
          data => {
            console.log(data);
            this.ngOnInit();
          },
          error => {
            console.log(error);
          });
      }
    });
  }

  cancel(): void {
    this.route.paramMap.subscribe(params => {
      if (localStorage.getItem(AppComponent.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(AppComponent.NOT_AUTH));
      } else{
        this.taskService.cancel({Authorization : localStorage.getItem(AppComponent.TOKEN)}, params.get('task')).subscribe(
          data => {
            console.log(data);
            this.ngOnInit();
          },
          error => {
            console.log(error);
          });
      }
    });
  }

  edit(task): void {
    this.taskService.edit(task);
  }

  backToTasks(): void {
    this.router.navigate(['tasks']).then(() => console.log(AppComponent.NAVIGATED));
  }

  backToQuests(): void {
    this.router.navigate(['quests']).then(() => console.log(AppComponent.NAVIGATED));
  }

  isContainsWitcher(witchers: TaskUserModel[]): boolean {
    let check = false;
    witchers.forEach(item => {
      if (item.id == this.id){
        return check = true;
      }
    });
    return check;
  }
}
