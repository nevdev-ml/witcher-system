import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {DealService} from '../../services/deal.service';
import {Constants} from '../../utils/constants';
import {faPlusCircle} from '@fortawesome/free-solid-svg-icons';
import {DealsViewModel} from '../../models/deal/deals-view-model';
import {DealViewModel} from '../../models/deal/deal-view-model';

@Component({
  selector: 'app-deals',
  templateUrl: './deals.component.html',
  styleUrls: ['./deals.component.css']
})
export class DealsComponent implements OnInit {
  deals: DealsViewModel;
  activeDeals: DealViewModel[];
  successDeals: DealViewModel[];
  bookmarkedDeals: DealViewModel[];
  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  isWitcherProfile = false;
  isCustomerProfile = false;
  isDataAvailable = false;
  isShop = false;
  isWorkshop = false;

  faAdd = faPlusCircle;


  constructor(private dealService: DealService, private router: Router) {}

  ngOnInit(): void {
    if (this.router.url === '/shop'){
      this.getShopDeals();
      this.isShop = true;
    } else {
      if (this.router.url === '/workshop'){
        this.getWorkshopDeals();
        this.isWorkshop = true;
      } else{
        if (this.role === 'VENDOR' || this.role === 'BLACKSMITH'){
          this.getCustomerDeals();
        }
        else{
          this.getAcceptedDeals();
        }
      }
    }
  }

  getShopDeals(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.dealService.shop({Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.init(data, false);
        },
        error => {
          console.log(error);
        });
    }
  }

  getWorkshopDeals(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.dealService.workshop({Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.init(data, false);
        },
        error => {
          console.log(error);
        });
    }
  }

  getAcceptedDeals(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.dealService.acceptedDeals({Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.isWitcherProfile = true;
          this.init(data, true);
        },
        error => {
          console.log(error);
        });
    }
  }

  getCustomerDeals(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.dealService.customerDeals({Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.isCustomerProfile = true;
          this.init(data, true);
        },
        error => {
          console.log(error);
        });
    }
  }


  init(data, isProfile: boolean) {
    if (isProfile){
      this.deals = data;
      this.deals.active.forEach((item) => {
        this.dealService.mapDeal(item);
      });
      this.activeDeals = this.deals.active;

      this.deals.success.forEach((item) => {
        this.dealService.mapDeal(item);
      });
      this.successDeals = this.deals.success;
      this.deals.bookmarked.forEach((item) => {
        this.dealService.mapDeal(item);
      });
      this.bookmarkedDeals = this.deals.bookmarked;
      this.isDataAvailable = true;
    }
    else{
      data.forEach((item) => {
        this.dealService.mapDeal(item);
      });
      this.activeDeals = data;
      this.isDataAvailable = true;
    }
  }

  add(): void {
    if (this.router.url === '/shop'){
      this.router.navigate(['add_shop']).then(() => console.log(Constants.NAVIGATED));
    } else{
      this.router.navigate(['add_workshop']).then(() => console.log(Constants.NAVIGATED));
    }
  }
}
