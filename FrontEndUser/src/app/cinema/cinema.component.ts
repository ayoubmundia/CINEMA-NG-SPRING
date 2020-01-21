import { Component, OnInit } from '@angular/core';
import { CinemaService } from '../services/cinema.service';

@Component({
  selector: 'app-cinema',
  templateUrl: './cinema.component.html',
  styleUrls: ['./cinema.component.scss']
})
export class CinemaComponent implements OnInit {
  public villes;
  public currentVille;
  public cinemas;
  public salles;
  public currentCinema;
  private currentProjection;
  private selectedTickets;
  
  constructor(private cinemaService : CinemaService ) { }

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

  }
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
}
