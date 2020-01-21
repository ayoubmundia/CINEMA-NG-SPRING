package org.sid.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.sid.dao.CinemaRep;
import org.sid.dao.FilmRep;
import org.sid.dao.SalleRep;
import org.sid.dao.TicketRep;
import org.sid.dao.VilleRep;
import org.sid.entites.Cinema;
import org.sid.entites.Film;
import org.sid.entites.Salle;
import org.sid.entites.Ticket;
import org.sid.entites.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public Salle saveCinema(@RequestBody Salle salle,@PathVariable Long id ){ 
		salle.setCinema(cinemas.findById(id).get());
		return salles.save(salle);
	}
	
	@RequestMapping(value ="/updateCinema/id={id}&ville={idVille}" , method=RequestMethod.PUT)
	public Cinema updateCinema(@RequestBody Cinema c,@PathVariable Long id,@PathVariable Long idVille){ 
//		Ville V = villes.getOne(id_ville);
//		c.setVille(V);
//		return cinemas.saveAndFlush(c);
		c.setVille(villes.getOne(idVille));
		cinemas.getOne(id).equals(c);
		return c;
	}
	
	@RequestMapping(value="/deleteCinema/{id}", method=RequestMethod.DELETE)
	public void deleteCinema(@PathVariable Long id ){ 
		Cinema C = cinemas.getOne(id);
		Ville V = C.getVille();
		V.getCinemas().remove(C);
		cinemas.deleteById(id);
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


