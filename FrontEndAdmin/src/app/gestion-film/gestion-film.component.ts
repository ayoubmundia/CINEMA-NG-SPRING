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
  public selectedFile: File;

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
      photo : ['', [Validators.required]],
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
    this.update_film.setValue({
      titre: f.titre,
      photo: f.photo,
      description: f.description,
      realisateur: f.realisateur,
      duree: f.duree,
      dateSortie: f.dateSortie,
    });
  }
  UpdateFilm(){
    let id_film = sessionStorage.getItem("thisIdFilm")
    var postData ={
    "titre": this.update_film.get('titre').value,
    "description": this.update_film.get('description').value,
    "photo": this.update_film.get('photo').value,
    "realisateur": this.update_film.get('realisateur').value,
    "duree": this.update_film.get('duree').value,
    "dateSortie": this.update_film.get('dateSortie').value,
  }
    this.http.put("http://localhost:8089/films/"+id_film,postData).subscribe(
      ()=>{
        $('#UpdateFilm').modal('hide');
        this.onGetAllFilm();
      }
    );
  }
  onFileChanged(event) {
    this.selectedFile = event.target.files[0]
  }
  onUpload() {
    this.http.post('my-backend.com/file-upload', this.selectedFile)
      .subscribe(
        ()=>{
          $('#UpdateFilm').modal('hide');
          this.onGetAllFilm();
        }
      );
  }

}
