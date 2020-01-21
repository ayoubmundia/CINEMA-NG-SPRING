import { Component, OnInit, NgModule } from '@angular/core';
import { CinemaService } from '../services/cinema.service';
import { FormBuilder, FormGroup, Validators , ReactiveFormsModule} from '@angular/forms';
import { Router } from '@angular/router';
import { Cinema } from '../cinema/Cinema';
import { HttpClient } from '@angular/common/http';
declare var $ : any;

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
  public new_salle: FormGroup;
  public up_salle: FormGroup;
  cinema: Cinema = new Cinema();
  
  constructor(private cinemaService : CinemaService,
    private formBuilder: FormBuilder,
    private router: Router,
    private http: HttpClient ) { }

  ngOnInit() {
    this.cinemaService.getVilles().subscribe(
      data =>{
        this.villes = data;
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
    this.new_salle = this.formBuilder.group({
      name_of_salle: ['', [Validators.required]],
      number_of_place: ['', [Validators.required]]
  });
    this.up_city = this.formBuilder.group({
      name_up_city: ['', [Validators.required]],
      longitude_up_city: ['', [Validators.required]],
      altitude_up_city: ['', [Validators.required]],
      latitude_up_city: ['', [Validators.required]]
  });
  this.up_salle = this.formBuilder.group({
    name_up_salle: ['', [Validators.required]],
    number_up_place: ['', [Validators.required]]
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
    
    this.cinemaService.UpCity(v.id,this.send_nc ,this.send_loc,this.send_ac,this.send_lac).subscribe(
      ()=>{
        document.location.reload(true)
      }
    );
  }
  onDeletVille(v) {
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
        this.salles._embedded.salles.forEach(
        salle=>{
          this.cinemaService.getProjections(salle)
            .subscribe(data=>{
              salle.projections = data;
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
  onSubmitUpdate(){
    let n = this.currentCinema._links.self.href.substring(this.currentCinema._links.self.href.lastIndexOf('/') + 1);
    let v = this.currentCinema.id;
    this.cinemaService.updateCinema(this.cinema,n,v)
      .subscribe((data:any) =>{
        data => console.log(data)
        $('#updatecinemaModel').modal('hide');
        this.onGetCinema(this.currentVille);
      },
      err=>{
        console.log("error");
      })
  }
  onCancel(){
    this.cinema = new Cinema();
  }
  //End 
  onUpdatecinema(c){
    this.currentCinema = c ;
    this.cinema.name = c.name;
    this.cinema.altitude = c.altitude;
    this.cinema.logitude = c.logitude;
    this.cinema.latitude = c.latitude;
    this.cinema.nombreSalles = c.nombreSalles;
  }

  //deleteCinema
  deleteCinemaModal(c){
    this.currentCinema = c ;
  }
  confirmSuppression(){
    let n = this.currentCinema._links.self.href.substring(this.currentCinema._links.self.href.lastIndexOf('/') + 1);
    this.cinemaService.deleteCinema(n)
      .subscribe((data:any) =>{
        data => console.log(data)
        this.cinema = new Cinema();
        this.onGetCinema(this.currentVille);
      },
      err=>{
        console.log("error");
      })
  }
  //shadow was here 
  onGetthisSalles(c){
    
    let n = c._links.self.href.substring(c._links.self.href.lastIndexOf('/') + 1);
    sessionStorage.setItem("thiscinema",n);
  }
  AddNewSalle(){
    var postData ={ "name" : this.new_salle.get('name_of_salle').value ,"nombrePlaces" : this.new_salle.get('number_of_place').value};
    console.log(postData);
    let id_cinema = sessionStorage.getItem("thiscinema");
    console.log(id_cinema)
    this.http.post("http://localhost:8089/saveSalle/"+id_cinema, postData).subscribe(
      ()=>{
        $('#AddNewSalle').modal('hide');
        this.onGetSalles(this.currentCinema);
      }
    );
  }
  onGetInfoSalles(s){
    sessionStorage.setItem("thisIdSalle",s.id);
    this.up_salle.setValue({
      name_up_salle : s.name,
      number_up_place : s.nombrePlaces
    });
  }
  UpdateInfoSalle(){
    var postData ={ "name" : this.up_salle.get('name_up_salle').value ,"nombrePlaces" : this.up_salle.get('number_up_place').value};
    this.http.put("http://localhost:8089/updateSalle/id="+sessionStorage.getItem("thisIdSalle")+"&cinema="+this.currentCinema.id, postData).subscribe(
      ()=>{
        $('#UpdateSalle').modal('hide');
        this.onGetSalles(this.currentCinema);
      }
    );
  }

}
