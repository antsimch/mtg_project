import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Draft } from 'src/app/models';
import { DeckService } from 'src/app/services/deck.service';
import { DraftService } from 'src/app/services/draft.service';

@Component({
  selector: 'app-draft-settings',
  templateUrl: './draft-settings.component.html',
  styleUrls: ['./draft-settings.component.css']
})
export class DraftSettingsComponent {

  @Input() draftHasStarted!: boolean
  setArr!: string[]
  draft!: Draft

  @Output() draftEvent = new EventEmitter<Draft>();

  settingsForm!: FormGroup
  error!: any

  constructor(
      private fb: FormBuilder,
      private deckSvc: DeckService,
      private draftSvc: DraftService) { }

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
          alert(error['message'])
        }
      )
  }

  createForm() {
    this.settingsForm = this.fb.group({
      set: this.fb.control<string>('', [ Validators.required ])
    })
  }

  startDraft() {
    this.draftSvc.createDraftSession(this.settingsForm)
      .then(
        result => {
          console.log(result)
          this.draft = result
          this.draftEvent.emit(this.draft)
        }
      )
      .catch(
        error => {
          console.log(error['message'])
        }
      )
  }
}
