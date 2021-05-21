/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Services.ReservationService;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import static gui.AffReservation.idExp;

/**
 *
 * @author Admin
 */
public class ReservationForm extends Form {
    
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
        btnReserver.addActionListener(e->{
            
            rs.ajouterRes(tfemail, tfcin, tftel);
        });
        
         btnUpdate.addActionListener(e->{
            
            rs.UpdateRes(tfemail, tfcin, tftel,idExp);
          
        });
        
        
        
        addAll(tfcin,tfemail,tftel,btnReserver,btnUpdate);
    }
    
}
