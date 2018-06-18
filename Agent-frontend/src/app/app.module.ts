 import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AccommodationService } from './services/accommodation.service';
import { AccommodationComponent } from './components/accommodation/accommodation.component';
import { ApartmentComponent } from './components/apartment/apartment.component';
import { AccommodationTableComponent } from './components/accommodation-table/accommodation-table.component';
import { HomeComponent } from './components/home/home.component';
import { AccommodationDetailComponent } from './components/accommodation-detail/accommodation-detail.component';
import { ApartmentTableComponent } from './components/apartment-table/apartment-table.component';
import { PricePlanComponent } from './components/price-plan/price-plan.component';
         
@NgModule({
  declarations: [
    AppComponent,
    AccommodationComponent,
    ApartmentComponent,
    AccommodationTableComponent,
    HomeComponent,
    AccommodationDetailComponent,
    ApartmentTableComponent,
    PricePlanComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
  ],
  providers: [
    AccommodationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
