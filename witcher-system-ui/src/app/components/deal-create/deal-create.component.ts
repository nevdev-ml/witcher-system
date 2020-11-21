import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Constants} from '../../utils/constants';
import {DealModifyModel} from '../../models/deal/deal-modify-model';
import {DealService} from '../../services/deal.service';

@Component({
  selector: 'app-deal-create',
  templateUrl: './deal-create.component.html',
  styleUrls: ['./deal-create.component.css']
})
export class DealCreateComponent implements OnInit {
  deal: DealModifyModel = new DealModifyModel();
  role = localStorage.getItem(Constants.ROLES);
  errorMessage: string;
  form: FormGroup;
  currency: {name: string}[] = [];
  type: {name: string}[] = [];
  titleType = 'Тип сделки';
  titleCurrency = 'Валюта';
  dealId: number;

  constructor(public dealService: DealService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.dealId = Number(params.get('deal'));
    });
    const mappedDeal = this.dealService.mapAnswers(this.currency);
    this.currency = mappedDeal.currency;
    if (this.role === 'VENDOR'){
      this.type = [{name: 'Продажа'}, {name: 'Покупка'}];
    } else{
      if (this.role === 'BLACKSMITH'){
        this.type = [{name: 'Ремонт'}, {name: 'Выкуп'}];
      }
      else {
        if (this.router.url === '/add_shop/' + this.dealId){
          this.type = [{name: 'Продажа'}, {name: 'Покупка'}];
        } else{
          this.type = [{name: 'Ремонт'}, {name: 'Выкуп'}];
        }
      }
    }
    this.form = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      checkedRewardValue: new FormControl('', [Validators.required, Validators.min(0),
        Validators.pattern('^[0-9]*$')])
    });
  }

  onChangedType(checkedValue: string) {
    this.deal.checkedType = checkedValue;
  }

  onChangedCurrency(checkedValue: string) {
    this.deal.checkedCurrency = checkedValue;
  }

  add(): void {
    if (this.form.invalid || !this.isValidForm()) {
      return;
    }
    this.deal.title = this.form.get('title').value;
    this.deal.description = this.form.get('description').value;
    this.deal.checkedRewardValue = this.form.get('checkedRewardValue').value;

    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.dealService.add(this.deal, {Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.router.navigate(['deals', data.id]).then(() => console.log(Constants.NAVIGATED));
        },
        error => {
          console.log(error);
        });
    }
  }

  isValidForm(){
    return !(this.deal.checkedCurrency === undefined);
  }

  back(): void {
    if (this.router.url === '/add_shop'){
      this.router.navigate(['shop']).then(() => console.log(Constants.NAVIGATED));
    } else{
      this.router.navigate(['workshop']).then(() => console.log(Constants.NAVIGATED));
    }
  }

}
