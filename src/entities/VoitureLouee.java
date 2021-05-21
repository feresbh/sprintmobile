/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author HP
 */
public class VoitureLouee {
    
    private int id;
    private String datedebut;
    private String datefin;
    private String serie;
    private int clientid;
    private int voitureid;
    private int prix;

    public VoitureLouee() {
    }

    
    public VoitureLouee(String datedebut, String datefin, int voitureid) {
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.voitureid = voitureid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(String datedebut) {
        this.datedebut = datedebut;
    }

    public String getDatefin() {
        return datefin;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public int getVoitureid() {
        return voitureid;
    }

    public void setVoitureid(int voitureid) {
        this.voitureid = voitureid;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "VoitureLouee{" + "id=" + id + ", datedebut=" + datedebut + ", datefin=" + datefin + ", serie=" + serie + ", clientid=" + clientid + ", voitureid=" + voitureid + ", prix=" + prix + '}';
    }
    
    
    
}
