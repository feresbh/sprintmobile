/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
//import com.codename1.ui.Calendar;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import entities.Voiture;
import entities.VoitureLouee;
import java.io.IOException;
import java.util.Date;
import services.ServiceVoiture;

/**
 *
 * @author HP
 */
public class AddlocationVoitureForm extends BaseForm {
    private Resources theme;
    
    public AddlocationVoitureForm(Voiture v,Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        this.theme = res;
        //setTitle("Add a new location voiture");
        //setLayout(BoxLayout.y());
        //image
        Label i = new Label();
        Image img = null;
        try {
            img = Image.createImage("/" + v.getImage());
        } catch (IOException ex) {
            System.out.println(ex);
        }
        i.setIcon(img);

        // date pickers
        Picker dateDebut = new Picker();
        dateDebut.setType(Display.PICKER_TYPE_DATE);
        Picker dateFin = new Picker();
        dateFin.setType(Display.PICKER_TYPE_DATE);
        addStringValue("Date Debut", dateDebut);
        addStringValue("Date Fin", dateFin);

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date datef = dateFin.getDate();
        Date dated = dateDebut.getDate();
        String datedebut = dateFormat.format(dated);
        String datefin = dateFormat.format(datef);

        //TextField tfModel = new TextField("", Integer.toString(id));
        Button btnValider = new Button("Add location");

        btnValider.addActionListener((evt) -> {
            VoitureLouee vl = new VoitureLouee(datedebut, datefin, v.getId());

            if (ServiceVoiture.getInstance().addVoitureLocation(vl)) {
                Dialog.show("Success", "Connection accepted", new Command("OK"));
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
        });

        addAll (i, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ListVoitureForm(res).show());
    }
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

}
