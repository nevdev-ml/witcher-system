import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {User} from '../../models/model.user';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {AppComponent} from '../../app.component';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {
  user: User = new User();
  errorMessage: string;

  constructor(private authService: AuthService, private router: Router) { }



  ngOnInit() {
  }

  login(){
    this.authService.login(this.user)
      .subscribe(response => {
        console.log(response);
        localStorage.setItem(AppComponent.TOKEN, response.token);
        localStorage.setItem(AppComponent.ROLES, response.authorities);
        this.router.navigate(['/profile']).then(() => console.log('Success login'));
      }, error => {
        console.log(error);
        this.errorMessage = 'Неверный логин или пароль';
      });
  }
}
