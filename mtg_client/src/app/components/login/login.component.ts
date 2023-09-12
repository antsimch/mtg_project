import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup
  error!: any

  constructor(
      private fb: FormBuilder,
      private deckSvc: DeckService,
      private router: Router) { }

  ngOnInit(): void {
    this.createForm()
  }

  createForm() {
    this.loginForm = this.fb.group({
      username: this.fb.control<string>('', [ Validators.required ]),
      password: this.fb.control<string>('', [ Validators.required ])
    })
  }

  login() {
    this.deckSvc.login(this.loginForm)
      .then(
        result => {
          const userId = result['userId']
          this.router.navigate(['/', userId])
        }
      )
      .catch(
        error => {
          this.error = error
        }
      )
  }
}
