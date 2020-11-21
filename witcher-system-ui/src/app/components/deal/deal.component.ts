import { Component, OnInit } from '@angular/core';
import {Constants} from '../../utils/constants';
import {ActivatedRoute, Router} from '@angular/router';
import {TaskUserModel} from '../../models/task/task-user-model';
import {DealViewModel} from '../../models/deal/deal-view-model';
import {DealService} from '../../services/deal.service';

@Component({
  selector: 'app-deal',
  templateUrl: './deal.component.html',
  styleUrls: ['./deal.component.css']
})
export class DealComponent implements OnInit {
  deal: DealViewModel;
  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  isDataAvailable = false;

  constructor(private dealService: DealService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.getDeal();
  }

  getDeal(): void {
    this.route.paramMap.subscribe(params => {
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.dealService.deal({Authorization : localStorage.getItem(Constants.TOKEN)}, params.get('deal')).subscribe(
          data => {
            this.deal = this.dealService.mapDeal(data);
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
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.dealService.accept({Authorization : localStorage.getItem(Constants.TOKEN)}, params.get('deal')).subscribe(
          () => {
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
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.dealService.cancel({Authorization : localStorage.getItem(Constants.TOKEN)}, params.get('deal')).subscribe(
          () => {
            this.ngOnInit();
          },
          error => {
            console.log(error);
          });
      }
    });
  }

  edit(deal: DealViewModel): void {
    if (this.router.url === '/shop'){
      this.router.navigate(['edit_shop', deal.id]).then(() => console.log(Constants.NAVIGATED));
    } else{
      this.router.navigate(['edit_workshop', deal.id]).then(() => console.log(Constants.NAVIGATED));
    }
  }

  delete(deal: DealViewModel): void {
    if (this.router.url === '/shop'){
      this.router.navigate(['delete_shop', deal.id]).then(() => console.log(Constants.NAVIGATED));
    } else{
      this.router.navigate(['delete_workshop', deal.id]).then(() => console.log(Constants.NAVIGATED));
    }
  }

  complete(): void {
    this.route.paramMap.subscribe(params => {
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.dealService.complete({Authorization : localStorage.getItem(Constants.TOKEN)}, params.get('deal')).subscribe(
          () => {
            this.ngOnInit();
          },
          error => {
            console.log(error);
          });
      }
    });
  }

  getAcceptedExecutors(deal: DealViewModel): void {
    this.router.navigate(['reward_deal',  deal.id]).then(() => console.log(Constants.NAVIGATED));
  }

  backToDeals(): void {
    this.router.navigate(['profile']).then(() => console.log(Constants.NAVIGATED));
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
