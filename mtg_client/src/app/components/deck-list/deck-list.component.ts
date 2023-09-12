import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-deck-list',
  templateUrl: './deck-list.component.html',
  styleUrls: ['./deck-list.component.css']
})
export class DeckListComponent implements OnInit {

  deckInfoList: {
    id: string,
    name: string,
    colors: string[],
    creationDate: number
  }[] = []

  constructor(
      private deckSvc: DeckService,
      private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const userId = this.activatedRoute.snapshot.params['userId']
    this.deckSvc.getDecksByUserId(userId)
      .then(
        result => {
          this.deckInfoList.forEach(
            deckInfo => {
              deckInfo.id = result['deckId']
              deckInfo.name = result['deckName']
              deckInfo.colors = result['colors']
              deckInfo.creationDate = result['creationDate']
            }
          )
        }
      )
      .catch(
        error => {
          
        }
      )
  }
}
