import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {Constants} from '../../utils/constants';
import {FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {ObjectBase} from '../../models/objectBase';
import {User} from '../../models/model-user';
import { AuthService } from 'src/app/services/auth-service';
import { AccountService } from 'src/app/services/account-service';

@Component({
  selector: 'app-change-info',
  templateUrl: './change-info.component.html',
  styleUrls: ['./change-info.component.css']
})
export class ChangeInfoComponent implements OnInit {
  title = 'Роль';
  roles: ObjectBase[];
  form: FormGroup;
  errorMessage: string;
  checkedValue: string;
  user: User = new User();
  data: User;
  role = localStorage.getItem(Constants.ROLES);
  id = Number(localStorage.getItem(Constants.ID));
  isDataAvailable = false;
  isInfo = false;
  hide = true;

  constructor(private accountService: AccountService, private authService: AuthService, private router: Router) {}


  ngOnInit(): void {
    if (this.router.url === '/change_info' || this.router.url === '/change_user'){
      this.isInfo = true;
      this.form = new FormGroup({
        firstName: new FormControl('', Validators.required),
        lastName: new FormControl('', Validators.required),
        email: new FormControl('', [Validators.required, Validators.email])
      });
      this.data = history.state.data;
      if (this.data === undefined){
        this.user = JSON.parse(localStorage.getItem('userChange'));
      } else {
        this.user.id = this.data.id;
        this.user.firstName = this.data.firstName;
        this.user.lastName = this.data.lastName;
        this.user.email = this.data.email;
        this.user.checkedRole = history.state.data.role;
        localStorage.setItem('userChange', JSON.stringify(this.user));
      }
      this.form.setValue({
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        email: this.user.email
      });
      if (this.role === 'KING'){
        this.roles = [
          {name: 'Ведьмак'},
          {name: 'Пользователь'},
          {name: 'Ремесленник'},
          {name: 'Торговец'},
          {name: 'Король'},
        ];
      } else {
        this.roles = [
          {name: 'Ведьмак'},
          {name: 'Пользователь'},
          {name: 'Ремесленник'},
          {name: 'Торговец'}
        ];
      }
    }
    else{
      const passwordErrorValidator: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
        const parent = control.parent as FormGroup;
        if (!parent) { return null; }
        const password = parent.get('password').value;
        const repeatPassword = parent.get('confirmPassword').value;
        return password === repeatPassword ? null : { passwordError: true };
      };
      this.form = new FormGroup({
        password: new FormControl('', [Validators.required, Validators.minLength(6)]),
        confirmPassword: new FormControl('', [Validators.required, passwordErrorValidator])
      });
    }
    this.isDataAvailable = true;
  }

  onChangedRole(checkedValue: string) {
    this.checkedValue = checkedValue;
  }

  changeUserInfo(): void {
    if (this.form.invalid || !this.isValidForm()) {
      return;
    }
    this.user.firstName = this.form.get('firstName').value;
    this.user.lastName = this.form.get('lastName').value;
    this.user.email = this.form.get('email').value;
    this.user.checkedRole = this.checkedValue;
    this.accountService.edit(this.user).subscribe(response => {
        if (this.router.url === '/change_info'){
          localStorage.removeItem('userChange');
          localStorage.removeItem(Constants.ROLES);
          localStorage.setItem(Constants.ROLES, response.toString());
          this.router.navigate(['/profile']).then(() => console.log(Constants.SUCCESS_EDIT));
        } else{
          this.router.navigate(['/users']).then(() => console.log(Constants.SUCCESS_EDIT));
        }
      }, err => {
        console.log(err);
        this.errorMessage = 'Данный логин уже существует.';
      }
    );
  }

  changeUserPassword(): void {
    if (this.form.invalid) {
      return;
    }
    this.user.password = this.form.get('password').value;
    this.accountService.editPassword(this.user.password, this.id).subscribe(() => {
      this.authService.logout().subscribe(() => {
        localStorage.removeItem(Constants.TOKEN);
        localStorage.removeItem(Constants.ROLES);
        localStorage.removeItem(Constants.ID);
        this.router.navigate(['/login']).then(() => console.log(Constants.SUCCESS_EDIT));
      });
      }, err => {
        console.log(err);
        this.errorMessage = 'Данного пользователя не существует.';
      }
    );
  }

  isValidForm(){
    return !(this.checkedValue === undefined);
  }

  back(): void {
    this.router.navigate(['profile']).then(() => console.log(Constants.NAVIGATED));
  }
}
