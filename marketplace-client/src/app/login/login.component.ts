import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginService} from './login.service';
import {HttpResponse} from '@angular/common/http';

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;

  constructor(formBuilder: FormBuilder,
              private router: Router,
              private loginService: LoginService) {
    this.loginForm = formBuilder.group({
      'username': new FormControl('', Validators.required),
      'password': new FormControl('', Validators.required)
    });
  }

  login() {
    if (!this.loginForm.valid) {
      return;
    }

    this.loginService.login(this.loginForm.value.username, this.loginForm.value.password)
      .toPromise()
      .then((response: HttpResponse<any>) => {
        localStorage.setItem('token', response.headers.get('Authorization'));
        this.router.navigate(['/tasks']);
      });
  }

}
