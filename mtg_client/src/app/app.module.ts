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
import { RochesterComponent } from './components/rochester/rochester.component';
import { DeckBuilderComponent } from './components/deck-builder/deck-builder.component';
import { CardPoolComponent } from './components/card-pool/card-pool.component';
import { DeckComponent } from './components/deck/deck.component';
import { DraftedCardsComponent } from './components/drafted-cards/drafted-cards.component';
import { DraftComponent } from './components/draft/draft.component';
import { DraftSettingsComponent } from './components/draft-settings/draft-settings.component';
import { DeckBuilderSettingsComponent } from './components/deck-builder-settings/deck-builder-settings.component';
import { MaterialModule } from './material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    DeckListComponent,
    MenuComponent,
    RegisterComponent,
    RochesterComponent,
    DeckBuilderComponent,
    CardPoolComponent,
    DeckComponent,
    DraftedCardsComponent,
    DraftComponent,
    DraftSettingsComponent,
    DeckBuilderSettingsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    MaterialModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
