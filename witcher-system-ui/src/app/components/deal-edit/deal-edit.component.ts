import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Constants} from '../../utils/constants';
import {ActivatedRoute, Router} from '@angular/router';
import {DealModifyModel} from '../../models/deal/deal-modify-model';
import {DealService} from '../../services/deal.service';
import {DealViewModel} from '../../models/deal/deal-view-model';

@Component({
  selector: 'app-deal-edit',
  templateUrl: './deal-edit.component.html',
  styleUrls: ['./deal-edit.component.css']
})
export class DealEditComponent implements OnInit {
  errorMessage: string;
  form: FormGroup;
  currency: {name: string}[] = [];
  type: {name: string}[] = [];
  titleType = 'Тип сделки';
  titleCurrency = 'Валюта';
  dealId: number;

  deal: DealViewModel;
  modifyDeal: DealModifyModel = new DealModifyModel();
  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  isDataAvailable = false;


  constructor(private dealService: DealService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.dealId = Number(params.get('deal'));
    });
    this.getDeal();
    const mappedDeal = this.dealService.mapAnswers(this.currency);
    this.currency = mappedDeal.currency;
    if (this.role === 'VENDOR'){
      this.type = [{name: 'Продажа'}, {name: 'Покупка'}];
    } else{
      if (this.role === 'BLACKSMITH'){
        this.type = [{name: 'Ремонт'}, {name: 'Выкуп'}];
      }
      else {
        if (this.router.url === '/edit_shop/' + this.dealId){
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

  getDeal() {
    this.route.paramMap.subscribe(params => {
      if (localStorage.getItem(Constants.TOKEN) == null) {
        this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
      } else{
        this.dealService.deal({Authorization : localStorage.getItem(Constants.TOKEN)}, params.get('deal')).subscribe(
          data => {
            this.deal = this.dealService.mapDeal(data);
            this.isDataAvailable = true;
            this.mapDealToModify();
          },
          error => {
            console.log(error);
          });
      }
    });
  }

  mapDealToModify(){
    this.modifyDeal.title = this.deal.title;
    this.modifyDeal.description = this.deal.description;
    this.modifyDeal.checkedRewardValue = this.deal.reward.reward.toString();
    this.form.setValue({
      title: this.modifyDeal.title,
      description: this.modifyDeal.description,
      checkedRewardValue: this.modifyDeal.checkedRewardValue
    });
  }

  isValidForm(){
    return !(this.modifyDeal.checkedCurrency === undefined);
  }

  edit(): void {
    if (this.form.invalid || !this.isValidForm()) {
      return;
    }
    this.modifyDeal.title = this.form.get('title').value;
    this.modifyDeal.description = this.form.get('description').value;
    this.modifyDeal.checkedRewardValue = this.form.get('checkedRewardValue').value;

    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.dealService.edit(this.modifyDeal, this.deal.id, {Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        () => {
          if (this.router.url === '/edit_shop/' + this.dealId){
            this.router.navigate(['shop']).then(() => console.log(Constants.NAVIGATED));
          } else{
            if (this.router.url === '/edit_workshop/' + this.dealId){
              this.router.navigate(['workshop']).then(() => console.log(Constants.NAVIGATED));
            } else {
              this.router.navigate(['profile']).then(() => console.log(Constants.NAVIGATED));
            }
          }
        },
        error => {
          console.log(error);
        });
    }
  }

  onChangedType(checkedValue: string) {
    this.modifyDeal.checkedType = checkedValue;
  }

  onChangedCurrency(checkedValue: string) {
    this.modifyDeal.checkedCurrency = checkedValue;
  }

  back(): void {
    console.log(this.router.url === '/edit_workshop/' + this.dealId);
    if (this.router.url === '/edit_shop/' + this.dealId){
      this.router.navigate(['shop']).then(() => console.log(Constants.NAVIGATED));
    } else{
      if (this.router.url === '/edit_workshop/' + this.dealId){
        this.router.navigate(['workshop']).then(() => console.log(Constants.NAVIGATED));
      } else{
        this.router.navigate(['profile']).then(() => console.log(Constants.NAVIGATED));
      }
    }
  }
}
