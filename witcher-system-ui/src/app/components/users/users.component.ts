import {Component, OnInit} from '@angular/core';
import {Constants} from '../../utils/constants';
import {faPlusCircle} from '@fortawesome/free-solid-svg-icons';
import {Router} from '@angular/router';
import {AccountService} from '../../services/account-service';
import {AuthService} from '../../services/auth-service';
import { UserView } from 'src/app/models/user-view';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: UserView[];
  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  isDataAvailable = false;
  faAdd = faPlusCircle;


  constructor(private accountService: AccountService, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    if (localStorage.getItem(Constants.TOKEN) == null) {
      this.router.navigate(['/login']).then(() => console.log(Constants.NOT_AUTH));
    } else{
      this.accountService.getUsers({Authorization : localStorage.getItem(Constants.TOKEN)}).subscribe(
        data => {
          this.init(data.map(r => new UserView(r.id, r.username, r.firstName, r.lastName, r.email, r.role, r.bank, r.enabled)));
        },
        error => {
          console.log(error);
        });
    }
  }


  init(data) {
    data.forEach((item) => {
      this.authService.mapUser(item);
    });
    this.users = data;
    this.isDataAvailable = true;
  }

  add(): void {
    this.router.navigate(['add_user']).then(() => console.log(Constants.NAVIGATED));
  }
}
