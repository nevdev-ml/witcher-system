import {Component, Input, OnChanges, OnInit, ViewChild} from '@angular/core';
import {TaskUserModel} from '../../models/task/task-user-model';
import {Constants} from '../../utils/constants';
import {MatTableDataSource} from '@angular/material/table';
import {TaskViewModel} from '../../models/task/task-view-model';
import {TaskService} from '../../services/task.service';
import {ActivatedRoute, Router} from '@angular/router';
import {faCheckSquare, faExternalLinkAlt, faMinusSquare, faEdit, faTrashAlt} from '@fortawesome/free-solid-svg-icons';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-tasks-table',
  templateUrl: './tasks-table.component.html',
  styleUrls: ['./tasks-table.component.css']
})
export class TasksTableComponent implements OnInit, OnChanges {
  id = Number(localStorage.getItem(Constants.ID));
  role = localStorage.getItem(Constants.ROLES);
  displayedColumns: string[] = ['id', 'createOn', 'location.region', 'beast', 'reward.reward', 'customer', 'action'];
  dataSource: MatTableDataSource<TaskViewModel>;
  isDataAvailable = false;
  // Icons
  faOpen = faExternalLinkAlt;
  faAccept = faCheckSquare;
  faCancel = faMinusSquare;
  faEdit = faEdit;
  faTrashAlt = faTrashAlt;


  @Input() tasksList: TaskViewModel[];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  constructor(private taskService: TaskService, private router: Router, private route: ActivatedRoute) {}

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

    this.dataSource = new MatTableDataSource(this.tasksList);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sortingDataAccessor = TasksTableComponent.sortingDataAccessor;
    this.dataSource.sort = this.sort;
  }

  ngOnChanges(): void {
    if (this.tasksList !== undefined){
      if (this.dataSource === undefined){
        this.dataSource = new MatTableDataSource(this.tasksList);
      } else{
        this.dataSource.data = this.tasksList;
      }
      this.isDataAvailable = this.tasksList.length !== 0;
    }
  }

  accept(task): void {
    this.route.paramMap.subscribe(() => {
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.taskService.accept({Authorization : localStorage.getItem(Constants.TOKEN)}, task.id).subscribe(
          data => {
            this.tasksList[this.tasksList.indexOf(task)] = this.taskService.mapTask(data);
            this.ngOnChanges();
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
            if (this.router.url === '/tasks'){
              this.tasksList[this.tasksList.indexOf(task)] = this.taskService.mapTask(data);
              this.ngOnChanges();
            } else {
              if (this.role === 'WITCHER'){
                this.tasksList.splice(this.tasksList.indexOf(task), 1);
                this.ngOnChanges();
              }
            }
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
    this.router.navigate(['edit_task', task.id]).then(() => console.log(Constants.NAVIGATED));
  }

  delete(task): void {
    this.router.navigate(['delete_task', task.id]).then(() => console.log(Constants.NAVIGATED));
  }

  isContainsWitcher(witchers: TaskUserModel[]): boolean {
    let check = false;
    witchers.forEach(item => {
      if (item.id === this.id){
        return check = true;
      }
    });
    return check;
  }
}
