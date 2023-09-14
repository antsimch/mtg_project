import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DeckListComponent } from './components/deck-list/deck-list.component';
import { MenuComponent } from './components/menu/menu.component';
import { RegisterComponent } from './components/register/register.component';
import { DeckBuilderComponent } from './components/deck-builder/deck-builder.component';
import { DraftComponent } from './components/draft/draft.component';
import { ViewDeckComponent } from './components/view-deck/view-deck.component';
import { LogoutComponent } from './components/logout/logout.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: MenuComponent },
  { path: 'playdraft', component: DraftComponent },
  { path: 'deckbuilder', component: DeckBuilderComponent },
  { path: 'decks', component: DeckListComponent },
  { path: 'deck/:deckId', component: ViewDeckComponent },
  { path: 'logout', component: LogoutComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
