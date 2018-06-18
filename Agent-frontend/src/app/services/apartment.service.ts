import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApartmentService {

  private url = 'http://localhost:9000/restIntercepter';
  data = {};
  newPricePlan:any;
  allPricePlans: any;
  
  ngOnInit() {
  }
  //private url = 'http://localhost:8080/greeting';
  
  constructor(private http: HttpClient) { }

  getBedTypes() {
    return this.http.get(this.url + '/getBedTypes');

  }
  
  getAccommodationCategories() {
    return this.http.get(this.url + '/getAccommodationCategories');
  }
  
  getCities() {
    return this.http.get(this.url + '/getCities');
  }
  
  getAdditionalServices() {
      return this.http.get(this.url + '/getAdditionalServices');
  }
 
  getPricePlans() {
      return this.http.get(this.url + '/getPricePlans');
  }
 
  setNewPricePlan(newPricePlan){
      this.newPricePlan = newPricePlan;
      console.log(this.newPricePlan);
  }
  
  getNewPricePlans(){
      return this.newPricePlan;
  }
  
  addApartment(apartment, checkedItems, pricePlans, accommodationId, imgUrl) {
      
    this.data = {
      'name': apartment.name,
      'bedType': apartment.bedType,
      'size': apartment.size,
      'numOfRooms': apartment.numOfRooms,
      'numOfGuests': apartment.numOfGuests,
      'description': apartment.description,
      'image': imgUrl,
      'additionalService': checkedItems,
      'pricePlans': pricePlans
    };
    console.log(pricePlans.endDate);
    var dat = {
            'startDate': pricePlans.startDate,
            'endDate': pricePlans.endDate,
            'price': pricePlans.price
    }
    console.log(dat);
    console.log(this.data);
    return this.http.post(this.url + '/addApartment/' + accommodationId, this.data);
  }
}
