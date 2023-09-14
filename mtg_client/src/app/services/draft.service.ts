import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import { CardPick, Draft } from '../models';

@Injectable({
  providedIn: 'root'
})
export class DraftService {

  constructor(private http: HttpClient) {}

  createDraftSession(form: FormGroup) {
    const payload = {
      playerName: sessionStorage.getItem('username'),
      playerId: sessionStorage.getItem('userId')
    }

    const set = form.get('set')?.value

    return firstValueFrom(this.http.post<Draft>(`/drafting/create/${set}`, payload))
  }

  pickCard(cardPick: CardPick) {

    return firstValueFrom(this.http.post<Draft>('/drafting/pick', cardPick))
  }
}
