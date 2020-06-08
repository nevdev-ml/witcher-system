import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {User} from '../../models/model.user';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Constants} from '../../utils/constants';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {
  user: User = new User();
  errorMessage: string;
  form: FormGroup;
  hide = true;

  constructor(private authService: AuthService, private router: Router) {
    this.form = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }



  ngOnInit() {
  }

  login(){
    this.authService.login(this.user)
      .subscribe(response => {
        localStorage.setItem(Constants.TOKEN, response.token);
        localStorage.setItem(Constants.ROLES, response.authorities);
        localStorage.setItem(Constants.ID, response.id);
        this.router.navigate(['/profile']).then(() => console.log('Success login'));
      }, error => {
        console.log(error);
        this.errorMessage = 'Неверный логин или пароль';
      });
  }
}
