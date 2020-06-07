import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {User} from '../models/model.user';
import {AppComponent} from '../app.component';
import {Observable} from 'rxjs';
import {Token} from '../models/token';
@Injectable()
export class AuthService implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem(AppComponent.JWT_HEADER);
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
    return this.http.post<Token>(AppComponent.PATH_AUTH, {
      username: user.username,
      password: user.password
    });
  }

  logout() {
    return this.http.get(AppComponent.PATH_LOGOUT, {});
  }

  profile(p: { Authorization: string }){
    return this.http.get<User>(AppComponent.PATH_PROFILE, {headers: p});
  }
}

