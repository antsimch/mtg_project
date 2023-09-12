import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { DeckListComponent } from './components/deck-list/deck-list.component';
import { MenuComponent } from './components/menu/menu.component';
import { RegisterComponent } from './components/register/register.component';
import { HistoryComponent } from './components/history/history.component';
import { DeckBuilderComponent } from './components/deck-builder/deck-builder.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'menu/:userId', component: MenuComponent },
  { path: 'decks/:userId', component: DeckListComponent },
  { path: 'history/:userId', component: HistoryComponent },
  { path: 'deck/:userId', component: DeckBuilderComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
