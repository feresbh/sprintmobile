/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author SBS
 */
public class Materiels {
    private int id;
    private String type;
    private String description;
    private float prix_jour;
    private boolean disponibilite;
    private String image;
    private Date dateDebut;
    private Date dateFin;
    
    public Materiels() {
    }

    public Materiels(int id, String type, String description, float prix_jour, boolean disponibilite, String image, Date dateDebut, Date dateFin) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.prix_jour = prix_jour;
        this.disponibilite = disponibilite;
        this.image = image;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Materiels(String type, String description, float prix_jour, boolean disponibilite, String image, Date dateDebut, Date dateFin) {
        this.type = type;
        this.description = description;
        this.prix_jour = prix_jour;
        this.disponibilite = disponibilite;
        this.image = image;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrix_jour() {
        return prix_jour;
    }

    public void setPrix_jour(float prix_jour) {
        this.prix_jour = prix_jour;
    }

    public boolean isDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Materiels{" + "id=" + id + ", type=" + type + ", description=" + description + ", prix_jour=" + prix_jour + ", disponibilite=" + disponibilite + ", image=" + image + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + '}';
    }

    
    
    
}
