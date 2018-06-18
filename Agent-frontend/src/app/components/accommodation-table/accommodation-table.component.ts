import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationService } from '../../services/accommodation.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-accommodation-table',
  templateUrl: './accommodation-table.component.html',
  styleUrls: ['./accommodation-table.component.css'],
  animations: [fadeIn()]
})
export class AccommodationTableComponent implements OnInit {

    title = 'The Booking App';
    accommodations: any;
    accommodation = [];
    data = {};
    constructor(private accommodationService : AccommodationService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
      this.getData();
  }

  onSelect(selectedItem: any) {
//      this.accommodationService.currentMessage.subscribe(message => this.accommodation = message)
      this.accommodation = selectedItem.name;
      this.data = {
          'id': selectedItem.id,
          'name': selectedItem.name,
          'type': selectedItem.type,
          'city': selectedItem.city,
          'street': selectedItem.street,
          'description': selectedItem.description,
          'category': selectedItem.category,
          'country': selectedItem.country,
          'image': selectedItem.image
    };
      console.log(this.data['id']);
      this.accommodationService.selectedItem(this.data);
  }
  
  getData(){
      this.accommodationService.getAccommodations()
      .subscribe(res => {
          this.accommodations = res['return']
    });
  }
}
