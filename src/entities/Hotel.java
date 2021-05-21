/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author lenovo
 */
public class Hotel {
    
    private int id;
    private String Nomhotel;
    private String SiteWeb;
    private String Email;
    private String Telephone;
    private String Adresse;
    private String Description;

    public Hotel() {
    }
    
  

    public Hotel(int id, String Nomhotel, String SiteWeb, String Email, String Telephone, String Adresse, String Description) {
        this.id = id;
        this.Nomhotel = Nomhotel;
        this.SiteWeb = SiteWeb;
        this.Email = Email;
        this.Telephone = Telephone;
        this.Adresse = Adresse;
        this.Description = Description;
    }

    public Hotel(String Nomhotel, String SiteWeb, String Email, String Telephone, String Adresse, String Description) {
        this.Nomhotel = Nomhotel;
        this.SiteWeb = SiteWeb;
        this.Email = Email;
        this.Telephone = Telephone;
        this.Adresse = Adresse;
        this.Description = Description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomhotel() {
        return Nomhotel;
    }

    public void setNomhotel(String Nomhotel) {
        this.Nomhotel = Nomhotel;
    }

    public String getSiteWeb() {
        return SiteWeb;
    }

    public void setSiteWeb(String SiteWeb) {
        this.SiteWeb = SiteWeb;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String Adresse) {
        this.Adresse = Adresse;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    
    
    
    
    
    
}
