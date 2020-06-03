import { Injectable } from '@angular/core';
import {User} from '../models/model.user';
import {HttpClient} from '@angular/common/http';
import {AppComponent} from '../app.component';

@Injectable()
export class AccountService {
  constructor(public http: HttpClient) { }

  register(user: User){
    return this.http.post(AppComponent.PATH_REGISTER, user);
  }
}
