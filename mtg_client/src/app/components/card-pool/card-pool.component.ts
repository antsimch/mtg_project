import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Card } from 'src/app/models';

@Component({
  selector: 'app-card-pool',
  templateUrl: './card-pool.component.html',
  styleUrls: ['./card-pool.component.css']
})
export class CardPoolComponent {

  @Input() cardsInPool!: Card[]
  @Output() cardToAddToDeck = new EventEmitter<Card>()
  @Output() indexToRemoveFromPool = new EventEmitter<number>()

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

  addCardToDeck(i: number) {
    const card = this.cardsInPool.at(i)
    this.cardToAddToDeck.emit(card)
    this.indexToRemoveFromPool.emit(i)
  }
}
