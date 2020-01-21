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
public class Cinema {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name; 
	private double logitude ,latitude ,altitude;
	private int nombreSalles;
	
	@ManyToOne
	private Ville ville;
	
	@OneToMany(mappedBy = "cinema" )
	@JsonProperty(access=Access.WRITE_ONLY)
	private Collection<Salle> salles;
	
	public Cinema() {
		super();
	}
	public Cinema(String name, double logitude, double latitude, double altitude, int nombreSalles, Ville ville,
			Collection<Salle> salles) {
		super();
		this.name = name;
		this.logitude = logitude;
		this.latitude = latitude;
		this.altitude = altitude;
		this.nombreSalles = nombreSalles;
		this.ville = ville;
		this.salles = salles;
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
	public double getLogitude() {
		return logitude;
	}
	public void setLogitude(double logitude) {
		this.logitude = logitude;
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
	public int getNombreSalles() {
		return nombreSalles;
	}
	public void setNombreSalles(int nombreSalles) {
		this.nombreSalles = nombreSalles;
	}
	public Ville getVille() {
		return ville;
	}
	public void setVille(Ville ville) {
		this.ville = ville;
	}
	public Collection<Salle> getSalles() {
		return salles;
	}
	public void setSalles(Collection<Salle> salles) {
		this.salles = salles;
	}
	
}
