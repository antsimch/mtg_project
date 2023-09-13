import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Card } from 'src/app/models';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-deck-builder',
  templateUrl: './deck-builder.component.html',
  styleUrls: ['./deck-builder.component.css']
})
export class DeckBuilderComponent implements OnInit {

  randomCardPoolGenerated = false
  cardsInPool: Card[] = []
  cardsInDeck: Card[] = []

  constructor(
    private deckSvc: DeckService,
    private activatedRoute: ActivatedRoute) { }

  generateCardPool(form: FormGroup) {
    const numberOfBoosterPacks = form.get('numberOfBoosterPacks')?.value
    const set = form.get('set')?.value
    for (let i = 0; i < numberOfBoosterPacks; i++) {
      this.deckSvc.getBoosterPack(set)
        .then(
          result => {
            console.log(result)
            const oldArr = this.cardsInPool
            this.cardsInPool = oldArr.concat(result)
            if (this.cardsInPool.length > 0)
              this.randomCardPoolGenerated = true
          }
        )
        .catch()
    }
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

  saveDeck() {
    let cardList: string[] = []
    this.cardsInDeck.forEach(card => {
      cardList.push(card.cardId)
    })
    const deck = {
      deckId: '',
      deckName: "sample deck",
      userId: 'sample id',
      draftId: 'sample id',
      cards: cardList
    }
    this.deckSvc.saveDeck(deck)
      .then()
      .catch()
  }

  ngOnInit(): void {
    // const userId = this.activatedRoute.snapshot.params['userId']
    // this.deckSvc.getCardPool(userId)
    //   .then(
    //     result => {
    //       this.cardsInPool = result
    //     }
    //   )
    //   .catch(
    //     error => {

    //     }
    //   )
  }
}
