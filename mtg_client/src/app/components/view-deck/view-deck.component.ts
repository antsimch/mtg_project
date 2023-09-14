import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Card } from 'src/app/models';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-view-deck',
  templateUrl: './view-deck.component.html',
  styleUrls: ['./view-deck.component.css']
})
export class ViewDeckComponent implements OnInit {

  cardsInDeck: Card[] = []

  constructor(
    private activatedRoute: ActivatedRoute,
    private deckSvc: DeckService,
    private router: Router) {}

  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  ngOnInit(): void {
    const deckId = this.activatedRoute.snapshot.params['deckId']
    this.deckSvc.getDeckByDeckId(deckId)
      .then(
        result => {
          result.cards.forEach(
            cardId => {
              this.deckSvc.getCard(cardId)
              .then(
                card => {
                  this.cardsInDeck.push(card)
                }
              )
              .catch(
                error => {
                  console.log(error)
                }
              ) 
            }
          )
        }
      )
      .catch(
        error => {
          console.log(error['message'])
        }
      )
  }
}
