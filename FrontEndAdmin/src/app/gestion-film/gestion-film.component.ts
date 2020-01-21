import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
declare var $ : any;

@Component({
  selector: 'app-gestion-film',
  templateUrl: './gestion-film.component.html',
  styleUrls: ['./gestion-film.component.scss']
})

export class GestionFilmComponent implements OnInit {
  public films;
  public currentfilms;
  public update_film: FormGroup;
  
  constructor(private formBuilder: FormBuilder,
    private http: HttpClient ) { }
    onGetAllFilm(){
      this.http.get("http://localhost:8089/films").subscribe(
    (data)=>{
      this.films= data;
      console.log(this.films);
    }
    );
    this.update_film = this.formBuilder.group({
      titre: ['', [Validators.required]],
      description: ['', [Validators.required]],
      realisateur: ['', [Validators.required]],
      duree: ['', [Validators.required]],
      dateSortie: ['', [Validators.required]]
    });
    }
  ngOnInit() {
    this.onGetAllFilm();
    
  }
  

  DeletFilm(f){
    this.http.delete("http://localhost:8089/films/"+f.id).subscribe(
      ()=>{
        this.onGetAllFilm();
      }
      );
    
  }
  infoFilm(f){
    this.currentfilms =f;
    sessionStorage.setItem("thisIdFilm",f.id);
    console.log(f.dateSortie)
    this.update_film.setValue({
      titre: f.titre,
      description: f.description,
      realisateur: f.realisateur,
      duree: f.duree,
      dateSortie: f.dateSortie,
    });
  }
  

}
