package org.sid.entites;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Ville {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private double longitude, latitude, altiude;
	
	@OneToMany(mappedBy = "ville")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Collection<Cinema> cinemas;
	
	public Ville() {
		super();
	}
	public Ville(String name, double longitude, double latitude, double altiude, Collection<Cinema> cinemas) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.altiude = altiude;
		this.cinemas = cinemas;
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
	public double getAltiude() {
		return altiude;
	}
	public void setAltiude(double altiude) {
		this.altiude = altiude;
	}
	public Collection<Cinema> getCinemas() {
		return cinemas;
	}
	public void setCinemas(Collection<Cinema> cinemas) {
		this.cinemas = cinemas;
	}
	
	
}
