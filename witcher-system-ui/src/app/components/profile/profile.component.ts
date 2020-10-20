import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {UserView} from '../../models/user.view';
import {Constants} from '../../utils/constants';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit {
  currentUser: UserView = new UserView();
  role = localStorage.getItem(Constants.ROLES);
  isDataAvailable = false;

  constructor(public authService: AuthService, public router: Router) { }

  ngOnInit() {
    this.getUser();
  }

  getUser(){
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.authService.profile({Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
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


}
