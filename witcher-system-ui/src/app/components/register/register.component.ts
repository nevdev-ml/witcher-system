import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {User} from '../../models/model-user';
import {AccountService} from '../../services/account-service';
import {Router} from '@angular/router';
import {ObjectBase} from '../../models/objectBase';
import {FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {Constants} from '../../utils/constants';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  form: FormGroup;
  hide = true;
  errorMessage: string;
  checkedValue: string;
  roles: ObjectBase[];
  title = 'Роль';
  formTitle: string;
  formButton: string;


  constructor(public accountService: AccountService, public router: Router) {
    const passwordErrorValidator: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
      const parent = control.parent as FormGroup;
      if (!parent) { return null; }
      const password = parent.get('password').value;
      const repeatPassword = parent.get('confirmPassword').value;
      return password === repeatPassword ? null : { passwordError: true };
    };
    this.form = new FormGroup({
        firstName: new FormControl('', Validators.required),
        lastName: new FormControl('', Validators.required),
        username: new FormControl('', Validators.required),
        email: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', [Validators.required, Validators.minLength(6)]),
        confirmPassword: new FormControl('', [Validators.required, passwordErrorValidator]),
    });
  }

  ngOnInit() {
    if (this.router.url === '/register'){
      this.formTitle = 'Регистрация';
      this.formButton = 'Создать аккаунт';
      this.roles = [
        {name: 'Ведьмак'},
        {name: 'Пользователь'},
        {name: 'Ремесленник'},
        {name: 'Торговец'},
      ];
    } else{
      this.formTitle = 'Добавление пользователя';
      this.formButton = 'Создать пользователя';
      this.roles = [
        {name: 'Ведьмак'},
        {name: 'Пользователь'},
        {name: 'Ремесленник'},
        {name: 'Торговец'},
        {name: 'Король'},
      ];
    }
  }

  onChangedRole(checkedValue: string) {
    this.checkedValue = checkedValue;
  }

  register() {
    if (this.form.invalid || this.isValidForm()) {
      return;
    }
    this.user.firstName = this.form.get('firstName').value;
    this.user.lastName = this.form.get('lastName').value;
    this.user.username = this.form.get('username').value;
    this.user.email = this.form.get('email').value;
    this.user.password = this.form.get('password').value;
    this.user.checkedRole = this.checkedValue;
    this.accountService.register(this.user).subscribe(() => {
      if (this.router.url === '/register'){
        this.router.navigate(['/login']).then(() => console.log(Constants.SUCCESS_REGISTER));
      } else{
        this.router.navigate(['/users']).then(() => console.log(Constants.SUCCESS_REGISTER));
      }
      }, err => {
        console.log(err);
        this.errorMessage = 'Данный логин уже существует.';
      }
    );
  }

  isValidForm(){
    return !(this.user.checkedRole === undefined);
  }
}
