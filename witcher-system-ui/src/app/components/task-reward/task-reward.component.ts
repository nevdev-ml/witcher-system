import {Component, OnInit, ViewChild} from '@angular/core';
import {TaskService} from '../../services/task.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TaskUserModel} from '../../models/task/task-user-model';
import {Constants} from '../../utils/constants';
import {MatTableDataSource} from '@angular/material/table';
import {faCheckSquare, faMinusSquare} from '@fortawesome/free-solid-svg-icons';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-task.reward',
  templateUrl: './task-reward.component.html',
  styleUrls: ['./task-reward.component.css']
})
export class TaskRewardComponent implements OnInit {
  witchers: TaskUserModel[];
  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  taskId: number;
  customerId: number;
  isDataAvailable = false;
  displayedColumns: string[] = ['id', 'username', 'firstName', 'lastName', 'email', 'action'];
  dataSource = new MatTableDataSource<TaskUserModel>(this.witchers);
  faAccept = faCheckSquare;
  faCancel = faMinusSquare;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  constructor(private taskService: TaskService, private router: Router, private route: ActivatedRoute) { }

  static sortingDataAccessor(item, property) {
    return item[property];
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.taskId = Number(params.get('task'));
    });
    this.init(this.taskId);
  }

  reward(witcherId: number): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.taskService.reward({Authorization : localStorage.getItem(Constants.TOKEN)}, witcherId, this.taskId).subscribe(
        data => {
          this.router.navigate(['tasks', data.id]).then(() => console.log(Constants.NAVIGATED));
        },
        error => {
          console.log(error);
        });
    }
  }

  cancel(witcherId: number): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.taskService.refuse({Authorization : localStorage.getItem(Constants.TOKEN)}, witcherId, this.taskId).subscribe(
        data => {
          this.router.navigate(['tasks', data.id]).then(() => console.log(Constants.NAVIGATED));
        },
        error => {
          console.log(error);
        });
    }
  }
  init(taskId: number){
    this.taskService.task({Authorization : localStorage.getItem(Constants.TOKEN)}, taskId.toString()).subscribe(
      data => {
        this.customerId = data.customer.id;
        this.witchers = data.witchersCompleted;
        this.getWitchers(this.witchers);
      },
      error => {
        console.log(error);
      });
  }

  private getWitchers(witchers: TaskUserModel[]) {
    this.dataSource = new MatTableDataSource(witchers);
    this.dataSource.sortingDataAccessor = TaskRewardComponent.sortingDataAccessor;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.isDataAvailable = this.witchers.length !== 0;
  }
}
