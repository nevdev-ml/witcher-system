import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  // paths
  static API_URL = 'http://localhost:8080';
  static PATH_AUTH = AppComponent.API_URL + '/account/login';
  static PATH_LOGOUT = AppComponent.API_URL + '/account/logout';
  static PATH_REGISTER = AppComponent.API_URL + '/account/register';
  static PATH_PROFILE = AppComponent.API_URL + '/account/profile';
  // constants
  static TOKEN = 'token';
  static JWT_HEADER = 'Authorization';
  static ROLES = 'authorities';
  title = 'witcher-system-ui';
}
