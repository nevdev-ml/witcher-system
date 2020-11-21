import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Constants} from '../utils/constants';
import {DealViewModel} from '../models/deal/deal-view-model';
import {CurrencyEnum} from '../enums/currency-enum';
import { DealsViewModel } from '../models/deal/deals-view-model';
import { DealModifyModel } from '../models/deal/deal-modify-model';
import { DealModifyUtil } from '../models/deal/deal-modify-util';

@Injectable({
  providedIn: 'root'
})
export class DealService {

  constructor(public http: HttpClient) { }

  deal(token: { Authorization: string }, deal: string){
    return this.http.get<DealViewModel>(Constants.PATH_DEAL + deal, {headers: token});
  }

  deals(token: { Authorization: string }){
    return this.http.post<DealViewModel[]>(Constants.PATH_DEALS, null, {headers: token});
  }

  shop(token: { Authorization: string }){
    return this.http.post<DealViewModel[]>(Constants.PATH_SHOP, null, {headers: token});
  }

  workshop(token: { Authorization: string }){
    return this.http.post<DealViewModel[]>(Constants.PATH_WORKSHOP, null, {headers: token});
  }

  acceptedDeals(token: { Authorization: string }){
    return this.http.post<DealsViewModel>(Constants.PATH_ACCEPTED_DEALS, null, {headers: token});
  }

  customerDeals(token: { Authorization: string }){
    return this.http.post<DealsViewModel>(Constants.PATH_CUSTOMER_DEALS, null, {headers: token});
  }

  accept(token: { Authorization: string }, deal: string) {
    return this.http.post<DealViewModel>(Constants.PATH_ACCEPT_DEAL + deal, null, {headers: token});
  }

  cancel(token: { Authorization: string }, deal: string) {
    return this.http.post<DealViewModel>(Constants.PATH_CANCEL_DEAL + deal, null, {headers: token});
  }

  complete(token: { Authorization: string }, deal: string) {
    return this.http.post<DealViewModel>(Constants.PATH_COMPLETE_DEAL + deal, null, {headers: token});
  }

  reward(token: { Authorization: string }, witcherId: number, dealId: number){
    return this.http.post<DealViewModel>(Constants.PATH_REWARD_DEAL + dealId, witcherId, {headers: token});
  }

  refuse(token: { Authorization: string }, witcherId: number, dealId: number){
    return this.http.post<DealViewModel>(Constants.PATH_REFUSE_DEAL + dealId, witcherId, {headers: token});
  }

  edit(deal: DealModifyModel, dealId: number, token: { Authorization: string }) {
    return this.http.post<DealViewModel>(Constants.PATH_EDIT_DEAL + dealId, deal, {headers: token});
  }

  delete(dealId: number, token: { Authorization: string }) {
    return this.http.post<DealViewModel>(Constants.PATH_DELETE_DEAL + dealId, null, {headers: token});
  }

  add(deal: DealModifyModel, token: { Authorization: string }) {
    return this.http.post<DealViewModel>(Constants.PATH_ADD_DEAL, deal, {headers: token});
  }

  mapDeal(data): DealViewModel {
    data.reward.type = Constants.CurrencyMap.get(Number(CurrencyEnum[data.reward.type]));
    return data;
  }

  mapAnswers(currency: {name: string}[]): DealModifyUtil{
    for (const item of Object.keys(CurrencyEnum).filter(key => !isNaN(Number(CurrencyEnum[key])))){
      currency = currency.concat({name: Constants.CurrencyMap.get(Number(CurrencyEnum[item]))});
    }
    return new DealModifyUtil(currency);
  }
}
