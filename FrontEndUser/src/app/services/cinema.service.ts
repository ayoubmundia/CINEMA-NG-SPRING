import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CinemaService {
  public host:string ="http://localhost:8089"
  public villes;

  constructor(private http: HttpClient) { }

  public getVilles(){
    return this.http.get(this.host+"/villes")
  }

  getCinemas(v) {
   return this.http.get(v._links.cinemas.href);
  }

  getSalles(c) {
   return this.http.get(c._links.salles.href);
    
  }
  getProjections(salle: any){
    let url = salle._links.projection.href.replace("{?projection}", "");
    console.log(salle)
    return this.http.get(url+"?projection=p1");
  }
  getTicketsPLaces(p){
    let url = p._links.tickets.href.replace("{?projection}", "");
    return this.http.get(url+"?projection=ticketProj");
  }

  payerTickets(dataForm) {
    return this.http.post(this.host+"/payerTickets", dataForm);
  }
}
