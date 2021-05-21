/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;


/**
 *
 * @author HP
 */
public class HomeForm extends Form {

    Form current;

    public HomeForm() {

        current = this;
        setTitle("Home");
        setLayout(BoxLayout.y());

        add(new Label("Choose an option"));
        Button btnAddVoiture = new Button("Add Voiture");
        Button btnListVoitures = new Button("List Voitures");
        Button btnListVoitureslouee = new Button("List Voitures louee");

        //btnAddVoiture.addActionListener(e -> new AddVoitureForm(current).show());
        //btnListVoitures.addActionListener(e -> new ListVoitureForm(current).show());
        //btnListVoitureslouee.addActionListener(e -> new ListVoitureLouee(current).show());

        addAll(btnAddVoiture, btnListVoitures,btnListVoitureslouee);
    }

}
