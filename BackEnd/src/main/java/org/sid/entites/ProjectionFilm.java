package org.sid.entites;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class ProjectionFilm {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Temporal(TemporalType.DATE)
	private Date dateProjection;
	
	private double prix;
	
	@ManyToOne
	private Seance seance;
	
	@OneToMany(mappedBy = "projections")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Collection<Ticket> tickets;
	
	@ManyToOne
	private Film film;
	
	@ManyToOne
	@JsonProperty(access=Access.WRITE_ONLY)
	private Salle salle;
	
	public ProjectionFilm() {
		super();
	}
	public ProjectionFilm(Date dateProjection, double prix, Seance seance, Salle salle,
			Collection<Ticket> tickets, Film film) {
		super();
		this.dateProjection = dateProjection;
		this.prix = prix;
		this.seance = seance;
		this.salle = salle;
		this.tickets = tickets;
		this.film = film;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDateProjection() {
		return dateProjection;
	}
	public void setDateProjection(Date dateProjection) {
		this.dateProjection = dateProjection;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public Seance getSeance() {
		return seance;
	}
	public void setSeance(Seance seance) {
		this.seance = seance;
	}
	public Salle getSalles() {
		return salle;
	}
	public void setSalle(Salle salle) {
		this.salle = salle;
	}
	public Collection<Ticket> getTickets() {
		return tickets;
	}
	public void setTickets(Collection<Ticket> tickets) {
		this.tickets = tickets;
	}
	public Film getFilms() {
		return film;
	}
	public void setFilms(Film films) {
		this.film = films;
	}
	
	
}
