import {BankModel} from './bank-model';

export class UserView {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  bank: BankModel;
  role: string;
  enabled: boolean;

  constructor(id: number, username: string, firstName: string, lastName: string, email: string, role: string, bank: BankModel,
              enabled: boolean){
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.role = role;
    this.bank = bank;
    this.enabled = enabled;
  }
}
