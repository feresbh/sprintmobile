/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author seif
 */
public class MenuMateriels extends Form{
    Form current;

    public MenuMateriels() {
                current=this;
        setTitle("Gestions des Materiels");
        setLayout(BoxLayout.y());
        
        //BUTTONS
        add(new Label("Choose an option"));
        Button btnAjoutMateriel = new Button("Ajouter");
        Button btnListVelos = new Button("Liste des Materiels");
        Button btnStat= new Button("Stat");
        
        btnAjoutMateriel.addActionListener(e-> new AddMateriel(current).show());
        btnListVelos.addActionListener(e-> new ListMateriels().show());
        btnStat.addActionListener(e-> new Stat().show());
        
        //Tool Bar
        getToolbar().addCommandToSideMenu("Gestions des Materiels", null, e -> new MenuMateriels().show());
       

        addAll(btnAjoutMateriel,btnListVelos,btnStat);

    }

}

