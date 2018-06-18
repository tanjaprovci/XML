import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { AccommodationService } from '../../services/accommodation.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-accommodation-detail',
  templateUrl: './accommodation-detail.component.html',
  styleUrls: ['./accommodation-detail.component.css'],
  animations: [fadeIn()]
})
export class AccommodationDetailComponent implements OnInit {

  constructor(private accommodationService : AccommodationService, private formBuilder: FormBuilder, private router: Router) { }
  
  title = 'The Booking App';
  accommodation:any;
  temp: any;
  apartments = [];
  message: string;
  
  ngOnInit() {
      this.temp = this.accommodationService.getSelected();
      this.accommodation = {
              'id': this.temp.id,
              'name': this.temp.name,
              'type': this.temp.type,
              'city': this.temp.city,
              'street': this.temp.street,
              'country': this.temp.country,
              'description': this.temp.description,
              'category': this.temp.category,
              'image': this.temp.image 
          }
      
      this.accommodationService.getApartments(this.accommodation.id)
      .subscribe(res => { if(res['return'] == 'This accommodation has no apartments.')
      { this.message = res['return']
      }     else  
          this.apartments = res['return']
    });
  }

}
