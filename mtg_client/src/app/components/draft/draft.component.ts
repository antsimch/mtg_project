import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Card, CardPick, Draft } from 'src/app/models';
import { DeckService } from 'src/app/services/deck.service';
import { DraftService } from 'src/app/services/draft.service';

@Component({
  selector: 'app-draft',
  templateUrl: './draft.component.html',
  styleUrls: ['./draft.component.css']
})
export class DraftComponent implements OnInit {

  draft!: Draft
  cardsDrafted: Card[] = []
  draftHasStarted = false
  myTurn = true
  
  constructor(
    private draftSvc: DraftService,
    private deckSvc: DeckService,
    private router: Router) { }

  ngOnInit(): void {

  }

  startDraft(draft: Draft) {
    this.draft = draft
    this.draftHasStarted = true
  }

  draftCard(card: Card) {

    if (this.myTurn) {
      this.cardsDrafted.push(card)
      console.log('myDraftedCards: ')
      console.log(this.cardsDrafted)
      const index = this.draft.cards.indexOf(card)
      this.draft.cards.splice(index, 1)
      console.log('cardsRemaining: ')
      console.log(this.draft.cards)
      const cardPick: CardPick = {
        draftId: this.draft.draftId,
        index: index
      }
      this.draftSvc.pickCard(cardPick)
        .then(
          result => {
            this.draft = result
            this.myTurn = true
          }
        )
        .catch(
  
        )
    }

    this.myTurn = false
  }

  saveCardPool() {
    const draftId = this.draft.draftId
    this.deckSvc.saveCardPool(this.cardsDrafted, draftId)
      .then(
        result => {
          console.log(result['message'])
        }
      )
      .catch(
        
      )
  }
}
