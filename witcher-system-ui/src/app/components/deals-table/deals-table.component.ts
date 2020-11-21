import {Component, Input, OnChanges, OnInit, ViewChild} from '@angular/core';
import {Constants} from '../../utils/constants';
import {MatTableDataSource} from '@angular/material/table';
import {DealViewModel} from '../../models/deal/deal-view-model';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {faCheckSquare, faExternalLinkAlt, faMinusSquare, faEdit, faTrashAlt} from '@fortawesome/free-solid-svg-icons';
import {ActivatedRoute, Router} from '@angular/router';
import {DealService} from '../../services/deal.service';
import {TaskUserModel} from '../../models/task/task-user-model';

@Component({
  selector: 'app-deals-table',
  templateUrl: './deals-table.component.html',
  styleUrls: ['./deals-table.component.css']
})
export class DealsTableComponent implements OnInit, OnChanges {
  id = Number(localStorage.getItem(Constants.ID));
  role = localStorage.getItem(Constants.ROLES);
  displayedColumns: string[] = ['id', 'title', 'createOn', 'reward.reward', 'customer', 'action'];
  dataSource: MatTableDataSource<DealViewModel>;
  isDataAvailable = false;
  // Icons
  faOpen = faExternalLinkAlt;
  faAccept = faCheckSquare;
  faCancel = faMinusSquare;
  faEdit = faEdit;
  faTrashAlt = faTrashAlt;

  @Input() dealsList: DealViewModel[];
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  constructor(private dealService: DealService, private router: Router, private route: ActivatedRoute) {}

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
    this.dataSource = new MatTableDataSource(this.dealsList);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sortingDataAccessor = DealsTableComponent.sortingDataAccessor;
    this.dataSource.sort = this.sort;
  }

  ngOnChanges(): void {
    if (this.dealsList !== undefined){
      if (this.dataSource === undefined){
        this.dataSource = new MatTableDataSource(this.dealsList);
      } else{
        this.dataSource.data = this.dealsList;
      }
      this.isDataAvailable = this.dealsList.length !== 0;
    }
  }

  accept(deal): void {
    this.route.paramMap.subscribe(() => {
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.dealService.accept({Authorization : localStorage.getItem(Constants.TOKEN)}, deal.id).subscribe(
          data => {
            this.dealsList[this.dealsList.indexOf(deal)] = this.dealService.mapDeal(data);
            this.ngOnChanges();
          },
          error => {
            console.log(error);
          });
      }
    });
  }

  cancel(deal): void {
    this.route.paramMap.subscribe(() => {
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.dealService.cancel({Authorization : localStorage.getItem(Constants.TOKEN)}, deal.id).subscribe(
          data => {
            if (this.router.url === '/shop' || this.router.url === '/workshop'){
              this.dealsList[this.dealsList.indexOf(deal)] = this.dealService.mapDeal(data);
              this.ngOnChanges();
            } else {
              if (this.role === 'WITCHER'){
                this.dealsList.splice(this.dealsList.indexOf(deal), 1);
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

  read(deal): void {
    this.router.navigate(['deals', deal.id]).then(() => console.log(Constants.NAVIGATED));
  }

  edit(deal): void {
    if (this.router.url === '/shop'){
      this.router.navigate(['edit_shop', deal.id]).then(() => console.log(Constants.NAVIGATED));
    } else{
      this.router.navigate(['edit_workshop', deal.id]).then(() => console.log(Constants.NAVIGATED));
    }
  }

  delete(deal): void {
    if (this.router.url === '/shop'){
      this.router.navigate(['delete_shop', deal.id]).then(() => console.log(Constants.NAVIGATED));
    } else{
      this.router.navigate(['delete_workshop', deal.id]).then(() => console.log(Constants.NAVIGATED));
    }
  }

  isContainsExecutor(executors: TaskUserModel[]): boolean {
    let check = false;
    executors.forEach(item => {
      if (item.id === this.id){
        return check = true;
      }
    });
    return check;
  }
}
