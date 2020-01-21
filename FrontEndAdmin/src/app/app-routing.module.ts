import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CinemaComponent } from './cinema/cinema.component';
import { PageErrorComponent } from './page-error/page-error.component';
import { GestionFilmComponent } from './gestion-film/gestion-film.component';


const routes: Routes = [
  {path : "cinema" , component : CinemaComponent },
  {path : "film" , component : GestionFilmComponent },
  {path : "" , component : CinemaComponent },
  {path : "**" , component : PageErrorComponent }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
