import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Card } from 'src/app/models';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-rochester',
  templateUrl: './rochester.component.html',
  styleUrls: ['./rochester.component.css']
})
export class RochesterComponent {

  draftedCards!: Card[]
  
  constructor(
    private deckSvc: DeckService,
    private activatedRoute: ActivatedRoute) {}

  draftCard(card: Card) {
    this.draftedCards.push(card)
  }

  saveCardPool() {
    const userId = this.activatedRoute.snapshot.params['userId']
    this.deckSvc.saveCardPool(this.draftedCards, userId)
      .then(

      )
      .catch(

      )
  }
}
