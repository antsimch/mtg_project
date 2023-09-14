import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm!: FormGroup 

  constructor(
      private fb: FormBuilder,
      private deckSvc: DeckService,
      private router: Router) { }

  registerUser() {
    this.deckSvc.registerUser(this.registerForm)
      .then(
        result => {
          this.router.navigate([''])
        }
      )
      .catch(
        error => {
          alert(error['message'])
        }
      )
  }

  ngOnInit(): void {
    this.createForm()
  }

  createForm() {
    this.registerForm = this.fb.group({
      username: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [Validators.required]),
      password: this.fb.control<string>('', [Validators.required])
    })
  }
}
