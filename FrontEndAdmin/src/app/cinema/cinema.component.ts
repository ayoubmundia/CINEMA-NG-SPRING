import { Component, OnInit, NgModule } from '@angular/core';
import { CinemaService } from '../services/cinema.service';
import { FormBuilder, FormGroup, Validators , ReactiveFormsModule} from '@angular/forms';
import { Router } from '@angular/router';
import { Cinema } from '../cinema/Cinema';

@Component({
  selector: 'app-cinema',
  templateUrl: './cinema.component.html',
  styleUrls: ['./cinema.component.scss']
})
@NgModule({
  imports: [
    ReactiveFormsModule
  ],
})
export class CinemaComponent implements OnInit {
  public villes;
  public currentVille;
  public cinemas;
  public salles;
  public currentCinema;
  private currentProjection;
  private selectedTickets;
  public new_city: FormGroup;
  public up_city: FormGroup;
  cinema: Cinema = new Cinema();
  
  constructor(private cinemaService : CinemaService,
    private formBuilder: FormBuilder,
    private router: Router ) { }

  ngOnInit() {
    this.cinemaService.getVilles().subscribe(
      data =>{
        this.villes = data;
        console.log(data)
      },
      err =>{
        console.log(err);

      }
    );
    //shadow is here
    this.new_city = this.formBuilder.group({
        name_new_city: ['', [Validators.required]],
        longitude_new_city: ['', [Validators.required]],
        altitude_new_city: ['', [Validators.required]],
        latitude_new_city: ['', [Validators.required]]
    });
    this.up_city = this.formBuilder.group({
      name_up_city: ['', [Validators.required]],
      longitude_up_city: ['', [Validators.required]],
      altitude_up_city: ['', [Validators.required]],
      latitude_up_city: ['', [Validators.required]]
  });

  }
  //shadow is here Add new city 
  public send_nc;
  public send_loc;
  public send_ac;
  public send_lac;
  addNewCity(){
    const nc = this.new_city.get('name_new_city').value;
    const loc = this.new_city.get('longitude_new_city').value;
    const ac = this.new_city.get('altitude_new_city').value;
    const lac= this.new_city.get('latitude_new_city').value;
    console.log("test")
    this.cinemaService.AddCity(nc,loc,ac,lac).subscribe(
      ()=>{
        document.location.reload(true)
      }
    );
  }
  UpdateCity(v){
    // verification name
    if(this.up_city.get('name_up_city').value){
      this.send_nc = this.up_city.get('name_up_city').value;
      
    }else{
      this.send_nc = v.name; 
    }
    // verification longitude
    if(this.up_city.get('longitude_up_city').value){
      this.send_loc = this.up_city.get('longitude_up_city').value;
      
    }else{
      this.send_loc = v.longitude; 
    }
    //verification altitude
    if(this.up_city.get('altitude_up_city').value){
      this.send_ac = this.up_city.get('altitude_up_city').value;
      
    }else{
      this.send_ac= v.altiude; 
    }
    //verification latitude
    if(this.up_city.get('latitude_up_city').value){
      this.send_lac = this.up_city.get('latitude_up_city').value;
      
    }else{
      this.send_lac = v.latitude; 
    }
    
    // const loc = this.up_city.get('longitude_up_city').value;
    // const ac = this.up_city.get('altitude_up_city').value;
    // const lac= this.up_city.get('latitude_up_city').value;
    
    console.log(v.id+'-'+this.send_nc+'-'+this.send_loc+'-'+this.send_ac+'-'+this.send_lac);
    this.cinemaService.UpCity(v.id,this.send_nc ,this.send_loc,this.send_ac,this.send_lac).subscribe(
      ()=>{
        document.location.reload(true)
      }
    );
  }
  onDeletVille(v) {
    console.log(v)
    this.cinemaService.deleteCity(v.id).subscribe(
            ()=> {
              console.log('this would have removed', v.id);
              document.location.reload(true)
            },
            error => console.log(error));
  }
  //end shadow is here Add new city 
  onGetCinema(v){
    this.cinemaService.getCinemas(v).subscribe(
      data =>{
        this.cinemas = data;
        this.salles = "";
        this.currentVille = v;
        console.log(this.currentVille)
      },
      err =>{
        console.log(err);

      }
    );
  }
  onGetSalles(c){
    this.currentCinema=c;
    this.cinemaService.getSalles(c)
      .subscribe(data=>{
        this.salles = data;
        console.log(data)
        this.salles._embedded.salles.forEach(
        salle=>{
          console.log(salle)
          this.cinemaService.getProjections(salle)
            .subscribe(data=>{
              salle.projections = data;
              console.log(data)
            },err=>{
              console.log(err);
            })
        })
      },err=>{
        console.log(err);
      })
  }

  onGetTicketsPlaces(p) {
    this.currentProjection=p;
    this.cinemaService.getTicketsPLaces(p)
      .subscribe(data=>{
        this.currentProjection.tickets = data;
        this.selectedTickets=[];
      },err=>{
        console.log(err);
      })
  }

  OnSelectTicket(t: any) {
    if(!t.selected) {
      t.selected = true;
      this.selectedTickets.push(t);
    }else{
      t.selected = false;
      this.selectedTickets.splice(this.selectedTickets.indexOf(t),1);
    }
  }

  getTicketClass(t) {
    let str = "btn ticket ";
    if(t.reserve==true){
      str+="btn-danger"
    }else if (t.selected){
      str+="btn-light";
    }else{
      str+="btn-outline-light";
    }
    return str;
  }

  onPayTickets(dataForm) {
    let tickets=[];
    this.selectedTickets.forEach(t=>{
      tickets.push(t.id);
    });
    dataForm.tickets = tickets;
    this.cinemaService.payerTickets(dataForm)
      .subscribe(data=>{
        this.onGetTicketsPlaces(this.currentProjection);
      },err=>{
        console.log(err);
      })
  }

  //Add Cinema Part
  addButton(){
    console.log(this.currentVille);
  }

  onSubmit(){
    console.log(this.cinema);
    // console.log(this.currentVille._links.ville.href);
    console.log(this.currentVille._links.ville.href.substring(this.currentVille._links.ville.href.lastIndexOf('/') + 1));
    let n = this.currentVille._links.ville.href.substring(this.currentVille._links.ville.href.lastIndexOf('/') + 1);
    this.cinemaService.ajouterCinema(this.cinema,n)
      .subscribe((data:any) =>{
        data => console.log(data)
        this.onGetCinema(this.currentVille);
        this.cinema = new Cinema();
      },
      err=>{
        console.log("error");
      })
      
  }
  onCancel(){
    this.cinema = new Cinema();
  }
  //End 
  onUpdatecinema(){
    console.log(195);
  }
}
