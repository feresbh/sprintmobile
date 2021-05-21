/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author HP
 */
public class Voiture {
    private int id;
    private String marque;
    private String model;
    private String couleur;
    private int diponibilite;
    private String serie;
    private int prix;
    private String image;

    public Voiture(int id, String marque, String model, String couleur, int diponibilite, String serie, int prix, String image) {
        this.id = id;
        this.marque = marque;
        this.model = model;
        this.couleur = couleur;
        this.diponibilite = diponibilite;
        this.serie = serie;
        this.prix = prix;
        this.image = image;
    }

    public Voiture(String marque, String model, int prix) {
        this.marque = marque;
        this.model = model;
        this.prix = prix;
    }

    public Voiture() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getDiponibilite() {
        return diponibilite;
    }

    public void setDiponibilite(int diponibilite) {
        this.diponibilite = diponibilite;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Voiture{" + "id=" + id + ", marque=" + marque + ", model=" + model + ", couleur=" + couleur + ", diponibilite=" + diponibilite + ", serie=" + serie + ", prix=" + prix + ", image=" + image + '}';
    }
    
    
}
