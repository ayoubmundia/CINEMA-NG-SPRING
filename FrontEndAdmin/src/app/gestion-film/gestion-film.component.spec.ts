import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionFilmComponent } from './gestion-film.component';

describe('GestionFilmComponent', () => {
  let component: GestionFilmComponent;
  let fixture: ComponentFixture<GestionFilmComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestionFilmComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestionFilmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
