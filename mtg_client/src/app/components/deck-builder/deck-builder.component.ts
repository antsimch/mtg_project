import { Component } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Card } from 'src/app/models';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-deck-builder',
  templateUrl: './deck-builder.component.html',
  styleUrls: ['./deck-builder.component.css']
})
export class DeckBuilderComponent {

  randomCardPoolGenerated = false
  cardsInPool: Card[] = []
  cardsInDeck: Card[] = []

  constructor(
    private deckSvc: DeckService,
    private router: Router) { }

  generateCardPool(form: FormGroup) {
    const set = form.get('set')?.value
    this.deckSvc.getBoosterPack(set)
      .then(
        result => {
          console.log(result)
          const oldArr = this.cardsInPool
          this.cardsInPool = oldArr.concat(result)
        }
      )
      .catch(
        error => {
          console.log(error['message'])
        }
      )
  }

  addCardToDeck(card: Card) {
    this.cardsInDeck.push(card)
  }

  removeIndexFromPool(index: number) {
    this.cardsInPool.splice(index, 1)
  }

  removeCardFromDeck(card: Card) {
    this.cardsInPool.push(card)
  }

  removeIndexFromDeck(index: number) {
    this.cardsInDeck.splice(index, 1)
  }

  saveDeck(form: FormGroup) {
    let cardList: string[] = []
    this.cardsInDeck.forEach(card => {
      cardList.push(card.cardId)
    })

    const deck = {
      deckId: '',
      deckName: form.get('deckName')?.value,
      userId: sessionStorage.getItem('userId') + '',
      cards: cardList
    }
    this.deckSvc.saveDeck(deck)
      .then(
        result => {
          alert(result['message'])
          this.router.navigate(['/home'])
        }
      )
      .catch(
        error => {
          console.log(error['message'])
        }
      )
  }
}
