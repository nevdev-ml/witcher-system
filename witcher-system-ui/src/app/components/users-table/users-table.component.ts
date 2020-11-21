import {Component, Input, OnChanges, OnInit, ViewChild} from '@angular/core';
import {Constants} from '../../utils/constants';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {AccountService} from '../../services/account-service';
import {Router} from '@angular/router';
import {faCheckSquare, faEdit, faMinusSquare, faTrashAlt} from '@fortawesome/free-solid-svg-icons';
import {UserView} from '../../models/user-view';
import {AuthService} from '../../services/auth-service';

@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.css']
})
export class UsersTableComponent implements OnInit, OnChanges {
  id = Number(localStorage.getItem(Constants.ID));
  role = localStorage.getItem(Constants.ROLES);
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'username', 'email', 'role', 'action'];
  dataSource: MatTableDataSource<UserView>;
  isDataAvailable = false;
  // Icons
  faEdit = faEdit;
  faTrashAlt = faTrashAlt;
  faAccept = faCheckSquare;
  faCancel = faMinusSquare;

  @Input() usersList: UserView[];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  constructor(private accountService: AccountService,  private authService: AuthService, private router: Router) {}

  static sortingDataAccessor(item, property) {
    switch (property) {
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
    this.dataSource = new MatTableDataSource(this.usersList);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sortingDataAccessor = UsersTableComponent.sortingDataAccessor;
    this.dataSource.sort = this.sort;
  }

  ngOnChanges(): void {
    if (this.usersList !== undefined){
      if (this.dataSource === undefined){
        this.dataSource = new MatTableDataSource(this.usersList);
      } else{
        this.dataSource.data = this.usersList;
      }
      this.isDataAvailable = this.usersList.length !== 0;
    }
  }

  enable(user): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.accountService.enable({Authorization : localStorage.getItem(Constants.TOKEN)}, user).subscribe(
        data => {
          this.usersList[this.usersList.indexOf(user)] = this.authService.mapUser(data);
          this.ngOnChanges();
        },
        error => {
          console.log(error);
        });
    }
  }

  disable(user): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.accountService.disable({Authorization : localStorage.getItem(Constants.TOKEN)}, user).subscribe(
        data => {
          this.usersList[this.usersList.indexOf(user)] = this.authService.mapUser(data);
          this.ngOnChanges();
        },
        error => {
          console.log(error);
        });
    }
  }

  edit(user): void {
    this.router.navigate(['change_user'], {state: {data: user}}).then(() => console.log(Constants.NAVIGATED));
  }

  delete(user): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.accountService.delete({Authorization : localStorage.getItem(Constants.TOKEN)}, user).subscribe(
        () => {
          this.usersList.splice(this.usersList.indexOf(user), 1);
          this.ngOnChanges();
        },
        error => {
          console.log(error);
        });
    }
  }
}
