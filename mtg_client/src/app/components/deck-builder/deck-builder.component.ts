import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Card } from 'src/app/models';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-deck-builder',
  templateUrl: './deck-builder.component.html',
  styleUrls: ['./deck-builder.component.css']
})
export class DeckBuilderComponent implements OnInit {

  cardsInPool!: Card[]
  cardsInDeck!: Card[]

  constructor(
    private deckSvc: DeckService,
    private activatedRoute: ActivatedRoute) { }

  addCardToDeck(card: Card) {
    this.cardsInDeck.push(card)
  }

  removeIndexFromPool(index: number) {
    this.cardsInPool.splice(index)
  }

  removeCardFromDeck(card: Card) {
    this.cardsInPool.push(card)
  }

  removeIndexFromDeck(index: number) {
    this.cardsInDeck.splice(index)
  }

  ngOnInit(): void {
    const userId = this.activatedRoute.snapshot.params['userId']
    this.deckSvc.getCardPool(userId)
      .then(
        result => {
          this.cardsInPool = result
        }
      )
      .catch(
        error => {

        }
      )
  }
}
