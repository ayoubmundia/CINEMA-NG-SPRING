package org.sid.entites;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Salle {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private int nombrePlaces;
	
	@ManyToOne
	private Cinema cinema;
	
	@OneToMany(mappedBy = "salle")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Collection<Place> places;
	
	@OneToMany(mappedBy = "salle")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Collection<ProjectionFilm> projection;
	
	public Salle() {
		super();
	}
	public Salle(String name, int nombrePlaces, Cinema cinema, Collection<Place> places, Collection<ProjectionFilm> projection) {
		super();
		this.name = name;
		this.nombrePlaces = nombrePlaces;
		this.cinema = cinema;
		this.places = places;
		this.projection = projection;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNombrePlaces() {
		return nombrePlaces;
	}
	public void setNombrePlaces(int nombrePlaces) {
		this.nombrePlaces = nombrePlaces;
	}
	public Cinema getCinema() {
		return cinema;
	}
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	public Collection<Place> getPlaces() {
		return places;
	}
	public void setPlaces(Collection<Place> places) {
		this.places = places;
	}
	public Collection<ProjectionFilm> getProjection() {
		return projection;
	}
	public void setProjection(Collection<ProjectionFilm> projection) {
		this.projection = projection;
	}
	

}
