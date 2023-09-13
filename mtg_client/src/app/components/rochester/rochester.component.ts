import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Card, Draft } from 'src/app/models';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-rochester',
  templateUrl: './rochester.component.html',
  styleUrls: ['./rochester.component.css']
})
export class RochesterComponent {

  @Input() draft!: Draft
  @Output() draftedCardsEvent = new EventEmitter<Card>()
  
  constructor(
    private deckSvc: DeckService,
    private activatedRoute: ActivatedRoute) {}

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
