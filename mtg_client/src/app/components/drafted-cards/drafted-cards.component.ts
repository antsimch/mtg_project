import { Component, Input } from '@angular/core';
import { Card } from 'src/app/models';

@Component({
  selector: 'app-drafted-cards',
  templateUrl: './drafted-cards.component.html',
  styleUrls: ['./drafted-cards.component.css']
})
export class DraftedCardsComponent {

  @Input() cardsDrafted!: Card[]
  
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
}
