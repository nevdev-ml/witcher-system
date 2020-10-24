import {Component} from '@angular/core';
import {AuthService} from './services/auth-service';
import {Router} from '@angular/router';
import {Constants} from './utils/constants';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'witcher-system-ui';

  constructor(public authService: AuthService, public router: Router) { }

  logout() {
    this.authService.logout()
      .subscribe(
        () => {
          localStorage.removeItem(Constants.TOKEN);
          localStorage.removeItem(Constants.ROLES);
          localStorage.removeItem(Constants.ID);
          this.router.navigate(['/login']).then(() => console.log('Success logout'));
        },
        error => {
          console.log(error);
        });
  }

  isAuth() {
    return localStorage.getItem(Constants.ROLES);
  }
}
