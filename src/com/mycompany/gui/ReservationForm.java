/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import Services.ReservationService;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import static com.mycompany.gui.AffReservation.idExp;
import java.io.IOException;

/**
 *
 * @author Admin
 */
public class ReservationForm extends Form {
    private Form current;
    
    public ReservationForm(Form previous) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
        */
        setTitle("Reservation");
        setLayout(BoxLayout.y());
        ReservationService rs=new ReservationService();
        TextField tfcin = new TextField("","Cin");
        TextField tfemail = new TextField("","email");
         TextField tftel = new TextField("","tel");
        Button btnReserver= new Button("Reserver");
         Button btnUpdate= new Button("modifier");
         Button btnvol= new Button("liste des vols");
         btnvol.addActionListener(e->{
            
            try {
                new VolForm(current).show();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
        btnReserver.addActionListener(e->{
            
            rs.ajouterRes(tfemail, tfcin, tftel);
            new AffReservation(current).show();
        });
        
         btnUpdate.addActionListener(e->{
            
            rs.UpdateRes(tfemail, tfcin, tftel,idExp);
            new AffReservation(current).show();
          
        });
        
        
        
        addAll(btnvol,tfcin,tfemail,tftel,btnReserver,btnUpdate);
    }
    
}
