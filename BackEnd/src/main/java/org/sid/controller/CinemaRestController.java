package org.sid.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.sid.dao.CinemaRep;
import org.sid.dao.FilmRep;
import org.sid.dao.PlaceRep;
import org.sid.dao.PrejectioFIlmRep;
import org.sid.dao.SalleRep;
import org.sid.dao.SeanceRep;
import org.sid.dao.TicketRep;
import org.sid.dao.VilleRep;
import org.sid.entites.Cinema;
import org.sid.entites.Film;
import org.sid.entites.Place;
import org.sid.entites.ProjectionFilm;
import org.sid.entites.Salle;
import org.sid.entites.Seance;
import org.sid.entites.Ticket;
import org.sid.entites.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin("*")
@RestController
public class CinemaRestController {
	
	@Autowired
	private TicketRep ticketRep;
	
	@Autowired
	private FilmRep films;
	
	@Autowired
	private CinemaRep cinemas;
	
	@Autowired
	private VilleRep villes;
	
	@Autowired
	private SalleRep salles;
	
	@Autowired
	private SeanceRep seances;
	
	@Autowired
	private PrejectioFIlmRep projections;
	
	@Autowired
	private PlaceRep places;
	
	@GetMapping(path = "/imageFilm/{id}", produces=MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(@PathVariable (name="id") Long id) throws Exception {
		Film f = films.findById(id).get();
		String picName = f.getPhoto();
		File file = new File(System.getProperty("user.home")+"/cinema/images/"+picName);
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}
	
	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payerTicket(@RequestBody TicketForm ticketFrom) {
		List<Ticket> listTicket = new ArrayList<>();
		ticketFrom.getTickets().forEach(idTicket ->{
			Ticket ticket = ticketRep.findById(idTicket).get();
			ticket.setNomClient(ticketFrom.getNomClient());
			ticket.setReservee(true);
			ticket.setCodePayement(ticketFrom.getCodePayement());
			ticketRep.save(ticket);
			listTicket.add(ticket);
		});
		return listTicket;
	}
	

	@RequestMapping(value ="/saveCinema/{id}" , method=RequestMethod.POST)
	public Cinema saveCinema(@RequestBody Cinema c,@PathVariable Long id ){ 
		c.setVille(villes.findById(id).get());
		return cinemas.save(c);
	}
	
	@RequestMapping(value ="/saveSalle/{id}" , method=RequestMethod.POST)
	public void saveSalle(@RequestBody Salle salle,@PathVariable Long id ){ 
		salle.setCinema(cinemas.findById(id).get());
		salles.save(salle);
		for(int i=0;i<salle.getNombrePlaces();i++) {
			Place place = new Place();
			place.setNumero(i+1);
			place.setSalle(salle);
			places.save(place);
		}
	}

	
	@RequestMapping(value="/deleteCinema/{id}", method=RequestMethod.DELETE)
	public void deleteCinema(@PathVariable Long id ){ 
		Cinema C = cinemas.getOne(id);
		Ville V = C.getVille();
		V.getCinemas().remove(C);
		cinemas.deleteById(id);
	}
	

	@RequestMapping(value="/updateCinema/id={id}&ville={id_ville}", method=RequestMethod.PUT)
	public Cinema updateCinema(@RequestBody Cinema cinema, @PathVariable Long id,@PathVariable Long id_ville){
		cinema.setId(id);
		cinema.setVille(villes.findById(id_ville).get());
		return cinemas.saveAndFlush(cinema);
	}
	
	@RequestMapping(value="/updateSalle/id={id}&cinema={id_cinema}", method=RequestMethod.PUT)
	public Salle updateSalle(@RequestBody Salle salle, @PathVariable Long id, @PathVariable Long id_cinema){
		salle.setId(id);
		salle.setCinema(cinemas.findById(id_cinema).get());
		return salles.saveAndFlush(salle);
	}
	
	@RequestMapping(value="/saveProjection/salle={id_salle}&film={id_film}&seance={id_seance}&price={price}", method=RequestMethod.POST)
	public void addProjection( @PathVariable Long id_salle, @PathVariable Long id_seance , @PathVariable Long id_film , @PathVariable Long price){
		Salle s = salles.findById(id_salle).get();
		Seance sceance = seances.findById(id_seance).get();
		Film f = films.findById(id_film).get();
		ProjectionFilm pr = new ProjectionFilm();
		//Collection<Ticket> tickets = new ArrayList<Ticket>();
		pr.setFilms(f);
		pr.setSeance(sceance);
		pr.setSalle(s);
		pr.setDateProjection(new Date());
		pr.setPrix(price);
		projections.save(pr);
		s.getPlaces().forEach(place ->{
			Ticket t = new Ticket();
			t.setPlace(place);
			t.setPrix(price);
			t.setProjections(pr);
			t.setNomClient("clientX");
			t.setReservee(false);
			ticketRep.save(t);
		});
		
	}
	
	@RequestMapping(value="uploadFile/id={id}", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException{
		File convertFile = new File(System.getProperty("user.home")+"/cinema/images/"+file.getOriginalFilename());
		convertFile.createNewFile();
		FileOutputStream fout  =new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		System.out.println(file.getOriginalFilename());
		films.findById(id).get().setPhoto(file.getOriginalFilename());
		films.save(films.findById(id).get());
		return new ResponseEntity<>("File is uploaded Successfully",HttpStatus.OK);
		
	}
	
}

class TicketForm {
	private List<Long> tickets = new ArrayList<Long>();
	private String nomClient;
	private int codePayement;
	
	public List<Long> getTickets() {
		return this.tickets;
	}
	public String getNomClient() {
		return this.nomClient;
	}
	public int getCodePayement() {
		return this.codePayement;
	}
}


