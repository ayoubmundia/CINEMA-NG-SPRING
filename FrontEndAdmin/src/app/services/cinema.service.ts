import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


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
  //ShadowLight is here  //
  AddCity(nc: any, loc: any, ac: any, lac: any) {
    var postData ={ "name": nc,"longitude" : loc , "latitude" : lac,"altiude": ac};  
    return this.http.post(this.host+"/villes", postData);
  }
  deleteCity(id: number) {
    return this.http.delete(this.host+"/villes/"+id);
  }
  UpCity(id: any, nc: any, loc: any, ac: any, lac: any) {
    var postData ={ "name": nc,"longitude" : loc , "latitude" : lac,"altiude": ac};  
    return this.http.put(this.host+"/villes/"+id, postData);
  }
  //add cinema
  ajouterCinema(cinema: Object, ville: number): Observable<Object> {
    return this.http.post(this.host+"/saveCinema/"+ville, cinema );
  }
  //update cinema
  updateCinema(cinema: Object, id: number, id_ville: number): Observable<Object> {
    return this.http.put(this.host+"/updateCinema/id="+id+"&ville="+id_ville, cinema );
  }
  //add salle
  ajouterSalle(salle: Object, cinema: number): Observable<Object> {
    return this.http.post(this.host+"/saveSalle/"+cinema, salle );
  }
  //delete cinema
  deleteCinema(cinema: number): Observable<Object> {
    return this.http.delete(this.host+"/cinemas/"+cinema );
  }

}
