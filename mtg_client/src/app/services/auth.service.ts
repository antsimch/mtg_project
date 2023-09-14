import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, firstValueFrom } from 'rxjs';
import { LoginDetails } from '../models';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private router: Router) { }

  authenticate(login: LoginDetails) {
    return firstValueFrom(this.http
      .post<any>("/api/login", login)
      .pipe(
        map((userData: any) => {
          console.log("in authenticate map")
          sessionStorage.setItem('userId', userData.id)
          sessionStorage.setItem('username', login.username)
          let tokenStr = 'Bearer ' + userData.token
          sessionStorage.setItem('token', tokenStr)
          return userData;
        })
      ))
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username')
    console.log(!(user === null))
    return !(user === null)
  }

  logout() {
    sessionStorage.removeItem('username')
    sessionStorage.removeItem('userId')
    sessionStorage.removeItem('token')
  }
}
