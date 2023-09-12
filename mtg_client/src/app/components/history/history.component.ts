import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Draft } from 'src/app/models';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  drafts!: Draft[]

  constructor(
    private deckSvc: DeckService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const userId = this.activatedRoute.snapshot.params['userId']
    this.deckSvc.getDraftsByUserId(userId)
      .then(
        result => {
          
        }
      )
      .catch(

      )
  }
}
