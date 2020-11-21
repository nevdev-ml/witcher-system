import { Component, OnInit } from '@angular/core';
import {Constants} from '../../utils/constants';
import {TaskService} from '../../services/task.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-task-delete',
  templateUrl: './task-delete.component.html',
  styleUrls: ['./task-delete.component.css']
})
export class TaskDeleteComponent implements OnInit {
  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  taskId: number;

  constructor(private taskService: TaskService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.taskId = Number(params.get('task'));
    });
  }


  delete(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.taskService.delete(this.taskId, {Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        () => {
          this.router.navigate(['/tasks']).then(() => console.log('Success delete'));
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
