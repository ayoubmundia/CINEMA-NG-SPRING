package org.sid.entites;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ticket {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nomClient;
	private double prix;
	private boolean reserve;
	private int codePayement;
	
	@ManyToOne
	private ProjectionFilm projections;
	
	@ManyToOne
	private Place place;
	
	public Ticket() {
		super();
	}
	public Ticket(String nomClient, double prix, int codePayement, boolean reservee, ProjectionFilm projections,
			Place place) {
		super();
		this.nomClient = nomClient;
		this.prix = prix;
		this.codePayement = codePayement;
		this.reserve = reservee;
		this.projections = projections;
		this.place = place;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getCodePayement() {
		return codePayement;
	}
	public void setCodePayement(int codePayement) {
		this.codePayement = codePayement;
	}
	public boolean isReservee() {
		return reserve;
	}
	public void setReservee(boolean reservee) {
		this.reserve = reservee;
	}
	public ProjectionFilm getProjections() {
		return projections;
	}
	public void setProjections(ProjectionFilm projection) {
		this.projections = projection;
	}
	public Place getPlace() {
		return place;
	}
	public void setPlace(Place place) {
		this.place = place;
	}
	
}
