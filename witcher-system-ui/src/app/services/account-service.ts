import { Injectable } from '@angular/core';
import {User} from '../models/model-user';
import {HttpClient} from '@angular/common/http';
import {Constants} from '../utils/constants';
import { PasswordModifyModel } from '../models/password-modify-model';
import { Observable } from 'rxjs/internal/Observable';
import { UserView } from '../models/user-view';

@Injectable()
export class AccountService {
  constructor(public http: HttpClient) { }

  register(user: User){
    console.log(user);
    return this.http.post(Constants.PATH_REGISTER, user);
  }

  edit(user: User){
    return this.http.post(Constants.PATH_EDIT_USER, user);
  }

  editPassword(password: string, id: number){
    return this.http.post(Constants.PATH_EDIT_USER_PASSWORD, new PasswordModifyModel(id, password));
  }

  getUsers(token: { Authorization: string }): Observable<UserView[]>{
    return this.http.post<UserView[]>(Constants.PATH_GET_USERS, null, {headers: token});
  }

  delete(token: { Authorization: string }, user: User){
    return this.http.post(Constants.PATH_DELETE_USER, user.id, {headers: token});
  }

  enable(token: { Authorization: string }, user: User){
    return this.http.post(Constants.PATH_ENABLE_USER, user.id, {headers: token});
  }

  disable(token: { Authorization: string }, user: User){
    return this.http.post(Constants.PATH_DISABLE_USER, user.id, {headers: token});
  }
}
