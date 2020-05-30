import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from '../../models/model.user';
import {AccountService} from '../../services/account.service';
import {Router} from '@angular/router';
import {FormControl, Validators} from '@angular/forms';
import {Role} from '../../models/role';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  errorMessage: string;
  checkedRole = new FormControl('', Validators.required);
  roles: Role[] = [
    {name: 'Король'},
    {name: 'Пользователь'},
    {name: 'Ремесленник'},
    {name: 'Торговец'},
  ];

  constructor(public accountService: AccountService, public router: Router) {
  }

  ngOnInit() {
  }

  register() {
    this.user.checkedRole = this.checkedRole.value;
    this.accountService.createAccount(this.user).subscribe(data => {
        this.router.navigate(['/login']);
      }, err => {
        console.log(err);
        this.errorMessage = 'Данный логин уже существует.';
      }
    );
  }
}
