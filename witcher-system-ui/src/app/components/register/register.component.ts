import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from '../../models/model.user';
import {AccountService} from '../../services/account.service';
import {Router} from '@angular/router';
import {ObjectBase} from '../../models/objectBase';
import {AppComponent} from '../../app.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  errorMessage: string;
  checkedValue: string;
  roles: ObjectBase[] = [
    {name: 'Ведьмак'},
    {name: 'Пользователь'},
    {name: 'Ремесленник'},
    {name: 'Торговец'},
  ];
  title = 'Роль';

  constructor(public accountService: AccountService, public router: Router) { }

  ngOnInit() { }

  onChangedRole(checkedValue: string) {
    this.checkedValue = checkedValue;
  }

  register() {
    console.log(this.user);
    console.log(this.checkedValue);
    this.user.checkedRole = this.checkedValue;
    this.accountService.register(this.user).subscribe(data => {
        console.log(data);
        this.router.navigate(['/login']).then(() => console.log(AppComponent.SUCCESS_REGISTER));
      }, err => {
        console.log(err);
        this.errorMessage = 'Данный логин уже существует.';
      }
    );
  }
}
