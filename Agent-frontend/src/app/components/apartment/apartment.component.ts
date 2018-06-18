import { Component, OnInit } from '@angular/core';
import { fadeIn } from '../../animations';
import { ApartmentService } from '../../services/apartment.service';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-apartment',
  templateUrl: './apartment.component.html',
  styleUrls: ['./apartment.component.css'],
  animations: [fadeIn()]
})

export class ApartmentComponent implements OnInit {

    bedTypes = [];
    additionalServices: any;
    pricePlan: any;
    msg: any;
    showNewPricePlan: any;
    accommodationId:string;
    url:string;
    urls:string;
    imgName:string;
    i = '';
    data = {};
    moreThanOne: any;
    pricePlans = [];
    
  constructor(private apartmentService: ApartmentService, private activatedRoute: ActivatedRoute, private formBuilder: FormBuilder, private router: Router) {
      
  }
  
  ngOnInit() {
      this.getData();
      this.activatedRoute.params.subscribe((params: Params) => {
          this.accommodationId = params['id'];
        });
  }

  apartmentForm= this.formBuilder.group({
      name: ['', Validators.required],
      bedType: ['', Validators.required],
      size: ['', Validators.required],
      numOfRooms: ['', Validators.required],
      numOfGuests: ['', Validators.required],
      additional: ['', Validators.required],
      description: ['', Validators.required],
      image: ['', Validators.required],
      pricePlans: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      price: ['', Validators.required]
   });
  
  addPricePlan(){

      this.pricePlan = {
        'startDate': this.apartmentForm.value.startDate,
        'endDate': this.apartmentForm.value.endDate,
        'price': this.apartmentForm.value.price
      }
      
      this.pricePlans.push(this.pricePlan);
      console.log(this.pricePlans);
      
//      this.pricePlans.push({ id: 1, pricePlan: { startDate: this.pricePlan.startDate, endDate: this.pricePlan.endDate, price: this.pricePlan.price }});
//      console.log(this.pricePlan);
//      console.log(this.pricePlans);
//      this.pricePlans.push(this.pricePlan.price);
      
      this.showNewPricePlan = false;
      this.moreThanOne = true;
  }
 
  onFileChanged(event) {
      
      const file = event.target.files[0];
      if(this.imgName != undefined)
          this.imgName = this.imgName + '; ' + file.name;
      else 
          this.imgName = file.name;

      if (event.target.files && event.target.files[0]) {
          var reader = new FileReader();
          reader.readAsDataURL(event.target.files[0]); // read file as data url
          reader.onload = (event) => { // called once readAsDataURL is completed
          
          this.url = reader.result;
          if(this.urls != undefined)
              this.urls = this.urls + 'ovo-je-separator' + this.url;
          else
              this.urls = this.url;
          }
      }
  }
  
  getData(): void {
      this.apartmentService.getBedTypes().subscribe(
              res => {this.bedTypes = res['return']});
      
      this.apartmentService.getAdditionalServices().subscribe(
              res => {this.additionalServices = res['return']});
     /* this.apartmentService.getPricePlans().subscribe(
              res => {
              if(res['return'] == 'No price plan'){
                  this.msg = res['return']
              }else
                  this.pricePlans = res['return']
              });
      this.pricePlans = this.pricePlans + this.apartmentService.getNewPricePlans();
         console.log(this.pricePlans);  */
  }
  
  checkedList = [];
  isAnswerProvided(event: any, check:any)
  {
      console.log(event + ' ' + check);
    if(event.target.checked) {
        this.checkedList.push(check);
      }
    console.log(this.checkedList);
  }
  
  isAnswerProvidedPP(event: any, check:any)
  {
      console.log(event + ' ' + check);
    if(event.target.checked) {
        this.pricePlans.push(check);
      }
    console.log(this.checkedList);
  }
 
  show(){
      this.showNewPricePlan = true;
  }
  
  addApartment() {
      console.log(this.checkedList);
      console.log(JSON.stringify(this.checkedList));
      console.log(JSON.stringify(this.pricePlans));

      this.apartmentService.addApartment(this.apartmentForm.value, JSON.stringify(this.checkedList), JSON.stringify(this.pricePlans), this.accommodationId, this.url)
      .subscribe(res => { 
          swal({
              text: res['return']
          });
        });
   }
}
