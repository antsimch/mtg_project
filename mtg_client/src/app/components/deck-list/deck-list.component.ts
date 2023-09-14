import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DeckService } from 'src/app/services/deck.service';

@Component({
  selector: 'app-deck-list',
  templateUrl: './deck-list.component.html',
  styleUrls: ['./deck-list.component.css']
})
export class DeckListComponent implements OnInit {

  deckInfoList: {
    id: string,
    name: string
  }[] = []

  constructor(
    private deckSvc: DeckService,
    private router: Router) { }

  viewDeck(deckId: string) {
    this.router.navigate(['deck', deckId])
  }

  deleteDeck(deckId: string) {
    this.deckSvc.deleteDeckByDeckId(deckId)
    .then(
      result => {
        alert(result['message'])
        this.router.navigate(['/home'])
      }
    )
    .catch(
      error => {
        console.log(error['message'])
      }
    )
  }

  ngOnInit(): void {
    const userId = sessionStorage.getItem('userId')
    if (userId != null) {
      this.deckSvc.getDecksByUserId(userId)
        .then(
          result => {
            result.forEach(
              (res: any) => {
                console.log(res)
                this.deckSvc.getDeckByDeckId(res['deckId'])
                  .then(
                    deck => {
                      console.log(deck)
                      this.deckInfoList.push({ id: res['deckId'], name: deck.deckName })
                      console.log(this.deckInfoList)
                    }
                  )
                  .catch(
                    error => {
                      console.log(error['message'])
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
}
