import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { DeckListComponent } from './components/deck-list/deck-list.component';
import { MenuComponent } from './components/menu/menu.component';
import { RegisterComponent } from './components/register/register.component';
import { HistoryComponent } from './components/history/history.component';
import { RochesterComponent } from './components/rochester/rochester.component';
import { DeckBuilderComponent } from './components/deck-builder/deck-builder.component';
import { CardPoolComponent } from './components/card-pool/card-pool.component';
import { DeckComponent } from './components/deck/deck.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    DeckListComponent,
    MenuComponent,
    RegisterComponent,
    HistoryComponent,
    RochesterComponent,
    DeckBuilderComponent,
    CardPoolComponent,
    DeckComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
