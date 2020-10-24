import { Injectable } from '@angular/core';
import {User} from '../models/model-user';
import {HttpClient} from '@angular/common/http';
import {Constants} from '../utils/constants';

@Injectable()
export class AccountService {
  constructor(public http: HttpClient) { }

  register(user: User){
    return this.http.post(Constants.PATH_REGISTER, user);
  }
}
