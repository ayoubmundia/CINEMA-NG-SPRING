import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CinemaComponent } from './cinema/cinema.component';
import { PageErrorComponent } from './page-error/page-error.component';


const routes: Routes = [
  {path : "cinema" , component : CinemaComponent },
  {path : "" , component : CinemaComponent },
  {path : "**" , component : PageErrorComponent }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
