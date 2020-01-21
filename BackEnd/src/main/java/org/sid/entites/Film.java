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
public class Film {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String titre, description, photo, realisateur;
	private double duree;
	
	@Temporal(TemporalType.DATE)
	private Date dateSortie;
	
	@ManyToOne
	private Categorie categorie;
	
	@OneToMany(mappedBy = "film")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Collection<ProjectionFilm> projection;
	
	public Film() {
		super();
	}
	public Film(String titre, String description, String photo, String realisateur, double duree, Date dateSortie,
			Categorie categorie, Collection<ProjectionFilm> projection) {
		super();
		this.titre = titre;
		this.description = description;
		this.photo = photo;
		this.realisateur = realisateur;
		this.duree = duree;
		this.dateSortie = dateSortie;
		this.categorie = categorie;
		this.projection = projection;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getRealisateur() {
		return realisateur;
	}
	public void setRealisateur(String realisateur) {
		this.realisateur = realisateur;
	}
	public double getDuree() {
		return duree;
	}
	public void setDuree(double duree) {
		this.duree = duree;
	}
	public Date getDateSortie() {
		return dateSortie;
	}
	public void setDateSortie(Date dateSortie) {
		this.dateSortie = dateSortie;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public Collection<ProjectionFilm> getProjection() {
		return projection;
	}
	public void setProjection(Collection<ProjectionFilm> projection) {
		this.projection = projection;
	}
	
}
