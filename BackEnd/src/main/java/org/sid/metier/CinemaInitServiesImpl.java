package org.sid.metier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.sid.dao.CategorieRep;
import org.sid.dao.CinemaRep;
import org.sid.dao.FilmRep;
import org.sid.dao.PlaceRep;
import org.sid.dao.PrejectioFIlmRep;
import org.sid.dao.SalleRep;
import org.sid.dao.SeanceRep;
import org.sid.dao.TicketRep;
import org.sid.dao.VilleRep;
import org.sid.entites.Categorie;
import org.sid.entites.Cinema;
import org.sid.entites.Film;
import org.sid.entites.Place;
import org.sid.entites.ProjectionFilm;
import org.sid.entites.Salle;
import org.sid.entites.Seance;
import org.sid.entites.Ticket;
import org.sid.entites.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CinemaInitServiesImpl implements ICinemaInitService{
	
	@Autowired
	private VilleRep villeRep;
	
	@Autowired
	private CinemaRep cinemaRep;
	
	@Autowired
	private SalleRep salleRep;
	
	@Autowired
	private PlaceRep placeRep;
	
	@Autowired
	private SeanceRep seanceRep;
	
	@Autowired 
	private FilmRep filmRep;
	
	@Autowired
	private PrejectioFIlmRep projectionRep;
	
	@Autowired
	private CategorieRep categorieRep;
	
	@Autowired
	private TicketRep ticketRep;
	
	@Override
	public void initVilles() {
		Stream.of("Casablanca","Rabat","Fes","Oujda").forEach(nameVille->{
			Ville ville = new Ville();
			ville.setName(nameVille);
			villeRep.save(ville);
		});
	}

	@Override
	public void initCinemas() {
		villeRep.findAll().forEach(v->{
			Stream.of("Mega","Sa3aDa","IMAX","Fonoun").forEach(nameCinema->{
				Cinema cinema = new Cinema();
				cinema.setName(nameCinema);
				cinema.setNombreSalles((int)(3+Math.random()+7));
				cinema.setVille(v);
				cinemaRep.save(cinema);
			});
		});
	}

	@Override
	public void initSalles() {
		cinemaRep.findAll().forEach(cinema -> {
			for(int i=0;i<cinema.getNombreSalles();i++) {
				Salle salle = new Salle();
				salle.setName("Salle"+(i+1));
				salle.setCinema(cinema);
				salle.setNombrePlaces((int)(15+(Math.random()*10)+20));
				salleRep.save(salle);
			}
		});
	}

	@Override
	public void initPlaces() {
		salleRep.findAll().forEach(salle->{
			for(int i=0;i<salle.getNombrePlaces();i++) {
				Place place = new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRep.save(place);
			}
		});
		
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","11:11","15:50","10:20")
		.forEach(date_hm->{
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(date_hm));
				seanceRep.save(seance);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		});
	}

	@Override
	public void initCategories() {
		Stream.of("Action","Fiction","Drama","Horror").forEach(cat->{
			Categorie categorie = new Categorie();
			categorie.setName(cat);
			categorieRep.save(categorie);
		});
	}

	@Override
	public void initFilms() {
		double[] duree = new double[] {1,1,5,2,2,5,3};
		List<Categorie> categories = categorieRep.findAll();
		Stream.of("detachement","see","captain fantastic","her","togo","the fault in our stars","zero")
		.forEach(filmName->{
			Film film = new Film();
			film.setTitre(filmName);
			film.setDuree(duree[new Random().nextInt(duree.length)]);
			film.setPhoto(filmName.replaceAll(" ", "")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRep.save(film);
		});
	}

	@Override
	public void initProjections() {
		double[] prices = new double[] {30,50,60,70,90,100};
		List<Film> films = filmRep.findAll();
		villeRep.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema ->{
				cinema.getSalles().forEach(salle ->{
					int index = new Random().nextInt(films.size());
					Film film = films.get(index);
					seanceRep.findAll().forEach(seance ->{
						ProjectionFilm projection = new ProjectionFilm();
						projection.setDateProjection(new Date());
						projection.setFilms(film);
						projection.setPrix(prices[new Random().nextInt(prices.length)]);
						projection.setSalle(salle);
						projection.setSeance(seance);
						projectionRep.save(projection);
					});
					
				});
			});
		});
	}

	@Override
	public void initTickets() {
		projectionRep.findAll().forEach(p ->{
			p.getSalles().getPlaces().forEach(place ->{
				Ticket ticket =  new Ticket();
				ticket.setPlace(place);
				ticket.setPrix(p.getPrix());
				ticket.setProjections(p);
				ticket.setNomClient("clientX");
				ticket.setReservee(false);
				
				ticketRep.save(ticket);
			});
		});
	}

}
