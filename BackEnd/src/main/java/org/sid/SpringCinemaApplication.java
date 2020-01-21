package org.sid;

import org.sid.entites.Cinema;
import org.sid.entites.Film;
import org.sid.entites.Salle;
import org.sid.entites.Seance;
import org.sid.entites.Ville;
import org.sid.metier.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;



@SpringBootApplication
public class SpringCinemaApplication implements CommandLineRunner{
	
	@Autowired
	private RepositoryRestConfiguration restConfiguration;
	
	@Autowired
	private ICinemaInitService cinemaService;

	public static void main(String[] args) {
		SpringApplication.run(SpringCinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Welcome to Spring cinema Application");
		restConfiguration.exposeIdsFor(Film.class,Salle.class,Ville.class,Cinema.class,Seance.class);
		cinemaService.initVilles();
		cinemaService.initCinemas();
		cinemaService.initSalles();
		cinemaService.initPlaces();
		cinemaService.initSeances();
		cinemaService.initCategories();
		cinemaService.initFilms();
		cinemaService.initProjections();
		cinemaService.initTickets();
	}

}
