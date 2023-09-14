import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Card, Draft } from 'src/app/models';

@Component({
  selector: 'app-rochester',
  templateUrl: './rochester.component.html',
  styleUrls: ['./rochester.component.css']
})
export class RochesterComponent {

  @Input() draft!: Draft
  @Output() draftedCardsEvent = new EventEmitter<Card>()
  
  constructor() {}

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

  draftCard(card: Card) {
    this.draftedCardsEvent.emit(card)
  }
}
