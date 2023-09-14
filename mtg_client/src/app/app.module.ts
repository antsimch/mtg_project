import { NgModule, isDevMode } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
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
import { BasicAuthHttpInterceptorService } from './services/basic-auth-http-interceptor.service';
import { ViewDeckComponent } from './components/view-deck/view-deck.component';
import { LogoutComponent } from './components/logout/logout.component';
import { ServiceWorkerModule } from '@angular/service-worker';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
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
    DeckBuilderSettingsComponent,
    ViewDeckComponent,
    LogoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    MaterialModule,
    BrowserAnimationsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
  providers: [
    {
      provide:HTTP_INTERCEPTORS, useClass:BasicAuthHttpInterceptorService, multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
