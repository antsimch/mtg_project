import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Card } from 'src/app/models';

@Component({
  selector: 'app-deck',
  templateUrl: './deck.component.html',
  styleUrls: ['./deck.component.css'],
})
export class DeckComponent {

  @Input() cardsInDeck: Card[] = []
  @Output() cardToRemoveFromDeck = new EventEmitter<Card>()
  @Output() indexToRemoveFromDeck = new EventEmitter<number>()

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

  removeCardFromDeck(i: number) {
    const card = this.cardsInDeck.at(i)
    this.cardToRemoveFromDeck.emit(card)
    this.indexToRemoveFromDeck.emit(i)
  }
}
