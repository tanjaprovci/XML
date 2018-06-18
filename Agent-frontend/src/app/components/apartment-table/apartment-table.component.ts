import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import {ApartmentService } from '../../services/apartment.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
//import { DomSanitizationService } from '@angular/platform-browser'

@Component({
  selector: 'app-apartment-table',
  templateUrl: './apartment-table.component.html',
  styleUrls: ['./apartment-table.component.css'],
  animations: [fadeIn()]
})
export class ApartmentTableComponent implements OnInit {
    images;
  constructor(private apartmentService : ApartmentService, private formBuilder: FormBuilder, private router: Router) {
      this.images = [
                     {"url":"G://tanja//slike//out-1.png"},
                     {"url":"G://tanja//slike//out9.png"}
                    
                       ];
  }

  ngOnInit() {
      
  }
  selectedImage;
  
  setSelectedImage(image){
     this.selectedImage= image;    
  }

}
