/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author MY HP
 */
public class Panier {
  //  Product product;
    int id;
    int quant;
    public  double totale;
    

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }
    

    public void setId(int id) {
        this.id = id;
       // return id;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public Panier(int id, int quant) {
        this.id = id;
        this.quant = quant;
    }

    public int getId() {
        return id;
    }

    public int getQuant() {
        return quant;
    }

    public Panier() {
    }

    @Override
    public String toString() {
        return "Panier{" + "id=" + id + ", quant=" + quant + '}';
    }
    
    
    public double incremanterTotal(double total){
        
        this.totale=this.totale+total;
        return this.totale;
        
    }
}
