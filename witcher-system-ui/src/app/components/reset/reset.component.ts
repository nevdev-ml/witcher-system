import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ResetComponent implements OnInit {
  errorLink: string;
  errorMessage: string;
  changeMessage: string;
  isLoaded = false;
  hide = true;
  form: FormGroup;

  constructor(private authService: AuthService, private router: Router, private route: ActivatedRoute) {
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
  ngOnInit(): void {
    this.getResetPage();
  }


  getResetPage(): void {
    this.route.paramMap.subscribe(params => {
      this.authService.getResetPage(params.get('token')).subscribe(
        response => {
          if (response !== null) {
            this.errorLink = 'Неверная ссылка для сброса пароля';
          }
          this.isLoaded = true;
        },
        error => {
          console.log(error);
          this.errorLink = 'Неверная ссылка для сброса пароля';
          this.isLoaded = true;
        });
    });
  }

  reset(): void {
    if (this.form.invalid) {
      return;
    }
    this.route.paramMap.subscribe(params => {
      this.authService.reset(this.form.get('password').value, params.get('token')).subscribe(
        response => {
          if (response.id !== null){
            this.changeMessage = 'Пароль был успешно изменен!';
          }
        },
        error => {
          console.log(error);
          this.errorMessage = 'Введите валидный пароль';
        });
    });
  }

}
