import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-forgot',
  templateUrl: './forgot.component.html',
  styleUrls: ['./forgot.component.css']
})
export class ForgotComponent implements OnInit {
  // email: string;
  errorMessage: string;
  form: FormGroup;

  constructor(private authService: AuthService, private router: Router) {
    this.form = new FormGroup({
      email: new FormControl('', Validators.required),
    });
  }

  ngOnInit(): void {
  }

  forgot(){
    this.authService.forgot(this.form.get('email').value)
      .subscribe(response => {
        if (response === undefined) {
          this.errorMessage = 'Неверная электронная почта';
        }
        else{
          this.router.navigate(['']).then(() => console.log('Success sent link for password reset'));
        }
      }, error => {
        console.log(error);
        this.errorMessage = 'Неверная электронная почта';
      });
  }
}
