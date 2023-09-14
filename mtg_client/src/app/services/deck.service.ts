import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import { Card, Deck, Draft, User } from '../models';

@Injectable({
  providedIn: 'root'
})
export class DeckService {

  constructor(private http: HttpClient) { }

  registerUser(form: FormGroup) {
    const payload: User = {
      userId: '',
      username: form.get('username')?.value,
      userEmail: form.get('email')?.value,
      userPassword: form.get('password')?.value
    }
    return firstValueFrom(this.http.post<any>('/api/register', payload))
  }

  saveDeck(deck: Deck) {
    const payload = {
      deckId: '',
      deckName: deck.deckName,
      userId: deck.userId,
      cards: deck.cards
    }
    return firstValueFrom(this.http.post<any>('/api/save', payload))
  }

  getDecksByUserId(userId: string) {
    return firstValueFrom(this.http.get<any>(`/api/decks/${userId}`))    
  }

  getDeckByDeckId(deckId: string) {
    return firstValueFrom(this.http.get<Deck>(`/api/deck/${deckId}`))
  }

  deleteDeckByDeckId(deckId: string) {
    return firstValueFrom(this.http.delete<any>(`/api/deck/${deckId}`))
  }

  getDraftsByUserId(userId: string) {
    return firstValueFrom(this.http.get<Draft[]>(`/api/drafts/${userId}`))
  }

  getDraftByDraftId(draftId: string) {
    return firstValueFrom(this.http.get<Draft>(`/api/draft/${draftId}`))
  }

  getCardPool(userId: string) {
    return firstValueFrom(this.http.get<Card[]>(`/api/pool/${userId}`))
  }

  saveCardPool(cards: Card[], userId: string) {
    return firstValueFrom(
      this.http.post<any>(`/api/pool/${userId}`, JSON.stringify(cards)))
  }

  getAllSets() {
    return firstValueFrom(this.http.get<string[]>('/api/sets'))
  }

  getBoosterPack(set: string) {
    return firstValueFrom(this.http.get<Card[]>(`/api/pack/${set}`))
  }

  getCard(cardId: string) {
    return firstValueFrom(this.http.get<Card>(`/api/card/${cardId}`))
  }
}
