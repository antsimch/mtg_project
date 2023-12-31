import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-deck-builder-settings',
  templateUrl: './deck-builder-settings.component.html',
  styleUrls: ['./deck-builder-settings.component.css']
})
export class DeckBuilderSettingsComponent {

  @Input() randomCardPoolGenerated!: boolean
  setArr!: string[]
  
  @Output() formEvent = new EventEmitter<FormGroup>()
  @Output() saveEvent = new EventEmitter<FormGroup>()

  settingsForm!: FormGroup
  error!: any

  constructor(
      private fb: FormBuilder,
      private deckSvc: DeckService) { }

  ngOnInit(): void {
    this.createForm()
    this.deckSvc.getAllSets()
      .then(
        result => {
          console.log(result)
          this.setArr = result
        }
      )
      .catch(
        error => {
          console.log(error['message'])
        }
      )
  }

  createForm() {
    this.settingsForm = this.fb.group({
      deckName: this.fb.control<string>('', [ Validators.required ]),
      set: this.fb.control<string>('', [ Validators.required ])
    })
  }

  addCards() {
    this.formEvent.emit(this.settingsForm)
  }

  saveDeck() {
    this.saveEvent.emit(this.settingsForm)
  }
}
