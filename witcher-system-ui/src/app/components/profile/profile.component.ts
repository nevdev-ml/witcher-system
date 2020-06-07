import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {AppComponent} from '../../app.component';
import {UserView} from '../../models/user.view';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit {
  currentUser: UserView;
  isDataAvailable = false;

  constructor(public authService: AuthService, public router: Router) { }

  ngOnInit() {
    this.getUser();
  }

  getUser(){
    if (localStorage.getItem(AppComponent.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(AppComponent.NOT_AUTH));
    } else{
      this.authService.profile({Authorization : localStorage.getItem(AppComponent.TOKEN)}).subscribe(
        data => {
          console.log(data);
          this.currentUser = data;
          this.isDataAvailable = true;
        },
        error => {
          console.log(error);
        });
    }
  }

  logout() {
    this.authService.logout()
      .subscribe(
        () => {
          localStorage.removeItem(AppComponent.TOKEN);
          localStorage.removeItem(AppComponent.ROLES);
          this.router.navigate(['/login']).then(() => console.log('Success logout'));
        },
        error => {
          console.log(error);
        });
  }
}
