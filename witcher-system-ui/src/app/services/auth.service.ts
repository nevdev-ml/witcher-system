import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpRequest, HttpResponse} from '@angular/common/http';
import {User} from '../models/model.user';
import {AppComponent} from '../app.component';
import {map} from 'rxjs/operators';
@Injectable()
export class AuthService {
  constructor(public http: HttpClient) { }

  public logIn(user: User){

    let headers = new HttpHeaders();
    headers = headers.append('Accept', 'application/json');
    // creating base64 encoded String from user name and password
    const base64Credential: string = btoa( user.username + ':' + user.password);
    headers = headers.append('Authorization', 'Basic ' + base64Credential);

    // const options = new HttpRequest();
    // options.headers = headers;
    const loginServiceUrl = AppComponent.API_URL + '/account/login';
    console.log(base64Credential);
    console.log(headers);
    return this.http.get(loginServiceUrl, {headers})
      .pipe(map((response: HttpResponse<any>) => {
      // login successful if there's a jwt token in the response
      const userPrincipal = response.body.principal; // the returned user object is a principal object
        console.log(userPrincipal);
      if (userPrincipal) {
        // store user details  in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify(userPrincipal));
      }
    }));
  }

  logOut() {
    const logoutServiceUrl = AppComponent.API_URL + 'logout';
    // remove user from local storage to log user out
    return this.http.post(logoutServiceUrl, {})
      .pipe(map((response: HttpResponse<any>) => {
        localStorage.removeItem('currentUser');
      }));

  }
}
