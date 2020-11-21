import { Component, OnInit } from '@angular/core';
import {Constants} from '../../utils/constants';
import {ActivatedRoute, Router} from '@angular/router';
import {DealService} from '../../services/deal.service';

@Component({
  selector: 'app-deal-delete',
  templateUrl: './deal-delete.component.html',
  styleUrls: ['./deal-delete.component.css']
})
export class DealDeleteComponent implements OnInit {
  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  dealId: number;

  constructor(private dealService: DealService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.dealId = Number(params.get('deal'));
    });
  }


  delete(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.dealService.delete(this.dealId, {Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        () => {
          if (this.router.url === '/delete_shop/' + this.dealId){
            this.router.navigate(['shop']).then(() => console.log(Constants.NAVIGATED));
          } else{
            this.router.navigate(['workshop']).then(() => console.log(Constants.NAVIGATED));
          }
        },
        error => {
          console.log(error);
        });
    }
  }

  back(): void {
    if (this.router.url === '/delete_shop/' + this.dealId){
      this.router.navigate(['shop']).then(() => console.log(Constants.NAVIGATED));
    } else{
      this.router.navigate(['workshop']).then(() => console.log(Constants.NAVIGATED));
    }
  }
}
