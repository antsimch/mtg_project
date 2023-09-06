import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DeckService {

  constructor(private http: HttpClient) { }

  login(form: FormGroup) {
    const payload = {
      username: form.get('username')?.value,
      password: form.get('password')?.value
    }
    return firstValueFrom(this.http.post<any>('/api/login', payload))
  }
}
