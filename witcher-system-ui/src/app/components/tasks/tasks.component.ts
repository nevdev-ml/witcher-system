import { Component, OnInit, ViewChild } from '@angular/core';
import {TaskService} from '../../services/task.service';
import {ActivatedRoute, Router} from '@angular/router';
import {TaskViewModel} from '../../models/task/task.view.model';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import { faExternalLinkAlt, faCheckSquare, faEdit, faPlusCircle, faMinusSquare } from '@fortawesome/free-solid-svg-icons';
import {TaskUserModel} from '../../models/task/task.user.model';
import {Constants} from '../../utils/constants';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  constructor(private taskService: TaskService, private router: Router, private route: ActivatedRoute) {}
  tasks: TaskViewModel[];
  role = localStorage.getItem(Constants.ROLES);
  id = localStorage.getItem(Constants.ID);
  isDataAvailable = false;
  displayedColumns: string[] = ['id', 'createOn', 'location.region', 'beast', 'reward.reward', 'customer', 'action'];
  dataSource = new MatTableDataSource<TaskViewModel>(this.tasks);
  faOpen = faExternalLinkAlt;
  faAccept = faCheckSquare;
  faCancel = faMinusSquare;
  faEdit = faEdit;
  faAdd = faPlusCircle;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  static sortingDataAccessor(item, property) {
    switch (property) {
      case 'beast': return item.beasts[0].beastName;
      case 'location.region': return item.location.region;
      case 'reward.reward': return item.reward.reward;
      case 'customer': return item.customer.lastName;
      default: return item[property];
    }
  }


  ngOnInit(): void {
    this.paginator._intl.itemsPerPageLabel = 'Элементов на странице';
    this.paginator._intl.firstPageLabel = 'Первая страница';
    this.paginator._intl.nextPageLabel = 'Следующая страница';
    this.paginator._intl.previousPageLabel = 'Предыдущая страница';
    this.paginator._intl.lastPageLabel = 'Последняя страница';
    if (this.router.url === '/quests'){
      this.getQuests();
    } else if (this.router.url === '/profile'){
      this.getQuests();
    }
    else{
      this.getTasks();
    }
  }

  getTasks(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.taskService.tasks({Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.init(data);
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
          this.init(data);
        },
        error => {
          console.log(error);
        });
    }
  }

  init(data) {
    console.log(data);
    data.forEach((item) => {
      this.taskService.mapTask(item);
    });
    this.tasks = data;
    this.dataSource = new MatTableDataSource(this.tasks);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sortingDataAccessor = TasksComponent.sortingDataAccessor;
    this.dataSource.sort = this.sort;
    this.isDataAvailable = this.tasks.length !== 0;
  }

  accept(task): void {
    this.route.paramMap.subscribe(() => {
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.taskService.accept({Authorization : localStorage.getItem(Constants.TOKEN)}, task.id).subscribe(
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
    this.route.paramMap.subscribe(() => {
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.taskService.cancel({Authorization : localStorage.getItem(Constants.TOKEN)}, task.id).subscribe(
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
    this.router.navigate(['tasks', task.id]).then(() => console.log(Constants.NAVIGATED));
  }

  edit(task): void {
    this.taskService.edit(task);
  }

  add(): void {
    this.router.navigate(['add']).then(() => console.log(Constants.NAVIGATED));
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
