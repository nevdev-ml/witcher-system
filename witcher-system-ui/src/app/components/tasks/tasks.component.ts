import { Component, OnInit, ViewChild } from '@angular/core';
import {TaskService} from '../../services/task.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AppComponent} from '../../app.component';
import {TaskViewModel} from '../../models/task/task.view.model';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import { faExternalLinkAlt, faCheckSquare, faEdit, faPlusCircle, faMinusSquare } from '@fortawesome/free-solid-svg-icons';
import {TaskUserModel} from '../../models/task/task.user.model';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
  tasks: TaskViewModel[];
  role = localStorage.getItem(AppComponent.ROLES);
  id = localStorage.getItem(AppComponent.ID);
  isDataAvailable = false;
  displayedColumns: string[] = ['id', 'createOn', 'location.region', 'beast', 'reward.reward', 'customer', 'action'];
  dataSource = new MatTableDataSource<TaskViewModel>(this.tasks);
  faOpen = faExternalLinkAlt;
  faAccept = faCheckSquare;
  faCancel = faMinusSquare;
  faEdit = faEdit;
  faAdd = faPlusCircle;

  constructor(private taskService: TaskService, private router: Router, private route: ActivatedRoute) {}

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;


  ngOnInit(): void {
    this.paginator._intl.itemsPerPageLabel = 'Элементов на странице';
    this.paginator._intl.firstPageLabel = 'Первая страница';
    this.paginator._intl.nextPageLabel = 'Следующая страница';
    this.paginator._intl.previousPageLabel = 'Предыдущая страница';
    this.paginator._intl.lastPageLabel = 'Последняя страница';
    if (this.router.url === '/quests'){
      this.getQuests();
    }
    else{
      this.getTasks();
    }
  }

  getTasks(): void {
    if (localStorage.getItem(AppComponent.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(AppComponent.NOT_AUTH));
    } else{
      this.taskService.tasks({Authorization : localStorage.getItem(AppComponent.TOKEN)}).subscribe(
        data => {
          console.log(data);
          data.forEach((item) => {
            this.taskService.mapTask(item);
          });
          this.tasks = data;
          this.dataSource = new MatTableDataSource(this.tasks);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
          this.isDataAvailable = this.tasks.length !== 0;
        },
        error => {
          console.log(error);
        });
    }
  }

  getQuests(): void {
    if (localStorage.getItem(AppComponent.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(AppComponent.NOT_AUTH));
    } else{
      this.taskService.quests({Authorization : localStorage.getItem(AppComponent.TOKEN)}).subscribe(
        data => {
          console.log(data);
          data.forEach((item) => {
            this.taskService.mapTask(item);
          });
          this.tasks = data;
          this.dataSource = new MatTableDataSource(this.tasks);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
          this.isDataAvailable = this.tasks.length !== 0;
        },
        error => {
          console.log(error);
        });
    }
  }


  accept(task): void {
    this.route.paramMap.subscribe(params => {
      if (localStorage.getItem(AppComponent.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(AppComponent.NOT_AUTH));
      } else{
        this.taskService.accept({Authorization : localStorage.getItem(AppComponent.TOKEN)}, task.id).subscribe(
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

  cancel(task): void {
    this.route.paramMap.subscribe(params => {
      if (localStorage.getItem(AppComponent.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(AppComponent.NOT_AUTH));
      } else{
        this.taskService.cancel({Authorization : localStorage.getItem(AppComponent.TOKEN)}, task.id).subscribe(
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

  read(task): void {
    this.router.navigate(['tasks', task.id]).then(() => console.log(AppComponent.NAVIGATED));
  }

  edit(task): void {
    this.taskService.edit(task);
  }

  add(): void {
    this.router.navigate(['add']).then(() => console.log(AppComponent.NAVIGATED));
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
