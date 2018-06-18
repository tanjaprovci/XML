import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccommodationComponent } from './components/accommodation/accommodation.component';
import { AccommodationTableComponent } from './components/accommodation-table/accommodation-table.component';
import { HomeComponent } from './components/home/home.component';
import { ApartmentComponent } from './components/apartment/apartment.component';
import { AccommodationDetailComponent } from './components/accommodation-detail/accommodation-detail.component';
import { ApartmentTableComponent } from './components/apartment-table/apartment-table.component';
import { PricePlanComponent } from './components/price-plan/price-plan.component';


const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'accommodation', component: AccommodationComponent },
  { path: 'accommodationsTable', component: AccommodationTableComponent },
  { path: 'apartment/:id', component: ApartmentComponent },
  { path: 'accommodationDetail/:id', component: AccommodationDetailComponent},
  { path: 'apartmentTable', component: ApartmentTableComponent},
  { path: 'pricePlan', component: PricePlanComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
