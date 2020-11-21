import {Component, OnInit, ViewChild} from '@angular/core';
import {TaskUserModel} from '../../models/task/task-user-model';
import {Constants} from '../../utils/constants';
import {MatTableDataSource} from '@angular/material/table';
import {faCheckSquare, faMinusSquare} from '@fortawesome/free-solid-svg-icons';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {ActivatedRoute, Router} from '@angular/router';
import {DealService} from '../../services/deal.service';

@Component({
  selector: 'app-deal-reward',
  templateUrl: './deal-reward.component.html',
  styleUrls: ['./deal-reward.component.css']
})
export class DealRewardComponent implements OnInit {
  executors: TaskUserModel[];
  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  dealId: number;
  customerId: number;
  isDataAvailable = false;
  displayedColumns: string[] = ['id', 'username', 'firstName', 'lastName', 'email', 'action'];
  dataSource = new MatTableDataSource<TaskUserModel>(this.executors);
  faAccept = faCheckSquare;
  faCancel = faMinusSquare;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  constructor(private dealService: DealService, private router: Router, private route: ActivatedRoute) { }

  static sortingDataAccessor(item, property) {
    return item[property];
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.dealId = Number(params.get('deal'));
    });
    this.init(this.dealId);
  }

  reward(witcherId: number): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.dealService.reward({Authorization : localStorage.getItem(Constants.TOKEN)}, witcherId, this.dealId).subscribe(
        data => {
          this.router.navigate(['deals', data.id]).then(() => console.log(Constants.NAVIGATED));
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
      this.dealService.refuse({Authorization : localStorage.getItem(Constants.TOKEN)}, witcherId, this.dealId).subscribe(
        data => {
          this.router.navigate(['deals', data.id]).then(() => console.log(Constants.NAVIGATED));
        },
        error => {
          console.log(error);
        });
    }
  }

  init(dealId: number){
    this.dealService.deal({Authorization : localStorage.getItem(Constants.TOKEN)}, dealId.toString()).subscribe(
      data => {
        this.customerId = data.customer.id;
        this.executors = data.executors;
        this.getExecutors(this.executors);
      },
      error => {
        console.log(error);
      });
  }

  private getExecutors(executors: TaskUserModel[]) {
    this.dataSource = new MatTableDataSource(executors);
    this.dataSource.sortingDataAccessor = DealRewardComponent.sortingDataAccessor;
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.isDataAvailable = this.executors.length !== 0;
  }
}
