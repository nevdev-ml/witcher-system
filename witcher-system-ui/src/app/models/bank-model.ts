import {DepositModel} from './deposit-model';

export class BankModel {
  id: number;
  kingRepository: boolean;
  deposits: DepositModel[];
}
