import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {User} from '../models/model-user';
import {Observable} from 'rxjs';
import {Token} from '../models/token';
import {Constants} from '../utils/constants';
import {UserView} from '../models/user-view';
import {CurrencyEnum} from '../enums/currency-enum';

@Injectable()
export class AuthService implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem(Constants.JWT_HEADER);
    if (token) {
      const requestClone = req.clone({
        headers: new HttpHeaders({
          Authorization : token
        })
      });
      return next.handle(requestClone);
    }
    return next.handle(req);
  }

  constructor(public http: HttpClient) { }

  public login(user: User){
    return this.http.post<Token>(Constants.PATH_AUTH, {
      username: user.username,
      password: user.password
    });
  }

  logout() {
    return this.http.get(Constants.PATH_LOGOUT, {});
  }

  profile(p: { Authorization: string }){
    return this.http.get<UserView>(Constants.PATH_PROFILE, {headers: p});
  }

  forgot(email: string) {
    return this.http.get(Constants.PATH_FORGOT_PASSWORD + email);
  }

  getResetPage(token: string) {
    return this.http.get(Constants.PATH_RESET_PASSWORD + token);
  }

  reset(password: string, token: string) {
    return this.http.post<User>(Constants.PATH_RESET_PASSWORD + token, password);
  }

  mapUser(data): UserView {
    data.bank.deposits.forEach((item, index) => {
      data.bank.deposits[index].type = Constants.CurrencyMap.get(Number(CurrencyEnum[item.type]));
    });
    return data;
  }
}

