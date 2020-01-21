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
public class Place {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int numero;
	private double longitude, latitude, altitude;
	
	@ManyToOne
	private Salle salle;
	
	@OneToMany(mappedBy = "place")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Collection<Ticket> ticket;
	
	public Place() {
		super();
	}
	public Place(int numero, double longitude, double latitude, double altitude, Salle salle, Collection<Ticket> ticket) {
		super();
		this.numero = numero;
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
		this.salle = salle;
		this.ticket = ticket;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public Salle getSalle() {
		return salle;
	}
	public void setSalle(Salle salle) {
		this.salle = salle;
	}
	public Collection<Ticket> getTicket() {
		return ticket;
	}
	public void setTicket(Collection<Ticket> ticket) {
		this.ticket = ticket;
	}
	
}
