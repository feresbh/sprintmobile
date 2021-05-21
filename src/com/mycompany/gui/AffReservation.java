/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import Entities.reservation;
import Services.ReservationService;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class AffReservation extends Form {
 private Form current;
 public static int idExp;
    public AffReservation(Form previous) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
         */
        ReservationService es = new ReservationService();
        int i;
        ArrayList<reservation> listRes = new ArrayList<>();
        listRes = es.afficheRese();
        setTitle("Vos Reservation");
        setLayout(BoxLayout.y());

        for (i = 0; i < listRes.size(); i++) {
            Label tfnom = new Label("Date Vol:" + listRes.get(i).getDate_v());
            Label tfIdExp = new Label(String.valueOf(listRes.get(i).getId()));
            Label tfdesc = new Label("Tel:" + listRes.get(i).getNum_p());
            listRes.get(i).setVol_id(i);
           
            Button btnReserver = new Button("Supprimer");
            Button btnUpdate = new Button("Update");
            addAll(tfnom, tfdesc, btnReserver,btnUpdate);
            
             btnUpdate.addPointerPressedListener(l -> {
                 new ReservationForm(current).show(); 
                  idExp=Integer.valueOf(tfIdExp.getText());
            });

            btnReserver.addPointerPressedListener(l -> {
               es.supprimerRes(Integer.valueOf(tfIdExp.getText()));
              new AffReservation(current).show(); 
            });

        }
        Button btnValider = new Button("Reserver");
        btnValider.addPointerPressedListener(l -> {
             new ReservationForm(current).show();
        });
 
        addAll(btnValider);
    }

}
